CREATE TABLE clientes
(
    id_cliente serial primary key,
    email varchar(100),
    nome_completo varchar(100),
    cpf varchar(15),
    telefone varchar(15),
    data_nascimento date  
)