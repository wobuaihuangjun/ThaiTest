package com.xtc.libthai.CRF;

import com.xtc.libthai.Config;
import com.xtc.libthai.collection.trie.DATrie;
import com.xtc.libthai.util.ByteArray;
import com.xtc.libthai.util.ICacheAble;
import com.xtc.libthai.util.IOUtil;
import com.xtc.libthai.util.StringUtil;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.zip.GZIPOutputStream;

public class CRFModel implements ICacheAble {
    Map<String, Integer> tag2id;
    protected String[] id2tag;
    DATrie<FeatureFunction> featureFunctionTrie;
    List<FeatureTemplate> featureTemplateList;
    protected double[][] matrix;

    public CRFModel() {
    }

    public static CRFModel loadBinModel(String path) {
        ByteArray byteArray = ByteArray.createByteArray(path + Config.FileExtensions.BIN);
        if (byteArray == null) {
            return null;
        } else {
            CRFModel model = new CRFModel();
            return model.load(byteArray) ? model : null;
        }
    }

    public static CRFModel loadGzModel(String path) {
        ByteArray byteArray = ByteArray.createByteArrayByGz(path + Config.FileExtensions.GZ);
        if (byteArray == null) {
            return null;
        } else {
            CRFModel model = new CRFModel();
            return model.load(byteArray) ? model : null;
        }
    }

