update transaction set id_new = (select id_new from adres where firma like 'Centrowet%' and ulica like 'Dąb%' limit 1) where id = 5527
