package imageProcessingAlgo;

import imageProcessingAlgo.ImageIterators.*;
import UI.Conteneur;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;

public class OperationsImages {
	
	private static int[][] tab;
	private static BufferedImage copy;
	private int indiceV;

	public synchronized void setTab(int[][] t) {
		if(getTab() == null) {
			OperationsImages.tab = t;
		}
	}
	
	public synchronized void setCopy(BufferedImage b) {
		if(getCopy() == null) {
			OperationsImages.copy = b;
		}
	}
	
	public int[][] getTab() {
		return OperationsImages.tab;
	}
	
	public BufferedImage getCopy() {
		return OperationsImages.copy;
	}
	
	public void reset() {
		OperationsImages.tab = null;
		OperationsImages.copy = null;
	}
	
	public Conteneur bruitagePoivreEtSel(Conteneur conte) {
		Conteneur res= new Conteneur();
		BufferedImage img = conte.getImg();
		Random r = new Random();
		
		int largeur = img.getWidth();
		int longueur = img.getHeight();
		for(int i=0; i<longueur; i++){
			for(int j=0; j<largeur; j++){
				int nb = r.nextInt(256);
				if(nb == 0) {
					int blanc = new Color(255,255,255).getRGB();
					img.setRGB(j, i, blanc);
				}
				if(nb == 255) {
					int noir = new Color(0,0,0).getRGB();
					img.setRGB(j, i, noir);
				}
			}
		}
		res.setImg(img);
		return res;
	}
	
	public static double median(int[] m, int indice) {
	    int middle = indice/2;
	    if (indice%2 == 0) {
	        return m[middle];
	    } else {
	        return (m[middle-1] + m[middle]) / 2.0;
	    }
	}
	
	/** Nous avions commencer a faire le code de debruitagePoivreetSel que pour les images en noir et blanc */ 
	/**Adapt� au couleur apres �a*/
	/**Et refactorer ensuite : */
	
	public Conteneur debruitagePoivreEtSel(Conteneur conte) {
		Conteneur res = new Conteneur();
		BufferedImage img = conte.getImg();
		int longueur = img.getHeight();
		int largeur = img.getWidth();
		
		TraitementImageNormal t1 = new TraitementImageNormal();
		TraitementImageAngleHautGauche t2 = new TraitementImageAngleHautGauche();
		TraitementImageBordHaut t3 = new TraitementImageBordHaut();
		TraitementImageAngleHautDroit t4 = new TraitementImageAngleHautDroit();
		TraitementImageBordGauche t5 = new TraitementImageBordGauche();
		TraitementImageAngleBasGauche t6 = new TraitementImageAngleBasGauche();
		TraitementImageBordDroit t7 = new TraitementImageBordDroit();
		TraitementImageAngleBasDroit t8 = new TraitementImageAngleBasDroit();
		TraitementImageBordBas t9 = new TraitementImageBordBas();
		
		t1.setSuivant(t2);
		t2.setSuivant(t3);
		t3.setSuivant(t4);
		t4.setSuivant(t5);
		t5.setSuivant(t6);
		t6.setSuivant(t7);
		t7.setSuivant(t8);
		t8.setSuivant(t9);
		
		t1.traitementPixelPoivreEtSel(img, longueur, largeur);
		
		res.setImg(img);
		return(res);
	}
	
	public Conteneur bruitageGaussien(Conteneur conte) {
		Conteneur res= new Conteneur();
		BufferedImage img = conte.getImg();
		
		Random r = new Random();
		int largeur = img.getWidth();
		int longueur = img.getHeight();
		for(int i=0; i<longueur; i++){
			for(int j=0; j<largeur; j++){
				double nb = r.nextGaussian();
				//On change l'ecart type de la loi gaussien parce qu'il est de base � 1.
				nb = (nb*0.6);
				
				Color colorDeBase = new Color(img.getRGB(j, i));
				int bruitRouge = (int)(colorDeBase.getRed()+ 20*nb);
				int bruitVert = (int)(colorDeBase.getGreen()+ 20*nb);
				int bruitBleu = (int)(colorDeBase.getBlue()+ 20*nb);
				int c = colorDeBase.getRGB();
				
				if((bruitRouge > 0 && bruitRouge < 255) && (bruitVert > 0 && bruitVert < 255) && (bruitBleu > 0 && bruitBleu < 255)) {
					c = new Color(bruitRouge,bruitVert,bruitBleu).getRGB();
				}
				img.setRGB(j, i, c);
			}
		}
		res.setImg(img);
		return res;
		
	}
	
