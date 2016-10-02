alter table transaction drop column data_wy
update transaction set opis = concat(sygnatura, ' ', opis) where data between (date '2014-01-01') and (date '2014-02-20')
update transaction set plik_sciezka = null where id > 3999 and plik_sciezka is not null and plik_sciezka not like concat('%',id,'.pdf')
