package org.sqlproc.engine.model;

public class EnumTypes {

    private Integer id;
    private EnumInt t_int;
    private EnumVarchar t_varchar;

    public EnumTypes() {
    }

    public EnumTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnumInt getT_int() {
        return t_int;
    }

    public void setT_int(EnumInt t_int) {
        this.t_int = t_int;
    }

    public EnumVarchar getT_varchar() {
        return t_varchar;
    }

    public void setT_varchar(EnumVarchar t_varchar) {
        this.t_varchar = t_varchar;
    }

    @Override
    public String toString() {
        return "EnumTypes [id=" + id + ", t_int=" + t_int + ", t_varchar=" + t_varchar + "]";
    }
}
