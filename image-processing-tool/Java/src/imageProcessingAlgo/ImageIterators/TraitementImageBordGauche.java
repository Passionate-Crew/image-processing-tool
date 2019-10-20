package imageProcessingAlgo.ImageIterators;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class TraitementImageBordGauche extends TraitementImage {
	
	private double [] noyau = {0.16667,0.08333,0.33333,0.16667,0.16667,0.08333};
	int nbMedianRouge;
	int nbMedianVert;
	int nbMedianBleu;
	
	public void traitementPixelGaussien(BufferedImage img, int L, int l, String nom) {
		int j = 0;

		for (int i=1 ; i < L-1 ; i++) {
			if(TraitementImage.tab[j][i] == 0) {
				for(int y=i-1 ; y <i+2 ; y++){
					for(int x=j ; x <j+2 ; x++){
						int rouge = new Color(img.getRGB(x,y)).getRed();
						int vert = new Color(img.getRGB(x, y)).getGreen();
						int bleu = new Color(img.getRGB(x, y)).getBlue();
						super.tabRouge[super.indice] = rouge;
						super.tabVert[super.indice] = vert;
						super.tabBleu[super.indice] = bleu;
						super.indice++;
					}
				}
				if(nom.equals("Gaussien")) {
					nbMedianRouge = calculerColorPonderee(tabRouge, indice, this.noyau);
					nbMedianVert = calculerColorPonderee(tabVert, indice, this.noyau);
					nbMedianBleu = calculerColorPonderee(tabBleu, indice, this.noyau);
				} else {
					nbMedianRouge = calculerColorMoyenne(tabRouge, indice);
					nbMedianVert = calculerColorMoyenne(tabVert, indice);
					nbMedianBleu = calculerColorMoyenne(tabBleu, indice);
				}
				int newColor = new Color(nbMedianRouge,nbMedianVert,nbMedianBleu).getRGB();
				img.setRGB(j, i, newColor);
				indice = 0;
			} else {
				img.setRGB(j, i, getCopy().getRGB(j, i));
			}
		}
		super.traitementPixelGaussien(img, L, l, nom);
	}

	public void traitementPixelPoivreEtSel(BufferedImage img, int longueur, int largeur) {
		int j = 0;

		for (int i=1 ; i < longueur-1 ; i++) {
			if(img.getRGB(j, i) == new Color(255,255,255).getRGB()  || img.getRGB(j, i) == new Color(0,0,0).getRGB()) {
				for(int y=i-1 ; y <i+2 ; y++){
					for(int x=j ; x <j+2 ; x++){
						int rouge = new Color(img.getRGB(x,y)).getRed();
						int vert = new Color(img.getRGB(x, y)).getGreen();
						int bleu = new Color(img.getRGB(x, y)).getBlue();
						tabRouge[indice] = rouge;
						tabVert[indice] = vert;
						tabBleu[indice] = bleu;
						indice++;
					}
				}
				nbMedianRouge = preparerColorInt(tabRouge, indice);
				nbMedianVert = preparerColorInt(tabVert, indice);
				nbMedianBleu = preparerColorInt(tabBleu, indice);
				int newColor = new Color(nbMedianRouge,nbMedianVert,nbMedianBleu).getRGB();
				img.setRGB(j, i, newColor);
				indice = 0;
				super.tabRouge = new int [9];
				super.tabVert = new int [9];
				super.tabBleu = new int [9];
			}
		}
		super.traitementPixelPoivreEtSel(img, longueur, largeur);
	}
}
