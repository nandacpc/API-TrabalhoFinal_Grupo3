CREATE TABLE produtos
(
    id_produto serial primary key,
    nome varchar(100) not null,
    descricao varchar(200),
    qnt_estoque int not null,
    data_cadastro date not null,
    valor_unitario numeric(38,2) not null,
    imagem varchar(255),
    id_categoria int not null,
    constraint fk_produto_categoria foreign key (id_categoria)
    references categorias (id_categoria)   
)