package com.xtc.libthai.CRF;

import com.xtc.libthai.util.ByteArray;
import com.xtc.libthai.util.ICacheAble;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

public class FeatureTemplate implements ICacheAble {
    static final Pattern pattern = Pattern.compile("%x\\[(-?\\d*),(\\d*)]");
    String template;
    ArrayList<int[]> offsetList;
    List<String> delimiterList;

    public FeatureTemplate() {
    }

    public static FeatureTemplate create(String template) {
        FeatureTemplate featureTemplate = new FeatureTemplate();
        featureTemplate.delimiterList = new LinkedList();
        featureTemplate.offsetList = new ArrayList(3);
        featureTemplate.template = template;
        Matcher matcher = pattern.matcher(template);
        int start = 0;

        while(matcher.find()) {
            featureTemplate.delimiterList.add(template.substring(start, matcher.start()));
            start = matcher.end();
            featureTemplate.offsetList.add(new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))});
        }

        return featureTemplate;
    }

    public char[] generateParameter(Table table, int current) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        Iterator var5 = this.delimiterList.iterator();

        while(var5.hasNext()) {
            String d = (String)var5.next();
            sb.append(d);
            int[] offset = (int[])this.offsetList.get(i++);
            sb.append(table.get(current + offset[0], offset[1]));
        }

        char[] o = new char[sb.length()];
        sb.getChars(0, sb.length(), o, 0);
        return o;
    }

    public void save(DataOutputStream out) throws Exception {
        out.writeUTF(this.template);
        out.writeInt(this.offsetList.size());
        Iterator var2 = this.offsetList.iterator();

        while(var2.hasNext()) {
            int[] offset = (int[])var2.next();
            out.writeInt(offset[0]);
            out.writeInt(offset[1]);
        }

        out.writeInt(this.delimiterList.size());
        var2 = this.delimiterList.iterator();

        while(var2.hasNext()) {
            String s = (String)var2.next();
            out.writeUTF(s);
        }

    }

    public void save(ObjectOutputStream oos) throws Exception {
        oos.writeUTF(this.template);
        oos.writeInt(this.offsetList.size());
        Iterator var2 = this.offsetList.iterator();

        while(var2.hasNext()) {
            int[] offset = (int[])var2.next();
            oos.writeInt(offset[0]);
            oos.writeInt(offset[1]);
        }

        oos.writeInt(this.delimiterList.size());
        var2 = this.delimiterList.iterator();

        while(var2.hasNext()) {
            String s = (String)var2.next();
            oos.writeUTF(s);
        }

    }

    public void save(ZipOutputStream zos) throws Exception {
        zos.write(this.template.getBytes());
        zos.write(this.offsetList.size());
        Iterator var2 = this.offsetList.iterator();

        while(var2.hasNext()) {
            int[] offset = (int[])var2.next();
            zos.write(offset[0]);
            zos.write(offset[1]);
        }

        zos.write(this.delimiterList.size());
        var2 = this.delimiterList.iterator();

        while(var2.hasNext()) {
            String s = (String)var2.next();
            zos.write(s.getBytes());
        }

    }

    public boolean load(ByteArray byteArray) {
        this.template = byteArray.nextUTF();
        int size = byteArray.nextInt();
        this.offsetList = new ArrayList(size);

        int i;
        for(i = 0; i < size; ++i) {
            this.offsetList.add(new int[]{byteArray.nextInt(), byteArray.nextInt()});
        }

        size = byteArray.nextInt();
        this.delimiterList = new ArrayList(size);

        for(i = 0; i < size; ++i) {
            this.delimiterList.add(byteArray.nextUTF());
        }

        return true;
    }

    public boolean load(ObjectInputStream ois) throws IOException {
        this.template = ois.readUTF();
        int size = ois.read();
        this.offsetList = new ArrayList(size);

        int i;
        for(i = 0; i < size; ++i) {
            this.offsetList.add(new int[]{ois.read(), ois.read()});
        }

        size = ois.read();
        this.delimiterList = new ArrayList(size);

        for(i = 0; i < size; ++i) {
            System.out.println(ois.readUTF());
            this.delimiterList.add(ois.readUTF());
        }

        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("FeatureTemplate{");
        sb.append("template='").append(this.template).append('\'');
        sb.append(", delimiterList=").append(this.delimiterList);
        sb.append('}');
        return sb.toString();
    }
}
