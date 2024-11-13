insert into categories (id, name) values
                                      (gen_random_uuid(), 'Zdrowie'),
                                      (gen_random_uuid(), 'Zwierzęta'),
                                      (gen_random_uuid(), 'Turystyka'),
                                      (gen_random_uuid(), 'Uroda i Styl'),
                                      (gen_random_uuid(), 'Kultura'),
                                      (gen_random_uuid(), 'Edukacja'),
                                      (gen_random_uuid(), 'Finanse'),
                                      (gen_random_uuid(), 'Motoryzacja'),
                                      (gen_random_uuid(), 'Prezenty'),
                                      (gen_random_uuid(), 'Zakupy'),
                                      (gen_random_uuid(), 'Biznes'),
                                      (gen_random_uuid(), 'Elektronika'),
                                      (gen_random_uuid(), 'Gry'),
                                      (gen_random_uuid(), 'Kulinaria'),
                                      (gen_random_uuid(), 'Dom i Ogród'),
                                      (gen_random_uuid(), 'Polityka');


insert into questions(id, name, category_id)
values
    (gen_random_uuid(), 'Jak dbać o zdrową dietę?', (select id from categories where name = 'Zdrowie')),
    (gen_random_uuid(), 'Jakie są objawy przeziębienia?', (select id from categories where name = 'Zdrowie')),
    (gen_random_uuid(), 'Co to jest zdrowy tryb życia?', (select id from categories where name = 'Zdrowie'));

insert into questions(id, name, category_id)
values
    (gen_random_uuid(), 'Jakie są najpopularniejsze rasy psów?', (select id from categories where name = 'Zwierzęta')),
    (gen_random_uuid(), 'Jak zadbać o kota w mieszkaniu?', (select id from categories where name = 'Zwierzęta')),
    (gen_random_uuid(), 'Jakie są najlepsze diety dla psów?', (select id from categories where name = 'Zwierzęta'));

insert into questions(id, name, category_id)
values
    (gen_random_uuid(), 'Jakie miejsca odwiedzić w Tatrach?', (select id from categories where name = 'Turystyka')),
    (gen_random_uuid(), 'Gdzie spędzić wakacje nad morzem?', (select id from categories where name = 'Turystyka')),
    (gen_random_uuid(), 'Jakie są najpiękniejsze szlaki turystyczne w Polsce?', (select id from categories where name = 'Turystyka'));

insert into questions(id, name, category_id)
values
    (gen_random_uuid(), 'Jakie kosmetyki są najlepsze dla cery wrażliwej?', (select id from categories where name = 'Uroda i Styl')),
    (gen_random_uuid(), 'Jakie są najnowsze trendy w modzie?', (select id from categories where name = 'Uroda i Styl')),
    (gen_random_uuid(), 'Jak dbać o włosy latem?', (select id from categories where name = 'Uroda i Styl'));

insert into questions(id, name, category_id)
values
    (gen_random_uuid(), 'Jakie książki warto przeczytać w tym roku?', (select id from categories where name = 'Kultura')),
    (gen_random_uuid(), 'Jakie filmy zdobyły Oscary w ostatnich latach?', (select id from categories where name = 'Kultura')),
    (gen_random_uuid(), 'Jakie są najważniejsze wydarzenia kulturalne w Polsce?', (select id from categories where name = 'Kultura'));

insert into questions(id, name, category_id)
values
    (gen_random_uuid(), 'Jakie są najlepsze metody nauki języków obcych?', (select id from categories where name = 'Edukacja')),
    (gen_random_uuid(), 'Jakie kursy online są najbardziej popularne?', (select id from categories where name = 'Edukacja')),
    (gen_random_uuid(), 'Jakie umiejętności są potrzebne w przyszłości?', (select id from categories where name = 'Edukacja'));

insert into questions(id, name, category_id)
values
    (gen_random_uuid(), 'Jak oszczędzać na emeryturę?', (select id from categories where name = 'Finanse')),
    (gen_random_uuid(), 'Jak inwestować w nieruchomości?', (select id from categories where name = 'Finanse')),
    (gen_random_uuid(), 'Jakie są najlepsze sposoby na budżet domowy?', (select id from categories where name = 'Finanse'));

insert into questions(id, name, category_id)
values
    (gen_random_uuid(), 'Jakie są najlepsze auta do miasta?', (select id from categories where name = 'Motoryzacja')),
    (gen_random_uuid(), 'Jak przygotować samochód do zimy?', (select id from categories where name = 'Motoryzacja')),
    (gen_random_uuid(), 'Jakie są najnowsze trendy w motoryzacji?', (select id from categories where name = 'Motoryzacja'));

insert into questions(id, name, category_id)
values
    (gen_random_uuid(), 'Jakie są główne partie polityczne w Polsce?', (select id from categories where name = 'Polityka')),
    (gen_random_uuid(), 'Jakie są najważniejsze wydarzenia polityczne ostatnich lat?', (select id from categories where name = 'Polityka')),
    (gen_random_uuid(), 'Jak wygląda system wyborczy w Polsce?', (select id from categories where name = 'Polityka'));
