package com.xtc.libthai.util;


import com.xtc.libthai.Config;

import java.io.InputStream;

public class ByteArray {
    private byte[] bytes;
    private int offset;

    public ByteArray(byte[] bytes) {
        this.bytes = bytes;
    }

    public static ByteArray createByteArray(String path) {
        InputStream is = IOUtil.getInputStream(path);
        if (is == null) {
            return null;
        } else {
            byte[] bytes = IOUtil.readBytes(is);
            return bytes == null ? null : new ByteArray(bytes);
        }
    }

    public static ByteArray createByteArrayByGz(String path) {
        InputStream is = IOUtil.getInputStream(path);
        byte[] bytes = IOUtil.readGzBytes(is);
        return bytes == null ? null : new ByteArray(bytes);
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int nextInt() {
        int result = ByteUtil.bytesHighFirstToInt(this.bytes, this.offset);
        this.offset += 4;
        return result;
    }

    public double nextDouble() {
        double result = ByteUtil.bytesHighFirstToDouble(this.bytes, this.offset);
        this.offset += 8;
        return result;
    }

    public char nextChar() {
        char result = ByteUtil.bytesHighFirstToChar(this.bytes, this.offset);
        this.offset += 2;
        return result;
    }

    public byte nextByte() {
        return this.bytes[this.offset++];
    }

    public boolean hasMore() {
        return this.offset < this.bytes.length;
    }

    public String nextString() {
        StringBuilder sb = new StringBuilder();
        int length = this.nextInt();

        for(int i = 0; i < length; ++i) {
            sb.append(this.nextChar());
        }

        return sb.toString();
    }

    public float nextFloat() {
        float result = ByteUtil.bytesHighFirstToFloat(this.bytes, this.offset);
        this.offset += 4;
        return result;
    }

    public int nextUnsignedShort() {
        byte a = this.nextByte();
        byte b = this.nextByte();
        return (a & 255) << 8 | b & 255;
    }

    public String nextUTF() {
        int utflen = this.nextUnsignedShort();
        byte[] bytearr = new byte[utflen];
        char[] chararr = new char[utflen];
        int count = 0;
        int chararr_count = 0;

        for(int i = 0; i < utflen; ++i) {
            bytearr[i] = this.nextByte();
        }

        int c;
        while(count < utflen) {
            c = bytearr[count] & 255;
            if (c > 127) {
                break;
            }

            ++count;
            chararr[chararr_count++] = (char)c;
        }

        while(true) {
            while(count < utflen) {
                c = bytearr[count] & 255;
                byte char2;
                switch(c >> 4) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        ++count;
                        chararr[chararr_count++] = (char)c;
                        break;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    default:
                        Config.Log.logger.severe("malformed input around byte " + count);
                        break;
                    case 12:
                    case 13:
                        count += 2;
                        if (count > utflen) {
                            Config.Log.logger.severe("malformed input: partial character at end");
                        }

                        char2 = bytearr[count - 1];
                        if ((char2 & 192) != 128) {
                            Config.Log.logger.severe("malformed input around byte " + count);
                        }

                        chararr[chararr_count++] = (char)((c & 31) << 6 | char2 & 63);
                        break;
                    case 14:
                        count += 3;
                        if (count > utflen) {
                            Config.Log.logger.severe("malformed input: partial character at end");
                        }

                        char2 = bytearr[count - 2];
                        int char3 = bytearr[count - 1];
                        if ((char2 & 192) != 128 || (char3 & 192) != 128) {
                            Config.Log.logger.severe("malformed input around byte " + (count - 1));
                        }

                        chararr[chararr_count++] = (char)((c & 15) << 12 | (char2 & 63) << 6 | (char3 & 63) << 0);
                }
            }

            return new String(chararr, 0, chararr_count);
        }
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLength() {
        return this.bytes.length;
    }
}