    public static CRFModel loadTxtModel(String path, CRFModel model) {
        CRFModel CRFModel = model;
        if (model.load(ByteArray.createByteArrayByGz(path + Config.FileExtensions.GZ))) {
            return model;
        } else {
            InputStream is = IOUtil.getInputStream(path + Config.FileExtensions.TXT);
            IOUtil.LineIterator lineIterator = new IOUtil.LineIterator(is);
            if (!lineIterator.hasNext()) {
                return null;
            } else {
                Config.Log.logger.info(lineIterator.next());
                Config.Log.logger.info(lineIterator.next());
                int maxid = Integer.parseInt(lineIterator.next().substring("maxid:".length()).trim());
                Config.Log.logger.info(lineIterator.next());
                lineIterator.next();
                int id = 0;

                String line;
                for(model.tag2id = new HashMap(); (line = lineIterator.next()).length() != 0; ++id) {
                    CRFModel.tag2id.put(line, id);
                }

                CRFModel.id2tag = new String[CRFModel.tag2id.size()];
                int size = CRFModel.id2tag.length;

                Entry entry;
                for(Iterator var9 = CRFModel.tag2id.entrySet().iterator(); var9.hasNext(); CRFModel.id2tag[(Integer)entry.getValue()] = (String)entry.getKey()) {
                    entry = (Entry)var9.next();
                }

                TreeMap<String, FeatureFunction> featureFunctionMap = new TreeMap();
                List<FeatureFunction> featureFunctionList = new LinkedList();
                CRFModel.featureTemplateList = new LinkedList();

                while((line = lineIterator.next()).length() != 0) {
                    if (!"B".equals(line)) {
                        FeatureTemplate featureTemplate = FeatureTemplate.create(line);
                        CRFModel.featureTemplateList.add(featureTemplate);
                    } else {
                        CRFModel.matrix = new double[size][size];
                    }
                }

                if (CRFModel.matrix != null) {
                    lineIterator.next();
                }

                while((line = lineIterator.next()).length() != 0) {
                    String[] args = line.split(" ", 2);
                    char[] charArray = args[1].toCharArray();
                    FeatureFunction featureFunction = new FeatureFunction(charArray, size);
                    featureFunctionMap.put(args[1], featureFunction);
                    featureFunctionList.add(featureFunction);
                }

                if (CRFModel.matrix != null) {
                    for(int i = 0; i < size; ++i) {
                        for(int j = 0; j < size; ++j) {
                            CRFModel.matrix[i][j] = Double.parseDouble(lineIterator.next());
                        }
                    }
                }

                Iterator var19 = featureFunctionList.iterator();

                while(var19.hasNext()) {
                    FeatureFunction featureFunction = (FeatureFunction)var19.next();

                    for(int i = 0; i < size; ++i) {
                        featureFunction.w[i] = Double.parseDouble(lineIterator.next());
                    }
                }

                if (lineIterator.hasNext()) {
                    Config.Log.logger.warning("文本读取有残留，可能会出问题！" + path);
                }

                lineIterator.close();
                Config.Log.logger.info("开始构建双数组trie树");
                CRFModel.featureFunctionTrie = new DATrie();
                CRFModel.featureFunctionTrie.build(featureFunctionMap);
                String gzName = System.getProperty("user.dir") + "/src/main/resources" + path + Config.FileExtensions.GZ;

                try {
                    Config.Log.logger.info("开始缓存" + gzName);
                    DataOutputStream out = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(gzName)));
                    CRFModel.save(out);
                    out.close();
                } catch (Exception var14) {
                    Config.Log.logger.warning("在缓存" + path + Config.FileExtensions.BIN + "时发生错误" + StringUtil.exceptionToString(var14));
                }

                return CRFModel;
            }
        }
    }

    public void tag(Table table) {
        int size = table.size();
        if (size == 1) {
            table.setLast(0, "S");
        } else {
            double bestScore = 0.0D;
//            int bestTag = false;
            int tagSize = this.id2tag.length;
            LinkedList<double[]> scoreList = this.computeScoreList(table, 0);
            bestScore = computeScore(scoreList, 0);
            int bestTag = 0;
            double curScore = computeScore(scoreList, 3);
            if (curScore > bestScore) {
                bestTag = 3;
            }

            table.setLast(0, this.id2tag[bestTag]);
            int preTag = bestTag;

            for(int i = 1; i < size - 1; ++i) {
                scoreList = this.computeScoreList(table, i);
                bestScore = 4.9E-324D;

                for(int j = 0; j < tagSize; ++j) {
                    double curScore1 = computeScore(scoreList, j);
                    if (this.matrix != null) {
                        curScore1 += this.matrix[preTag][j];
                    }

                    if (curScore1 > bestScore) {
                        bestScore = curScore1;
                        bestTag = j;
                    }
                }

                table.setLast(i, this.id2tag[bestTag]);
                preTag = bestTag;
            }

            table.setLast(size - 1, "S");
        }
    }

    public LinkedList<double[]> computeScoreList(Table table, int current) {
        LinkedList<double[]> scoreList = new LinkedList();
        Iterator var4 = this.featureTemplateList.iterator();

        while(var4.hasNext()) {
            FeatureTemplate featureTemplate = (FeatureTemplate)var4.next();
            char[] o = featureTemplate.generateParameter(table, current);
            FeatureFunction featureFunction = (FeatureFunction)this.featureFunctionTrie.get(o);
            if (featureFunction != null) {
                scoreList.add(featureFunction.w);
            }
        }

        return scoreList;
    }

    protected static double computeScore(LinkedList<double[]> scoreList, int tag) {
        double score = 0.0D;

        double[] w;
        for(Iterator var4 = scoreList.iterator(); var4.hasNext(); score += w[tag]) {
            w = (double[])var4.next();
        }

        return score;
    }

    public void save(DataOutputStream out) throws Exception {
        out.writeInt(this.id2tag.length);
        String[] var2 = this.id2tag;
        int var3 = var2.length;

        int var4;
        for(var4 = 0; var4 < var3; ++var4) {
            String tag = var2[var4];
            out.writeUTF(tag);
        }

        FeatureFunction[] valueArray = (FeatureFunction[])this.featureFunctionTrie.getValueArray(new FeatureFunction[0]);
        out.writeInt(valueArray.length);
        FeatureFunction[] var13 = valueArray;
        var4 = valueArray.length;

        int var17;
        for(var17 = 0; var17 < var4; ++var17) {
            FeatureFunction featureFunction = var13[var17];
            featureFunction.save(out);
        }

        this.featureFunctionTrie.save(out);
        out.writeInt(this.featureTemplateList.size());
        Iterator var14 = this.featureTemplateList.iterator();

        while(var14.hasNext()) {
            FeatureTemplate featureTemplate = (FeatureTemplate)var14.next();
            featureTemplate.save(out);
        }

        if (this.matrix != null) {
            out.writeInt(this.matrix.length);
            double[][] var15 = this.matrix;
            var4 = var15.length;

            for(var17 = 0; var17 < var4; ++var17) {
                double[] line = var15[var17];
                double[] var7 = line;
                int var8 = line.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    double v = var7[var9];
                    out.writeDouble(v);
                }
            }
        } else {
            out.writeInt(0);
        }

    }

    public void save(ObjectOutputStream oos) throws Exception {
        oos.writeInt(this.id2tag.length);
        String[] var2 = this.id2tag;
        int var3 = var2.length;

        int var4;
        for(var4 = 0; var4 < var3; ++var4) {
            String tag = var2[var4];
            oos.writeUTF(tag);
        }

        FeatureFunction[] valueArray = (FeatureFunction[])this.featureFunctionTrie.getValueArray(new FeatureFunction[0]);
        oos.writeInt(valueArray.length);
        FeatureFunction[] var13 = valueArray;
        var4 = valueArray.length;

        int var17;
        for(var17 = 0; var17 < var4; ++var17) {
            FeatureFunction featureFunction = var13[var17];
            featureFunction.save(oos);
        }

        this.featureFunctionTrie.save(oos);
        oos.writeInt(this.featureTemplateList.size());
        Iterator var14 = this.featureTemplateList.iterator();

        while(var14.hasNext()) {
            FeatureTemplate featureTemplate = (FeatureTemplate)var14.next();
            featureTemplate.save(oos);
        }

        if (this.matrix != null) {
            oos.writeInt(this.matrix.length);
            double[][] var15 = this.matrix;
            var4 = var15.length;

            for(var17 = 0; var17 < var4; ++var17) {
                double[] line = var15[var17];
                double[] var7 = line;
                int var8 = line.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    double v = var7[var9];
                    oos.writeDouble(v);
                }
            }
        } else {
            oos.writeInt(0);
        }

    }

    public boolean load(ByteArray byteArray) {
        if (byteArray == null) {
            return false;
        } else {
            int size = byteArray.nextInt();
            this.id2tag = new String[size];
            this.tag2id = new HashMap(size);

            for(int i = 0; i < this.id2tag.length; ++i) {
                this.id2tag[i] = byteArray.nextUTF();
                this.tag2id.put(this.id2tag[i], i);
            }

            FeatureFunction[] valueArray = new FeatureFunction[byteArray.nextInt()];

            int i;
            for(i = 0; i < valueArray.length; ++i) {
                valueArray[i] = new FeatureFunction();
                valueArray[i].load(byteArray);
            }

            this.featureFunctionTrie = new DATrie();
            this.featureFunctionTrie.load(byteArray, valueArray);
            size = byteArray.nextInt();
            this.featureTemplateList = new ArrayList(size);

            for(i = 0; i < size; ++i) {
                FeatureTemplate featureTemplate = new FeatureTemplate();
                featureTemplate.load(byteArray);
                this.featureTemplateList.add(featureTemplate);
            }

            size = byteArray.nextInt();
            if (size == 0) {
                return true;
            } else {
                this.matrix = new double[size][size];

                for(i = 0; i < size; ++i) {
                    for(int j = 0; j < size; ++j) {
                        this.matrix[i][j] = byteArray.nextDouble();
                    }
                }

                return true;
            }
        }
    }

    public static CRFModel loadModel(String path) {
        CRFModel model = loadGzModel(path);
        return model != null ? model : loadTxtModel(path, new CRFModel());
    }

    public static CRFModel loadCRFModel(String path) {
        return loadModel(path);
    }
}