	public Conteneur bruitageMultiplicatif(Conteneur conte) {
		Conteneur res= new Conteneur();
		BufferedImage img = conte.getImg();
		
		Random r = new Random();
		int largeur = img.getWidth();
		int longueur = img.getHeight();
		for(int i=0; i<longueur; i++){
			for(int j=0; j<largeur; j++){
				double nb = r.nextGaussian();
				//On change l'ecart type de la loi gaussien parce qu'il est de base � 1.
				nb = (nb*0.2D);
				Color colorDeBase = new Color(img.getRGB(j, i));
				int bruitRouge = (int)(colorDeBase.getRed()*(1+nb));
				int bruitVert = (int)(colorDeBase.getGreen()*(1+nb));
				int bruitBleu = (int)(colorDeBase.getBlue()*(1+nb));
				int c = colorDeBase.getRGB();
				
				if((bruitRouge > 0 && bruitRouge < 255) && (bruitVert > 0 && bruitVert < 255) && (bruitBleu > 0 && bruitBleu < 255)) {
					c = new Color(bruitRouge,bruitVert,bruitBleu).getRGB();
				}
				img.setRGB(j, i, c);
			}
		}
		res.setImg(img);
		return res;
		
	}
	
	//Directement adapt� au couleur
	//Puis refactorer
	
	public Conteneur debruitageMoyenneurRefactorer(Conteneur conte) {
		
		Conteneur res = new Conteneur();
		BufferedImage img = conte.getImg();
		int longueur = img.getHeight();
		int largeur = img.getWidth();

		TraitementImage t = new TraitementImage();
		t.resetTraitement();
		t.setCopy(OperationsImages.copy);
		t.setTab(OperationsImages.tab);
		
		TraitementImageNormal t1 = new TraitementImageNormal();
		TraitementImageAngleHautGauche t2 = new TraitementImageAngleHautGauche();
		TraitementImageBordHaut t3 = new TraitementImageBordHaut();
		TraitementImageAngleHautDroit t4 = new TraitementImageAngleHautDroit();
		TraitementImageBordGauche t5 = new TraitementImageBordGauche();
		TraitementImageAngleBasGauche t6 = new TraitementImageAngleBasGauche();
		TraitementImageBordDroit t7 = new TraitementImageBordDroit();
		TraitementImageAngleBasDroit t8 = new TraitementImageAngleBasDroit();
		TraitementImageBordBas t9 = new TraitementImageBordBas();
		
		t1.setSuivant(t2);
		t2.setSuivant(t3);
		t3.setSuivant(t4);
		t4.setSuivant(t5);
		t5.setSuivant(t6);
		t6.setSuivant(t7);
		t7.setSuivant(t8);
		t8.setSuivant(t9);
		
		t1.traitementPixelGaussien(img, longueur, largeur, "Moyenneur");
		
		res.setImg(img);
		return(res);
	}
	
	
	//Avant, pleins de methodes qui calculer noyau matrice avec differents poids.
	//Maintenant noyau fournit dans chaine de responsabilit�

	
	public Conteneur debruitageGaussienRefactorer(Conteneur conte){
		
		Conteneur res = new Conteneur();
		BufferedImage img = conte.getImg();
		int longueur = img.getHeight();
		int largeur = img.getWidth();
		
		TraitementImage t = new TraitementImage();
		t.resetTraitement();
		t.setCopy(OperationsImages.copy);
		t.setTab(OperationsImages.tab);
		
		TraitementImageNormal t1 = new TraitementImageNormal();
		TraitementImageAngleHautGauche t2 = new TraitementImageAngleHautGauche();
		TraitementImageBordHaut t3 = new TraitementImageBordHaut();
		TraitementImageAngleHautDroit t4 = new TraitementImageAngleHautDroit();
		TraitementImageBordGauche t5 = new TraitementImageBordGauche();
		TraitementImageAngleBasGauche t6 = new TraitementImageAngleBasGauche();
		TraitementImageBordDroit t7 = new TraitementImageBordDroit();
		TraitementImageAngleBasDroit t8 = new TraitementImageAngleBasDroit();
		TraitementImageBordBas t9 = new TraitementImageBordBas();
		
		t1.setSuivant(t2);
		t2.setSuivant(t3);
		t3.setSuivant(t4);
		t4.setSuivant(t5);
		t5.setSuivant(t6);
		t6.setSuivant(t7);
		t7.setSuivant(t8);
		t8.setSuivant(t9);
		
		t1.traitementPixelGaussien(img, longueur, largeur, "Gaussien");
		
		t.resetTraitement();
		res.setImg(img);
		return(res);
	}
	
