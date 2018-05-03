package ui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Loading extends JPanel {

	public Loading() {
		setLayout(new FlowLayout());
		
		JLabel lblChargementVeuillezPatienter = new JLabel("Chargement...");
		add(lblChargementVeuillezPatienter);
	}

}
