CREATE TABLE itens_pedido
(
    id_item_pedido serial primary key,
    quantidade int not null,
    preco_venda numeric not null,
    percentual_desconto numeric,
    valor_bruto numeric not null,
    valor_liquido numeric not null,
    id_pedido int not null,
    id_produto int not null,
    constraint fk_itens_pedido_pedido foreign key (id_pedido)
    references pedidos (id_pedido)
    constraint fk_itens_pedido_produto foreign key (id_produto)
    references produtos (id_produto) 
)