package UI;

import imageProcessingAlgo.OperationsImages;

import java.awt.CardLayout;
import java.util.Observable;

import javax.swing.JPanel;

public class ConteneurImage extends Observable {
	
	private JPanel containerGeneral = new JPanel();
	CardLayout cl = new CardLayout();
	private String[] cheminTab = new String[4];
	private Conteneur [] imageTab = new Conteneur [cheminTab.length];
	private int indice;
	private OperationsImages op = new OperationsImages();

	
	public ConteneurImage() {
		this.cheminTab[0] = "assets/Lena.gif";
		this.cheminTab[1] = "assets/bleue.jpg";
		this.cheminTab[2] = "assets/Lighthouse.jpg";
		
		// We create 3 differents containers with 3 differents images
		for(int i = 0 ; i < imageTab.length-1 ; i++) {
			imageTab[i] = new Conteneur();
			imageTab[i].setLayout(cl);
			imageTab[i].changerImg(this.cheminTab[i]);
		}
		OperationsImages op = new OperationsImages();
		imageTab[3] = new Conteneur();
		imageTab[3].setLayout(cl);
		imageTab[3].assignAndRepaint(op.testImageKuwa());
		
		
		this.containerGeneral.setLayout(cl);
		for (int i = 0 ; i < imageTab.length ; i++) {
			this.containerGeneral.add(imageTab[i]);
		}
		
	}
	
	// Methode utilisees par le controler UI.BoutonListener
	public void imageSuivante() {
		cl.next(containerGeneral);
		if(indice < cheminTab.length-1) {
			indice++;
		} else {
			indice = 0;
		}
	}
	
	public void imagePrecedente() {
		cl.previous(containerGeneral);
		if(indice > 0) {
			indice--;
		} else {
			indice = cheminTab.length-1;
		}
	}
	
	public void ImageInitiale() {
		if(indice < 6) {
			imageTab[indice].changerImg(cheminTab[indice]);
		} else {
			imageTab[indice].assignAndRepaint(op.testImageKuwa());
		}
	}
	
	public void remiseAZeroCopy() {
		op.reset();
		op.filtrePlusTableau3x3((Conteneur)containerGeneral.getComponent(indice));
		op.setCopy(((Conteneur)containerGeneral.getComponent(indice)).copyImage());
	}
	
	public Conteneur bruiterPoivreEtSel() {
		return(op.bruitagePoivreEtSel((Conteneur)containerGeneral.getComponent(indice)));
	}
	
	public Conteneur bruiterGaussien() {
		return(op.bruitageGaussien((Conteneur)containerGeneral.getComponent(indice)));
	}
	
	public Conteneur bruiterMultiplicatif() {
		return(op.bruitageMultiplicatif((Conteneur)containerGeneral.getComponent(indice)));
	}
	
	public Conteneur debruiterGaussienSansContour() {
		return(op.debruitageGaussienSansFiltre((Conteneur)containerGeneral.getComponent(indice)));
	}
	
	public Conteneur debruiterPoivreEtSel() {
		return(op.debruitagePoivreEtSel((Conteneur)containerGeneral.getComponent(indice)));
	}
	
	public Conteneur debruiterGaussienAvecContour() {
		return(op.debruitageGaussienRefactorer((Conteneur)containerGeneral.getComponent(indice)));
	}
	
	public Conteneur debruiterKuwaharaAvecContour() {
		return(op.debruitageKuwahara2((Conteneur)containerGeneral.getComponent(indice)));
	}
	
	public Conteneur debruiterMoyenneurAvecContour() {
		return(op.debruitageMoyenneurRefactorer((Conteneur)containerGeneral.getComponent(indice)));
	}
	
	public Conteneur Contour() {
		return(op.testCopieMarche((Conteneur)containerGeneral.getComponent(indice)));
	}
	
	public void OperationFinTraitement(Conteneur c) {
		imageTab[indice].add(c);
		cl.next(imageTab[indice]);
		imageTab[indice].remove(0);
	}
	
	public JPanel getContainerImage() {
		return this.containerGeneral;
	}
	
	public int getIndice() {
		return this.indice;
	}
}
