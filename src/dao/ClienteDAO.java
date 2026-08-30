package dao;

import model.Cliente;
import util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import util.SenhaUtil;

/**
 * DAO (Data Access Object) concentra os comandos SQL de Cliente.
 * Connection representa a conexao, PreparedStatement envia SQL parametrizado
 * e ResultSet permite percorrer as linhas devolvidas por um SELECT.
 */
public class ClienteDAO {

	public Cliente autenticar(String email, String senha) throws SQLException {
	    Connection c = null;
	    PreparedStatement p = null;
	    ResultSet r = null;
	    try {
	      c = Conexao.abrir();
	      p = c.prepareStatement("SELECT * FROM cliente WHERE email=? AND ativo=1");
	      p.setString(1, email.trim());
	      r = p.executeQuery();
	      if (r.next()) {
	        Cliente cliente = mapear(r);
	        if (
	          SenhaUtil.conferir(senha, cliente.getSenha_salt(), cliente.getSenha_hash())
	        ) return cliente;
	      }
	      return null;
	    } finally {
	      Conexao.fechar(c, p, r);
	    }
	  }

	
	
  public void salvar(Cliente cliente) throws SQLException {
    String sql =
      "INSERT INTO cliente (nome, cpf, email, data_nascimento, senha_hash, senha_salt, ativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    Connection conexao = null;
    PreparedStatement stmt = null;
    try {
      conexao = Conexao.abrir();
      stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      preencher(stmt, cliente, false);
      stmt.executeUpdate();
      ResultSet chaves = null;
      try {
        chaves = stmt.getGeneratedKeys();
        if (chaves.next()) cliente.setId(chaves.getInt(1));
      } finally {
        Conexao.fechar(chaves);
      }
    } finally {
      Conexao.fechar(conexao, stmt, null);
    }
  }

  public void atualizar(Cliente cliente) throws SQLException {
    String sql_senha =
      "UPDATE cliente SET nome=?, cpf=?, email=?, data_nascimento = ?, senha_salt=?, senha_hash = ?, ativo = ? WHERE id=?";
    String sql_sem_senha =
      "UPDATE cliente SET nome=?, cpf=?, email=?, data_nascimento = ?, ativo = ? WHERE id=?";
    boolean mudarSenha =  cliente.getSenha_hash() != null && cliente.getSenha_hash().length() > 0; 
    Connection c = null;
    PreparedStatement p = null;
    try {
      c = Conexao.abrir();
      //Caso o usuário tenha mudado a senha, vai dar update no banco. Se ele n mudou, n puxa a senha
      p = c.prepareStatement(mudarSenha ? sql_senha : sql_sem_senha);
      if (mudarSenha) preencher(p, cliente, true); else {
    	  p.setString(1, cliente.getNome().trim());
          p.setString(2, cliente.getCpf().trim());
          p.setString(3, cliente.getEmail());
          p.setDate(4, cliente.getData_nascimento());
          p.setBoolean(5, cliente.isAtivo());
          p.setInt(6, cliente.getId());
      }
      if (p.executeUpdate() == 0) throw new SQLException(
        "Cliente nao encontrado."
      );
    } finally {
      Conexao.fechar(c, p, null);
    }
  }

  /** Preserva o historico: excluir um cliente significa inativa-lo. */
  public void excluir(int id) throws SQLException {
    alterarAtivo(id, false);
  }

  public void alterarAtivo(int id, boolean ativo) throws SQLException {
    Connection conexao = null;
    PreparedStatement stmt = null;
    try {
      conexao = Conexao.abrir();
      stmt = conexao.prepareStatement("UPDATE cliente SET ativo=? WHERE id=?");
      stmt.setBoolean(1, ativo);
      stmt.setInt(2, id);
      if (stmt.executeUpdate() == 0) throw new SQLException(
        "Cliente nao encontrado."
      );
    } finally {
      Conexao.fechar(conexao, stmt, null);
    }
  }

  public Cliente buscarPorId(int id) throws SQLException {
    List<Cliente> lista = consultar(
      "SELECT * FROM cliente WHERE id=?",
      Integer.valueOf(id)
    );
    return lista.isEmpty() ? null : lista.get(0);
  }

  public List<Cliente> buscarPorNome(String nome) throws SQLException {
    return consultar(
      "SELECT * FROM cliente WHERE TRIM(nome) LIKE ? ORDER BY nome",
      "%" + nome.trim() + "%"
    );
  }

  public List<Cliente> listarTodos() throws SQLException {
    return consultar("SELECT * FROM cliente ORDER BY nome", null);
  }

  public List<Cliente> listarAtivos() throws SQLException {
    return consultar("SELECT * FROM cliente WHERE ativo=1 ORDER BY nome", null);
  }

  private List<Cliente> consultar(String sql, Object parametro)
    throws SQLException {
    List<Cliente> lista = new ArrayList<Cliente>();
    Connection conexao = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      conexao = Conexao.abrir();
      stmt = conexao.prepareStatement(sql);
      if (parametro instanceof Integer) stmt.setInt(
        1,
        ((Integer) parametro).intValue()
      );
      if (parametro instanceof String) stmt.setString(1, (String) parametro);
      rs = stmt.executeQuery();
      while (rs.next()) lista.add(mapear(rs));
      return lista;
    } finally {
      Conexao.fechar(conexao, stmt, rs);
    }
  }

  private Cliente mapear(ResultSet rs) throws SQLException {
    Cliente cliente = new Cliente();
    cliente.setId(rs.getInt("id"));
    cliente.setNome(rs.getString("nome"));
    cliente.setCpf(rs.getString("cpf"));
    cliente.setEmail(rs.getString("email"));
    cliente.setData_nascimento(rs.getDate("data_nascimento"));
    cliente.setSenha_hash(rs.getString("senha_hash"));
    cliente.setSenha_salt(rs.getString("senha_salt"));
    cliente.setAtivo(rs.getBoolean("ativo"));
    return cliente;
  }

  private void preencher(PreparedStatement stmt, Cliente cliente, boolean atualizacao)
    throws SQLException {
	  stmt.setString(1, cliente.getNome().trim());
	    stmt.setString(2, cliente.getCpf().trim());
	    stmt.setString(3, cliente.getEmail());
	    stmt.setDate(4, cliente.getData_nascimento());
	    stmt.setString(5, cliente.getSenha_hash());
	    stmt.setString(6, cliente.getSenha_salt());
	    if (atualizacao) {
	      stmt.setBoolean(7, cliente.isAtivo());
	      stmt.setInt(8, cliente.getId());
	    } else {
	      stmt.setBoolean(7, cliente.isAtivo());
    } 
  }
}

