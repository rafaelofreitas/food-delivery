insert into tb_kitchen(kitchen_id, name) values (1, 'Tailandesa');
insert into tb_kitchen(kitchen_id, name) values (2, 'Indiana');

insert into tb_restaurant(restaurant_id, name, freigh_rate, kitchen_id) values (1, 'Thai Gourmet', 10, 1);
insert into tb_restaurant(restaurant_id, name, freigh_rate, kitchen_id) values (2, 'Thai Delivery', 9.50, 1);
insert into tb_restaurant(restaurant_id, name, freigh_rate, kitchen_id) values (3, 'Tuk Tuk Comida Indiana', 15, 2);

insert into tb_state (state_id, name) values (1, 'Minas Gerais');
insert into tb_state (state_id, name) values (2, 'São Paulo');
insert into tb_state (state_id, name) values (3, 'Ceará');

insert into tb_city(city_id, name, state_id) values (1, 'Uberlândia', 1);
insert into tb_city(city_id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into tb_city(city_id, name, state_id) values (3, 'São Paulo', 2);
insert into tb_city(city_id, name, state_id) values (4, 'Campinas', 2);
insert into tb_city(city_id, name, state_id) values (5, 'Fortaleza', 3);

insert into tb_payment(payment_id, description) values (1, 'Cartão de crédito');
insert into tb_payment(payment_id, description) values (2, 'Cartão de débito');
insert into tb_payment(payment_id, description) values (3, 'Dinheiro');

insert into tb_permission(permission_id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into tb_permission(permission_id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into tb_restaurant_payment(restaurant_id, payment_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);