	public Conteneur debruitageGaussienSansFiltre(Conteneur conte){
		
		Conteneur res = new Conteneur();
		TraitementImage t = new TraitementImage();
		BufferedImage img = conte.getImg();
		int longueur = img.getHeight();
		int largeur = img.getWidth();
		int L = OperationsImages.tab.length;
		
		int[][] tabVide = new int [L][L];
		for(int[] row : tabVide)	{
			Arrays.fill(row, 0);
		}
		t.setCopy(OperationsImages.copy);
		t.setTab(tabVide);
		
		TraitementImageNormal t1 = new TraitementImageNormal();
		TraitementImageAngleHautGauche t2 = new TraitementImageAngleHautGauche();
		TraitementImageBordHaut t3 = new TraitementImageBordHaut();
		TraitementImageAngleHautDroit t4 = new TraitementImageAngleHautDroit();
		TraitementImageBordGauche t5 = new TraitementImageBordGauche();
		TraitementImageAngleBasGauche t6 = new TraitementImageAngleBasGauche();
		TraitementImageBordDroit t7 = new TraitementImageBordDroit();
		TraitementImageAngleBasDroit t8 = new TraitementImageAngleBasDroit();
		TraitementImageBordBas t9 = new TraitementImageBordBas();
		
		t1.setSuivant(t2);
		t2.setSuivant(t3);
		t3.setSuivant(t4);
		t4.setSuivant(t5);
		t5.setSuivant(t6);
		t6.setSuivant(t7);
		t7.setSuivant(t8);
		t8.setSuivant(t9);
		
		t1.traitementPixelGaussien(img, longueur, largeur, "SansFiltre");
		
		res.setImg(img);
		return(res);
	}
	
	private double calculerMoyenneNormal(int[] tab, int indice) {
		double moyenne = 0D;
		for(int i=0; i < indice; i++) {
				moyenne += tab[i];
		}
		moyenne/=indice;
		return moyenne;
	}
	/**
	private int calculVariance(double moyenne, int indice, int[] valeurs){
		double res = 0;
		for (int i = 0 ; i < indice ; i++) {
			res += Math.pow((valeurs[i]-moyenne),2D);
		}
		res /= indice;
		return ((int)Math.round(res));
		/**double esperance = moyenne/(double)indice;
		double variance = Math.pow((moyenne-esperance),2D)/(double)indice;
		return ((int)Math.round(variance));
	}*/

	private int calculVariance(int[] tab, int indice){
		double Ex = 0D;
		double Ex2 = 0D;
		for(int i = 0; i < indice; i++){
			Ex += tab[i];
			Ex2 += (Math.pow(tab[i],2D));
		}
		Ex /= indice;
		Ex2 /= indice;
		double variance = Ex2 - (Math.pow(Ex,2D));
		return (int)Math.round(variance);
	}
	
	private int trouverIndiceVariancePlusPetite(int[] tab, int indice){
		int var = tab[0];
		int ind = 0;
		for(int i = 0; i < indice ; i++){
			if(var > tab[i]){
				var = tab[i];
				ind = i;
			}
		}
		/**for(int i = 0; i < indice ; i++){
			if (var == tab[i]){
				ind = i;
			}
		}*/
		return ind;
	}

