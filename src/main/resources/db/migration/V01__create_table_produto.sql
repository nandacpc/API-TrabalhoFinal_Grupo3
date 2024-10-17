CREATE TABLE produtos
(
    id serial primary key,
    nome varchar(100),
    descricao varchar(200),
    qnt_estoque integer,
    data_cadastro date,
    valor_unitario double precision,
    imagem varchar(255)    
)