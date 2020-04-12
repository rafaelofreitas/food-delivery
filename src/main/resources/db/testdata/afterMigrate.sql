set foreign_key_checks = 0;

delete from tb_city;
delete from tb_kitchen;
delete from tb_state;
delete from tb_payment;
delete from tb_group;
delete from tb_group_permission;
delete from tb_permission;
delete from tb_product;
delete from tb_restaurant;
delete from tb_restaurant_payment;
delete from tb_user;
delete from tb_user_group;

set foreign_key_checks = 1;

alter table tb_city auto_increment = 1;
alter table tb_kitchen auto_increment = 1;
alter table tb_state auto_increment = 1;
alter table tb_payment auto_increment = 1;
alter table tb_group auto_increment = 1;
alter table tb_permission auto_increment = 1;
alter table tb_product auto_increment = 1;
alter table tb_restaurant auto_increment = 1;
alter table tb_user auto_increment = 1;

insert into tb_kitchen(kitchen_id, name) values (1, 'Tailandesa');
insert into tb_kitchen(kitchen_id, name) values (2, 'Indiana');
insert into tb_kitchen(kitchen_id, name) values (3, 'Argentina');
insert into tb_kitchen(kitchen_id, name) values (4, 'Brasileira');

insert into tb_state (state_id, name) values (1, 'Minas Gerais');
insert into tb_state (state_id, name) values (2, 'São Paulo');
insert into tb_state (state_id, name) values (3, 'Ceará');

insert into tb_city(city_id, name, state_id) values (1, 'Uberlândia', 1);
insert into tb_city(city_id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into tb_city(city_id, name, state_id) values (3, 'São Paulo', 2);
insert into tb_city(city_id, name, state_id) values (4, 'Campinas', 2);
insert into tb_city(city_id, name, state_id) values (5, 'Fortaleza', 3);

insert into tb_restaurant(restaurant_id, name, freigh_rate, date_register, update_date, kitchen_id, city_id, address_zip_code, address_neighborhood, address_number, address_public_place, active) values (1, 'Thai Gourmet', 10, utc_timestamp , utc_timestamp , 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', true);
insert into tb_restaurant(restaurant_id, name, freigh_rate, date_register, update_date, kitchen_id, active) values (2, 'Thai Delivery', 9.50, utc_timestamp , utc_timestamp , 1, true);
insert into tb_restaurant(restaurant_id, name, freigh_rate, date_register, update_date, kitchen_id, active) values (3, 'Tuk Tuk Comida Indiana', 15, utc_timestamp , utc_timestamp , 2, true);
insert into tb_restaurant(restaurant_id, name, freigh_rate, date_register, update_date, kitchen_id, active) values (4, 'Java Steakhouse', 12, utc_timestamp , utc_timestamp , 3, true);
insert into tb_restaurant(restaurant_id, name, freigh_rate, date_register, update_date, kitchen_id, active) values (5, 'Lanchonete do Tio Sam', 11, utc_timestamp , utc_timestamp , 4, true);
insert into tb_restaurant(restaurant_id, name, freigh_rate, date_register, update_date, kitchen_id, active) values (6, 'Bar da Maria', 6, utc_timestamp , utc_timestamp , 4, true);

insert into tb_payment(payment_id, description) values (1, 'Cartão de crédito');
insert into tb_payment(payment_id, description) values (2, 'Cartão de débito');
insert into tb_payment(payment_id, description) values (3, 'Dinheiro');

insert into tb_permission(permission_id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into tb_permission(permission_id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into tb_restaurant_payment(restaurant_id, payment_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into tb_product(name, description, price, active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into tb_product(name, description, price, active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into tb_product(name, description, price, active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into tb_product(name, description, price, active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into tb_product(name, description, price, active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into tb_product(name, description, price, active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into tb_product(name, description, price, active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into tb_product(name, description, price, active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into tb_product(name, description, price, active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into tb_group (name) values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

insert into tb_user (user_id, name, email, password, date_register) values
(1, 'João da Silva', 'joao.ger@fooddelivery.com', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@fooddelivery.com', '123', utc_timestamp),
(3, 'José Souza', 'jose.aux@fooddelivery.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@fooddelivery.com', '123', utc_timestamp);
