CREATE TABLE clientes
(
    id_cliente serial primary key,
    email varchar(100) not null,
    nome_completo varchar(100) not null,
    cpf varchar(15) not null,
    telefone varchar(15) not null,
    data_nascimento date not null,
    constraint fk_cliente_endereco foreign key (id_endereco)
    references enderecos (id_endereco) 
)