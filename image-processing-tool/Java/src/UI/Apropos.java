package UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Apropos extends JPanel {

   public void paint(Graphics g) {
      Image img = createImageWithText();
      g.drawImage(img, 20,20,this);
   }

   private Image createImageWithText(){
      BufferedImage bufferedImage = new 
      BufferedImage(400,200,BufferedImage.TYPE_INT_RGB);
      Graphics g = bufferedImage.getGraphics();

      g.drawString("Cette application fut con�ue dans un but p�dagogique.", 20,20);
      g.drawString("Dans le cadre d'un projet en mod�lisation math�matique.", 20, 40);
      g.drawString("Tout droits r�serv�s aux auteurs,", 20,80);
      g.drawString("ARTIGOUHA No�my et BOIRON Gr�goire", 20,100);
      return bufferedImage;
   }
}