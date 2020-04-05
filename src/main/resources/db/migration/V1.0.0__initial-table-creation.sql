#Creating kitchen table
create table tb_kitchen(
  kitchen_id integer not null auto_increment,
  name varchar(60) not null,

  primary key (kitchen_id)
) engine=InnoDB default charset=utf8;

#Creating State table
create table tb_state (
	state_id integer not null auto_increment,
	name varchar(80) not null,
	
	primary key (state_id)
) engine=InnoDB default charset=utf8;

#Creating City table
create table tb_city (
	city_id integer not null auto_increment,
	name varchar(80) not null,
	state_id integer not null,
	
	primary key (city_id),
	constraint fk_city_state_id foreign key (state_id) references tb_state (state_id)
) engine=InnoDB default charset=utf8;

#Creating Restaurant table
create table tb_restaurant (
	restaurant_id integer not null auto_increment, 
	date_register datetime not null, 
	freigh_rate decimal(10,2) not null, 
	name varchar(80) not null, 
	update_date datetime not null, 

	city_id integer, 
	kitchen_id integer not null, 
	
	address_complement varchar(60),
	address_neighborhood varchar(60), 
	address_number varchar(20), 
	address_public_place varchar(100), 
	address_zip_code varchar(9),

	primary key (restaurant_id),
	constraint fk_restaurant_city_id foreign key (city_id) references tb_city (city_id),
	constraint fk_restaurant_kitchen_id foreign key (kitchen_id) references tb_kitchen (kitchen_id)
) engine=InnoDB default charset=utf8;

#Creating Payment table
create table tb_payment (
	payment_id integer not null auto_increment, 
	description varchar(60) not null,
 
	primary key (payment_id)
) engine=InnoDB default charset=utf8;

#Creating Permission table
create table tb_permission (
	permission_id integer not null auto_increment, 
	description varchar(60) not null, 
	name varchar(100) not null, 

	primary key (permission_id)
) engine=InnoDB default charset=utf8;

#Creating Restaurant Payment table
create table tb_restaurant_payment (
	restaurant_id integer not null, 
	payment_id integer not null,
	
	primary key (restaurant_id, payment_id),
	constraint fk_restaurant_payment_restaurant_id foreign key (restaurant_id) references tb_restaurant (restaurant_id),
	constraint fk_restaurant_payment_payment_id foreign key (payment_id) references tb_payment (payment_id)
) engine=InnoDB default charset=utf8;

#Creating Product table
create table tb_product (
	product_id integer not null auto_increment, 
	active tinyint(1) not null, 
	description text not null, 
	name varchar(80) not null, 
	price decimal(10,2) not null, 
	restaurant_id integer, 

	primary key (product_id),
	constraint fk_product_restaurant_id foreign key (restaurant_id) references tb_restaurant (restaurant_id)
) engine=InnoDB default charset=utf8;