CREATE TABLE TB_ARGOS_CLIENTES (
                                   id_cliente BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   celular VARCHAR(20),
                                   cpf VARCHAR(11),
                                   email VARCHAR(50),
                                   idade INT,
                                   nome VARCHAR(50)
);

CREATE TABLE TB_ARGOS_PRODUTOS (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   descricao VARCHAR(255),
                                   imagem VARCHAR(255),
                                   nome VARCHAR(50),
                                   preco DECIMAL(10, 2),
                                   quantidade INT
);

CREATE SEQUENCE SEQ_ARGOS_PERFIL START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_ARGOS_USUARIO START WITH 1 INCREMENT BY 1;
