package UI;

import imageProcessingAlgo.OperationsImages;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class Fenetre implements Observer {

	private JFrame fenetre = new JFrame();
	private ConteneurImage ci;
	private ConteneurBouton cb;
	MenuItem item2;
	MenuItem item1;
	MenuItem item3;
	MenuItem item4;
	MenuItem item5;
	MenuItem item6;
	
	
	/**public void setObserver(UI.ConteneurImage c) {
		obs.addObserver(c);
	}*/
	
	public Fenetre(){ //UI.BoutonListener b
			this.ci = null;
			this.cb = null;

		//Configure la fenetre
			this.fenetre.setTitle("Traitement d'image");
			this.fenetre.setSize(800,600);
			this.fenetre.setLocationRelativeTo(null);
			this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		//Configuration barre de menus
			MenuBar menu = new MenuBar( );
		
			Menu MenuPrinc = new Menu("Image Initiale");
			item2 = new MenuItem("Image Initiale");
		
			Menu meprinc2 = new Menu("Suivant");
			item1 = new MenuItem("Suivant");
			
			Menu meprinc3 = new Menu("Precedent");
			item3 = new MenuItem("Precedent");
			
			Menu meprinc4 = new Menu("Autres Options");
			item4 = new MenuItem("Contour");
			item6 = new MenuItem("D�bruitage Gaussien sans contour");
			
			Menu meprinc5 = new Menu("A propos");
			item5 = new MenuItem("A propos");
	
		//Ajout des composants entre eux
			MenuPrinc.add(item2);
			meprinc2.add(item1);
			meprinc3.add(item3);
			meprinc4.add(item4);
			meprinc4.add(item6);
			meprinc5.add(item5);
			
			menu.add(meprinc3);
			menu.add(MenuPrinc);
			menu.add(meprinc2);
			menu.add(meprinc4);
			menu.add(meprinc5);
			
	    //Ajout du menu a la UI.Fenetre
			this.fenetre.setMenuBar(menu); 
	}
	
	public void setConteneurGeneral(ConteneurImage c) {
		this.ci = c;
		OperationsImages op = new OperationsImages();
		op.filtrePlusTableau3x3((Conteneur)c.getContainerImage().getComponent(c.getIndice()));
		op.setCopy(((Conteneur)c.getContainerImage().getComponent(c.getIndice())).copyImage());
	    this.fenetre.getContentPane().add(c.getContainerImage(), BorderLayout.CENTER);
	}
	
	public void miseAJour() {
		this.fenetre.repaint();
	}

	public void setConteneurBouton(ConteneurBouton c2) {
		this.cb = c2;
		this.fenetre.getContentPane().add(c2.getContainerBoutons(), BorderLayout.WEST);
	}
	
	public void afficherFenetre() {
		this.fenetre.setVisible(true);
	}
	
	public void setListener(ActionListener al) {
		item2.addActionListener(al);
		item1.addActionListener(al);
		item3.addActionListener(al);
		item4.addActionListener(al);
		item6.addActionListener(al);
		item5.addActionListener(al);
		this.cb.setActionListener(al);
	}

	@Override
	public void update(Observable ob, Object o) {
		System.out.println("ici5");
		ConteneurImage cRefait = (ConteneurImage) ob;
		this.setConteneurGeneral(cRefait);
	}
}
