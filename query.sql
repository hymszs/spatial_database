delete from user_sdo_geom_metadata
where table_name='FIREHYDRANT';
delete from user_sdo_geom_metadata
where table_name='BUILDING';
select * from user_sdo_geom_metadata;








select b.bid,b.bname from building b
where SDO_INSIDE(b.shape,
 SDO_GEOMETRY(2003, NULL, NULL,
 SDO_ELEM_INFO_ARRAY(1,1003,3),
 SDO_ORDINATE_ARRAY(0,0, 1000,1000))
 ) = 'TRUE';
 
select f.fid from building b,firehydrant f
where b.bname='OHE' and SDO_WITHIN_DISTANCE(b.shape,
f.shape,'distance=100'
 ) = 'TRUE'; 
 
select f.fid from building b,firehydrant f
where b.bid=1 and SDO_NN(f.shape,b.shape,'sdo_num_res=5')='TRUE';

//DEM0
select bname from building b
where b.bname like 'S%' and b.onfire=1;


select b.bname,f.fid
from building b,firehydrant f
where SDO_NN(f.shape,b.shape,'sdo_num_res=5')='TRUE' and b.onfire=1

select f.fid,count(b.bname)
from building b,firehydrant f
where SDO_WITHIN_DISTANCE(f.shape,b.shape,'distance = 120')='TRUE'
group by f.fid
having count(b.bname)>= all(select count(b.bname)
from building b,firehydrant f
where SDO_WITHIN_DISTANCE(f.shape,b.shape,'distance = 120')='TRUE'
group by f.fid)


select f.fid,count(f.fid)
from building b,firehydrant f
where SDO_NN(f.shape,b.shape,'sdo_num_res=1')='TRUE' 
group by f.fid
order by count(f.fid) desc;
 


select * from firehydrant





 