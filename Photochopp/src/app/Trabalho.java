package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import photochopp.FiltroGaussiano;
import photochopp.Operacoes;

/**
 *
 * @author Marcelo Pablo
 */
public class Trabalho {

	public static void main(String[] args) throws IOException {
		//entrada do usuário
		Scanner tec = new Scanner(System.in);
		boolean entradaValida = false;
		System.out.println("Seja bem vindo ao Photochopp™.\nDigite uma operação:");
		BufferedImage entrada, saida;
		String input;
		String[] operandos;
		File arqIn, arqOut;

		while (!entradaValida) {
			input = tec.nextLine();
			operandos = input.split(" ");

			//interpretação do comando
			switch (operandos[0]) {

				case "complemento":
					try {
						arqIn = new File(operandos[1]);
						arqOut = new File(operandos[2]);
						entrada = ImageIO.read(arqIn);
						saida = Operacoes.complemento(entrada);
						ImageIO.write(saida, "jpg", arqOut);
						entradaValida = true;
					} catch (IOException e) {
						entradaValida = false;
						System.out.println("Arquivo não encontrado. Coloque o caminho dos arquivos desejados, na forma: dir\\dir\\arq.ext");
						System.out.println("Digite uma operação:");
					}
					break;

				case "rgb-para-cinza":
					try {
						arqIn = new File(operandos[1]);
						arqOut = new File(operandos[2]);
						entrada = ImageIO.read(arqIn);
						saida = Operacoes.rgbParaCinza(entrada);
						ImageIO.write(saida, "jpg", arqOut);
						entradaValida = true;
					} catch (IOException e) {
						entradaValida = false;
						System.out.println("Arquivo não encontrado. Coloque o caminho dos arquivos desejados, na forma: dir\\dir\\arq.ext");
						System.out.println("Digite uma operação:");
					}
					break;

				case "filtro-gaussiano":
					try {
						arqIn = new File(operandos[1]);
						arqOut = new File(operandos[2]);
						FiltroGaussiano filt = new FiltroGaussiano();
						entrada = ImageIO.read(arqIn);
						saida = Operacoes.convolucao(entrada, filt);
						ImageIO.write(saida, "jpg", arqOut);
						entradaValida = true;
					} catch (IOException e) {
						entradaValida = false;
						System.out.println("Arquivo não encontrado. Coloque o caminho dos arquivos desejados, na forma: dir\\dir\\arq.ext");
						System.out.println("Digite uma operação:");
					}
					break;

				case "bordas":
					try {
						arqIn = new File(operandos[2]);
						arqOut = new File(operandos[3]);
						entrada = ImageIO.read(arqIn);
						saida = Operacoes.deteccaoDeBordas(entrada, Double.parseDouble(operandos[1]));
						ImageIO.write(saida, "jpg", arqOut);
						entradaValida = true;
					} catch (IOException e) {
						entradaValida = false;
						System.out.println("Arquivo não encontrado. Coloque o caminho dos arquivos desejados, na forma: dir\\dir\\arq.ext");
						System.out.println("Digite uma operação:");
					}
					break;

				case "filtro-linear":
					try {
						//preenchimento do vetor vals, que segura os valores do filtro definido pelo usuário
						int ordem = Integer.parseInt(operandos[1]), acm = 0;
						double[] vals = new double[ordem];
						for (int i = 2; i < Math.pow(ordem, 2); i++) {
							vals[acm] = Double.parseDouble(operandos[i]);
							acm++;
						}
						arqIn = new File(operandos[ordem * ordem + 2]);
						arqOut = new File(operandos[ordem * ordem + 3]);
						entrada = ImageIO.read(arqIn);
						saida = Operacoes.filtroLinear(entrada, ordem, vals);
						ImageIO.write(saida, "jpg", arqOut);
						entradaValida = true;
					} catch (IOException e) {
						entradaValida = false;
						System.out.println("Arquivo não encontrado. Coloque o caminho dos arquivos desejados, na forma: dir\\dir\\arq.ext");
						System.out.println("Digite uma operação:");
					}
					break;
				case "ajuda":
					System.out.println("Operação de complemento: complemento 'arquivo de entrada' 'arquivo de saída'\n"
							+ "Operação para conversão de RGB para escalas de cinza: rgb-para-cinza 'arquivo de entrada' 'arquivo de saída'\n"
							+ "Operação para aplicação do Filtro Gaussiano: filtro-gaussiano 'arquivo de entrada' 'arquivo de saída'\n"
							+ "Operação para detecção de bordas: bordas limiar 'arquivo de entrada' 'arquivo de saída'\n"
							+ "Operação para aplicação de um filtro linear definido por você: filtro-linear K w11 w12 ... w1K w21 w22 ... w2K ... wK1 wK2 ... wKK 'caminho do arquivo de entrada' 'caminho do arquivo de saída'\n");
					System.out.println("Digite uma operação:");
					break;
				default:
					System.out.println("entrada inválida. Digite uma operação na forma esperada. Para um índice com todas as operações existentes no pacote, digite 'ajuda'.");
					break;
			}
		}
	}
}
