package UI;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Bouton extends JButton {
	
	public Bouton(String nom) {
		super(nom);
		this.setActionCommand(nom);
		this.setEnabled(true);
	}
	
}
	

