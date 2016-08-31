package photochopp;

import java.awt.image.BufferedImage;

/**
 *
 * @author Marcelo Pablo
 */

public class Operacoes {

	final static int cheio = 0xFF;

	private static int obterAzul(int rgb) {
		return (rgb) & 0xFF;
	}

	private static int obterVerde(int rgb) {
		return (rgb >> 8) & 0xFF;
	}

	private static int obterVermelho(int rgb) {
		return (rgb >> 16) & 0xFF;
	}
	
	private static int corrigirCanal(int valor){
		if(valor < 0) return 0;
		else if(valor > 255) return 255;
		else return valor;
	}
	
	private static double calculo(int hor, int vert) {
		return Math.sqrt(Math.pow(obterVermelho(hor), 2) + Math.pow(obterVermelho(vert),2));
	}

	public static BufferedImage complemento(BufferedImage img) {
		BufferedImage saida = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		int vemelhoCp, verdeCp, azulCp, pixelAtual, empacotado;

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				//obtendo o pixel "empacotado"
				pixelAtual = img.getRGB(i, j);

				//"desempacotando" e convertendo na mesma linha
				azulCp = cheio - obterAzul(pixelAtual);
				verdeCp = cheio - obterVerde(pixelAtual);
				vemelhoCp = cheio - obterVermelho(pixelAtual);

				//"reempacotando" os valores em um único valor
				empacotado = vemelhoCp << 16 | verdeCp << 8 | azulCp;

				//inserindo na imagem
				saida.setRGB(i, j, empacotado);
			}
		}

		return saida;
	}

	public static BufferedImage rgbParaCinza(BufferedImage img) {
		BufferedImage saida = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		int cinza, vemelhoIn, verdeIn, azulIn, pixelAtual, empacotado;

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				//obtendo o pixel "empacotado"
				pixelAtual = img.getRGB(i, j);

				//"desempacotando" e preparando
				vemelhoIn = (int) (0.3 * obterVermelho(pixelAtual));
				verdeIn = (int) (0.59 * obterVerde(pixelAtual));
				azulIn = (int) (0.11 * obterAzul(pixelAtual));

				//resolvendo intensidade de cinza
				cinza = verdeIn + vemelhoIn + azulIn;

				//"empacotando" valor do "pixel Output", substituindo cada canal de cor pelo cinza
				empacotado = cinza << 16 | cinza << 8 | cinza;

				//inserindo na imagem
				saida.setRGB(i, j, empacotado);
			}
		}

		return saida;
	}

	public static BufferedImage convolucao(BufferedImage img, Filtro filt) {
		BufferedImage saida = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		//variáveis para verificação da imagem, usa-se o primeiro pixel para verificar se é cinza ou colorida
		int amostra = img.getRGB(0, 0),
				amostraAzul = obterAzul(amostra),
				amostraVerde = obterVerde(amostra),
				amostraVermelho = obterVermelho(amostra),
				//inicialização dos comprimentos do filtro em uso, das diversas variáveis de uso do pixel em operação
				u = filt.obterLargura() / 2, v = filt.obterAltura() / 2,
				pixelAtual, compCinza,
				operando;

		if (amostraAzul == amostraVerde && amostraVerde == amostraVermelho) {
			//se é cinza
			for (int i = u; i < img.getWidth() - u; i++) {
				for (int j = v; j < img.getHeight() - v; j++) {
					saida.setRGB(i, j, 0);
					//iterar o filtro
					for (int k = -u; k <= u; k++) {
						for (int l = -v; l <= v; l++) {
							pixelAtual = saida.getRGB(i, j);
							operando = img.getRGB(i - k, j - l);

							//decompondo em cada componente de cor e operando
							compCinza = obterVermelho(pixelAtual);
							compCinza += filt.obterValor(k + u, l + v) * obterVermelho(operando);
							
							//verificação de limites
							compCinza = corrigirCanal(compCinza);
							
							//reempacotando
							pixelAtual = compCinza << 16 | compCinza << 8 | compCinza;

							//escrevendo
							saida.setRGB(i, j, pixelAtual);
						}
					}
				}
			}

		} else {
			//se é colorido
			
			int vermelhoCp, azulCp, verdeCp;
			
			for (int i = u; i < img.getWidth() - u; i++) {
				for (int j = v; j < img.getHeight() - v; j++) {
					saida.setRGB(i, j, 0);
					//iterar o filtro
					for (int k = -u; k <= u; k++) {
						for (int l = -v; l <= v; l++) {
							pixelAtual = saida.getRGB(i, j);
							operando = img.getRGB(i - k, j - l);

							//decompondo em cada componente de cor e operando
							vermelhoCp = obterVermelho(pixelAtual);
							vermelhoCp += (int) (filt.obterValor(k + u, l + v) * obterVermelho(operando));

							azulCp = obterAzul(pixelAtual);
							azulCp += (int) (filt.obterValor(k + u, l + v) * obterAzul(operando));

							verdeCp = obterVerde(pixelAtual);
							verdeCp += (int) (filt.obterValor(k + u, l + v) * obterVerde(operando));

							//reempacotando
							pixelAtual = vermelhoCp << 16 | verdeCp << 8 | azulCp;

							//escrevendo
							saida.setRGB(i, j, pixelAtual);
						}
					}
				}
			}
		}

		return saida;
	}

	public static BufferedImage deteccaoDeBordas(BufferedImage img, double limiar) {
		BufferedImage saida = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		//Parte 1: convolução do filtro horizontal
		FiltroSobelHorizontal filth = new FiltroSobelHorizontal();
		BufferedImage horizontal = Operacoes.convolucao(img, filth);

		//Parte 2: convolução do filtro vertical
		FiltroSobelVertical filtv = new FiltroSobelVertical();
		BufferedImage vertical = Operacoes.convolucao(img, filtv);

		//Parte 3: Cálculo
		int pixel;
		for (int i = 0; i < saida.getWidth(); i++) {
			for (int j = 0; j < saida.getHeight(); j++) {
				pixel = saida.getRGB(i, j);
				double calc = calculo(horizontal.getRGB(i, j), vertical.getRGB(i, j));
				if (calc >= limiar) {
					pixel = 0x00FFFFFF;
				}else {
					pixel = 0x00000000;
				}
				saida.setRGB(i, j, pixel);
			}
		}
		
		return saida;
	}
	
	public static BufferedImage filtroLinear(BufferedImage img, int ordem, double[] vals){
		BufferedImage saida = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		FiltroLinear filt = new FiltroLinear(ordem, vals);
		saida = convolucao(img, filt);
		return saida;
	}
}
