# --- !Ups

create table usuario (
  id                            integer auto_increment not null,
  nome                        varchar(255),
  constraint pk_produto primary key (id)
);


# --- !Downs

drop table if exists usuario;

