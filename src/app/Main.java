package app;

import javax.swing.SwingUtilities;

import view.TelaCoringa;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TelaCoringa tela = new TelaCoringa();
                tela.setVisible(true);
            }
        });
    }
}
