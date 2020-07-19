create table tb_product_photo
(
    product_product_id integer      not null,
    file_name        varchar(150) not null,
    description      varchar(150),
    content_type     varchar(80)  not null,
    size             integer      not null,

    primary key (product_product_id),
    constraint fk_product_photo_product foreign key(product_product_id) references tb_product(product_id)
) engine = InnoDB  default charset = utf8;
