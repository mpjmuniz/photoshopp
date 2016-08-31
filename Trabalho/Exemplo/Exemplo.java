import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ExemploSimples {
	public static void main(String[] args) throws IOException {
		BufferedImage img = ImageIO.read(new File("imagem_original.png"));

		final int blue = 0x000000FF; // Assumindo image.getType() == BufferedImage.TYPE_3BYTE_BGR
		final int green = 0x0000FF00; 
		final int red = 0x00FF0000;

		img.setRGB(0, 0, blue);
		img.setRGB(1, 0, green);
		img.setRGB(2, 0, red);
		
		ImageIO.write(img, "png", new File("imagem_modificada.png"));
	}
}