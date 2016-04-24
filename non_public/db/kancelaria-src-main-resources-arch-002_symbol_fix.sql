update transaction set symbol = 'ANEKS' where symbol like 'ANEKSY';
update transaction set symbol = 'PISMO' where symbol like '' or symbol like 'BB' or symbol like 'BBB' or symbol like 'BBZAM PUBL' or symbol like 'BRAK' or symbol like 'DOW%' or symbol like 'DPD' or symbol like 'NN' or symbol like 'WZ';
update transaction set symbol = 'FAKTURA' where symbol like 'FKTURA' or symbol like 'FAKTUIRA' or symbol like 'F-RA KOPIA';
update transaction set symbol = 'FAX' where symbol like 'FAKS'
update transaction set symbol = 'PRZETARG' where symbol like 'OFERTA';
update transaction set symbol = 'UZUPEŁN.' where symbol like 'UZUPEŁNIENIE';
