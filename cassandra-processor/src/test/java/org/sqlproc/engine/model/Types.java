package org.sqlproc.engine.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public class Types {

    private Integer id;

    private String t_ascii;
    private Long t_bigint;
    private Boolean t_boolean;
    private LocalDate t_date;
    private BigDecimal t_decimal;
    private Double t_double;
    private Float t_float;
    private Integer t_int;
    private Short t_smallint;
    private String t_text;
    private LocalTime t_time;
    private Instant t_timestamp;
    private Byte t_tinyint;
    private String t_varchar;
    private BigInteger t_varint;

    public Types() {
    }

    public Types(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getT_ascii() {
        return t_ascii;
    }

    public void setT_ascii(String t_ascii) {
        this.t_ascii = t_ascii;
    }

    public Long getT_bigint() {
        return t_bigint;
    }

    public void setT_bigint(Long t_bigint) {
        this.t_bigint = t_bigint;
    }

    public Boolean getT_boolean() {
        return t_boolean;
    }

    public void setT_boolean(Boolean t_boolean) {
        this.t_boolean = t_boolean;
    }

    public LocalDate getT_date() {
        return t_date;
    }

    public void setT_date(LocalDate t_date) {
        this.t_date = t_date;
    }

    public BigDecimal getT_decimal() {
        return t_decimal;
    }

    public void setT_decimal(BigDecimal t_decimal) {
        this.t_decimal = t_decimal;
    }

    public Double getT_double() {
        return t_double;
    }

    public void setT_double(Double t_double) {
        this.t_double = t_double;
    }

    public Float getT_float() {
        return t_float;
    }

    public void setT_float(Float t_float) {
        this.t_float = t_float;
    }

    public Integer getT_int() {
        return t_int;
    }

    public void setT_int(Integer t_int) {
        this.t_int = t_int;
    }

    public Short getT_smallint() {
        return t_smallint;
    }

    public void setT_smallint(Short t_smallint) {
        this.t_smallint = t_smallint;
    }

    public String getT_text() {
        return t_text;
    }

    public void setT_text(String t_text) {
        this.t_text = t_text;
    }

    public LocalTime getT_time() {
        return t_time;
    }

    public void setT_time(LocalTime t_time) {
        this.t_time = t_time;
    }

    public Instant getT_timestamp() {
        return t_timestamp;
    }

    public void setT_timestamp(Instant t_timestamp) {
        this.t_timestamp = t_timestamp;
    }

    public Byte getT_tinyint() {
        return t_tinyint;
    }

    public void setT_tinyint(Byte t_tinyint) {
        this.t_tinyint = t_tinyint;
    }

    public String getT_varchar() {
        return t_varchar;
    }

    public void setT_varchar(String t_varchar) {
        this.t_varchar = t_varchar;
    }

    public BigInteger getT_varint() {
        return t_varint;
    }

    public void setT_varint(BigInteger t_varint) {
        this.t_varint = t_varint;
    }

    @Override
    public String toString() {
        return "Types [id=" + id + ", t_ascii=" + t_ascii + ", t_bigint=" + t_bigint + ", t_boolean=" + t_boolean
                + ", t_date=" + t_date + ", t_decimal=" + t_decimal + ", t_double=" + t_double + ", t_float=" + t_float
                + ", t_int=" + t_int + ", t_smallint=" + t_smallint + ", t_text=" + t_text + ", t_time=" + t_time
                + ", t_timestamp=" + t_timestamp + ", t_tinyint=" + t_tinyint + ", t_varchar=" + t_varchar
                + ", t_varint=" + t_varint + "]";
    }
}
