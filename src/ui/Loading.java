package ui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Loading extends JPanel {

	public Loading() {
		setLayout(new FlowLayout());
		
		JLabel lblChargementVeuillezPatienter = new JLabel("Chargement...");
		add(lblChargementVeuillezPatienter);
	}

}
