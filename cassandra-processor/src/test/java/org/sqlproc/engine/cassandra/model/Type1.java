package org.sqlproc.engine.cassandra.model;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((t_int == null) ? 0 : t_int.hashCode());
        result = prime * result + ((t_varchar == null) ? 0 : t_varchar.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Type1 other = (Type1) obj;
        if (t_int == null) {
            if (other.t_int != null)
                return false;
        } else if (!t_int.equals(other.t_int))
            return false;
        if (t_varchar == null) {
            if (other.t_varchar != null)
                return false;
        } else if (!t_varchar.equals(other.t_varchar))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UdtType [t_varchar=" + t_varchar + ", t_int=" + t_int + "]";
    }
}
