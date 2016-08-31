package photochopp;

/**
 *
 * @author Marcelo Pablo
 */
public class FiltroLinear extends Filtro{
	public FiltroLinear(int ordem, double[] vals){
		this.altura = ordem;
		this.largura = ordem;
		this.valores = new double[ordem][ordem];
		for (int i = 0; i < ordem; i++) {
			for (int j = 0; j < ordem; j++) {
				this.valores[i][j] = vals[j + i * ordem];
			}
		}
	}
}
