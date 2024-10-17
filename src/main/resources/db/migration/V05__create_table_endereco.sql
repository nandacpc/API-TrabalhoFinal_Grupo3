CREATE TABLE enderecos
(
    id_endereco serial primary key,
    cep varchar(10),
    rua varchar(100),
    bairro varchar(50),
    cidade varchar(50),
    numero integer,
    complemento varchar(20),
    uf varchar(2)
)