delete from adres a where (select count(*) from transaction t where t.id_new = a.id_new) = 0;
