
create table users
(
    id               bigint primary key not null auto_increment,
    name             varchar(255)       not null,
    clan_id          bigint             not null,
    total_added_gold bigint,
    last_added_gold  bigint,
    created          TIMESTAMP          NOT NULL DEFAULT NOW(),
    updated          TIMESTAMP          NOT NULL
);

create table clan
(
    id                  bigint primary key not null auto_increment,
    name                varchar(255)       not null,
    gold                bigint             not null,
    previous_gold_count bigint             not null,
    created             TIMESTAMP          NOT NULL DEFAULT NOW(),
    updated             TIMESTAMP          NOT NULL
);

create table meta_task
(
    id      bigint primary key not null auto_increment,
    status  varchar(255)       not null,
    type    varchar(255)       not null,
    clan_id bigint             not null,
    gold    bigint             not null,
    created TIMESTAMP          NOT NULL DEFAULT NOW(),
    updated TIMESTAMP          NOT NULL
);