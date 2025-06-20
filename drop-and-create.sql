drop table if exists beer cascade ;
drop table if exists customer cascade ;
create table beer (beer_style tinyint not null check (beer_style between 0 and 9), price numeric(38,2) not null, quantity_on_hand integer, version integer, created_date timestamp(6), update_date timestamp(6), id varchar(36) not null, beer_name varchar(50) not null, upc varchar(255) not null, primary key (id));
create table customer (version integer, created_date timestamp(6), update_date timestamp(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id));

    drop table if exists beer;

    drop table if exists customer;

    create table beer (
        beer_style tinyint not null check (beer_style between 0 and 9),
        price decimal(38,2) not null,
        quantity_on_hand integer,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        beer_name varchar(50) not null,
        upc varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        email varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;
