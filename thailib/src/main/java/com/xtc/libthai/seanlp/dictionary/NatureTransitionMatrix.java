package com.xtc.libthai.seanlp.dictionary;

import com.xtc.libthai.seanlp.Config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class NatureTransitionMatrix<E extends Enum<E>> {
    Class<E> enumType;
    public int ordinaryMax;
    int[][] matrix;
    int[] total;
    int totalFrequency;
    public int[] states;
    public double[] start_probability;
    public double[][] transititon_probability;

    public NatureTransitionMatrix(Class<E> enumType) {
        this.enumType = enumType;
    }

    public boolean load(String path) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(path + Config.FileExtensions.TXT), "UTF-8"));
            String line = br.readLine();
            String[] _param = line.split("\t");
            String[] labels = new String[_param.length - 1];
            System.arraycopy(_param, 1, labels, 0, labels.length);
            int[] ordinaryArray = new int[labels.length];
            this.ordinaryMax = 0;

            int j;
            for(j = 0; j < ordinaryArray.length; ++j) {
                ordinaryArray[j] = this.convert(labels[j]).ordinal();
                this.ordinaryMax = Math.max(this.ordinaryMax, ordinaryArray[j]);
            }

            ++this.ordinaryMax;
            this.matrix = new int[this.ordinaryMax][this.ordinaryMax];

            int x;
            for(j = 0; j < this.ordinaryMax; ++j) {
                for(x = 0; x < this.ordinaryMax; ++x) {
                    this.matrix[j][x] = 0;
                }
            }

            int i;
            while((line = br.readLine()) != null) {
                String[] paramArray = line.split("\t");
                i = this.convert(paramArray[0]).ordinal();

                for(i = 0; i < ordinaryArray.length; ++i) {
                    this.matrix[i][ordinaryArray[i]] = Integer.valueOf(paramArray[1 + i]);
                }
            }

            br.close();
            this.total = new int[this.ordinaryMax];

            int[] var10000;
            for(j = 0; j < this.ordinaryMax; ++j) {
                this.total[j] = 0;

                for(i = 0; i < this.ordinaryMax; ++i) {
                    var10000 = this.total;
                    var10000[j] += this.matrix[i][j];
                }
            }

            for(j = 0; j < this.ordinaryMax; ++j) {
                var10000 = this.total;
                var10000[j] += this.matrix[j][j];
            }

            for(j = 0; j < this.ordinaryMax; ++j) {
                this.totalFrequency += this.total[j];
            }

            this.states = ordinaryArray;
            this.start_probability = new double[this.ordinaryMax];
            int[] var19 = this.states;
            i = var19.length;

            int from;
            for(i = 0; i < i; ++i) {
                from = var19[i];
                double frequency = (double)this.total[from] + 1.0E-8D;
                this.start_probability[from] = -Math.log(frequency / (double)this.totalFrequency);
            }

            this.transititon_probability = new double[this.ordinaryMax][this.ordinaryMax];
            var19 = this.states;
            i = var19.length;

            for(i = 0; i < i; ++i) {
                from = var19[i];
                int[] var20 = this.states;
                int var12 = var20.length;

                for(int var13 = 0; var13 < var12; ++var13) {
                    int to = var20[var13];
                    double frequency = (double)this.matrix[from][to] + 1.0E-8D;
                    this.transititon_probability[from][to] = -Math.log(frequency / (double)this.totalFrequency);
                }
            }
        } catch (Exception var17) {
            Config.Log.logger.warning("读取" + path + "失败" + var17);
        }

        return true;
    }

    public int getFrequency(String from, String to) {
        return this.getFrequency(this.convert(from), this.convert(to));
    }

    public int getFrequency(E from, E to) {
        return this.matrix[from.ordinal()][to.ordinal()];
    }

    public int getTotalFrequency(E e) {
        return this.total[e.ordinal()];
    }

    public int getTotalFrequency() {
        return this.totalFrequency;
    }

    protected E convert(String label) {
        return Enum.valueOf(this.enumType, label);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TransformMatrixDictionary{");
        sb.append("enumType=").append(this.enumType);
        sb.append(", ordinaryMax=").append(this.ordinaryMax);
        sb.append(", matrix=").append(Arrays.toString(this.matrix));
        sb.append(", total=").append(Arrays.toString(this.total));
        sb.append(", totalFrequency=").append(this.totalFrequency);
        sb.append('}');
        return sb.toString();
    }
}

