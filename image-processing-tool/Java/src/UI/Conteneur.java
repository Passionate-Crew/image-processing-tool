package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Conteneur extends JPanel implements Cloneable {
	
	private BufferedImage img;
	
	public Conteneur(){
		super();
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public void changerImg(String s) {
		try {
			this.img = ImageIO.read(new File(s));
		    repaint();
		} catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public void assignAndRepaint(BufferedImage img) {
		this.img = img;
		repaint();
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null) {
			return false;
		}
		if(!(o instanceof Conteneur)) {
			return false;
		}
		Conteneur i = (Conteneur) o;
		if(this.img == i.getImg()) {
			return true;
		} else {
			return false;
		}
	}
	
	//Permet de cloner un conteneur et d'empecher la propagation d'une modification
	public BufferedImage copyImage() {
		BufferedImage source = this.img;
		BufferedImage clone = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		Graphics g = clone.getGraphics();
		g.drawImage(source, 0, 0, null);
		g.dispose();
		return clone;
	}
}

