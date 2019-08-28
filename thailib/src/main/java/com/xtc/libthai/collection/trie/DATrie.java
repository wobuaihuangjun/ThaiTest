package com.xtc.libthai.collection.trie;

import com.xtc.libthai.Config;
import com.xtc.libthai.util.ByteArray;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.GZIPOutputStream;

/**
 * 双数组Trie<br>
 * 原始版本是KOMIYA Atsushi对Taku Kudo的 C++ 版 Double Array Trie的实现，https://github.com/komiya-atsushi/darts-java.git  <br>
 * hankcs实现了一个Searcher结构<br>
 * 这里基本没有做什么修改，只是加了几个接口实现
 *
 * @param <V>
 */
public class DATrie<V> implements ITrie {

    private static final String TAG = "DATrie: ";

    private static final int UNIT_SIZE = 8;
    protected int[] check = null;
    protected int[] base = null;
    private boolean[] used = null;
    /**
     * base 和 check 的大小
     */
    protected int size = 0;
    private int allocSize = 0;
    private List<String> key;
    private int keySize;
    private int[] length;
    private int[] value;
    protected V[] v;
    private int progress;
    private int nextCheckPos;
    private int maxLength;
    int error = 0;

    public DATrie() {
    }

    public int getUnitSize() {
        return 8;
    }

    public int getSize() {
        return this.size;
    }

    public int getTotalSize() {
        return this.size * 8;
    }

    public int getNonzeroSize() {
        int result = 0;

        for(int i = 0; i < this.check.length; ++i) {
            if (this.check[i] != 0) {
                ++result;
            }
        }

        return result;
    }

    public int size() {
        return this.v.length;
    }

    public int[] getCheck() {
        return this.check;
    }

    public int[] getBase() {
        return this.base;
    }

    public int build(List<String> keys) {
        assert keys.size() > 0 : "键值个数为0！";

        return this.build(keys, (int[])null, (int[])null, keys.size());
    }

    public int build(List<String> keys, List<V> values) {
        assert keys.size() == values.size() : "键的个数与值的个数不一样！";

        assert keys.size() > 0 : "键值个数为0！";

        v = (V[]) values.toArray();
        return build(keys, null, null, keys.size());
    }

    public int build(List<String> keys, V[] values) {
        assert keys.size() == this.value.length : "键的个数与值的个数不一样！";

        assert keys.size() > 0 : "键值个数为0！";

        this.v = values;
        return this.build(keys, (int[])null, (int[])null, keys.size());
    }

    /**
     * 构建DAT
     *
     * @param entrySet 注意此entrySet一定要是字典序的！否则会失败
     * @return
     */
    public int build(Set<Entry<String, V>> entrySet) {
        List<String> keyList = new ArrayList<>(entrySet.size());
        List<V> valueList = new ArrayList<>(entrySet.size());

        for (Map.Entry<String, V> entry : entrySet) {
            keyList.add(entry.getKey());
            valueList.add(entry.getValue());
        }

        return build(keyList, valueList);
    }

    /**
     * 方便地构造一个双数组trie树
     *
     * @param keyValueMap 升序键值对map
     * @return 构造结果
     */
    public int build(TreeMap<String, V> keyValueMap) {
        assert keyValueMap != null;

        Set<Entry<String, V>> entrySet = keyValueMap.entrySet();
        return this.build(entrySet);
    }

