package org.sqlproc.engine.model;

public class Type1 {
    private String t_varchar;
    private Integer t_int;

    public Type1(String t_varchar, Integer t_int) {
        super();
        this.t_varchar = t_varchar;
        this.t_int = t_int;
    }

    public String getT_varchar() {
        return t_varchar;
    }

    public void setT_varchar(String t_varchar) {
        this.t_varchar = t_varchar;
    }

    public Integer getT_int() {
        return t_int;
    }

    public void setT_int(Integer t_int) {
        this.t_int = t_int;
    }

    @Override
    public String toString() {
        return "UdtType [t_varchar=" + t_varchar + ", t_int=" + t_int + "]";
    }
}
