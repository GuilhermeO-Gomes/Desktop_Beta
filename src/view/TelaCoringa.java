package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class TelaCoringa extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelaCoringa() {
        
        setTitle("Sistema Bibliotecário");
        // Ele adiciona a tela login dentro dele, na mesma ideia de identação do html
        add(new TelaLogin());

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
      addWindowListener(new WindowAdapter() {
    	  public void windowClosing(WindowEvent e) {
    		  fechar_sistema();
    	  }
      });
    }
	public void fechar_sistema() {
		dispose();
	}
	 public void trocarTela (JPanel nova_tela) {
  	   setContentPane(nova_tela);
  	   
  	   pack();
  	   setLocationRelativeTo(null);
  	   
  	   revalidate();
  	   repaint();
     };
}