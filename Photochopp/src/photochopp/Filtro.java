package photochopp;

/**
 *
 * @author Marcelo Pablo
 */

public class Filtro {
	
	protected int altura;
	protected int largura;
	protected double[][] valores;
	
	public int obterLargura() {
		return this.largura;
	}
	
	public int obterAltura() {
		return this.altura;
	}

	public double obterValor(int x, int y) {
		return valores[x][y];
	}
	
}
