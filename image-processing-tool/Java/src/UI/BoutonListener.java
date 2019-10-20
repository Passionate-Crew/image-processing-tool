package UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class BoutonListener implements ActionListener {
	
	private ConteneurImage conteImage = new ConteneurImage();	//Modele1
	private ConteneurBouton cb = new ConteneurBouton(); 		//Modele2
	private Fenetre f;											//Vue
	
	//initialisation a null
	public BoutonListener() {
		this.f = null;
		this.conteImage = null;
	}

	//Modification du Modele UI.ConteneurImage
	public void setConteneurImage(ConteneurImage conteImage) {
		this.conteImage = conteImage;
	}
	
	//Modification du Modele UI.ConteneurBouton
	public void setConteneurBouton(ConteneurBouton conteBouton) {
		this.cb = conteBouton;
	}
	
	//Modification de la Vue
	public void setFenetre(Fenetre window) {
		this.f = window;
	}
	
	//Methode appel� si une action est detect�
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("A propos")){
			
			actionAPropos(e);
		} else if (e.getActionCommand().equals("Image Initiale") || 
				e.getActionCommand().equals("Suivant") || 
				e.getActionCommand().equals("Precedent")) {
			
			actionMenu(e);
		} else if (e.getActionCommand().equals("Bruit Poivre et sel") || 
				e.getActionCommand().equals("Bruit Additif") || 
				e.getActionCommand().equals("Bruit Multiplicatif")) {
			
			actionBruit(e);
		} else if (e.getActionCommand().equals("Debruitage Poivre et sel") || 
				e.getActionCommand().equals("Debruitage Gaussien") || 
				e.getActionCommand().equals("Debruitage Kuwahara") ||
				e.getActionCommand().equals("Debruitage Moyenneur")) {
			
			actionDebruiter(e);
		} else if (e.getActionCommand().equals("Contour") || 
				e.getActionCommand().equals("D�bruitage Gaussien sans contour")) {
			
			actionOptions(e);
		} else {
			
			//actionBesoin(e);
		}
	}
	
	public void actionAPropos(ActionEvent e) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Apropos());
		frame.setTitle("A propos");
		frame.setBounds(50, 50, 400, 200);
		frame.setBackground(Color.BLACK);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void actionMenu(ActionEvent e) {
		if(!e.getActionCommand().equals("Image Initiale")) {
			if(e.getActionCommand().equals("Suivant")) {
				this.conteImage.imageSuivante();
			} else {
				this.conteImage.imagePrecedente();
			}
			this.conteImage.remiseAZeroCopy();
		}
		this.conteImage.ImageInitiale();
		this.cb.RemiseAZeroBouton();
		this.cb.cacherSNR();
	}
	
	public void actionOptions(ActionEvent e) {
		Conteneur c;
		if(e.getActionCommand().equals("Contour")) {
			c = this.conteImage.Contour();
		} else {
			c = this.conteImage.debruiterGaussienSansContour();
		}
		this.conteImage.OperationFinTraitement(c);
		this.cb.afficherSNR(c);
		this.f.miseAJour();
	}
	
	public void actionBruit(ActionEvent e) {
		Conteneur c;
		if(e.getActionCommand().equals("Bruit Poivre et sel")) {
			c = this.conteImage.bruiterPoivreEtSel();
			this.cb.actionerBoutonPoivreEtSel();
		} else {
			if(e.getActionCommand().equals("Bruit Additif")) {
				c = this.conteImage.bruiterGaussien();
				this.cb.cacherBoutonConvolutif(0, 2);
			} else {
				c = this.conteImage.bruiterMultiplicatif();
				this.cb.cacherBoutonConvolutif(0, 1);
			}
			this.cb.actionerBoutonConvolutif();
		}
		this.conteImage.OperationFinTraitement(c);
		this.cb.afficherSNR(c);
		this.f.miseAJour();
	}
	
	public void actionDebruiter(ActionEvent e) {
		Conteneur c;
		if(e.getActionCommand().equals("Debruitage Poivre et sel")) {
			c = this.conteImage.debruiterPoivreEtSel();
		} else if(e.getActionCommand().equals("Debruitage Gaussien")) {
			c = this.conteImage.debruiterGaussienAvecContour();
		} else if(e.getActionCommand().equals("Debruitage Kuwahara")) {
			c = this.conteImage.debruiterKuwaharaAvecContour();
		} else {
			c = this.conteImage.debruiterMoyenneurAvecContour();
		}
		this.cb.afficherSNR(c);
		this.conteImage.OperationFinTraitement(c);
		this.f.miseAJour();
	}

}
