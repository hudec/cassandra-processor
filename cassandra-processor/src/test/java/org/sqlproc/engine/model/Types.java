package org.sqlproc.engine.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Types {

    private Integer id;

    private String t_ascii;
    private Long t_bigint;
    private Boolean t_boolean;
    private LocalDate t_date;
    private BigDecimal t_decimal;
    private Double t_double;
    private Float t_float;
    private InetAddress t_inet;
    private Integer t_int;
    private List<Integer> t_list_int;
    private Map<Integer, String> t_map;
    private List<String> t_list_text;
    private Set<Integer> t_set_int;
    private Set<String> t_set_text;
    private Short t_smallint;
    private String t_text;
    private LocalTime t_time;
    private Instant t_timestamp;
    private UUID t_timeuuid;
    private Byte t_tinyint;
    private String t_varchar;
    private BigInteger t_varint;
    private UUID t_uuid;

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

    public InetAddress getT_inet() {
        return t_inet;
    }

    public void setT_inet(InetAddress t_inet) {
        this.t_inet = t_inet;
    }

    public Integer getT_int() {
        return t_int;
    }

    public void setT_int(Integer t_int) {
        this.t_int = t_int;
    }

    public List<Integer> getT_list_int() {
        return t_list_int;
    }

    public void setT_list_int(List<Integer> t_list_int) {
        this.t_list_int = t_list_int;
    }

    public List<String> getT_list_text() {
        return t_list_text;
    }

    public void setT_list_text(List<String> t_list_text) {
        this.t_list_text = t_list_text;
    }

    public Map<Integer, String> getT_map() {
        return t_map;
    }

    public void setT_map(Map<Integer, String> t_map) {
        this.t_map = t_map;
    }

    public Set<Integer> getT_set_int() {
        return t_set_int;
    }

    public void setT_set_int(Set<Integer> t_set_int) {
        this.t_set_int = t_set_int;
    }

    public Set<String> getT_set_text() {
        return t_set_text;
    }

    public void setT_set_text(Set<String> t_set_text) {
        this.t_set_text = t_set_text;
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

    public UUID getT_timeuuid() {
        return t_timeuuid;
    }

    public void setT_timeuuid(UUID t_timeuuid) {
        this.t_timeuuid = t_timeuuid;
    }

    public Byte getT_tinyint() {
        return t_tinyint;
    }

    public void setT_tinyint(Byte t_tinyint) {
        this.t_tinyint = t_tinyint;
    }

    public UUID getT_uuid() {
        return t_uuid;
    }

    public void setT_uuid(UUID t_uuid) {
        this.t_uuid = t_uuid;
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
                + ", t_inet=" + t_inet + ", t_int=" + t_int + ", t_list_int=" + t_list_int + ", t_map=" + t_map
                + ", t_list_text=" + t_list_text + ", t_set_int=" + t_set_int + ", t_set_text=" + t_set_text
                + ", t_smallint=" + t_smallint + ", t_text=" + t_text + ", t_time=" + t_time + ", t_timestamp="
                + t_timestamp + ", t_timeuuid=" + t_timeuuid + ", t_tinyint=" + t_tinyint + ", t_varchar=" + t_varchar
                + ", t_varint=" + t_varint + ", t_uuid=" + t_uuid + "]";
    }
}
