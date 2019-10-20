package UI;

import imageProcessingAlgo.OperationsImages;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConteneurBouton extends Observable{

	private JPanel containerBoutons = new JPanel();
	private JPanel containerChamp = new JPanel();
	private JLabel champ = new JLabel();
	private JButton [] BoutonTab = new JButton [7];
	private OperationsImages op = new OperationsImages();
	
	//Constructeur
	public ConteneurBouton() {
		this.BoutonTab[0] = new Bouton("Bruit Poivre et sel");
		this.BoutonTab[1] = new Bouton("Bruit Additif");
		this.BoutonTab[2] = new Bouton("Bruit Multiplicatif");
		this.BoutonTab[3] = new Bouton("Debruitage Poivre et sel");
		this.BoutonTab[4] = new Bouton("Debruitage Gaussien");
		this.BoutonTab[5] = new Bouton("Debruitage Kuwahara");
		this.BoutonTab[6] = new Bouton("Debruitage Moyenneur");
		this.setChamp("");
		
		this.containerBoutons.setLayout(new GridLayout(9,1));
		for(int i =0 ; i < this.BoutonTab.length ; i++) {
			this.containerBoutons.add(this.BoutonTab[i]);
			if (i > 2) {
				this.BoutonTab[i].setEnabled(false);
			}
		}
		containerChamp.add(champ, BorderLayout.CENTER);
		containerBoutons.add(new JPanel());
		containerBoutons.add(containerChamp);
		containerBoutons.getComponent(8).setVisible(false);
	}
	
	//Methode qui met en forme le SNR
	public void setChamp(String s) {
		this.champ.setText("SNR : "+s);
	}
	
	public void setActionListener(ActionListener al) {
		for(int i =0 ; i < this.BoutonTab.length ; i++) {
			this.BoutonTab[i].addActionListener(al);
			if (i > 2) {
				this.BoutonTab[i].setEnabled(false);
			}
		}
	}
	
	//Methode utilis� par le Controller UI.BoutonListener
	public void RemiseAZeroBouton() {
		for(int i = 0 ; i < BoutonTab.length ; i++) {
			if(i<3 || i > 6) {
				containerBoutons.getComponent(i).setEnabled(true);
			} else {
				containerBoutons.getComponent(i).setEnabled(false);
			}
		}
	}
	
	public void cacherSNR() {
		containerBoutons.getComponent(8).setVisible(false);
	}
	
	public void afficherSNR(Conteneur c) {
		if(op.snr(c).toString().length() < 5){
			setChamp(op.snr(c).toString());
		} else {
			setChamp(op.snr(c).toString().substring(0,5));
		}
		containerBoutons.getComponent(8).setVisible(true);
	}
	
	public void cacherBoutonConvolutif(int i, int j) {
		containerBoutons.getComponent(i).setEnabled(false);
		containerBoutons.getComponent(j).setEnabled(false);
	}
	
	public void actionerBoutonConvolutif() {
		containerBoutons.getComponent(4).setEnabled(true);
		containerBoutons.getComponent(5).setEnabled(true);
		containerBoutons.getComponent(6).setEnabled(true);
	}
	
	public void actionerBoutonPoivreEtSel() {
		containerBoutons.getComponent(1).setEnabled(false);
		containerBoutons.getComponent(2).setEnabled(false);
		containerBoutons.getComponent(3).setEnabled(true);
	}
	
	public JPanel getContainerBoutons() {
		return this.containerBoutons;
	}
}
