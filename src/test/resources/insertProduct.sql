insert into products (name,scale,description,inStock,inOrder,price,productlineId,version) values
('test','test','test',1,1,1,(select id from productlines where name='test'),0);