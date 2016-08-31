package photochopp;

/**
 *
 * @author Marcelo Pablo
 */
public class FiltroSobelVertical extends Filtro{
	public FiltroSobelVertical(){
		this.altura = 3;
		this.largura = 3;
		this.valores = new double[][]{	{-1.0, -2.0, -1.0}, 
										{0.0, 0.0, 0.0}, 
										{1.0, 2.0, 1.0}};
	}
}
