package photochopp;

/**
 *
 * @author Marcelo Pablo
 */
public class FiltroGaussiano extends Filtro{
	public FiltroGaussiano(){
		this.altura = 5;
		this.largura = 5;
		this.valores = new double[][]{	{0.0352, 0.0387, 0.0398, 0.0387, 0.0352}, 
										{0.0387, 0.0425, 0.0438, 0.0425, 0.0387}, 
										{0.0398, 0.0438, 0.0452, 0.0438, 0.0398},
										{0.0387, 0.0425, 0.0438, 0.0425, 0.0387},
										{0.0352, 0.0387, 0.0398, 0.0387, 0.0352}};
	}
}
