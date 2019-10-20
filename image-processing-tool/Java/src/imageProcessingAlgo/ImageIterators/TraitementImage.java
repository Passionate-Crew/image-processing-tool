package imageProcessingAlgo.ImageIterators;

import imageProcessingAlgo.OperationsImages;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class TraitementImage {

	private TraitementImage suivant;
	protected static int[][] tab;
	protected static BufferedImage copy;

	public int[][] getTab() {
		return TraitementImage.tab;
	}

	public synchronized void setTab(int[][] t) {
		if(getTab() == null) {
			TraitementImage.tab = t;
		}
	}
	
	public synchronized void setCopy(BufferedImage b) {
		if(getCopy() == null) {
			this.copy = b;
		}
	}
	
	public void resetTraitement() {
		TraitementImage.tab = null;
		this.copy = null;
	}
	
	public BufferedImage getCopy() {
		return this.copy;
	}

	//Presence d'un seul tableau de taille 9
	protected int [] tabRouge = new int [9];
	protected int [] tabVert = new int [9];
	protected int [] tabBleu = new int [9];
	protected int indice = 0;

	public int calculerColorPonderee(int[] tab, int indice, double [] noyau) {
		double couleurRes = 0D;
		for(int i=0; i < indice; i++) {
				couleurRes += (tab[i]*noyau[i]);
		}
		return((int)Math.round(couleurRes));
	}
	
	public int calculerColorMoyenne(int[] tab, int indice) {
		double couleurRes = 0D;
		for(int i=0; i < indice; i++) {
				couleurRes += tab[i];
		}
		couleurRes/=indice;
		return((int)Math.round(couleurRes));
	}
	
	public int preparerColorInt(int[] tab, int indice) {
		int nbMedian;
		if(indice == 9) {
			Arrays.sort(tab);
			nbMedian = (int) OperationsImages.median(tab, indice);
		} else if(indice == 6) {
			int [] tabBis = new int [6];
			for(int i = 0 ; i < indice ; i++) {
				tabBis[i] = tab[i];
			}
			Arrays.sort(tabBis);
			nbMedian = (int)OperationsImages.median(tabBis, indice);
		} else {
			int [] tabBis = new int [4];
			for(int i = 0 ; i < indice ; i++) {
				tabBis[i] = tab[i];
			}
			Arrays.sort(tabBis);
			nbMedian = (int)OperationsImages.median(tabBis, indice);
		}
		return(nbMedian);
	}
	
	public void setSuivant(TraitementImage t){
		this.suivant = t;
	}
	
	public void traitementPixelGaussien(BufferedImage img, int L, int l, String nom) {
		if (this.suivant != null) {
			this.suivant.traitementPixelGaussien(img, L, l, nom);
		}
	}
	
	public void traitementPixelPoivreEtSel(BufferedImage img, int longueur, int largeur) {
		if (this.suivant != null) {
			this.tabBleu = new int [9];
			this.tabVert = new int [9];
			this.tabBleu = new int [9];
			this.suivant.traitementPixelPoivreEtSel(img, longueur, largeur);
		} else {
			this.resetTraitement();
		}
	}
}
