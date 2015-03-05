delete from user_sdo_geom_metadata
where table_name='BUILDING';
delete from user_sdo_geom_metadata
where table_name='FIREHYDRANT';
DROP INDEX index_building;
drop index index_firehydrant;
drop table building;
drop table firehydrant;

