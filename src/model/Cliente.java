package model;

import java.sql.Date;

public class Cliente {

    private int id;
    private String nome;
    private String cpf;
    private Date data_nascimento;
    private String email;
    private String senha_salt;
    private String senha_hash;
    private boolean ativo;
    
    public Cliente() {
    	this.ativo = true;
    }

    public Cliente(String nome, String cpf, String email, Date data_nascimento) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.data_nascimento = data_nascimento;
      }

    public String getSenha_salt() {
		return senha_salt;
	}



	public void setSenha_salt(String senha_salt) {
		this.senha_salt = senha_salt;
	}



	public String getSenha_hash() {
		return senha_hash;
	}



	public void setSenha_hash(String senha_hash) {
		this.senha_hash = senha_hash;
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

    public boolean isAtivo() {
    	return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
      }
    public void setEmail(String email) {
        this.email = email;
    }
}
