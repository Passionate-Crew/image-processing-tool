import UI.BoutonListener;
import UI.ConteneurBouton;
import UI.ConteneurImage;
import UI.Fenetre;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Les modeles
		ConteneurImage ci = new ConteneurImage();
		ConteneurBouton cb = new ConteneurBouton();
		//Le controler
		BoutonListener boutonL = new BoutonListener();
		//La vue
		Fenetre f = new Fenetre();
		
		//Connaissance des objets entre eux
		boutonL.setConteneurImage(ci);
		boutonL.setConteneurBouton(cb);
		boutonL.setFenetre(f);
		f.setConteneurGeneral(ci);
		f.setConteneurBouton(cb);
		
		//Set des observers
		f.setListener(boutonL);
		ci.addObserver(f);
		f.afficherFenetre();
    }
}
