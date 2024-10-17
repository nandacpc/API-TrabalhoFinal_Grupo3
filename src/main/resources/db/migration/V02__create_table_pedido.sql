CREATE TABLE pedidos
(
    id_pedido serial primary key,
    data_pedido date,
    data_entrega date,
    data_envio date,
    status_pedido varchar(50),
    valor_total double precision   
)