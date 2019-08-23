package com.xtc.libthai;

public enum Language {
    Thai("th"),
    Vietnamese("vi"),
    Khmer("km"),
    Lao("lo"),
    Burmese("bu"),
    Myanmar("my"),
    Chinese("zh"),
    English("en");

    private String label;

    private Language(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
