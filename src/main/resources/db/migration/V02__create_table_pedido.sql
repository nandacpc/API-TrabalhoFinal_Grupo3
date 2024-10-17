CREATE TABLE pedidos
(
    id_pedido serial primary key,
    data_pedido date not null,
    data_entrega date not null,
    data_envio date not null,
    status_pedido varchar(50) not null,
    valor_total numeric not null,
    constraint fk_pedido_cliente foreign key (id_cliente)
    references clientes (id_cliente) 
)