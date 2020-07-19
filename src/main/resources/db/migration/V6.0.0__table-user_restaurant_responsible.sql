create table tb_user_restaurant_responsible
(
    restaurant_id integer not null,
    user_id       integer not null,

    primary key (restaurant_id, user_id),
    constraint fk_user_restaurant_responsible_restaurant foreign key (restaurant_id) references tb_restaurant (restaurant_id),
    constraint fk_user_restaurant_responsible_user foreign key (user_id) references tb_user (user_id)
) engine = InnoDB default charset = utf8;