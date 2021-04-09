package com.topcuoglu.twitter;

public class Rule {

    private long id;
    private String value;
    private String tag;

    public Rule() {
    }

    public Rule(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
