CREATE TYPE type1 (
t_varchar varchar,
t_int int
);

CREATE TABLE types (
id int, 
t_ascii ascii, 
t_bigint bigint, 
t_blob blob, 
t_boolean boolean, 
t_date date, 
t_decimal decimal, 
t_double double, 
t_float float, 
t_inet inet, 
t_int int, 
t_list_int list<int>,
t_list_text list<text>,
t_map map<int,text>, 
t_set_int set<int>, 
t_set_text set<text>, 
t_smallint smallint, 
t_text text, 
t_time time, 
t_timestamp timestamp, 
t_timeuuid timeuuid, 
t_tinyint tinyint, 
t_tuple tuple<int, text, float>,
t_varchar varchar, 
t_varint varint, 
t_uuid uuid, 
t_type1 frozen<type1>,
t_phone varchar,
PRIMARY KEY(id));

CREATE TABLE clustering_types (
id int, 
t_ascii ascii, 
t_bigint bigint, 
t_boolean boolean, 
t_date date, 
t_decimal decimal, 
t_double double, 
t_float float, 
t_int int, 
t_smallint smallint, 
t_text text, 
t_time time, 
t_timestamp timestamp, 
t_timeuuid timeuuid, 
t_tinyint tinyint, 
t_varchar varchar, 
t_varint varint, 
t_uuid uuid, 
PRIMARY KEY((id), t_ascii, t_bigint, t_boolean, t_date, t_decimal, t_double, t_float, t_int, t_smallint, t_text, t_time, t_timestamp, t_timeuuid, t_tinyint, t_varchar, t_varint, t_uuid));

CREATE TABLE enum_types (
id int, 
t_int int, 
t_varchar varchar, 
PRIMARY KEY(id));

/*
t_counter counter, 
*/

INSERT INTO types(
id
,t_ascii 
,t_bigint 
,t_blob 
,t_boolean
,t_date
,t_decimal
,t_double
,t_float
,t_inet
,t_int
,t_list_int
,t_list_text
,t_map
,t_set_int
,t_set_text
,t_smallint
,t_text
,t_time
,t_timestamp
,t_timeuuid
,t_tinyint
,t_tuple
,t_varchar
,t_varint 
,t_uuid
,t_type1
,t_phone
) values(
1
,'ascii'
,2
,textAsBlob('{"blob1": "blob2"}')
,true
,'2016-07-26'
,3
,4
,5
,'1.2.3.4'
,6
,[101,102]
,['list1', 'list2']
,{201: 'map1', 202: 'map2'}
,{301, 302}
,{'set1', 'set2'}
,7
,'text'
,'10:11:12'
,'2016-07-26 10:11:12'
,e12229de-5eda-11e6-a6a7-cc3d827302bc
,8
,(401, 'tuple', 402)
,'varchar'
,9
,a9c9b8ae-4911-4bf4-a855-4b5f634d0664
,{t_varchar: 'varchar', t_int: 501}
,'001-002-0003'
);


INSERT INTO types(
id
) values(
2
);

INSERT INTO clustering_types(
id
,t_ascii 
,t_bigint 
,t_boolean
,t_date
,t_decimal
,t_double
,t_float
,t_int
,t_smallint
,t_text
,t_time
,t_timestamp
,t_timeuuid
,t_tinyint
,t_varchar
,t_varint 
,t_uuid
) values(
1
,'ascii'
,2
,true
,'2016-07-26'
,3
,4
,5
,6
,7
,'text'
,'10:11:12'
,'2016-07-26 10:11:12'
,e12229de-5eda-11e6-a6a7-cc3d827302bc
,8
,'varchar'
,9
,a9c9b8ae-4911-4bf4-a855-4b5f634d0664
);

INSERT INTO enum_types(
id
,t_int
,t_varchar
) values(
1
,1
,'e1'
);

INSERT INTO enum_types(
id
,t_int
,t_varchar
) values(
2
,2
,'e2'
);

INSERT INTO enum_types(
id
,t_int
,t_varchar
) values(
3
,3
,'e3'
);
