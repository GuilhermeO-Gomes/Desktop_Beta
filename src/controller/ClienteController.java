package controller;

import dao.ClienteDAO;
import model.Cliente;
import util.Validador;
import view.TelaCliente;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;

import util.SenhaUtil;

/** Faz a ponte entre os componentes da tela, o objeto Cliente e o DAO. */
public class ClienteController {

  private final TelaCliente tela;
  private final ClienteDAO dao;

  public ClienteController(TelaCliente tela) {
    this.tela = tela;
    this.dao = new ClienteDAO();
  }

  public void novo() {
    tela.limparFormulario();
    tela.definirEdicao(true);
    tela.getTxtNome().requestFocus();
  }

  public void limpar() {
    tela.limparFormulario();
    tela.definirEdicao(true);
  }

  public void carregarTabela() {
    consultar(false);
  }

  public void buscar() {
    consultar(true);
  }

  public void salvar() {
    try {
      validar();
      Cliente cliente = lerFormulario();
      String senha = new String(tela.getTxtSenha().getPassword());
      if (senha.length() > 0) {
        String salt = SenhaUtil.gerarSalt();
        cliente.setSenha_salt(salt);
        cliente.setSenha_hash(SenhaUtil.gerarHash(senha, salt));
      }
      if (cliente.getId() == 0) {
        dao.salvar(cliente);
        mensagem(
          "Cliente cadastrado com sucesso.",
          JOptionPane.INFORMATION_MESSAGE
        );
      } else {
        dao.atualizar(cliente);
        mensagem(
          "Cliente atualizado com sucesso.",
          JOptionPane.INFORMATION_MESSAGE
        );
      }
      limpar();
      carregarTabela();
    } catch (Exception e) {
      erro(e);
    }
  }

  public void excluir() {
    int id = tela.getIdSelecionado();
    if (id == 0) {
      mensagem("Selecione um cliente.", JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (
      JOptionPane.showConfirmDialog(
        tela,
        "Deseja inativar este cliente?",
        "Confirmacao",
        JOptionPane.YES_NO_OPTION
      ) == JOptionPane.YES_OPTION
    ) {
      try {
        dao.excluir(id);
        mensagem("Cliente inativado.", JOptionPane.INFORMATION_MESSAGE);
        limpar();
        carregarTabela();
      } catch (SQLException e) {
        erro(e);
      }
    }
  }

  public void selecionarLinha() {
    int linha = tela.getTabela().getSelectedRow();
    if (linha < 0) return;
    int id = ((Integer) tela.getTabela().getValueAt(linha, 0)).intValue();
    try {
      Cliente c = dao.buscarPorId(id);
      if (c != null) tela.mostrarCliente(c);
    } catch (SQLException e) {
      erro(e);
    }
  }
//Aqui
  private Cliente lerFormulario() {
    Cliente cliente = new Cliente();
    cliente.setId(tela.getIdSelecionado());
    cliente.setNome(tela.getTxtNome().getText().trim());
    cliente.setCpf(tela.getTxtCpf().getText().trim());
    cliente.setEmail(tela.getTxtEmail().getText().trim());
    //Formatar a data de nascimento
    String txtData = tela.getTxtData_nascimento().getText().trim();
    
    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate data_convertida = LocalDate.parse(txtData, formatador);
    java.sql.Date dataSql = java.sql.Date.valueOf(data_convertida);
    cliente.setData_nascimento(dataSql);
    cliente.setAtivo(tela.getChkAtivo().isSelected());
    return cliente;
  }

  private void validar() {
    if (
      Validador.vazio(tela.getTxtNome().getText())
    ) throw new IllegalArgumentException("Informe o nome.");
    if (
      Validador.vazio(tela.getTxtCpf().getText())
    ) throw new IllegalArgumentException("Informe o CPF.");
    if (
      !Validador.emailValido(tela.getTxtEmail().getText())
    ) throw new IllegalArgumentException("Informe um e-mail valido.");
  }

  private void consultar(boolean filtro) {
    try {
      List<Cliente> l = filtro
        ? dao.buscarPorNome(tela.getTxtPesquisa().getText())
        : dao.listarTodos();
      tela.preencherTabela(l);
    } catch (SQLException e) {
      erro(e);
    }
  }

  private void mensagem(String m, int tipo) {
    JOptionPane.showMessageDialog(tela, m, "Biblioteca", tipo);
  }

  private void erro(Exception e) {
    e.printStackTrace();
    mensagem(
      "Nao foi possivel concluir a operacao.\n" + e.getMessage(),
      JOptionPane.ERROR_MESSAGE
    );
  }
}
