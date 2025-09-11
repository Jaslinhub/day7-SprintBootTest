create table if not exists employee (
    id int auto_increment primary key,
    company_id int null,
    gender varchar(255) null,
    name varchar(255) null,
    salary double null,
    status int default 1,
    foreign key (company_id) references company(id)
    );