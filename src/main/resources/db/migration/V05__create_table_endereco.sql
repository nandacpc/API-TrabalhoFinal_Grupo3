CREATE TABLE enderecos
(
    id_endereco serial primary key,
    cep varchar(10) not null,
    rua varchar(100) not null,
    bairro varchar(50) not null,
    cidade varchar(50) not null,
    numero integer not null,
    complemento varchar(20),
    uf varchar(2) not null
)