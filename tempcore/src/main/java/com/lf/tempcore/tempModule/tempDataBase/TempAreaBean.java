package com.lf.tempcore.tempModule.tempDataBase;

/**
 * Created by longf on 2016/5/19.
 */
public class TempAreaBean {
//    @Column(name="a_id",isId = true,autoGen = false)
    private int a_id;
    //    @Column(name="a_name")
    private String a_name;
    //    @Column(name="a_parent_id")
    private String a_parent_id;
    //    @Column(name="a_level")
    private String a_level;
    //    @Column(name="a_relation")
    private String a_relation;
    //    @Column(name="a_description")
    private String a_description;
    //    @Column(name="a_sort")
    private String a_sort;
    //    @Column(name="a_lat")
    private String a_lag;
    //    @Column(name="a_lng")
    private String a_lng;
    //    @Column(name="a_pinyin")
    private String a_pinyin;

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_parent_id() {
        return a_parent_id;
    }

    public void setA_parent_id(String a_parent_id) {
        this.a_parent_id = a_parent_id;
    }

    public String getA_level() {
        return a_level;
    }

    public void setA_level(String a_level) {
        this.a_level = a_level;
    }

    public String getA_relation() {
        return a_relation;
    }

    public void setA_relation(String a_relation) {
        this.a_relation = a_relation;
    }

    public String getA_description() {
        return a_description;
    }

    public void setA_description(String a_description) {
        this.a_description = a_description;
    }

    public String getA_sort() {
        return a_sort;
    }

    public void setA_sort(String a_sort) {
        this.a_sort = a_sort;
    }

    public String getA_lag() {
        return a_lag;
    }

    public void setA_lag(String a_lag) {
        this.a_lag = a_lag;
    }

    public String getA_lng() {
        return a_lng;
    }

    public void setA_lng(String a_lng) {
        this.a_lng = a_lng;
    }

    public String getA_pinyin() {
        return a_pinyin;
    }

    public void setA_pinyin(String a_pinyin) {
        this.a_pinyin = a_pinyin;
    }

    @Override
    public String toString() {
        return "dbarea{" +
                "a_id=" + a_id +
                ", a_name='" + a_name + '\'' +
                ", a_parent_id='" + a_parent_id + '\'' +
                ", a_level='" + a_level + '\'' +
                ", a_relation='" + a_relation + '\'' +
                ", a_description='" + a_description + '\'' +
                ", a_sort='" + a_sort + '\'' +
                ", a_lag='" + a_lag + '\'' +
                ", a_lng='" + a_lng + '\'' +
                ", a_pinyin='" + a_pinyin + '\'' +
                '}';
    }
}
