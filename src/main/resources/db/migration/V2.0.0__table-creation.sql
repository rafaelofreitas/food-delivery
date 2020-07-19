#Creating Group table
create table tb_group
(
    group_id integer      not null auto_increment,
    name     varchar(255) not null,

    primary key (group_id)
) engine = InnoDB default charset = utf8;

#Creating Group Permission table
create table tb_group_permission
(
    group_id      integer not null,
    permission_id integer not null,

    primary key (group_id, permission_id),
    constraint fk_group_permission_group_id foreign key (group_id) references tb_group (group_id),
    constraint fk_group_permission_permission_id foreign key (permission_id) references tb_permission (permission_id)
) engine = InnoDB default charset = utf8;

#Creating User table
create table tb_user
(
    user_id       integer      not null auto_increment,
    date_register datetime     not null,
    name          varchar(80)  not null,
    email         varchar(255) not null,
    password      varchar(255) not null,

    primary key (user_id)
) engine = InnoDB default charset = utf8;

#Creating User Group table
create table tb_user_group
(
    user_id  integer not null,
    group_id integer not null,

    primary key (user_id, group_id),
    constraint fk_user_group_user_id foreign key (user_id) references tb_user (user_id),
    constraint fk_user_group_group_id foreign key (group_id) references tb_group (group_id)
) engine = InnoDB default charset = utf8;