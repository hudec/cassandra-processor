CREATE KEYSPACE IF NOT EXISTS simple WITH replication = {'class': 'SimpleStrategy', 'replication_factor':  '1' };

USE simple; 

CREATE TYPE IF NOT EXISTS type1 (
t_varchar varchar,
t_int int
);

CREATE TABLE IF NOT EXISTS types (
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

CREATE TABLE IF NOT EXISTS clustering_types (
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

CREATE TABLE IF NOT EXISTS enum_types (
id int, 
t_int int, 
t_varchar varchar, 
PRIMARY KEY(id));
