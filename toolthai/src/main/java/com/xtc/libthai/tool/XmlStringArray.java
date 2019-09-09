package com.xtc.libthai.tool;

import java.util.List;

public class XmlStringArray {

    private String name;

    private List<String> item;

    public XmlStringArray() {
    }

    public XmlStringArray(String name, List<String> item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }
}
