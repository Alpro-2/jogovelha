public class JogoVelha {
	public enum Jogador { X, O, VAZIO };
	public enum Vencedor { X, O, NENHUM, EMPATE };
	private Jogador[][] tab;
	private Jogador atual;
	private int totalJogadas;

	public JogoVelha() {
		tab = new Jogador[3][3];
		reinicia();
	}

	public void reinicia() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				tab[i][j] = Jogador.VAZIO;
		atual = Jogador.X;
		totalJogadas = 0;
	}
	
	public boolean jogar(int i, int j) {
		if (tab[i][j] != Jogador.VAZIO)
			return false; // posição já ocupada!
		tab[i][j] = atual;
		totalJogadas++; // conta mais uma jogada
		// Troca para outro jogador
		if(atual == Jogador.X)
			atual = Jogador.O;
		else atual = Jogador.X;
		return true;
	}

	public Jogador get(int i, int j) {
		return tab[i][j];
	}
	
	public Jogador getAtual() {
		return atual;
	}

	private Vencedor converteVencedor(Jogador jog) {
		if(jog == Jogador.X)
			return Vencedor.X;
		return Vencedor.O;
	}
	
	public Vencedor getVencedor() {
		for(int i=0; i<3; i++) {
			if(tab[i][0] !=Jogador.VAZIO
				&& tab[i][0] == tab[i][1] && tab[i][1] == tab[i][2]) {
				return converteVencedor(tab[i][0]);
			}
			if(tab[0][i] != Jogador.VAZIO
				&& tab[0][i] == tab[1][i] && tab[1][i] == tab[2][i]) {
				return converteVencedor(tab[0][i]);
			}
		}
		if(tab[0][0] != Jogador.VAZIO
			&& tab[0][0] == tab[1][1] && tab[1][1] == tab[2][2]) {
			return converteVencedor(tab[0][0]);
		}
		if(totalJogadas != 9)
			return Vencedor.NENHUM; // jogo ainda não acabou
		return Vencedor.EMPATE;
	}

	public String toString() {
		String aux = "";
		for (Jogador[]linha : tab) {
			for (Jogador celula : linha) {
				aux += celula + " ";
			}
			aux += "\n";
		}
		return aux;
	}
}
