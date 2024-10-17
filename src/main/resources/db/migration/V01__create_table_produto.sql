CREATE TABLE produtos
(
    id_produto serial primary key,
    nome varchar(100),
    descricao varchar(200),
    qnt_estoque integer,
    data_cadastro date,
    valor_unitario double precision,
    imagem varchar(255),
    constraint fk_produto_categoria foreign key (id_categoria)
    references categorias (id_categoria)   
)