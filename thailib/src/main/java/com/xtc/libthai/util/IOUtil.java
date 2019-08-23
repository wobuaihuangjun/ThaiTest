package com.xtc.libthai.util;

import com.xtc.libthai.Config;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;

public class IOUtil {
    public IOUtil() {
    }

    public static InputStream getInputStream(String path) {
        return IOUtil.class.getResourceAsStream(path);
    }

    public static boolean saveObjectTo(Object o, String path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(o);
            oos.close();
            return true;
        } catch (IOException var3) {
            Config.Log.logger.warning("在保存对象" + o + "到" + path + "时发生异常" + var3);
            return false;
        }
    }

    public static Object readObjectFrom(String path) {
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(path));
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception var3) {
            Config.Log.logger.warning("在从" + path + "读取对象时发生异常" + var3);
            return null;
        }
    }

    public static String readTxt(String path) {
        if (path == null) {
            return null;
        } else {
            File file = new File(path);
            Long fileLength = file.length();
            byte[] fileContent = new byte[fileLength.intValue()];

            try {
                FileInputStream in = new FileInputStream(file);
                in.read(fileContent);
                in.close();
            } catch (FileNotFoundException var5) {
                Config.Log.logger.warning("找不到" + path + var5);
                return null;
            } catch (IOException var6) {
                Config.Log.logger.warning("读取" + path + "发生IO异常" + var6);
                return null;
            }

            return new String(fileContent, Charset.forName("UTF-8"));
        }
    }

    public static LinkedList<String[]> readCsv(String path) {
        LinkedList<String[]> resultList = new LinkedList();
        LinkedList<String> lineList = readLineList(path);
        Iterator var3 = lineList.iterator();

        while(var3.hasNext()) {
            String line = (String)var3.next();
            resultList.add(line.split(","));
        }

        return resultList;
    }

    public static boolean saveTxt(String path, String content) {
        try {
            FileChannel fc = (new FileOutputStream(path)).getChannel();
            fc.write(ByteBuffer.wrap(content.getBytes()));
            fc.close();
            return true;
        } catch (Exception var3) {
            Config.Log.logger.throwing("IOUtil", "saveTxt", var3);
            Config.Log.logger.warning("IOUtil saveTxt 到" + path + "失败" + var3.toString());
            return false;
        }
    }

    public static boolean saveTxt(String path, StringBuilder content) {
        return saveTxt(path, content.toString());
    }

    public static <T> boolean saveCollectionToTxt(Collection<T> collection, String path) {
        StringBuilder sb = new StringBuilder();
        Iterator var3 = collection.iterator();

        while(var3.hasNext()) {
            Object o = var3.next();
            sb.append(o);
            sb.append('\n');
        }

        return saveTxt(path, sb.toString());
    }

    public static byte[] readBytes(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            FileChannel channel = fis.getChannel();
            int fileSize = (int)channel.size();
            ByteBuffer byteBuffer = ByteBuffer.allocate(fileSize);
            channel.read(byteBuffer);
            byteBuffer.flip();
            byte[] bytes = byteBuffer.array();
            byteBuffer.clear();
            channel.close();
            fis.close();
            return bytes;
        } catch (Exception var6) {
            Config.Log.logger.warning("读取" + path + "时发生异常" + var6.getMessage());
            return null;
        }
    }

    public static byte[] readBytesByMapped(String path) {
        FileChannel fc = null;

        try {
            fc = (new RandomAccessFile(path, "r")).getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0L, fc.size()).load();
            byte[] bytes = new byte[(int)fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(bytes, 0, byteBuffer.remaining());
            }

            fc.close();
            return bytes;
        } catch (Exception var4) {
            Config.Log.logger.warning("读取" + path + "时发生异常" + var4.getMessage());
            return null;
        }
    }

    public static byte[] readBytes(InputStream is) {
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        boolean var3 = true;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            while((len = is.read(buffer, 0, bufferSize)) != -1) {
                baos.write(buffer, 0, len);
            }

            baos.close();
            is.close();
            return baos.toByteArray();
        } catch (Exception var5) {
            var5.printStackTrace();
            Config.Log.logger.warning("读取输入流时发生异常" + var5);
            return null;
        }
    }

    public static byte[] readGzBytes(InputStream in) {
        int bufferSize = 1024;
        byte[] byteBuffer = new byte[bufferSize];
        boolean var3 = true;

        try {
            DataInputStream dos = new DataInputStream(new GZIPInputStream(in));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            while((len = dos.read(byteBuffer, 0, bufferSize)) != -1) {
                baos.write(byteBuffer, 0, len);
            }

            baos.close();
            dos.close();
            in.close();
            return baos.toByteArray();
        } catch (Exception var6) {
            Config.Log.logger.warning("读取时发生异常" + var6);
            return null;
        }
    }

    public static LinkedList<String> readLineList(String path) {
        LinkedList<String> result = new LinkedList();
        String txt = readTxt(path);
        if (txt == null) {
            return result;
        } else {
            StringTokenizer tokenizer = new StringTokenizer(txt, "\n");

            while(tokenizer.hasMoreTokens()) {
                result.add(tokenizer.nextToken());
            }

            return result;
        }
    }

    public static LinkedList<String> readLineListWithLessMemory(String path) {
        LinkedList<String> result = new LinkedList();
        String line = null;

        try {
            BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

            while((line = bw.readLine()) != null) {
                result.add(line);
            }

            bw.close();
        } catch (Exception var4) {
            Config.Log.logger.warning("加载" + path + "失败，" + var4);
        }

        return result;
    }

