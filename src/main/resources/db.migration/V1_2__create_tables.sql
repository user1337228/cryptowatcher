use cryptowatch;

create table cryptocurrency
(
    apis_id int         not null,
    name    varchar(45) not null,
    id      int auto_increment
        primary key,
    constraint cryptocurrency_apis_id_uindex
        unique (apis_id),
    constraint cryptocurrency_id_uindex
        unique (id),
    constraint symbol_UNIQUE
        unique (name)
)
    auto_increment = 0;

create table crypto_dynamics
(
    id           int auto_increment
        primary key,
    crypto_id    int    not null,
    crypto_price double null,
    date         date   not null,
    constraint crypto_dynamics_id_uindex
        unique (id),
    constraint crypto_dynamics_cryptocurrency_apis_id_fk
        foreign key (crypto_id) references cryptocurrency (apis_id)
)
    auto_increment = 0;

create table hibernate_sequence
(
    next_val bigint null
);

create table user
(
    id        int auto_increment
        primary key,
    user_name varchar(45) not null,
    email     varchar(45) not null,
    constraint id_UNIQUE
        unique (id),
    constraint user_name_UNIQUE
        unique (user_name)
)
    auto_increment = 0;

create table tracked_cryptos
(
    id             int auto_increment
        primary key,
    user_id        int    not null,
    crypto_id      int    not null,
    register_price double not null,
    constraint tracked_cryptos_id_uindex
        unique (id),
    constraint tracked_cryptos_cryptocurrency_apis_id_fk
        foreign key (crypto_id) references cryptocurrency (apis_id),
    constraint tracked_cryptos_user_id_fk
        foreign key (user_id) references user (id)
);

