CREATE TABLE itens_pedido
(
    id_item_pedido serial primary key,
    quantidade integer,
    preco_venda double precision,
    percentual_desconto double precision,
    valor_bruto double precision,
    valor_liquido double precision
    constraint fk_itens_pedido_pedido foreign key (id_pedido)
    references pedidos (id_pedido)
    constraint fk_itens_pedido_produto foreign key (id_produto)
    references produtos (id_produto) 
)