    /**
     * 唯一的构建方法
     *
     * @param _key 值set，必须字典序
     * @param _length 对应每个key的长度，留空动态获取
     * @param _value 每个key对应的值，留空使用key的下标作为值
     * @param _keySize key的长度，应该设为_key.size
     * @return 是否出错
     */
    public int build(List<String> _key, int[] _length, int[] _value, int _keySize) {
        if (_keySize > _key.size() || _key == null)
            return 0;

        // progress_func_ = progress_func;
        key = _key;
        length = _length;
        keySize = _keySize;
        value = _value;
        progress = 0;

        resize(65536 * 32); // 32个双字节

        base[0] = 1;
        nextCheckPos = 0;

        Node root_node = new Node();
        root_node.left = 0;
        root_node.right = keySize;
        root_node.depth = 0;

        List<Node> siblings = new ArrayList<>();
        fetch(root_node, siblings);
        insert(siblings);

        // size += (1 << 8 * 2) + 1; // ???
        // if (size >= allocSize) resize (size);

        used = null;
        key = null;
        length = null;

        return error;
    }
    /**
     * 拓展数组
     *
     * @param newSize 新数组大小
     * @return
     */
    private int resize(int newSize) {
        int[] newBase = new int[newSize];
        int[] newCheck = new int[newSize];
        boolean[] newUsed = new boolean[newSize];
        if (allocSize > 0) {
            System.arraycopy(base, 0, newBase, 0, allocSize);
            System.arraycopy(check, 0, newCheck, 0, allocSize);
            System.arraycopy(used, 0, newUsed, 0, allocSize);
        }
        base = newBase;
        check = newCheck;
        used = newUsed;
        return allocSize = newSize;
    }
    /**
     * 获取直接相连的子节点
     *
     * @param parent 父节点
     * @param siblings （子）兄弟节点
     * @return 兄弟节点个数
     */
    private int fetch(DATrie.Node parent, List<Node> siblings) {
        if (this.error < 0) {
            return 0;
        } else {
            int prev = 0;

            for(int i = parent.left; i < parent.right; ++i) {
                if ((this.length != null ? this.length[i] : ((String)this.key.get(i)).length()) >= parent.depth) {
                    String tmp = (String)this.key.get(i);
                    int cur = 0;
                    if ((this.length != null ? this.length[i] : tmp.length()) != parent.depth) {
                        cur = tmp.charAt(parent.depth) + 1;
                    }

                    if (prev > cur) {
                        this.error = -3;
                        return 0;
                    }

                    if (cur != prev || siblings.size() == 0) {
                        DATrie.Node tmp_node = new DATrie.Node();
                        tmp_node.depth = parent.depth + 1;
                        tmp_node.code = cur;
                        tmp_node.left = i;
                        if (siblings.size() != 0) {
                            ((DATrie.Node)siblings.get(siblings.size() - 1)).right = i;
                        }

                        siblings.add(tmp_node);
                    }

                    prev = cur;
                }
            }

            if (siblings.size() != 0) {
                ((DATrie.Node)siblings.get(siblings.size() - 1)).right = parent.right;
            }

            return siblings.size();
        }
    }

