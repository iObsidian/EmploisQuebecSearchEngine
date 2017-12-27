package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AdvancedSearch extends JPanel {

	boolean isInAdvancedSearchMode = true;

	public JTextField offerNumber;
	public JTextField jobName;
	public JTextField employer;
	public JTextField numberOfOffers;
	public JTextField scholarity;
	public JTextField yearsOfExperience;
	public JTextField workPlace;

	public JPanel simpleSearch;

	JButton toggleAdvancedSearchBtn;

	public JPanel getSimpleSearchPanel() {

		if (simpleSearch == null) {

			simpleSearch = new JPanel();
			simpleSearch.setLayout(new BorderLayout());

			Component horizontalStrut = Box.createHorizontalStrut(1);
			simpleSearch.add(horizontalStrut);

			JLabel lblNewLabel_1 = new JLabel("Recherche : ");
			lblNewLabel_1.setToolTipText("Recherche par mots clef");
			simpleSearch.add(lblNewLabel_1);

			JTextArea textArea_1 = new JTextArea();

			textArea_1.getDocument().addDocumentListener(new DocumentListener() {

				public void changedUpdate(DocumentEvent arg0) {
					updateKeywords();
				}

				public void insertUpdate(DocumentEvent arg0) {
					updateKeywords();
				}

				public void removeUpdate(DocumentEvent arg0) {
					updateKeywords();
				}

				private void updateKeywords() {

					String keywords = textArea_1.getText();

					String[] keyWords;

					if (keywords.contains(", ")) {
						keyWords = keywords.split(", ");
					} else if (keywords.contains(",")) {
						keyWords = keywords.split(",");
					} else {
						keyWords = new String[] { keywords };
					}

					//table.filterByKeyword(keyWords);

				}
			});

			simpleSearch.add(textArea_1, BorderLayout.CENTER);

			toggleAdvancedSearchBtn = new JButton("Recherche avancée \u2304");

			toggleAdvancedSearchBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					isInAdvancedSearchMode = !isInAdvancedSearchMode;

					if (isInAdvancedSearchMode) {
						setVisible(true);
						toggleAdvancedSearchBtn.setText("Recherche avancée \u2304");
					} else {
						setVisible(false);
						toggleAdvancedSearchBtn.setText("Recherche avancée \u02C4");
					}

				}
			});

			simpleSearch.add(toggleAdvancedSearchBtn, BorderLayout.EAST);

			simpleSearch.setVisible(true);

			return simpleSearch;
		} else {
			return simpleSearch;
		}

	}

	/**
	 * Create the panel.
	 */
	public AdvancedSearch() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// details for a File
		setLayout(new BorderLayout(4, 2));
		setBorder(new EmptyBorder(0, 6, 0, 6));

		JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
		add(fileDetailsLabels, BorderLayout.WEST);

		JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
		add(fileDetailsValues, BorderLayout.CENTER);

		//

		fileDetailsLabels.add(new JLabel("N° de l'offre", JLabel.TRAILING));
		offerNumber = new JTextField();
		fileDetailsValues.add(offerNumber);

		fileDetailsLabels.add(new JLabel("Appellation d'emploi", JLabel.TRAILING));
		jobName = new JTextField();
		jobName.setEditable(false);
		fileDetailsValues.add(jobName);

		fileDetailsLabels.add(new JLabel("Employeur", JLabel.TRAILING));
		employer = new JTextField();
		fileDetailsValues.add(employer);

		fileDetailsLabels.add(new JLabel("Nombre de poste(s)", JLabel.TRAILING));
		numberOfOffers = new JTextField();
		fileDetailsValues.add(numberOfOffers);

		fileDetailsLabels.add(new JLabel("Scolarité", JLabel.TRAILING));
		scholarity = new JTextField();
		fileDetailsValues.add(scholarity);

		fileDetailsLabels.add(new JLabel("Années d'expérience", JLabel.TRAILING));
		yearsOfExperience = new JTextField();
		fileDetailsValues.add(yearsOfExperience);

		fileDetailsLabels.add(new JLabel("Lieu de travail", JLabel.TRAILING));
		workPlace = new JTextField();
		fileDetailsValues.add(workPlace);

	}

}
