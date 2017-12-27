package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import emplois.Emploi;
import utils.StringArrayUtils;

public class AdvancedSearch extends JPanel {

	private AdvancedSearch me = this;

	private Table table;

	boolean isInAdvancedSearchMode = true;

	/**
	 * -1 = Simple search
	 * 
	 * 0 = N° de l'offre
	 * 1 = Appellation d'emploi
	 * 2 = Employeur
	 * 3 = Nombre de poste(s)
	 * 4 = Scolarité
	 * 5 = Années d'expérience
	 * 6 = Lieu de travail
	 */

	String[] searchForSimple;

	String[] searchForNumeroDeLoffre = new String[0];
	String[] searchForAppellationDeLemploi = new String[0];
	String[] searchForEmployeur = new String[0];
	String[] searchForNombreDePoste = new String[0];
	String[] searchForScolarite = new String[0];
	String[] searchForAnnesDexperience = new String[0];
	String[] searchForLieuDeTravail = new String[0];

	public JPanel simpleSearch;

	JButton toggleAdvancedSearchBtn;
	private JLabel jobsFoundLabel;

	public JPanel getSimpleSearchPanel(Table table) {
		this.table = table;

		if (simpleSearch == null) {

			simpleSearch = new JPanel();
			simpleSearch.setLayout(new BorderLayout());

			Component horizontalStrut = Box.createHorizontalStrut(1);
			simpleSearch.add(horizontalStrut);

			JLabel searchLabel = new JLabel("Recherche : ");
			searchLabel.setToolTipText("Recherche par mots clef");
			simpleSearch.add(searchLabel);

			JTextArea simpleSearchTextArea = new JTextArea();
			simpleSearchTextArea.getDocument().addDocumentListener(new SearchParam(me, simpleSearchTextArea, table, -1));

			simpleSearch.add(simpleSearchTextArea, BorderLayout.CENTER);

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

		// details for a File
		setLayout(new BorderLayout(0, 0));

		JPanel advancedSearchPanel = new JPanel();
		add(advancedSearchPanel, BorderLayout.CENTER);

		advancedSearchPanel.setLayout(new BorderLayout(4, 2));
		advancedSearchPanel.setBorder(new EmptyBorder(0, 6, 0, 6));

		JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
		advancedSearchPanel.add(fileDetailsLabels, BorderLayout.WEST);

		JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
		advancedSearchPanel.add(fileDetailsValues, BorderLayout.CENTER);

		//

		fileDetailsLabels.add(new JLabel("N° de l'offre", JLabel.TRAILING));
		JTextArea offerNumber = new JTextArea();
		offerNumber.getDocument().addDocumentListener(new SearchParam(me, offerNumber, table, 0));
		fileDetailsValues.add(offerNumber);

		fileDetailsLabels.add(new JLabel("Appellation d'emploi", JLabel.TRAILING));
		JTextArea jobName = new JTextArea();
		jobName.getDocument().addDocumentListener(new SearchParam(me, jobName, table, 1));
		fileDetailsValues.add(jobName);

		fileDetailsLabels.add(new JLabel("Employeur", JLabel.TRAILING));
		JTextArea employer = new JTextArea();
		employer.getDocument().addDocumentListener(new SearchParam(me, employer, table, 2));
		fileDetailsValues.add(employer);

		fileDetailsLabels.add(new JLabel("Nombre de poste(s)", JLabel.TRAILING));
		JTextArea numberOfOffers = new JTextArea();
		numberOfOffers.getDocument().addDocumentListener(new SearchParam(me, numberOfOffers, table, 3));
		fileDetailsValues.add(numberOfOffers);

		fileDetailsLabels.add(new JLabel("Scolarité", JLabel.TRAILING));
		JTextArea scholarity = new JTextArea();
		scholarity.getDocument().addDocumentListener(new SearchParam(me, scholarity, table, 4));
		fileDetailsValues.add(scholarity);

		fileDetailsLabels.add(new JLabel("Années d'expérience", JLabel.TRAILING));
		JTextArea yearsOfExperience = new JTextArea();
		yearsOfExperience.getDocument().addDocumentListener(new SearchParam(me, yearsOfExperience, table, 5));
		fileDetailsValues.add(yearsOfExperience);

		fileDetailsLabels.add(new JLabel("Lieu de travail", JLabel.TRAILING));
		JTextArea workPlace = new JTextArea();
		workPlace.getDocument().addDocumentListener(new SearchParam(me, workPlace, table, 6));
		fileDetailsValues.add(workPlace);

		JPanel searchSettingsPanel = new JPanel();
		add(searchSettingsPanel, BorderLayout.SOUTH);
		searchSettingsPanel.setLayout(new BoxLayout(searchSettingsPanel, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		searchSettingsPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		jobsFoundLabel = new JLabel("En attente...");
		panel.add(jobsFoundLabel);

		/**JCheckBox chckbxNewCheckBox = new JCheckBox("Inclusif/Exclusif");
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				inclusive = chckbxNewCheckBox.isSelected();
				searchParamsChanged();
		
				System.out.println("Inclusive : " + inclusive);
			}
		};
		chckbxNewCheckBox.addActionListener(actionListener);
		
		panel.add(chckbxNewCheckBox);*/

	}

	public void searchParamsChanged() {
		jobsFoundLabel.setText("Emplois trouvés : " + table.updateAdvancedSearchChanged(this));
	}

	public boolean accept(Emploi emploi) {

		boolean accept = false;

		if (!isInAdvancedSearchMode) {
			accept = emploi.contains(searchForSimple);
		} else {

			if (StringArrayUtils.isBlank(searchForNumeroDeLoffre) && StringArrayUtils.isBlank(searchForAppellationDeLemploi) && StringArrayUtils.isBlank(searchForEmployeur) && StringArrayUtils.isBlank(searchForNombreDePoste) && StringArrayUtils.isBlank(searchForScolarite)
					&& StringArrayUtils.isBlank(searchForAnnesDexperience) && StringArrayUtils.isBlank(searchForLieuDeTravail)) {
				System.out.println("Is all blank...");
				return true;
			} else {

				boolean m_NumeroDeLoffre = emploi.searchNumeroDeLoffre(searchForNumeroDeLoffre);
				boolean m_AppellationDeLemploi = emploi.searchAppellationDeLemploi(searchForAppellationDeLemploi);
				boolean m_Employeur = emploi.searchEmployeur(searchForEmployeur);
				boolean m_NombreDePoste = emploi.searchNombreDePostes(searchForNombreDePoste);
				boolean m_Scolarite = emploi.searchScolarite(searchForScolarite);
				boolean m_AnnesDexperience = emploi.searchAnnesDexperience(searchForAnnesDexperience);
				boolean m_LieuDeTravail = emploi.searchLieuDeTravail(searchForLieuDeTravail);

				System.out.println(m_NumeroDeLoffre + " " + m_AppellationDeLemploi + " " + m_Employeur + " " + m_NombreDePoste + " " + m_Scolarite + " " + m_Scolarite + " " + m_AnnesDexperience + " " + m_LieuDeTravail);

				if (m_NumeroDeLoffre && m_AppellationDeLemploi && m_Employeur && m_NombreDePoste && m_Scolarite && m_AnnesDexperience && m_LieuDeTravail) {
					accept = true;
				} else {
					accept = false;
				}

			}
		}

		System.out.println(accept);

		return accept;
	}

}

class SearchParam implements DocumentListener {

	AdvancedSearch as;

	JTextArea j;
	Table table;
	int searchType;

	public SearchParam(AdvancedSearch as, JTextArea j, Table table, int searchType) {
		this.as = as;
		this.j = j;
		this.table = table;
		this.searchType = searchType;
	}

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

		/**
		 * 0 = N° de l'offre
		 * 1 = Appellation d'emploi
		 * 2 = Employeur
		 * 3 = Nombre de poste(s)
		 * 4 = Scolarité
		 * 5 = Années d'expérience
		 * 6 = Lieu de travail
		 */

		String[] params = getParamsFor(j.getText());

		switch (searchType) {
		case -1:
			as.searchForSimple = params;
			break;
		case 0:
			as.searchForNumeroDeLoffre = params;
			break;
		case 1:
			as.searchForAppellationDeLemploi = params;
			break;
		case 2:
			as.searchForEmployeur = params;
			break;
		case 3:
			as.searchForNombreDePoste = params;
			break;
		case 4:
			as.searchForScolarite = params;
			break;
		case 5:
			as.searchForAnnesDexperience = params;
			break;
		case 6:
			as.searchForLieuDeTravail = params;
			break;
		default:
			System.err.println("Error, wrong parameter for searchType : " + searchType);
		}

		as.searchParamsChanged();

	}

	private String[] getParamsFor(String keywords) {
		String[] keyWords;

		if (keywords.contains(", ")) {
			keyWords = keywords.split(", ");
		} else if (keywords.contains(",")) {
			keyWords = keywords.split(",");
		} else {
			keyWords = new String[] { keywords };
		}

		return keyWords;
	}

}
