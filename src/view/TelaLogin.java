package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.LoginController;

public class TelaLogin extends JPanel {
	private final JTextField email = new JTextField(50);
	private final JPasswordField senha = new JPasswordField(20);
	private final LoginController controller;
//Ver as alterações, uma vez que esse JPanel n vai ter as mesmas funções disponiveis. Onde tiver "//" é onde tenho que ver
	//Rever essa TelaLogin abaixo inteira
	public TelaLogin() {
		super("Acesso - Sistema Bibliotecário");
		controller = new LoginController(this);
		montar_tela();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE)
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}
	
	private void montar_tela() {
		//A janela em si, acima do body
		JPanel principal = new JPanel(new BorderLayout(10,10));
		principal.setBorder(BorderFactory.createEmptyBorder(18,22,18,22));
		JLabel titulo = new JLabel ("SISTEMA DE GESTÃO DE BIBLIOTECA", SwingConstants.CENTER);
		titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
		principal.add(titulo, BorderLayout.NORTH);
		//O f é o "<body>" do projeto
		JPanel f = new JPanel(new GridBagLayout());
		//O g seria uma div que organiza todo a disposição e layout da tela
		GridBagConstraints g = new GridBagConstraints();
		//Define margem de cada elemento
		g.insets = new Insets(6,5,6,5);
		g.anchor = GridBagConstraints.WEST;
		//gridx define a linha que o elemento estará, o gridy define a coluna
		g.gridx = 0;
		g.gridy = 0;
		f.add(new JLabel("Email"), g);
		g.gridx = 1;
		f.add(email, g);
		g.gridx = 0;
		g.gridy = 1;
		f.add(new JLabel("Senha:"), g);
		g.gridx = 1;
		f.add(senha, g);
		principal.add(f);
		//FlowLayout serve como um administrador de layouut
		JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton entrar = new JButton("Entrar"), sair = new JButton("Sair");
		b.add(entrar);
		b.add(sair);
		principal.add(b, BorderLayout.SOUTH);
		setContentPane(principal);
		//Pressiona Enter pra executar o mesmo evento do botão
		getRootPane().setDefaultButton(entrar);
		entrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.entrar();
			}
		});
		
		sair.addActionListener(new ActionListener() {
			public void actionPerfomed(ActionEvent e) {
				controller.sair();
			}
		}) ;
		addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						controller.sair();
					}
				});
		
	}

	public JTextField getTxtEmail() {
		return email;
	}
	
	public JPasswordField getTxtSenha() {
		return senha;
	}
}
