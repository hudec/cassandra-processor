package org.sqlproc.engine.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.TupleType;
import com.datastax.driver.core.TupleValue;

public class Types {

    private Integer id;

    private String t_ascii;
    private Long t_bigint;
    private ByteBuffer t_blob;
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
    private TupleValue t_tuple;
    private String t_varchar;
    private BigInteger t_varint;
    private UUID t_uuid;
    private Type1 t_type1;

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

    public ByteBuffer getT_blob() {
        return t_blob;
    }

    public void setT_blob(ByteBuffer t_blob) {
        this.t_blob = t_blob;
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

    public TupleValue getT_tuple() {
        return t_tuple;
    }

    public void setT_tuple(TupleValue t_tuple) {
        this.t_tuple = t_tuple;
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

    public Type1 getT_type1() {
        return t_type1;
    }

    public void setT_type1(Type1 t_type1) {
        this.t_type1 = t_type1;
    }

    @Override
    public String toString() {
        return "Types [id=" + id + ", t_ascii=" + t_ascii + ", t_bigint=" + t_bigint + ", t_blob=" + t_blob
                + ", t_boolean=" + t_boolean + ", t_date=" + t_date + ", t_decimal=" + t_decimal + ", t_double="
                + t_double + ", t_float=" + t_float + ", t_inet=" + t_inet + ", t_int=" + t_int + ", t_list_int="
                + t_list_int + ", t_map=" + t_map + ", t_list_text=" + t_list_text + ", t_set_int=" + t_set_int
                + ", t_set_text=" + t_set_text + ", t_smallint=" + t_smallint + ", t_text=" + t_text + ", t_time="
                + t_time + ", t_timestamp=" + t_timestamp + ", t_timeuuid=" + t_timeuuid + ", t_tinyint=" + t_tinyint
                + ", t_tuple=" + t_tuple + ", t_varchar=" + t_varchar + ", t_varint=" + t_varint + ", t_uuid=" + t_uuid
                + ", t_type1=" + t_type1 + "]";
    }

    public static void assertTypes(Types t1, Types t2) {
        assertThat(t1, notNullValue());
        assertThat(t2, notNullValue());
        assertThat(t1.getId(), is(t2.getId()));
        assertThat(t1.getT_ascii(), is(t2.getT_ascii()));
        assertThat(t1.getT_bigint(), is(t2.getT_bigint()));
        if (t1.getT_blob() != null || t2.getT_blob() != null)
            assertThat(t1.getT_blob().array(), equalTo(t2.getT_blob().array()));
        assertThat(t1.getT_boolean(), is(t2.getT_boolean()));
        assertThat(t1.getT_date(), is(t2.getT_date()));
        assertThat(t1.getT_decimal(), is(t2.getT_decimal()));
        assertThat(t1.getT_double(), is(t2.getT_double()));
        assertThat(t1.getT_float(), is(t2.getT_float()));
        assertThat(t1.getT_inet(), is(t2.getT_inet()));
        assertThat(t1.getT_int(), is(t2.getT_int()));
        assertThat(t1.getT_list_int(), equalTo(t2.getT_list_int()));
        assertThat(t1.getT_list_text(), equalTo(t2.getT_list_text()));
        assertThat(t1.getT_map(), equalTo(t2.getT_map()));
        assertThat(t1.getT_set_int(), equalTo(t2.getT_set_int()));
        assertThat(t1.getT_set_text(), equalTo(t2.getT_set_text()));
        assertThat(t1.getT_smallint(), is(t2.getT_smallint()));
        assertThat(t1.getT_text(), is(t2.getT_text()));
        assertThat(t1.getT_time(), is(t2.getT_time()));
        assertThat(t1.getT_timestamp(), is(t2.getT_timestamp()));
        assertThat(t1.getT_timeuuid(), is(t2.getT_timeuuid()));
        assertThat(t1.getT_tinyint(), is(t2.getT_tinyint()));
        assertThat(t1.getT_varchar(), is(t2.getT_varchar()));
        assertThat(t1.getT_varint(), is(t2.getT_varint()));
        assertThat(t1.getT_tuple(), equalTo(t2.getT_tuple()));
        assertThat(t1.getT_uuid(), is(t2.getT_uuid()));
        if (t1.getT_type1() != null || t2.getT_type1() != null) {
            assertThat(t1.getT_type1().getT_varchar(), is(t2.getT_type1().getT_varchar()));
            assertThat(t1.getT_type1().getT_int(), is(t2.getT_type1().getT_int()));
        }
    }

    public static Types getDefaultTypes(Cluster cluster) throws UnknownHostException {
        Types t = new Types();
        t.setId(1);
        t.setT_ascii("ascii");
        t.setT_bigint(2L);
        t.setT_blob(ByteBuffer.wrap("{\"blob1\": \"blob2\"}".getBytes()));
        t.setT_boolean(true);
        t.setT_date(LocalDate.of(2016, 7, 26));
        t.setT_decimal(BigDecimal.valueOf(3));
        t.setT_double(4.0);
        t.setT_float(5.0F);
        t.setT_inet(InetAddress.getByName("1.2.3.4"));
        t.setT_int(6);
        t.setT_list_int(Arrays.asList(101, 102));
        t.setT_list_text(Arrays.asList("list1", "list2"));
        Map<Integer, String> map = new HashMap<>();
        map.put(201, "map1");
        map.put(202, "map2");
        t.setT_map(map);
        t.setT_set_int(new HashSet<>(Arrays.asList(301, 302)));
        t.setT_set_text(new HashSet<>(Arrays.asList("set1", "set2")));
        t.setT_smallint((short) 7);
        t.setT_text("text");
        t.setT_time(LocalTime.of(10, 11, 12));
        // TODO zone?
        t.setT_timestamp(Instant.parse("2016-07-26T08:11:12Z"));
        t.setT_timeuuid(UUID.fromString("e12229de-5eda-11e6-a6a7-cc3d827302bc"));
        t.setT_tinyint((byte) 8);
        t.setT_varchar("varchar");
        t.setT_varint(BigInteger.valueOf(9));
        TupleType tupleType = cluster.getMetadata().newTupleType(DataType.cint(), DataType.text(), DataType.cfloat());
        TupleValue tuple = tupleType.newValue();
        tuple.setInt(0, 401);
        tuple.setString(1, "tuple");
        tuple.setFloat(2, 402.0f);
        t.setT_tuple(tuple);
        t.setT_uuid(UUID.fromString("a9c9b8ae-4911-4bf4-a855-4b5f634d0664"));
        Type1 t1 = new Type1("varchar", 501);
        t.setT_type1(t1);
        return t;
    }

    public static Types getNewTypes(Cluster cluster, int id) throws UnknownHostException {
        Types t = new Types();
        t.setId(id);
        t.setT_ascii("ascii");
        t.setT_bigint(Long.MAX_VALUE);
        t.setT_blob(ByteBuffer.wrap("{\"blob1\": \"blob2\"}".getBytes()));
        t.setT_boolean(true);
        t.setT_date(LocalDate.now());
        t.setT_decimal(BigDecimal.ONE);
        t.setT_double(Double.MAX_VALUE);
        t.setT_float(Float.MAX_VALUE);
        t.setT_inet(InetAddress.getByName("1.2.3.4"));
        t.setT_int(Integer.MAX_VALUE);
        t.setT_list_int(Arrays.asList(101, 102));
        t.setT_list_text(Arrays.asList("value1", "value2"));
        Map<Integer, String> map = new HashMap<>();
        map.put(201, "map1");
        map.put(202, "map2");
        t.setT_map(map);
        t.setT_set_int(new HashSet<>(Arrays.asList(301, 302)));
        t.setT_set_text(new HashSet<>(Arrays.asList("set1", "set2")));
        t.setT_smallint(Short.MAX_VALUE);
        t.setT_text("ěščřžýáíéúů");
        t.setT_time(LocalTime.now());
        t.setT_timestamp(Instant.now());
        t.setT_timeuuid(UUID.fromString("e12229de-5eda-11e6-a6a7-cc3d827302bc"));
        t.setT_tinyint(Byte.MAX_VALUE);
        t.setT_varchar("ěščřžýáíéúů");
        t.setT_varint(BigInteger.ONE);
        TupleType tupleType = cluster.getMetadata().newTupleType(DataType.cint(), DataType.text(), DataType.cfloat());
        TupleValue tuple = tupleType.newValue();
        tuple.setInt(0, 401);
        tuple.setString(1, "tuple");
        tuple.setFloat(2, 402.0f);
        t.setT_tuple(tuple);
        t.setT_uuid(UUID.fromString("a9c9b8ae-4911-4bf4-a855-4b5f634d0664"));
        t.setT_type1(new Type1("varchar", 501));
        return t;
    }

    public static Types getNativeNullTypes(int id) {
        Types t = new Types();
        t.setId(id);
        t.setT_bigint(0L);
        t.setT_boolean(false);
        t.setT_double(0.0);
        t.setT_float(0.0F);
        t.setT_int(0);
        t.setT_list_int(Arrays.asList());
        t.setT_list_text(Arrays.asList());
        t.setT_map(new HashMap<>());
        t.setT_set_int(new HashSet<>());
        t.setT_set_text(new HashSet<>());
        t.setT_smallint((short) 0);
        t.setT_tinyint((byte) 0);
        return t;
    }

    public static Types getNullTypes(int id) {
        Types t = new Types();
        t.setId(id);
        t.setT_list_int(Arrays.asList());
        t.setT_list_text(Arrays.asList());
        t.setT_map(new HashMap<>());
        t.setT_set_int(new HashSet<>());
        t.setT_set_text(new HashSet<>());
        return t;
    }
}
