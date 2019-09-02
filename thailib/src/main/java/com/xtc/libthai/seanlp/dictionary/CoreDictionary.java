package com.xtc.libthai.seanlp.dictionary;

import com.xtc.libthai.seanlp.Config;
import com.xtc.libthai.seanlp.collection.trie.DATrie;
import com.xtc.libthai.seanlp.segmenter.domain.Nature;
import com.xtc.libthai.seanlp.util.ByteArray;
import com.xtc.libthai.seanlp.util.IOUtil;
import com.xtc.libthai.seanlp.util.StringUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * 使用DoubleArrayTrie实现的核心词典
 */
public class CoreDictionary implements Dictionary<CoreDictionary> {

    private static final String TAG = "CoreDictionary: ";

    public static int totalFrequency = 221894;
    public DATrie<Attribute> dictionaryTrie;

    public static CoreDictionary loadTxtDictionary(String path) {
        CoreDictionary coreDictionary = new CoreDictionary();
        TreeMap<String, Attribute> map = new TreeMap<>();
        BufferedReader br = null;
        InputStream inputStream = IOUtil.getInputStream(path + Config.FileExtensions.TXT);
        if (inputStream == null) {
            return null;
        }
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int MAX_FREQUENCY = 0;

            String line;
            long start = System.currentTimeMillis();
            CoreDictionary.Attribute attribute;
            while ((line = br.readLine()) != null) {
                System.out.println(TAG + "loadTxtDictionary, line:" + line);

                //line结构：param[0] \t nature \t frequency \t nature \t frequency...
                String[] param = line.split("\t");
                int natureCount = (param.length - 1) / 2;
                System.out.println(TAG + "loadTxtDictionary, natureCount:" + natureCount);
                attribute = new CoreDictionary.Attribute(natureCount);

                for (int i = 0; i < natureCount; ++i) {
                    attribute.nature[i] = Enum.valueOf(Nature.class, param[1 + 2 * i]);
                    attribute.frequency[i] = Integer.parseInt(param[2 + 2 * i]);
                    attribute.totalFrequency += attribute.frequency[i];
                }

                map.put(param[0], attribute);

                MAX_FREQUENCY += attribute.totalFrequency;
            }

            System.out.println(TAG + "核心词典读入词条" + map.size() + " 全部频次" + MAX_FREQUENCY + "，耗时" + (System.currentTimeMillis() - start) + "ms");
            Config.Log.logger.info("核心词典读入词条" + map.size() + " 全部频次" + MAX_FREQUENCY + "，耗时" + (System.currentTimeMillis() - start) + "ms");
            br.close();
            coreDictionary.dictionaryTrie = new DATrie<>();
            coreDictionary.dictionaryTrie.build(map);
            Config.Log.logger.info("核心词典加载成功:" + coreDictionary.dictionaryTrie.size() + "个词条，下面将写入缓存……");
            String binName = System.getProperty("user.dir") + "/src/main/resources" + path + Config.FileExtensions.BIN;
            if (Config.DEBUG) {
                System.out.println(binName);
            }

            try {
                DataOutputStream out = new DataOutputStream(new FileOutputStream(binName));
                Collection<Attribute> attributeList = map.values();
                out.writeInt(attributeList.size());
                Iterator var21 = attributeList.iterator();

                while (var21.hasNext()) {
                    CoreDictionary.Attribute attribute1 = (CoreDictionary.Attribute) var21.next();
                    out.writeInt(attribute1.totalFrequency);
                    out.writeInt(attribute1.nature.length);

                    for (int i = 0; i < attribute1.nature.length; ++i) {
                        out.writeInt(attribute1.nature[i].ordinal());
                        out.writeInt(attribute1.frequency[i]);
                    }
                }

                coreDictionary.dictionaryTrie.save(out);
                out.close();
                return coreDictionary;
            } catch (Exception var15) {
                Config.Log.logger.warning("保存失败" + var15);
                return null;
            }
        } catch (FileNotFoundException var16) {
            Config.Log.logger.warning("核心词典" + path + "不存在！" + var16);
            return null;
        } catch (IOException var17) {
            Config.Log.logger.warning("核心词典" + path + "读取错误！" + var17);
            return null;
        }

    }

    public static CoreDictionary loadBinDictionary(String path) {
        ByteArray byteArray = ByteArray.createByteArray(path + Config.FileExtensions.BIN);
        if (byteArray == null) {
            return null;
        } else {
            CoreDictionary dictionary = new CoreDictionary();
            return dictionary.loadDat(byteArray) ? dictionary : null;
        }
    }

    /**
     * 从ByteArrays加载双数组
     */
    public boolean loadDat(ByteArray byteArray) {
        this.dictionaryTrie = new DATrie<>();
        try {
            int size = byteArray.nextInt();
            System.out.println(TAG + "loadDat, size:" + size);
            CoreDictionary.Attribute[] attributes = new CoreDictionary.Attribute[size];
            Nature[] natureIndexArray = Nature.values();

            for (int i = 0; i < size; ++i) {
                // 第一个是全部频次，第二个是词性个数
                int currentTotalFrequency = byteArray.nextInt();
                int length = byteArray.nextInt();
//                System.out.println(TAG + "loadDat, currentTotalFrequency:" + currentTotalFrequency + ", length:" + length);
                attributes[i] = new CoreDictionary.Attribute(length);
                attributes[i].totalFrequency = currentTotalFrequency;

                for (int j = 0; j < length; ++j) {
                    int nature = byteArray.nextInt();
                    int frequency = byteArray.nextInt();
//                    System.out.println(TAG + "loadDat, nature:" + nature + ", frequency:" + frequency);

                    attributes[i].nature[j] = natureIndexArray[nature];
                    attributes[i].frequency[j] = frequency;
                }
            }

            if (this.dictionaryTrie.loadString(byteArray, attributes) && !byteArray.hasMore()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception var9) {
            Config.Log.logger.warning("读取失败，问题发生在" + var9);
            return false;
        }
    }

    public static CoreDictionary.Attribute get(DATrie<CoreDictionary.Attribute> dictionaryTrie, String key) {
        return dictionaryTrie.get(key);
    }

    public static CoreDictionary.Attribute get(DATrie<CoreDictionary.Attribute> dictionaryTrie, int wordID) {
        return dictionaryTrie.getValue(wordID);
    }

    public static int getTermFrequency(DATrie<CoreDictionary.Attribute> dictionaryTrie, String term) {
        CoreDictionary.Attribute attribute = get(dictionaryTrie, term);
        return attribute == null ? 0 : attribute.totalFrequency;
    }

    public static boolean contains(DATrie<CoreDictionary.Attribute> dictionaryTrie, String key) {
        return dictionaryTrie.get(key) != null;
    }

    public static int getWordID(DATrie<CoreDictionary.Attribute> dictionaryTrie, String key) {
        return dictionaryTrie.exactMatchSearch(key);
    }

    public static CoreDictionary loadCoreDictionary(String path) {
        Config.Log.logger.info("核心词典开始加载:" + path);
        System.out.println(TAG + "核心词典开始加载:" + path);
        CoreDictionary dictionary = loadBinDictionary(path);
        if (dictionary != null) {
            return dictionary;
        }
        return loadTxtDictionary(path);
    }

    /**
     * 核心词典中的词属性
     */
    public static class Attribute {
        /**
         * 词性列表
         */
        public Nature[] nature;
        /**
         * 词性对应的词频
         */
        public int[] frequency;
        public int totalFrequency;

        public Attribute(int size) {
            this.nature = new Nature[size];
            this.frequency = new int[size];
        }

        public Attribute(Nature[] nature, int[] frequency) {
            this.nature = nature;
            this.frequency = frequency;
        }

        public Attribute(Nature nature, int frequency) {
            this(1);
            this.nature[0] = nature;
            this.frequency[0] = frequency;
            this.totalFrequency = frequency;
        }

        public Attribute(Nature[] nature, int[] frequency, int totalFrequency) {
            this.nature = nature;
            this.frequency = frequency;
            this.totalFrequency = totalFrequency;
        }

        /**
         * 使用单个词性，默认词频1000构造
         */
        public Attribute(Nature nature) {
            this(nature, 1000);
        }

        public static CoreDictionary.Attribute create(String natureWithFrequency) {
            try {
                String[] param = natureWithFrequency.split(" ");
                int natureCount = param.length / 2;
                CoreDictionary.Attribute attribute = new CoreDictionary.Attribute(natureCount);

                for (int i = 0; i < natureCount; ++i) {
                    attribute.nature[i] = Enum.valueOf(Nature.class, param[2 * i]);
                    attribute.frequency[i] = Integer.parseInt(param[1 + 2 * i]);
                    attribute.totalFrequency += attribute.frequency[i];
                }

                return attribute;
            } catch (Exception var5) {
                Config.Log.logger.warning("使用字符串" + natureWithFrequency + "创建词条属性失败！" + StringUtil.exceptionToString(var5));
                return null;
            }
        }

        /**
         * @deprecated
         */
        public int getNatureFrequency(String nature) {
            try {
                Nature pos = Enum.valueOf(Nature.class, nature);
                return this.getNatureFrequency(pos);
            } catch (IllegalArgumentException var3) {
                return 0;
            }
        }

        public int getNatureFrequency(Nature nature) {
            int result = 0;
            int i = 0;
            Nature[] var4 = this.nature;
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Nature pos = var4[var6];
                if (nature == pos) {
                    return this.frequency[i];
                }

                ++i;
            }

            return result;
        }

        public boolean hasNature(Nature nature) {
            return this.getNatureFrequency(nature) > 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < this.nature.length; ++i) {
                sb.append(this.nature[i]).append(' ').append(this.frequency[i]).append(' ');
            }

            return sb.toString();
        }
    }
}

