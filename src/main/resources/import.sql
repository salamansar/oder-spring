insert into user (login, password, last_name, first_name, middle_name) values ('salamansar', '100500', 'Ковляшенко', 'Антон', 'Сергеевич');

insert into income (income_date, amount, document_number, description, user_id) values ('2018-01-19', 170000, 102, 'Доход от ТБН', 1);
insert into income (income_date, amount, document_number, user_id) values ('2018-02-26', 215000, 256, 1);
insert into income (income_date, amount, document_number, user_id) values ('2019-01-30', 150000, 12, 1);

insert into fixed_payment(payment_year, payment_value, id_category) values (2018, 26545, 1);
insert into fixed_payment(payment_year, payment_value, id_category) values (2018, 5840, 2);