    /**
     * 插入节点
     *
     * @param siblings 等待插入的兄弟节点
     * @return 插入位置
     */
    private int insert(List<Node> siblings) {
        if (this.error < 0) {
            return 0;
        } else {
            int pos = Math.max(((DATrie.Node)siblings.get(0)).code + 1, this.nextCheckPos) - 1;
            int nonzero_num = 0;
            boolean first = false;
            if (this.allocSize <= pos) {
                this.resize(pos + 1);
            }
            // 此循环体的目标是找出满足base[begin + a1...an] == 0的n个空闲空间,a1...an是siblings中的n个节点
            while(true) {
                label91:
                while(true) {
                    ++pos;
                    if (this.allocSize <= pos) {
                        this.resize(pos + 1);
                    }

                    if (this.check[pos] != 0) {
                        ++nonzero_num;
                    } else {
                        if (!first) {
                            this.nextCheckPos = pos;
                            first = true;
                        }

                        int begin = pos - ((DATrie.Node)siblings.get(0)).code;
                        if (this.allocSize <= begin + ((DATrie.Node)siblings.get(siblings.size() - 1)).code) {
                            double l = 1.05D > 1.0D * (double)this.keySize / (double)(this.progress + 1) ? 1.05D : 1.0D * (double)this.keySize / (double)(this.progress + 1);
                            this.resize((int)((double)this.allocSize * l));
                        }

                        if (!this.used[begin]) {
                            int i;
                            for(i = 1; i < siblings.size(); ++i) {
                                if (this.check[begin + ((DATrie.Node)siblings.get(i)).code] != 0) {
                                    continue label91;
                                }
                            }

                            if (1.0D * (double)nonzero_num / (double)(pos - this.nextCheckPos + 1) >= 0.95D) {
                                this.nextCheckPos = pos;
                            }

                            this.used[begin] = true;
                            this.size = this.size > begin + ((DATrie.Node)siblings.get(siblings.size() - 1)).code + 1 ? this.size : begin + ((DATrie.Node)siblings.get(siblings.size() - 1)).code + 1;

                            for(i = 0; i < siblings.size(); ++i) {
                                this.check[begin + ((DATrie.Node)siblings.get(i)).code] = begin;
                            }

                            for(i = 0; i < siblings.size(); ++i) {
                                List<Node> new_siblings = new ArrayList();
                                if (this.fetch((DATrie.Node)siblings.get(i), new_siblings) == 0) {
                                    this.base[begin + ((DATrie.Node)siblings.get(i)).code] = this.value != null ? -this.value[((DATrie.Node)siblings.get(i)).left] - 1 : -((DATrie.Node)siblings.get(i)).left - 1;
                                    if (this.value != null && -this.value[((DATrie.Node)siblings.get(i)).left] - 1 >= 0) {
                                        this.error = -2;
                                        return 0;
                                    }

                                    ++this.progress;
                                } else {
                                    int h = this.insert(new_siblings);
                                    this.base[begin + ((DATrie.Node)siblings.get(i)).code] = h;
                                }
                            }

                            return begin;
                        }
                    }
                }
            }
        }
    }

    public int addAll(List<Node> siblings) {
        if (this.error < 0) {
            return 0;
        } else {
            int pos = Math.max(((DATrie.Node)siblings.get(0)).code + 1, this.nextCheckPos) - 1;
            int nonzero_num = 0;
            boolean first = false;
            if (this.allocSize <= pos) {
                this.resize(pos + 1);
            }

            while(true) {
                label91:
                while(true) {
                    ++pos;
                    if (this.allocSize <= pos) {
                        this.resize(pos + 1);
                    }

                    if (this.check[pos] != 0) {
                        ++nonzero_num;
                    } else {
                        if (!first) {
                            this.nextCheckPos = pos;
                            first = true;
                        }

                        int begin = pos - ((DATrie.Node)siblings.get(0)).code;
                        if (this.allocSize <= begin + ((DATrie.Node)siblings.get(siblings.size() - 1)).code) {
                            double l = 1.05D > 1.0D * (double)this.keySize / (double)(this.progress + 1) ? 1.05D : 1.0D * (double)this.keySize / (double)(this.progress + 1);
                            this.resize((int)((double)this.allocSize * l));
                        }

                        if (!this.used[begin]) {
                            int i;
                            for(i = 1; i < siblings.size(); ++i) {
                                if (this.check[begin + ((DATrie.Node)siblings.get(i)).code] != 0) {
                                    continue label91;
                                }
                            }

                            if (1.0D * (double)nonzero_num / (double)(pos - this.nextCheckPos + 1) >= 0.95D) {
                                this.nextCheckPos = pos;
                            }

                            this.used[begin] = true;
                            this.size = this.size > begin + ((DATrie.Node)siblings.get(siblings.size() - 1)).code + 1 ? this.size : begin + ((DATrie.Node)siblings.get(siblings.size() - 1)).code + 1;

                            for(i = 0; i < siblings.size(); ++i) {
                                this.check[begin + ((DATrie.Node)siblings.get(i)).code] = begin;
                            }

                            for(i = 0; i < siblings.size(); ++i) {
                                List<Node> new_siblings = new ArrayList();
                                if (this.fetch((DATrie.Node)siblings.get(i), new_siblings) == 0) {
                                    this.base[begin + ((DATrie.Node)siblings.get(i)).code] = this.value != null ? -this.value[((DATrie.Node)siblings.get(i)).left] - 1 : -((DATrie.Node)siblings.get(i)).left - 1;
                                    if (this.value != null && -this.value[((DATrie.Node)siblings.get(i)).left] - 1 >= 0) {
                                        this.error = -2;
                                        return 0;
                                    }

                                    ++this.progress;
                                } else {
                                    int h = this.insert(new_siblings);
                                    this.base[begin + ((DATrie.Node)siblings.get(i)).code] = h;
                                }
                            }

                            return begin;
                        }
                    }
                }
            }
        }
    }

