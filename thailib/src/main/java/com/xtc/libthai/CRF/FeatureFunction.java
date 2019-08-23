package com.xtc.libthai.CRF;

import com.xtc.libthai.util.ByteArray;
import com.xtc.libthai.util.ICacheAble;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FeatureFunction implements ICacheAble {
    char[] o;
    double[] w;

    public FeatureFunction(char[] o, int tagSize) {
        this.o = o;
        this.w = new double[tagSize];
    }

    public FeatureFunction() {
    }

    public void save(DataOutputStream out) throws Exception {
        out.writeInt(this.o.length);
        char[] var2 = this.o;
        int var3 = var2.length;

        int var4;
        for(var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            out.writeChar(c);
        }

        out.writeInt(this.w.length);
        double[] var7 = this.w;
        var3 = var7.length;

        for(var4 = 0; var4 < var3; ++var4) {
            double v = var7[var4];
            out.writeDouble(v);
        }

    }

    public void save(ObjectOutputStream oos) throws Exception {
        oos.writeInt(this.o.length);
        char[] var2 = this.o;
        int var3 = var2.length;

        int var4;
        for(var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            oos.writeChar(c);
        }

        oos.writeInt(this.w.length);
        double[] var7 = this.w;
        var3 = var7.length;

        for(var4 = 0; var4 < var3; ++var4) {
            double v = var7[var4];
            oos.writeDouble(v);
        }

    }

    public boolean load(ObjectInputStream ois) throws IOException {
        int size = ois.read();
        this.o = new char[size];

        int i;
        for(i = 0; i < size; ++i) {
            this.o[i] = ois.readChar();
        }

        size = ois.read();
        this.w = new double[size];

        for(i = 0; i < size; ++i) {
            this.w[i] = ois.readDouble();
        }

        return true;
    }

    public boolean load(ByteArray byteArray) {
        int size = byteArray.nextInt();
        this.o = new char[size];

        int i;
        for(i = 0; i < size; ++i) {
            this.o[i] = byteArray.nextChar();
        }

        size = byteArray.nextInt();
        this.w = new double[size];

        for(i = 0; i < size; ++i) {
            this.w[i] = byteArray.nextDouble();
        }

        return true;
    }
}
