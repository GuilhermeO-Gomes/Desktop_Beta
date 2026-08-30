CREATE DATABASE Desktop_BetaBD;
USE Desktop_BetaBD;

CREATE TABLE IF NOT EXISTS cliente (
id INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
cpf CHAR(14) NOT NULL,
data_nascimento DATE NOT NULL,
email VARCHAR(100) NOT NULL,
senha_hash CHAR(64) NOT NULL,
senha_salt CHAR(32) NOT NULL,
ativo TINYINT(1) NOT NULL DEFAULT 1
);

INSERT INTO cliente
(nome, cpf, data_nascimento, email, senha_hash, senha_salt, ativo)
VALUES
(
    'Cliente Teste',
    '123.456.789-00',
    '2000-01-01',
    'teste@biblioteca.com',
    SHA2(CONCAT('a1b2c3d4e5f60718293a4b5c6d7e8f90', '123456'), 256),
    'a1b2c3d4e5f60718293a4b5c6d7e8f90',
    1
);