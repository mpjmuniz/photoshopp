package photochopp;

/**
 *
 * @author Marcelo Pablo
 */
public class FiltroSobelHorizontal extends Filtro{
	public FiltroSobelHorizontal(){
		this.altura = 3;
		this.largura = 3;
		this.valores = new double[][]{	{-1.0, 0.0, 1.0}, 
										{-2.0, 0.0, 2.0}, 
										{-1.0, 0.0, 1.0}};
	}
}
