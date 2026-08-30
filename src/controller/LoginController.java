package controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import dao.ClienteDAO;
import model.Cliente;
import util.Validador;
import view.TelaCliente;
import view.TelaLogin;
import view.TelaCoringa;

public class LoginController {

	private final TelaLogin tela;
	private final ClienteDAO dao = new ClienteDAO();
	
	
	public LoginController(TelaLogin t) {
		tela = t;
	}
	//Ver validador e como será a tela
	public void entrar() {
		String email = tela.getTxtEmail().getText().trim();
		String senha = new String(tela.getTxtSenha().getPassword());
		if (Validador.vazio(email) || Validador.vazio(senha)) {
			JOptionPane.showMessageDialog(tela, "Informe email e senha.", "Login", JOptionPane.WARNING_MESSAGE); 
			return;
		}
	{}
	try {
		Cliente cliente = dao.autenticar(email, senha);
		if (cliente == null) { JOptionPane.showMessageDialog(tela,
				"Email ou senha invalidos, ou email inativo.",
				"Email",
				JOptionPane.ERROR_MESSAGE
			);
		tela.getTxtSenha().setText("");
		tela.getTxtSenha().requestFocus();
		return;
			
		}
		TelaCoringa coringa = (TelaCoringa) SwingUtilities.getWindowAncestor(tela);
		coringa.trocarTela(new TelaCliente());
		//Vai ter que ocultar de alguma forma o JPanel Login
		
	} catch (SQLException e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(tela, "Falha ao acessar o banco. \n" + e.getMessage(),
				"Email", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void sair() {
		if (JOptionPane.showConfirmDialog(tela,
				"Deseja encerrar o sistema?",
				"Sair",
				JOptionPane.YES_NO_OPTION
				) == JOptionPane.YES_OPTION
				) System.exit(0);
	}
}
