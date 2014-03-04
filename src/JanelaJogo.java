import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

public class JanelaJogo extends JFrame {

	private JButton[][] grade = new JButton[3][3];
	private final Action acaoBotao = new SwingAction();
	private final Action acaoNovoJogo = new SwingAction_1();
	private final Action acaoSair = new SwingAction_2();

	private JogoVelha jogo;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaJogo frame = new JanelaJogo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JanelaJogo() {

		jogo = new JogoVelha();

		setBounds(100, 100, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);

		JMenuItem mntmNovoJogo = new JMenuItem("Novo jogo");
		mntmNovoJogo.setSelected(true);
		mntmNovoJogo.setAction(acaoNovoJogo);
		mntmNovoJogo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.META_MASK));
		mnArquivo.add(mntmNovoJogo);

		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.setAction(acaoSair);
		mntmSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.META_MASK));
		mnArquivo.add(mntmSair);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel painelStatus = new JPanel();
		getContentPane().add(painelStatus, BorderLayout.SOUTH);

		lblStatus = new JLabel("Jogador: " + jogo.getAtual());
		painelStatus.add(lblStatus);

		JPanel painelJogo = new JPanel();
		getContentPane().add(painelJogo, BorderLayout.CENTER);
		painelJogo.setLayout(new GridLayout(3, 3, 0, 0));

		Font fonte = new Font("Arial", Font.BOLD, 48);
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				grade[i][j] = new JButton();
				grade[i][j].setAction(acaoBotao);
				grade[i][j].setHideActionText(true);
				grade[i][j].setFont(fonte);
				grade[i][j].setText(i + "," + j);
				grade[i][j].setFocusable(false);
				painelJogo.add(grade[i][j]);
			}
	}

	private void reiniciaJogo() {
		jogo.reinicia();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				grade[i][j].setText(" ");
				grade[i][j].setEnabled(true);
			}
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, null);
			putValue(SHORT_DESCRIPTION, "Faz jogada");
		}

		public void actionPerformed(ActionEvent e) {
			JButton but = (JButton) e.getSource();
			but.setText(jogo.getAtual().toString());
			but.setEnabled(false);
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					if (grade[i][j] == e.getSource()) {
						jogo.jogar(i, j);
						lblStatus.setText("Jogador: " + jogo.getAtual());
						verificaVencedor();
						return;
					}
				}
		}

		private void verificaVencedor() {
			JogoVelha.Vencedor vencedor = jogo.getVencedor();
			if(vencedor == JogoVelha.Vencedor.NENHUM)
				return;
			if(vencedor == JogoVelha.Vencedor.EMPATE)
				JOptionPane.showMessageDialog(null, "O jogo EMPATOU!");
			else
				JOptionPane.showMessageDialog(null, "O jogador "+jogo.getVencedor()+" venceu!");
			reiniciaJogo();
		}
	}

	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Novo jogo");
			putValue(SHORT_DESCRIPTION, "Reinicia o jogo");
		}

		public void actionPerformed(ActionEvent e) {
			reiniciaJogo();
		}
	}

	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "Sair");
			putValue(SHORT_DESCRIPTION, "Finaliza o programa");
		}

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}
