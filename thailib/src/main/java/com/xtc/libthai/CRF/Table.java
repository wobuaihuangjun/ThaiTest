package com.xtc.libthai.CRF;


public class Table {
    public String[][] sheet;
    static final String HEAD = "_B";

    public Table() {
    }

    public String toString() {
        if (this.sheet == null) {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder(this.sheet.length * this.sheet[0].length * 2);
            String[][] var2 = this.sheet;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String[] line = var2[var4];
                String[] var6 = line;
                int var7 = line.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    String element = var6[var8];
                    sb.append(element).append('\t');
                }

                sb.append('\n');
            }

            return sb.toString();
        }
    }

    public String get(int x, int y) {
        if (x < 0) {
            return "_B" + x;
        } else {
            return x >= this.sheet.length ? "_B+" + (x - this.sheet.length + 1) : this.sheet[x][y];
        }
    }

    public void setLast(int x, String t) {
        this.sheet[x][this.sheet[x].length - 1] = t;
    }

    public int size() {
        return this.sheet.length;
    }

    public String[] toArray() {
        if (this.sheet == null) {
            return null;
        } else {
            String[] strs = new String[this.sheet.length];

            for(int i = 0; i < this.sheet.length; ++i) {
                strs[i] = this.sheet[i][0];
            }

            return strs;
        }
    }
}

