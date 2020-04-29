create table order0(
    id       bigint(11) not null comment '主键ID' primary key,
    user_id  bigint(11) null comment '用户ID',
    order_id bigint(11) null comment '订单ID'
);

create table order1(
    id       bigint(11) not null comment '主键ID' primary key,
    user_id  bigint(11) null comment '用户ID',
    order_id bigint(11) null comment '订单ID'
);