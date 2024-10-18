CREATE TABLE categorias
(
    id_categoria serial primary key,
    nome varchar(50) not null,
    descricao varchar(200) not null
)