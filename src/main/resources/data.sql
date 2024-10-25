insert into categories (id, name) values
                                      (gen_random_uuid(), 'Zdrowie'),
                                      (gen_random_uuid(), 'Zwierzęta'),
                                      (gen_random_uuid(), 'Turystyka'),
                                      (gen_random_uuid(), 'Uroda i Styl'),
                                      (gen_random_uuid(), 'Kultura'),
                                      (gen_random_uuid(), 'Edukacja'),
                                      (gen_random_uuid(), 'Finanse'),
                                      (gen_random_uuid(), 'Motoryzacja'),
                                      (gen_random_uuid(), 'Polityka');

insert into questions(id, name, category_id)
values (gen_random_uuid(), 'Gdzie w lutym na ferie ?', (select id from categories where name = 'Turystyka')),
       (gen_random_uuid(), 'Gdzie pojechać na piękny weekend w Polsce?', (select id from categories where name = 'Turystyka'));