    public int exactMatchSearch(String key) {
        return this.exactMatchSearch(key, 0, 0, 0);
    }

    /**
     * 关键字匹配搜索
     * @return >0表示匹配成果；
     */
    public int exactMatchSearch(String key, int pos, int len, int nodePos) {
//        System.out.println("exactMatchSearch");
        if (len <= 0) {
            len = key.length();
        }

        if (nodePos <= 0) {
            nodePos = 0;
        }

        int result = -1;
        char[] keyChars = key.toCharArray();
        int b = this.base[nodePos];
//        System.out.println("exactMatchSearch, len = " + len);
//        System.out.println("exactMatchSearch, b = " + b);
        int n;
        for(n = pos; n < len; ++n) {
            int p = b + keyChars[n] + 1;
            if (b != this.check[p]) {
                return result;
            }

            b = this.base[p];
        }
//        System.out.println("exactMatchSearch, b = " + b);
        n = this.base[b];
//        System.out.println("exactMatchSearch, n = " + n);
        if (b == this.check[b] && n < 0) {
            result = -n - 1;
        }

        return result;
    }

    public int exactMatchSearch(char[] keyChars, int pos, int len, int nodePos) {
        int result = -1;
        int b = this.base[nodePos];

        int n;
        for(n = pos; n < len; ++n) {
            int p = b + keyChars[n] + 1;
            if (b != this.check[p]) {
                return result;
            }

            b = this.base[p];
        }

        n = this.base[b];
        if (b == this.check[b] && n < 0) {
            result = -n - 1;
        }

        return result;
    }

    public List<Integer> commonPrefixSearch(String key) {
        return this.commonPrefixSearch(key, 0, 0, 0);
    }

    public List<Integer> commonPrefixSearch(String key, int pos, int len, int nodePos) {
        if (len <= 0) {
            len = key.length();
        }

        if (nodePos <= 0) {
            nodePos = 0;
        }

        List<Integer> result = new ArrayList();
        char[] keyChars = key.toCharArray();
        int b = this.base[nodePos];

        for(int i = pos; i < len; ++i) {
            int p = b + keyChars[i] + 1;
            if (b != this.check[p]) {
                return result;
            }

            b = this.base[p];
            int n = this.base[b];
            if (b == this.check[b] && n < 0) {
                result.add(-n - 1);
            }
        }

        return result;
    }

    /** @deprecated */
    public LinkedList<Entry<String, V>> commonPrefixSearchWithValue(String key) {
        int len = key.length();
        LinkedList<Entry<String, V>> result = new LinkedList();
        char[] keyChars = key.toCharArray();
        int b = this.base[0];

        int n;
        for(int i = 0; i < len; ++i) {
            n = this.base[b];
            if (b == this.check[b] && n < 0) {
                result.add(new SimpleEntry(new String(keyChars, 0, i), this.v[-n - 1]));
            }

            int p = b + keyChars[i] + 1;
            if (b != this.check[p]) {
                return result;
            }

            b = this.base[p];
        }

        n = this.base[b];
        if (b == this.check[b] && n < 0) {
            result.add(new SimpleEntry(key, this.v[-n - 1]));
        }

        return result;
    }

