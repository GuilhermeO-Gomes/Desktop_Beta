package view;

import javax.swing.JFrame;

public class TelaCoringa extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelaCoringa() {
        
        setTitle("Cadastro de Clientes");
        
        add(new TelaCliente());

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }}