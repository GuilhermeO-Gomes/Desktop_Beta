package model;

import java.sql.Date;

public class Cliente {

    private int id;
    private String nome;
    private String cpf;
    private Date data_nascimento;
    private String email;
    private String senha;
    
    public Cliente() {
    }

    public Cliente(String nome, String cpf, Date data_nascimento, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.data_nascimento = data_nascimento;
        this.email = email;
        this.senha = senha;
    }

    public Cliente(int id, String nome, String cpf, Date data_nascimento, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.data_nascimento = data_nascimento;
        this.email = email;
        this.senha = senha;
    }

    public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
