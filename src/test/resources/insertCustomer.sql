insert into customers (name,streetAndNumber,city,countryId,version) values
('test','test','test',(select id from countries where name ='test'),1);