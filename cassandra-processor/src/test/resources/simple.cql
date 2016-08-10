CREATE TABLE types (
id int, 
t_ascii ascii, 
t_bigint bigint, 
t_boolean boolean, 
t_date date, 
t_decimal decimal, 
t_double double, 
t_float float, 
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
t_tinyint tinyint, 
t_varchar varchar, 
t_varint varint, 
PRIMARY KEY(id));

/*
t_blob blob, 
t_counter counter, 
t_inet inet, 
t_timeuuid timeuuid, 
t_tuple tuple, 
t_uuid uuid, 
*/

INSERT INTO types(
id
,t_ascii 
,t_bigint 
,t_boolean
,t_date
,t_decimal
,t_double
,t_float
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
,t_tinyint
,t_varchar
,t_varint 
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
,[101,102]
,['list1', 'list2']
,{201 : 'map1', 202 : 'map2'}
,{301, 302}
,{'set1', 'set2'}
,7
,'text'
,'10:11:12'
,'2016-07-26 10:11:12'
,8
,'varchar'
,9
);


INSERT INTO types(
id
) values(
2
);
