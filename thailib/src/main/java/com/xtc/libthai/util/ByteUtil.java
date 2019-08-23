package com.xtc.libthai.util;


import java.io.DataOutputStream;
import java.io.IOException;

public class ByteUtil {
    public ByteUtil() {
    }

    public static char bytesToChar(byte[] b) {
        char c = (char)((int)((long)(b[0] << 8) & 65280L));
        c |= (char)((int)((long)b[1] & 255L));
        return c;
    }

    public static double bytesToDouble(byte[] b) {
        return Double.longBitsToDouble(bytesToLong(b));
    }

    public static double bytesHighFirstToDouble(byte[] bytes, int start) {
        long l = (long)bytes[start] << 56 & -72057594037927936L;
        l |= (long)bytes[1 + start] << 48 & 71776119061217280L;
        l |= (long)bytes[2 + start] << 40 & 280375465082880L;
        l |= (long)bytes[3 + start] << 32 & 1095216660480L;
        l |= (long)bytes[4 + start] << 24 & 4278190080L;
        l |= (long)bytes[5 + start] << 16 & 16711680L;
        l |= (long)bytes[6 + start] << 8 & 65280L;
        l |= (long)bytes[7 + start] & 255L;
        return Double.longBitsToDouble(l);
    }

    public static float bytesToFloat(byte[] b) {
        return Float.intBitsToFloat(bytesToInt(b));
    }

    public static int bytesToInt(byte[] b) {
        int i = b[0] << 24 & -16777216;
        i |= b[1] << 16 & 16711680;
        i |= b[2] << 8 & '\uff00';
        i |= b[3] & 255;
        return i;
    }

    public static long bytesToLong(byte[] b) {
        long l = (long)b[0] << 56 & -72057594037927936L;
        l |= (long)b[1] << 48 & 71776119061217280L;
        l |= (long)b[2] << 40 & 280375465082880L;
        l |= (long)b[3] << 32 & 1095216660480L;
        l |= (long)b[4] << 24 & 4278190080L;
        l |= (long)b[5] << 16 & 16711680L;
        l |= (long)b[6] << 8 & 65280L;
        l |= (long)b[7] & 255L;
        return l;
    }

    public static long bytesHighFirstToLong(byte[] b) {
        long l = (long)b[0] << 56 & -72057594037927936L;
        l |= (long)b[1] << 48 & 71776119061217280L;
        l |= (long)b[2] << 40 & 280375465082880L;
        l |= (long)b[3] << 32 & 1095216660480L;
        l |= (long)b[4] << 24 & 4278190080L;
        l |= (long)b[5] << 16 & 16711680L;
        l |= (long)b[6] << 8 & 65280L;
        l |= (long)b[7] & 255L;
        return l;
    }

    public static byte[] charToBytes(char c) {
        byte[] b = new byte[8];
        b[0] = (byte)(c >>> 8);
        b[1] = (byte)c;
        return b;
    }

    public static byte[] doubleToBytes(double d) {
        return longToBytes(Double.doubleToLongBits(d));
    }

    public static byte[] floatToBytes(float f) {
        return intToBytes(Float.floatToIntBits(f));
    }

    public static byte[] intToBytes(int i) {
        byte[] b = new byte[]{(byte)(i >>> 24), (byte)(i >>> 16), (byte)(i >>> 8), (byte)i};
        return b;
    }

    public static byte[] longToBytes(long l) {
        byte[] b = new byte[]{(byte)((int)(l >>> 56)), (byte)((int)(l >>> 48)), (byte)((int)(l >>> 40)), (byte)((int)(l >>> 32)), (byte)((int)(l >>> 24)), (byte)((int)(l >>> 16)), (byte)((int)(l >>> 8)), (byte)((int)l)};
        return b;
    }

    public static int bytesToInt(byte[] bytes, int start) {
        int num = bytes[start] & 255;
        num |= bytes[start + 1] << 8 & '\uff00';
        num |= bytes[start + 2] << 16 & 16711680;
        num |= bytes[start + 3] << 24 & -16777216;
        return num;
    }

    public static int bytesHighFirstToInt(byte[] bytes, int start) {
        int num = bytes[start + 3] & 255;
        num |= bytes[start + 2] << 8 & '\uff00';
        num |= bytes[start + 1] << 16 & 16711680;
        num |= bytes[start] << 24 & -16777216;
        return num;
    }

    public static char bytesHighFirstToChar(byte[] bytes, int start) {
        char c = (char)((bytes[start] & 255) << 8 | bytes[start + 1] & 255);
        return c;
    }

    public static float bytesHighFirstToFloat(byte[] bytes, int start) {
        int l = bytesHighFirstToInt(bytes, start);
        return Float.intBitsToFloat(l);
    }

    public static void writeUnsignedInt(DataOutputStream out, int uint) throws IOException {
        out.writeByte((byte)(uint >>> 8 & 255));
        out.writeByte((byte)(uint >>> 0 & 255));
    }

    public static int convertTwoCharToInt(char high, char low) {
        int result = high << 16;
        result |= low;
        return result;
    }

    public static char[] convertIntToTwoChar(int n) {
        char[] result = new char[]{(char)(n >>> 16), (char)('\uffff' & n)};
        return result;
    }
}
