TRUNCATE types;
TRUNCATE clustering_types;
TRUNCATE enum_types;

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
