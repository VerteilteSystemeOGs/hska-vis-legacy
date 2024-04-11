USE user_service

insert into `role` (`role_level`, `role_type`) values(0, 'admin');
insert into `role` (`role_level`, `role_type`) values(1, 'user');

insert into `customer` (`name`, `lastname`, `password`, `username`, `role_id`) values('admin', 'admin', 'admin', 'admin', 1);
