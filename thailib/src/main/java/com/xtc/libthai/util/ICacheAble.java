package com.xtc.libthai.util;

import java.io.DataOutputStream;

/**
 * 可写入或读取二进制
 */
public interface ICacheAble {
    /**
     * 写入
     */
    void save(DataOutputStream out) throws Exception;

    /**
     * 加载
     */
    boolean load(ByteArray byteArray);
}
