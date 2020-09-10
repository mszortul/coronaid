package com.coronaid.coronaid;

public class cd {
    String name, tc, nc, td, nd, tr, ac, sc, ratio, keyName;

    public cd() {}


    public cd(String name, String tc, String nc, String td, String nd, String tr, String ac, String sc, String ratio) {
        this.name = name;
        this.tc = tc;
        this.nc = nc;
        this.td = td;
        this.nd = nd;
        this.tr = tr;
        this.ac = ac;
        this.sc = sc;
        this.ratio = tc;
    }

    public String getName() {
        return name;
    }

    public String getAc() {
        return ac;
    }

    public String getNc() {
        return nc;
    }

    public String getNd() {
        return nd;
    }

    public String getRatio() {
        return ratio;
    }

    public String getSc() {
        return sc;
    }

    public String getTc() {
        return tc;
    }

    public String getTd() {
        return td;
    }

    public String getTr() {
        return tr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }

    public void setKeyName(String keyName) { this.keyName = keyName; }

    public String getKeyName() { return keyName; }
}
