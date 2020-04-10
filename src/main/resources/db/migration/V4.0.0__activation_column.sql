alter table tb_restaurant add active tinyint(1) not null;
update tb_restaurant set active = true;