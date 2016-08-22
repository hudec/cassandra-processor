package org.sqlproc.engine.cassandra.model;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.datastax.driver.core.Cluster;

public class NativeClusteringTypes {

    private int id;

    private String t_ascii;
    private long t_bigint;
    private boolean t_boolean;
    private LocalDate t_date;
    private BigDecimal t_decimal;
    private double t_double;
    private float t_float;
    private int t_int;
    private short t_smallint;
    private String t_text;
    private LocalTime t_time;
    private Instant t_timestamp;
    private UUID t_timeuuid;
    private byte t_tinyint;
    private String t_varchar;
    private BigInteger t_varint;
    private UUID t_uuid;

    public NativeClusteringTypes() {
    }

    public NativeClusteringTypes(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getT_ascii() {
        return t_ascii;
    }

    public void setT_ascii(String t_ascii) {
        this.t_ascii = t_ascii;
    }

    public long getT_bigint() {
        return t_bigint;
    }

    public void setT_bigint(long t_bigint) {
        this.t_bigint = t_bigint;
    }

    public boolean getT_boolean() {
        return t_boolean;
    }

    public void setT_boolean(boolean t_boolean) {
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

    public double getT_double() {
        return t_double;
    }

    public void setT_double(double t_double) {
        this.t_double = t_double;
    }

    public float getT_float() {
        return t_float;
    }

    public void setT_float(float t_float) {
        this.t_float = t_float;
    }

    public int getT_int() {
        return t_int;
    }

    public void setT_int(int t_int) {
        this.t_int = t_int;
    }

    public short getT_smallint() {
        return t_smallint;
    }

    public void setT_smallint(short t_smallint) {
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

    public byte getT_tinyint() {
        return t_tinyint;
    }

    public void setT_tinyint(byte t_tinyint) {
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

    public UUID getT_uuid() {
        return t_uuid;
    }

    public void setT_uuid(UUID t_uuid) {
        this.t_uuid = t_uuid;
    }

    @Override
    public String toString() {
        return "ClusteringTypes [id=" + id + ", t_ascii=" + t_ascii + ", t_bigint=" + t_bigint + ", t_boolean="
                + t_boolean + ", t_date=" + t_date + ", t_decimal=" + t_decimal + ", t_double=" + t_double
                + ", t_float=" + t_float + ", t_int=" + t_int + ", t_smallint=" + t_smallint + ", t_text=" + t_text
                + ", t_time=" + t_time + ", t_timestamp=" + t_timestamp + ", t_timeuuid=" + t_timeuuid + ", t_tinyint="
                + t_tinyint + ", t_varchar=" + t_varchar + ", t_varint=" + t_varint + ", t_uuid=" + t_uuid + "]";
    }

    public static void assertClusteringTypes(NativeClusteringTypes t1, NativeClusteringTypes t2) {
        assertThat(t1, notNullValue());
        assertThat(t2, notNullValue());
        assertThat(t1.getId(), is(t2.getId()));
        assertThat(t1.getT_ascii(), is(t2.getT_ascii()));
        assertThat(t1.getT_bigint(), is(t2.getT_bigint()));
        assertThat(t1.getT_boolean(), is(t2.getT_boolean()));
        assertThat(t1.getT_date(), is(t2.getT_date()));
        assertThat(t1.getT_decimal(), is(t2.getT_decimal()));
        assertThat(t1.getT_double(), is(t2.getT_double()));
        assertThat(t1.getT_float(), is(t2.getT_float()));
        assertThat(t1.getT_int(), is(t2.getT_int()));
        assertThat(t1.getT_smallint(), is(t2.getT_smallint()));
        assertThat(t1.getT_text(), is(t2.getT_text()));
        assertThat(t1.getT_time(), is(t2.getT_time()));
        assertThat(t1.getT_timestamp(), is(t2.getT_timestamp()));
        assertThat(t1.getT_timeuuid(), is(t2.getT_timeuuid()));
        assertThat(t1.getT_tinyint(), is(t2.getT_tinyint()));
        assertThat(t1.getT_varchar(), is(t2.getT_varchar()));
        assertThat(t1.getT_varint(), is(t2.getT_varint()));
        assertThat(t1.getT_uuid(), is(t2.getT_uuid()));
    }

    public static NativeClusteringTypes getDefaultTypes(Cluster cluster) throws UnknownHostException {
        NativeClusteringTypes t = new NativeClusteringTypes();
        t.setId(1);
        t.setT_ascii("ascii");
        t.setT_bigint(2L);
        t.setT_boolean(true);
        t.setT_date(LocalDate.of(2016, 7, 26));
        t.setT_decimal(BigDecimal.valueOf(3));
        t.setT_double(4.0);
        t.setT_float(5.0F);
        t.setT_int(6);
        t.setT_smallint((short) 7);
        t.setT_text("text");
        t.setT_time(LocalTime.of(10, 11, 12));
        // TODO zone?
        t.setT_timestamp(Instant.parse("2016-07-26T08:11:12Z"));
        t.setT_timeuuid(UUID.fromString("e12229de-5eda-11e6-a6a7-cc3d827302bc"));
        t.setT_tinyint((byte) 8);
        t.setT_varchar("varchar");
        t.setT_varint(BigInteger.valueOf(9));
        t.setT_uuid(UUID.fromString("a9c9b8ae-4911-4bf4-a855-4b5f634d0664"));
        return t;
    }

    public static NativeClusteringTypes getNewTypes(Cluster cluster) throws UnknownHostException {
        NativeClusteringTypes t = new NativeClusteringTypes();
        t.setId(101);
        t.setT_ascii("ascii");
        t.setT_bigint(Long.MAX_VALUE);
        t.setT_boolean(true);
        t.setT_date(LocalDate.now());
        t.setT_decimal(BigDecimal.ONE);
        t.setT_double(Double.MAX_VALUE);
        t.setT_float(Float.MAX_VALUE);
        t.setT_int(Integer.MAX_VALUE);
        t.setT_smallint(Short.MAX_VALUE);
        t.setT_text("ěščřžýáíéúů");
        t.setT_time(LocalTime.now());
        t.setT_timestamp(Instant.now());
        t.setT_timeuuid(UUID.fromString("e12229de-5eda-11e6-a6a7-cc3d827302bc"));
        t.setT_tinyint(Byte.MAX_VALUE);
        t.setT_varchar("ěščřžýáíéúů");
        t.setT_varint(BigInteger.ONE);
        t.setT_uuid(UUID.fromString("a9c9b8ae-4911-4bf4-a855-4b5f634d0664"));
        return t;
    }

    public static NativeClusteringTypes getNullTypes(int id) {
        NativeClusteringTypes t = new NativeClusteringTypes();
        t.setId(id);
        return t;
    }
}
