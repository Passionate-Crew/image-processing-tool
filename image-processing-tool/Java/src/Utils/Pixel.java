package Utils;

public class Pixel {
	
	private int x;
	private int y;
	private int couleur;
	
	public Pixel(int posx, int posy) {
		this.x = posx;
		this.y = posy;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

}