    public LinkedList<Entry<String, V>> commonPrefixSearchWithValue(char[] keyChars, int begin) {
        int len = keyChars.length;
        LinkedList<Entry<String, V>> result = new LinkedList();
        int b = this.base[0];

        int n;
        for(int i = begin; i < len; ++i) {
            n = this.base[b];
            if (b == this.check[b] && n < 0) {
                result.add(new SimpleEntry(new String(keyChars, begin, i - begin), this.v[-n - 1]));
            }

            int p = b + keyChars[i] + 1;
            if (b != this.check[p]) {
                return result;
            }

            b = this.base[p];
        }

        n = this.base[b];
        if (b == this.check[b] && n < 0) {
            result.add(new SimpleEntry(new String(keyChars, begin, len - begin), this.v[-n - 1]));
        }

        return result;
    }

    public V getValue(int index) {
        return this.v[index];
    }

    public V get(String key) {
        int index = this.exactMatchSearch(key);
        return index >= 0 ? this.getValue(index) : null;
    }

    public V get(char[] key) {
        int index = this.exactMatchSearch((char[])key, 0, key.length, 0);
        return index >= 0 ? this.getValue(index) : null;
    }

    public V[] getValueArray(V[] a) {
        int size = this.v.length;
        if (a.length < size) {
            a = (V[]) Array.newInstance(a.getClass().getComponentType(), size);
        }

        System.arraycopy(this.v, 0, a, 0, size);
        return a;
    }

    protected int transition(String path) {
        return this.transition(path.toCharArray());
    }

    protected int transition(char[] path) {
        int b = this.base[0];

        for(int i = 0; i < path.length; ++i) {
            int p = b + path[i] + 1;
            if (b != this.check[p]) {
                return -1;
            }

            b = this.base[p];
        }

        return b;
    }

    protected int transition(int current, char c) {
        int b = this.base[current];
        int p = b + c + 1;
        if (b == this.check[p]) {
            b = this.base[p];
            return b;
        } else {
            return -1;
        }
    }

    public int transition(String path, int from) {
        int b = from;

        for(int i = 0; i < path.length(); ++i) {
            int p = b + path.charAt(i) + 1;
            if (b != this.check[p]) {
                return -1;
            }

            b = this.base[p];
        }

        return b;
    }

    public int transition(char c, int from) {
        int p = from + c + 1;
        if (from == this.check[p]) {
            int b = this.base[p];
            return b;
        } else {
            return -1;
        }
    }

    public V output(int state) {
        if (state < 0) {
            return null;
        } else {
            int n = this.base[state];
            return state == this.check[state] && n < 0 ? this.v[-n - 1] : null;
        }
    }

    public boolean set(String key, V value) {
        int index = this.exactMatchSearch(key);
        if (index >= 0) {
            this.v[index] = value;
            return true;
        } else {
            return false;
        }
    }

    public int getMaxLength() {
        return 0;
    }

    public boolean containsKey(String key) {
        return this.exactMatchSearch(key) >= 0;
    }

    public boolean contains(char[] text, int offset, int count) {
        return this.exactMatchSearch(new String(text, offset, count)) >= 0;
    }

    public boolean contains(String text, int offset, int count) {
        return this.contains(text.toCharArray(), offset, count);
    }

    public boolean contains(String key) {
        int result = this.exactMatchSearch(key);
//        System.out.println("contains, result = " + result);
        return result >= 0;
    }

    public void clear() {
        this.check = null;
        this.base = null;
        this.used = null;
        this.allocSize = 0;
        this.size = 0;
    }

    public String toString() {
        return "DoubleArrayTrie{size=" + this.size + ", allocSize=" + this.allocSize + ", key=" + this.key + ", keySize=" + this.keySize + ", progress=" + this.progress + ", nextCheckPos=" + this.nextCheckPos + ", error =" + this.error + '}';
    }

