package com.xtc.libthai.algoritm;

import com.xtc.libthai.dictionary.NatureTransitionMatrix;
import com.xtc.libthai.segmenter.domain.Nature;
import com.xtc.libthai.segmenter.domain.Vertex;

import java.util.Iterator;
import java.util.List;

public class Viterbi {
    public Viterbi() {
    }

//    public static int[] compute(int[] obs, int[] states, double[] start_p, double[][] trans_p, double[][] emit_p) {
//        int _max_states_value = 0;
//        int[] var6 = states;
//        int var7 = states.length;
//
//        int t;
//        int s;
//        for(t = 0; t < var7; ++t) {
//            s = var6[t];
//            _max_states_value = Math.max(_max_states_value, s);
//        }
//
//        ++_max_states_value;
//        double[][] V = new double[obs.length][_max_states_value];
//        int[][] path = new int[_max_states_value][obs.length];
//        int[] var25 = states;
//        s = states.length;
//
//        int state;
//        int x;
//        for(state = 0; state < s; ++state) {
//            x = var25[state];
//            V[0][x] = start_p[x] + emit_p[x][obs[0]];
//            path[x][0] = x;
//        }
//
//        int var12;
//        int y;
//        for(t = 1; t < obs.length; ++t) {
//            int[][] newpath = new int[_max_states_value][obs.length];
//            int[] var28 = states;
//            y = states.length;
//
//            for(var12 = 0; var12 < y; ++var12) {
//                y = var28[var12];
//                double prob = 1.7976931348623157E308D;
//                int[] var17 = states;
//                int var18 = states.length;
//
//                for(int var19 = 0; var19 < var18; ++var19) {
//                    int y0 = var17[var19];
//                    double nprob = V[t - 1][y0] + trans_p[y0][y] + emit_p[y][obs[t]];
//                    if (nprob < prob) {
//                        prob = nprob;
//                        V[t][y] = nprob;
//                        System.arraycopy(path[y0], 0, newpath[y], 0, t);
//                        newpath[y][t] = y;
//                    }
//                }
//            }
//
//            path = newpath;
//        }
//
//        double prob = 1.7976931348623157E308D;
//        state = 0;
//        int[] var29 = states;
//        var12 = states.length;
//
//
//        for(y = 0; y < var12; ++y) {
//            int y = var29[y];
//            if (V[obs.length - 1][y] < prob) {
//                prob = V[obs.length - 1][y];
//                state = y;
//            }
//        }
//
//        return path[state];
//    }

    public static void compute(List<Vertex> vertexList, NatureTransitionMatrix<Nature> natureTransitionMatrix) {
        int length = vertexList.size() - 1;
        double[][] cost = new double[length][];
        Iterator<Vertex> iterator = vertexList.iterator();
        Vertex start = (Vertex)iterator.next();
        Nature pre = start.guessNature();
        double total = 0.0D;

        for(int i = 0; i < cost.length; ++i) {
            Vertex item = (Vertex)iterator.next();
            cost[i] = new double[item.attribute.nature.length];

            for(int j = 0; j < cost[i].length; ++j) {
                Nature cur = item.attribute.nature[j];
                cost[i][j] = total + natureTransitionMatrix.transititon_probability[pre.ordinal()][cur.ordinal()] - Math.log(((double)item.attribute.frequency[j] + 1.0E-8D) / (double)natureTransitionMatrix.getTotalFrequency(cur));
            }

            double perfect_cost_line = 1.7976931348623157E308D;
            int perfect_j = 0;

            for(int j = 0; j < cost[i].length; ++j) {
                if (perfect_cost_line > cost[i][j]) {
                    perfect_cost_line = cost[i][j];
                    perfect_j = j;
                }
            }

            total = perfect_cost_line;
            pre = item.attribute.nature[perfect_j];
            item.confirmNature(pre);
        }

    }
}