//    public static boolean saveMapToTxt(Map<Object, Object> map, String path) {
//        return saveMapToTxt(map, path, "=");
//    }
//
//    public static boolean saveMapToTxt(Map<Object, Object> map, String path, String separator) {
//        Map<Object, Object> map = new TreeMap(map);
//        return saveEntrySetToTxt(map.entrySet(), path, separator);
//    }

    public static boolean saveEntrySetToTxt(Set<Entry<Object, Object>> entrySet, String path, String separator) {
        StringBuilder sbOut = new StringBuilder();
        Iterator var4 = entrySet.iterator();

        while(var4.hasNext()) {
            Entry<Object, Object> entry = (Entry)var4.next();
            sbOut.append(entry.getKey());
            sbOut.append(separator);
            sbOut.append(entry.getValue());
            sbOut.append('\n');
        }

        return saveTxt(path, sbOut.toString());
    }

    public static IOUtil.LineIterator readLine(String path) {
        return new IOUtil.LineIterator(path);
    }

    public static class LineIterator implements Iterator<String> {
        BufferedReader bw;
        String line;

        public LineIterator(String path) {
            try {
                this.bw = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
                this.line = this.bw.readLine();
            } catch (FileNotFoundException var3) {
                Config.Log.logger.warning("文件" + path + "不存在，接下来的调用会返回null" + StringUtil.exceptionToString(var3));
            } catch (IOException var4) {
                Config.Log.logger.warning("在读取过程中发生错误" + StringUtil.exceptionToString(var4));
            }

        }

        public LineIterator(InputStream is) {
            try {
                this.bw = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                this.line = this.bw.readLine();
            } catch (FileNotFoundException var3) {
                Config.Log.logger.warning("文件不存在，接下来的调用会返回null" + StringUtil.exceptionToString(var3));
            } catch (IOException var4) {
                Config.Log.logger.warning("在读取过程中发生错误" + StringUtil.exceptionToString(var4));
            }

        }

        public void close() {
            if (this.bw != null) {
                try {
                    this.bw.close();
                    this.bw = null;
                } catch (IOException var2) {
                    Config.Log.logger.warning("关闭文件失败" + StringUtil.exceptionToString(var2));
                }

            }
        }

        public boolean hasNext() {
            if (this.bw == null) {
                return false;
            } else if (this.line == null) {
                try {
                    this.bw.close();
                    this.bw = null;
                } catch (IOException var2) {
                    Config.Log.logger.warning("关闭文件失败" + StringUtil.exceptionToString(var2));
                }

                return false;
            } else {
                return true;
            }
        }

        public String next() {
            String preLine = this.line;

            try {
                if (this.bw != null) {
                    this.line = this.bw.readLine();
                    if (this.line == null && this.bw != null) {
                        try {
                            this.bw.close();
                            this.bw = null;
                        } catch (IOException var3) {
                            Config.Log.logger.warning("关闭文件失败" + StringUtil.exceptionToString(var3));
                        }
                    }
                } else {
                    this.line = null;
                }
            } catch (IOException var4) {
                Config.Log.logger.warning("在读取过程中发生错误" + StringUtil.exceptionToString(var4));
            }

            return preLine;
        }

        public void remove() {
            throw new UnsupportedOperationException("只读，不可写！");
        }
    }
}