	public void traitementKuwahara(BufferedImage img, int x1, int x2, int y1, int y2, double[] tabMR, int[] tabVR, double[] tabMV,int[] tabVV, double[] tabMB, int[] tabVB) {
		int [] tabRouge = new int [4];
		int [] tabVert = new int [4];
		int [] tabBleu = new int [4];
		int indice = 0;
		for(int y = x1 ; y < x2 ; y++){
			for(int x = y1 ; x < y2 ; x++){
				int rouge = new Color(img.getRGB(x,y)).getRed();								
				int vert = new Color(img.getRGB(x, y)).getGreen();
				int bleu = new Color(img.getRGB(x, y)).getBlue();
				tabRouge[indice] = rouge;
				tabVert[indice] = vert;
				tabBleu[indice] = bleu;
				indice++;
			}
		}
		tabMR[indiceV] = calculerMoyenneNormal(tabRouge, indice);
		tabVR[indiceV] = calculVariance(tabRouge, indice);
		tabMV[indiceV] = calculerMoyenneNormal(tabVert, indice);
		tabVV[indiceV] = calculVariance(tabVert, indice);
		tabMB[indiceV] = calculerMoyenneNormal(tabBleu, indice);
		tabVB[indiceV] = calculVariance(tabBleu, indice);
		indiceV++;
		indice = 0;
	}	
	
