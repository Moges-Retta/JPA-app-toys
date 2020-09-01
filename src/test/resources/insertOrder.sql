insert into orders (ordered,required,shipped,comments,customerId,status,version)
values ('2001:1:1','2001:1:1','2001:1:1','test',(select id from customers where name = 'test'),'CANCELLED',1);