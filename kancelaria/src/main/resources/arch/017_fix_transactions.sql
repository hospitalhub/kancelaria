update transaction set typ_pisma = 'PISMO' where typ_pisma is null;
update transaction set typ_pisma = 'PISMO' where typ_pisma like 'BRAK' or typ_pisma like 'UZUPEŁN.';