	public Conteneur debruitageKuwahara2(Conteneur conte){
	
		Conteneur res = new Conteneur();
		BufferedImage img = conte.getImg();

		int longueur = img.getHeight();
		int largeur = img.getWidth();
		for(int i=0; i<longueur; i++){
			for(int j=0; j<largeur; j++){
				double[] tabMR = new double[4];
				int[] tabVR = new int[4];
				double[] tabMV = new double[4];
				int[] tabVV = new int[4];
				double[] tabMB = new double[4];
				int[] tabVB = new int[4];
				//Contient moyenne et variance
				
				int nbMoyenneRouge = 0;
				int nbMoyenneVert = 0;
				int nbMoyenneBleu = 0;
				
				if(OperationsImages.tab[j][i] == 0) {
					//1er ligne
					if (j == 0 && i == 0) {
						//pour l'angle haut-gauche
						this.traitementKuwahara(img, i, i+2, j, j+2, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
					} else if(i == 0 && j == largeur-1) {
						//pour l'angle haut-droit
						this.traitementKuwahara(img, i, i+2, j-1, j+1, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
					} else if (i == longueur-1 && j == 0) {
						//pour la l'angle bas-gauche
						this.traitementKuwahara(img, i-1, i+1, j, j+2, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
					} else if (j == largeur-1 && i == longueur-1) {
						//pour la l'angle bas-droit
						this.traitementKuwahara(img, i-1, i+1, j-1, j+1, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
					
					} else if ((j == 0 && i != 0) || (j==0 && i != longueur-1)) {
						//pour la longueur gauche partie 1
						this.traitementKuwahara(img, i-1, i+1, j, j+1, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la longueur gauche partie 2
						this.traitementKuwahara(img, i-1, i+1, j, j+2, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la longueur gauche partie 3
						this.traitementKuwahara(img, i, i+2, j, j+1, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la longueur gauche partie 4
						this.traitementKuwahara(img, i, i+2, j, j+2, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
					} else if(i == 0 && (j != 0 || j != largeur-1)) {
						//pour la largeur haute partie 1
						this.traitementKuwahara(img, i, i+1, j-1, j+1, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la largeur haute partie 2
						this.traitementKuwahara(img, i, i+1, j, j+2, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la largeur haute partie 3
						this.traitementKuwahara(img, i, i+2, j-1, j+1, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la largeur haute partie 4
						this.traitementKuwahara(img, i, i+2, j, j+2, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
					} else if ((i == longueur-1 && j != 0) || (i == longueur-1 && j != largeur-1)) {
						//pour la largeur basse partie 1
						this.traitementKuwahara(img, i-1, i+1, j-1, j+1, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la largeur basse partie 2
						this.traitementKuwahara(img, i-1, i+1, j, j+2, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la largeur basse partie 3
						this.traitementKuwahara(img, i, i+1, j-1, j+1, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la largeur basse partie 4
						this.traitementKuwahara(img, i, i+1, j, j+2, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
					} else if (j == largeur-1 && (i != longueur-1 || i != 0)) {
						//pour la longueur droite partie 1
						this.traitementKuwahara(img, i-1, i+1, j-1, j+1, tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la longueur droite partie 2
						this.traitementKuwahara(img, i-1, i+1, j, j+1,tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la longueur droite partie 3
						this.traitementKuwahara(img, i, i+2, j-1, j+1,tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour la longueur droite partie 4
						this.traitementKuwahara(img, i, i+2, j, j+1,tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
					} else {
					//if (i>1 && i<longueur-1 && j>1 && j<longueur-1) {
						//pour le cas g�n�ral partie 1
						this.traitementKuwahara(img, i-1, i+1, j-1, j+1,tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);

						//pour le cas g�n�ral partie 2
						this.traitementKuwahara(img, i-1, i+1, j, j+2,tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour le cas g�n�ral partie 3
						this.traitementKuwahara(img, i, i+2, j-1, j+1,tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
						//pour le cas g�n�ral partie 4
						this.traitementKuwahara(img, i, i+2, j, j+2,tabMR, tabVR, tabMV, tabVV, tabMB, tabVB);
						
					}
					nbMoyenneRouge = (int)tabMR[trouverIndiceVariancePlusPetite(tabVR,indiceV)];
					nbMoyenneVert = (int)tabMV[trouverIndiceVariancePlusPetite(tabVV,indiceV)];
					nbMoyenneBleu = (int)tabMB[trouverIndiceVariancePlusPetite(tabVB,indiceV)];
					int newColor = new Color(nbMoyenneRouge,nbMoyenneVert,nbMoyenneBleu).getRGB();
					img.setRGB(j, i, newColor);
					indiceV = 0;
				} else {
					img.setRGB(j, i, getCopy().getRGB(j, i));
				}
			}
		}
		res.setImg(img);
		return(res);
	}
	
	public void filtrePlusTableau3x3(Conteneur conte) {
		
        	//UI.Conteneur res= new UI.Conteneur();
    		BufferedImage copie = conte.copyImage();
    		int [][] pixel = new int[copie.getWidth()][copie.getHeight()];
    		int [][] tab = new int [copie.getWidth()][copie.getHeight()];
    		int x,y,g;
    		
    		//Conversion de l'image en niveau du Gris pour pouvoir gerer les images en couleurs
    		for (int i = 0; i < copie.getHeight(); i++) {
    			for (int j = 0; j < copie.getWidth(); j++) {
    				Color pixelcolor= new Color(copie.getRGB(j,i));
    				int r=pixelcolor.getRed();
    				int gb=pixelcolor.getGreen();
    				int b=pixelcolor.getBlue();
    				int hy=(r+gb+b)/3;
    				int rgb=new Color(hy,hy,hy).getRGB();    
    				copie.setRGB(j, i, rgb);
    			}
    		}     

    		// parcourir les pixels de l'image
    		/**for (int i = 0; i < copie.getHeight(); i++) {
    			for (int j = 0; j < copie.getWidth(); j++) {
    				// recuperer couleur de chaque pixel
    				Color pixelcolor= new Color(copie.getRGB(j, i));
    				// recuperer les valeur rgb (rouge ,vert ,bleu) de cette couleur
    				pixel[j][i]= pixelcolor.getRGB();    
    			}
    		}*/
    		//Pour chaque pixel cas general
    		for (int i = 1; i < copie.getHeight()-1; i++) {
    			for (int j = 1; j < copie.getWidth()-1; j++) {
    				//x = (p1+(p2+p2)+p3-p7-(p8+p8)-p9)
    				x = this.calculerX(j, i, copie);
    				//y = (p3+(p6+p6)+p9-p1-(p4+p4)-p7)
    				y = this.calculerY(j, i, copie);
    				//g est la variation totale (|variation en x| + |variation en y|)
    				g=Math.abs(x)+Math.abs(y); 
    				//on la stock a la place de la couleur rgb
    				pixel[j][i]=g;
    			}
    		}   
        
        for (int i = 1; i < copie.getHeight()-1; i++) {
        	for (int j = 1; j < copie.getWidth()-1; j++) {
        		Color pixelcolor = new Color(pixel[j][i]);
        		int r=pixelcolor.getRed();
        		int gb=pixelcolor.getGreen();
        		int b=pixelcolor.getBlue();   
        		int rgb=new Color(r,gb,b).getRGB();
        		
        		// met la valeur 0 pour les valeurs i et 70 j qui n'ont pas besoin d'etre modifi�
        		if (rgb < (new Color(60,60,60)).getRGB()) {
        			tab[j][i] = 0;
        		//1 sinon
        		} else {
        			tab[j][i] = 1;
        		}
        		// changer la couleur de pixel avec la nouvelle couleur invers�e
        		copie.setRGB(j, i, rgb);
        	}
        }
        this.setTab(tab);
	}
	
	public int calculerX(int j, int i, BufferedImage img) {
		/**x est le coefficient de variation en X { -1 0 1
		 *											-2 0 2
		 *											-1 0 1 }*/
		int x = (img.getRGB(j+1, i-1) + 2*img.getRGB(j+1, i) + img.getRGB(j+1, i+1))
		  -(img.getRGB(j-1, i-1) + 2*img.getRGB(j-1, i) + img.getRGB(j-1, i+1));
		return x;
	}
	
	public int calculerY(int j, int i, BufferedImage img) {
		/**y est le coefficient de variation en Y { -1 -2 -1
		 *											 0  0  0
		 *											 1  2  1 }*/
		int y = (img.getRGB(j-1, i+1) + 2 * img.getRGB(j, i+1) + img.getRGB(j+1, i+1)) 
			- (img.getRGB(j-1,i-1) + 2 * img.getRGB(j, i-1) + img.getRGB(j+1, i-1));
		return y;
	}
	
	public Conteneur testCopieMarche(Conteneur conte) {
		
    	Conteneur res = new Conteneur();
    	BufferedImage img = conte.getImg();  
		//Conversion de l'image en niveau du Gris pour pouvoir gerer les images en couleurs
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				if(OperationsImages.tab[j][i] == 0) {
					img.setRGB(j,i,Color.BLACK.getRGB());
				} else {
					img.setRGB(j,i,Color.WHITE.getRGB());
				}
			
			}
		}
		res.setImg(img);
		return res;
	}
	
	public Double snr(Object c){
		Conteneur conte = (Conteneur)c;
		Double pSignal = 0D;
		Double pBruit = 0D;
		int uoR = 0;
		int uoG = 0;
		int uoB = 0;
		int uR = 0;
		int uG = 0;
		int uB = 0;
		
		for (int i = 0 ; i < OperationsImages.copy.getHeight()-1 ; i++){
			for (int j = 0 ; j < OperationsImages.copy.getWidth()-1 ; j++){
				uoR = new Color(OperationsImages.copy.getRGB(j, i)).getRed();
				uoG = new Color(OperationsImages.copy.getRGB(j, i)).getGreen();
				uoB = new Color(OperationsImages.copy.getRGB(j, i)).getBlue();
				pSignal += Math.pow((uoR+uoG+uoB)/3,2);
			}
		}
		
		for (int i = 0 ; i < conte.getImg().getHeight()-1 ; i++){
			for (int j = 0 ; j < conte.getImg().getWidth()-1 ; j++){
				uR = new Color( conte.getImg().getRGB(j, i)).getRed();
				uG = new Color( conte.getImg().getRGB(j, i)).getGreen();
				uB = new Color( conte.getImg().getRGB(j, i)).getBlue();
				uoR = new Color(OperationsImages.copy.getRGB(j, i)).getRed();
				uoG = new Color(OperationsImages.copy.getRGB(j, i)).getGreen();
				uoB = new Color(OperationsImages.copy.getRGB(j, i)).getBlue();
				pBruit += Math.pow(((uR+uG+uB)/3)-((uoR+uoG+uoB)/3),2);
			}
		}
			return (10*(Math.log10(pSignal/pBruit)));	
	}
	
	public BufferedImage testImageKuwa(){
		BufferedImage image = new BufferedImage(11, 11,BufferedImage.TYPE_INT_RGB);
		int longueur = image.getHeight();
		int largeur = image.getWidth();

		for (int i = 0; i < longueur; i++){
			for (int j = 0; j < largeur; j++){
				image.setRGB(i, j, new Color(255,255,255).getRGB());
			}
		}
		image.setRGB(4, 4, new Color(17,15,0).getRGB());
		image.setRGB(5, 5, new Color(17,15,0).getRGB());
		image.setRGB(6, 6, new Color(17,15,0).getRGB());
		image.setRGB(5, 6, new Color(17,15,0).getRGB());
		image.setRGB(4, 5, new Color(17,15,0).getRGB());
		image.setRGB(4, 6, new Color(17,15,0).getRGB());
		image.setRGB(6, 4, new Color(17,15,0).getRGB());
		image.setRGB(6, 5, new Color(17,15,0).getRGB());
		image.setRGB(5, 4, new Color(17,15,0).getRGB());
		return image;
	}
}
