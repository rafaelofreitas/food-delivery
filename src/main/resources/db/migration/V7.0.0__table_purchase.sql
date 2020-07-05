alter table tb_purchase add purchase_code binary(16) not null after purchase_id;
update tb_purchase set purchase_code = uuid();
alter table tb_purchase add constraint uk_purchase_purchase_code unique (purchase_code);