--update transaction set userid = (select id from users where login like 'bozena') where 
delete from user_roles;
delete from roles;
insert into roles (id, role) values (1, 'admin'), (2, 'power user');
insert into user_roles (user_id, role_id) select u.id, r.id from users u, roles r where u.login like 'ax' and r.role like 'admin';
insert into user_roles (user_id, role_id) select u.id, r.id from users u, roles r where u.login like 'ax' and r.role like 'power user';
insert into user_roles (user_id, role_id) select u.id, r.id from users u, roles r where u.login like 'dorota' and r.role like 'power user';
insert into user_roles (user_id, role_id) select u.id, r.id from users u, roles r where u.login like 'bozena' and r.role like 'power user';
insert into user_roles (user_id, role_id) select u.id, r.id from users u, roles r where u.login like 'dyrektor' and r.role like 'power user';
commit;
select * from user_roles;
