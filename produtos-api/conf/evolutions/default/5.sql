# --- !Ups

alter table usuario add column verificado bit(1);

# --- !Downs

alter table usuario drop column verificado;

