#Creating Purchase table
create table tb_purchase (
	purchase_id integer not null auto_increment,
	subtotal decimal(10,2) not null,
	shipping_fee decimal(10,2) not null,
	amount decimal(10,2) not null,
    
	city_id bigint(20) not null,
	address_complement varchar(60),
	address_neighborhood varchar(60), 
	address_number varchar(20), 
	address_public_place varchar(100), 
	address_zip_code varchar(9),
    
   	order_status varchar(10) not null,
	creation_date datetime not null,
	confirmation_date datetime null,
	cancellation_date datetime null,
	delivery_date datetime null,
	restaurant_id integer not null,
	user_id integer not null,
	payment_id integer not null,

	primary key (purchase_id),
	constraint fk_purchase_restaurant foreign key (restaurant_id) references tb_restaurant (restaurant_id),
	constraint fk_purchase_user foreign key (user_id) references tb_user (user_id),
	constraint fk_purchase_payment foreign key (payment_id) references tb_payment (payment_id)
) engine=InnoDB default charset=utf8;

#Creating Order Item table
create table tb_order_item (
    order_item_id integer not null auto_increment,
    amount integer(6) not null,
    unit_price decimal(10,2) not null,
    total_price decimal(10,2) not null,
    note varchar(255) null,
    purchase_id integer not null,
    product_id integer not null,
    
    primary key (order_item_id),
    unique key uk_order_item_product (purchase_id, product_id),

    constraint fk_order_item_purchase foreign key (purchase_id) references tb_purchase (purchase_id),
    constraint fk_order_item_product foreign key (product_id) references tb_product (product_id)
) engine=InnoDB default charset=utf8;