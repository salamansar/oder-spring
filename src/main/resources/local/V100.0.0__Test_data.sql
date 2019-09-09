insert into oder_user (id, login, password, last_name, first_name, middle_name) values (next value for seq_user_id, 'akovlyashenko', '$2a$10$PtNuQ5Sv63aTXpov/8TZBOxb/VcAp4PoPh/pL6bRCLMg9NC5hze/y', 'Ковляшенко', 'Антон', 'Сергеевич');
insert into oder_user (id, login, password, last_name, first_name) values (next value for seq_user_id, 'salamansar', '$2a$10$FYoLaoOL6jofI6ODlF5SieA8wE91G1/grqs7IBbqAfaYxxhGRG3iq', 'Salamansar', 'Tiglapatasar');

insert into income (id, income_date, amount, document_number, description, id_user) values (next value for seq_income_id,'2018-01-19', 170000, 102, 'Доход от ТБН', 1);
insert into income (id, income_date, amount, document_number, id_user) values (next value for seq_income_id, '2018-02-26', 215000, 256, 1);
insert into income (id, income_date, amount, document_number, id_user) values (next value for seq_income_id, '2019-01-30', 150000, 12, 1);

insert into fixed_payment(id, payment_year, payment_value, id_category) values (next value for seq_fixed_payment_id, 2018, 26545, 1);
insert into fixed_payment(id, payment_year, payment_value, id_category) values (next value for seq_fixed_payment_id, 2018, 5840, 2);