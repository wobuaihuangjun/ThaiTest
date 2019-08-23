package com.xtc.libthai.util;

import java.io.DataOutputStream;

public interface ICacheAble {
    void save(DataOutputStream var1) throws Exception;

    boolean load(ByteArray var1);
}