    public boolean save(String fileName) {
        try {
            DataOutputStream out = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(fileName)));
            out.writeInt(this.size);

            for(int i = 0; i < this.size; ++i) {
                out.writeInt(this.base[i]);
                out.writeInt(this.check[i]);
            }

            out.close();
            return true;
        } catch (Exception var4) {
            Config.Log.logger.warning("保存失败" + var4);
            return false;
        }
    }

    public boolean save(DataOutputStream out) {
        try {
            out.writeInt(this.size);

            for(int i = 0; i < this.size; ++i) {
                out.writeInt(this.base[i]);
                out.writeInt(this.check[i]);
            }

            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public void save(ObjectOutputStream out) throws IOException {
        out.writeObject(this.base);
        out.writeObject(this.check);
    }

    public boolean loadString(ByteArray byteArray, V[] value) {
        if (byteArray == null) {
            return false;
        } else {
            this.size = byteArray.nextInt();
            this.base = new int[this.size + '\uffff'];
            this.check = new int[this.size + '\uffff'];

            System.out.println(TAG + "load, size: " + size);

            for(int i = 0; i < this.size; ++i) {
                this.base[i] = byteArray.nextInt();
                this.check[i] = byteArray.nextInt();
            }

            this.v = value;
            return true;
        }
    }


    public boolean load(ByteArray byteArray, V[] value) {
        if (byteArray == null) {
            return false;
        } else {
            size = byteArray.nextInt();
            base = new int[size + '\uffff'];
            check = new int[size + '\uffff'];

            System.out.println(TAG + "load, size: " + size);

            for(int i = 0; i < this.size; ++i) {
                this.base[i] = byteArray.nextInt();
                this.check[i] = byteArray.nextInt();
            }

            this.v = value;
            return true;
        }
    }

    public DATrie<V>.Searcher getSearcher(String text, int offset) {
        return new DATrie.Searcher(text.toCharArray(), offset);
    }

    public DATrie<V>.Searcher getSearcher(char[] text, int offset) {
        return new DATrie.Searcher(text, offset);
    }

    public class Searcher {
        public int begin;
        public int length;
        public int index;
        public V value;
        private char[] charArray;
        private int last;
        private int i;
        private int arrayLength;

        public Searcher(char[] charArray, int offset) {
            this.charArray = charArray;
            this.i = offset;
            this.last = DATrie.this.base[0];
            this.arrayLength = charArray.length;
            if (this.arrayLength == 0) {
                this.begin = -1;
            } else {
                this.begin = offset;
            }

        }

        public boolean next() {
            int b = this.last;

            while(true) {
                if (this.i == this.arrayLength) {
                    ++this.begin;
                    if (this.begin == this.arrayLength) {
                        break;
                    }

                    this.i = this.begin;
                    b = DATrie.this.base[0];
                }

                int p = b + this.charArray[this.i] + 1;
                if (b == DATrie.this.check[p]) {
                    b = DATrie.this.base[p];
                    int n = DATrie.this.base[b];
                    if (b == DATrie.this.check[b] && n < 0) {
                        this.length = this.i - this.begin + 1;
                        this.index = -n - 1;
                        this.value = DATrie.this.v[this.index];
                        this.last = b;
                        ++this.i;
                        return true;
                    }
                } else {
                    this.i = this.begin++;
                    if (this.begin == this.arrayLength) {
                        break;
                    }

                    b = DATrie.this.base[0];
                }

                ++this.i;
            }

            return false;
        }
    }

    private static class Node {
        int code;
        int depth;
        int left;
        int right;

        private Node() {
        }

        public String toString() {
            return "Node{code=" + this.code + ", depth=" + this.depth + ", left=" + this.left + ", right=" + this.right + '}';
        }
    }
}

