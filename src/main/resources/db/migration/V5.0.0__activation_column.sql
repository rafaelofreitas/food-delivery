alter table tb_restaurant add open tinyint(1) not null;

update tb_restaurant set open = false;