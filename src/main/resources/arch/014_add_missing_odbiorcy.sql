update transaction set odbiorca = 'LKA' where odbiorca like '' and opis like '% APTEKA%'
update transaction set odbiorca = 'L4' where odbiorca like '' and opis like '% DRUK%4%'
update transaction set odbiorca = 'EE' where odbiorca like '' and opis like '%EE%'
update transaction set odbiorca = 'EE' where odbiorca like '' and opis like '%NOTA O%'
update transaction set odbiorca = 'EE' where odbiorca like '' and opis like '%WEZWANIE%'
update transaction set odbiorca = 'EE' where odbiorca like '' and opis like '%FAKT%KOR%'
update transaction set odbiorca = 'EE' where odbiorca like '' and opis like '%POT%SALD%'
update transaction set odbiorca = 'EL' where odbiorca like '' and opis like '% EL%'
update transaction set odbiorca = 'EZ' where odbiorca like '' and opis like '% EZ%'
update transaction set odbiorca = 'NE' where odbiorca like '' and opis like '% NE %'
update transaction set odbiorca = 'NB' where odbiorca like '' and opis like '% NB%'
update transaction set odbiorca = 'NC' where odbiorca like '' and opis like '% NC%'
update transaction set odbiorca = 'NO' where odbiorca like '' and opis like '% NO %'
update transaction set odbiorca = 'NO3' where odbiorca like '' and opis like '%NO-3%'
update transaction set odbiorca = 'NP' where odbiorca like '' and opis like '% NP%'
update transaction set odbiorca = 'NO2' where odbiorca like '' and opis like '%RCH%'
update transaction set odbiorca = 'NO2' where odbiorca like '' and opis like '%RUCH CH%'
update transaction set odbiorca = 'NO2' where odbiorca like '' and opis like '%R CH%'
update transaction set odbiorca = 'NO2' where odbiorca like '' and opis like '%RC H%'
update transaction set odbiorca = 'EZ' where odbiorca like '' and opis like '%ZAM PUBL%'
update transaction set odbiorca = 'LKZDL' where odbiorca like '' and opis like '%ZDL%'
update transaction set odbiorca = 'TRA' where odbiorca like '' and opis like '% TS%'
update transaction set odbiorca = 'TR' where odbiorca like '' and opis like '% TR %'
update transaction set odbiorca = 'TP' where odbiorca like '' and opis like '% TP%'
update transaction set odbiorca = 'TR' where odbiorca like '' and opis like '% TU%'
update transaction set odbiorca = 'NPP' where odbiorca like '' and opis like '%PŁACE%'
update transaction set odbiorca = 'NP' where odbiorca like '' and opis like '%KADRY%'
update transaction set odbiorca = 'NPP' where odbiorca like '' and opis like '%WSZZ%'
update transaction set odbiorca = 'DN' where odbiorca like '' and opis like '%CZENIA%DN%'
update transaction set odbiorca = 'NI' where odbiorca like '' and opis like '%INFORMATYK%'
update transaction set odbiorca = 'POGD' where odbiorca like '' and opis like '%GASTROENTER%DZIEC%'
update transaction set odbiorca = 'LKPN' where odbiorca like '' and opis like '%NOWORODKA%'
update transaction set odbiorca = 'LKPN' where odbiorca like '' and opis like '%NOWRODKA%'
update transaction set odbiorca = 'LKPN' where odbiorca like '' and opis like '%NOWORDKA%'
update transaction set odbiorca = 'LKSOR' where odbiorca like '' and opis like '% SOR%'
update transaction set odbiorca = 'LKZDO' where odbiorca like '' and opis like '%RTG%'
update transaction set odbiorca = 'LKZ' where odbiorca like '' and opis like '%O/ZAKA%'
update transaction set odbiorca = 'LPSD' where odbiorca like '' and opis like '%PORADNIA DERMATOL%'
update transaction set odbiorca = 'LPSDPT' where odbiorca like '' and opis like '%PORADNIA%ODZWIERZ%'
update transaction set odbiorca = 'LKP' where odbiorca like '' and opis like '%PSYCHIATRYCZNY%'
update transaction set odbiorca = 'NS' where odbiorca like '' and opis like '%PIEL%ROD%'
update transaction set odbiorca = 'LKP' where odbiorca like '' and opis like '%PSYCHIATRYCZNY%'
update transaction set odbiorca = 'LKZMK' where odbiorca like '' and opis like '%MIKROBIOL%'
update transaction set odbiorca = 'LKCHI' where odbiorca like '' and opis like '%O/CHIR%NACZY%'
update transaction set odbiorca = 'LKCHSZ' where odbiorca like '' and opis like '%O/CHIR SZCZ%'
update transaction set odbiorca = 'LKWI' where odbiorca like '' and opis like '%O/CHOR%WEW I%'
update transaction set odbiorca = 'LKCHDZ' where odbiorca like '' and opis like '%O/CHOR DZIEC%'
update transaction set odbiorca = 'LKWII' where odbiorca like '' and opis like '%O/CHOR WEW II%'
update transaction set odbiorca = 'LKAI' where odbiorca like '' and opis like '%O/IN%TER%'
update transaction set odbiorca = 'LKNR' where odbiorca like '' and opis like '%O/NEUROCH%'
update transaction set odbiorca = 'LKO' where odbiorca like '' and opis like '%O/OKUL%'
update transaction set odbiorca = 'LKUO' where odbiorca like '' and opis like '%O/ORTOP%'
update transaction set odbiorca = 'LKPG' where odbiorca like '' and opis like '%O/POŁ%%'
update transaction set odbiorca = 'LKR' where odbiorca like '' and opis like '%O/REUMATOL%'
update transaction set odbiorca = 'LKWII' where odbiorca like '' and opis like '%O/WEW II%'
update transaction set odbiorca = 'LKP' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/PSYCHIATRYCZNY')
update transaction set odbiorca = 'LKN' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/NEUROLOGICZNY')
update transaction set odbiorca = 'LKRH' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/REHABILITACYJNY')
update transaction set odbiorca = 'LKAI' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/ANESTEZJOLOGII INTENSYWNEJ TERAPII')
update transaction set odbiorca = 'LKCHI' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/CHIR I  W/M')
update transaction set odbiorca = 'LKCHI' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/CHIR.NACZYNIOWEJ')
update transaction set odbiorca = 'LKCHD' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/CHIRURGII DZIECIĘCEJ')
update transaction set odbiorca = 'LKCHI' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/CHIRURGII OGÓLNEJ I NACZYNIOWEJ')
update transaction set odbiorca = 'LKCDZ' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/CHORÓB DZIECIĘCYCH%')
update transaction set odbiorca = 'LKWI' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/CHORÓB WEWNĘTRZNYCH I GASTROENTEROLOGII%')
update transaction set odbiorca = 'LKPG' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/GINEKOLOGICZNY%')
update transaction set odbiorca = 'LKK' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/KARDIOLOGICZNY%')
update transaction set odbiorca = 'LKNR' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/NEUROCHIRURGICZNY%')
update transaction set odbiorca = 'LKNF' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/Nefrologiczny wm.%')
update transaction set odbiorca = 'LKO' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/OKULISTYCZNY%')
update transaction set odbiorca = 'LKOT' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/Otolaryngologiczny wm.%')
update transaction set odbiorca = 'LKPN' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/PAT I INT.TER.NOWOR%')
update transaction set odbiorca = 'LKPG' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/POŁOŻNICZO-GINEKOLOGICZNY%')
update transaction set odbiorca = 'LKSOR' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/PRZYJĘĆ -POZNAŃSKA%')
update transaction set odbiorca = 'LKR' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/REUMATOLOGICZNY%')
update transaction set odbiorca = 'LKUO' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/URAZOWO-ORTOPEDYCZNY%')
update transaction set odbiorca = 'LKU' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/UROLOGICZNY%')
update transaction set odbiorca = 'LKWII' where odbiorca like '' and opis like '%INF%' and id_new = (select id_new from adres where firma like 'O/WEW II%')
