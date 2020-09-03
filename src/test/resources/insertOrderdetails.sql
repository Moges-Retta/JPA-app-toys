insert into orderdetails (orderId,productId,ordered,priceEach) values
((select id from orders where comments='test'),(select id from products where name='test'),0,0);