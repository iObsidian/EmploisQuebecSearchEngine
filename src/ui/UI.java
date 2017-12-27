package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import emplois.Emploi;
import i18n.Table;
import java.awt.Toolkit;

public class UI extends JFrame {

	Table table = new Table();
	LoadingScreen loadingScreen;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UI.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/capslock-icon.png")));
		setTitle("Placement en Ligne Emplois Qu\u00E9bec | Outil de recherche");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		contentPane.add(table, BorderLayout.CENTER);

		JPanel search = new JPanel();
		contentPane.add(search, BorderLayout.SOUTH);
		search.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		search.add(panel, BorderLayout.WEST);

		Component horizontalStrut = Box.createHorizontalStrut(1);
		panel.add(horizontalStrut);

		JLabel lblNewLabel_1 = new JLabel("Recherche : ");
		lblNewLabel_1.setToolTipText("Recherche par mots clef");
		panel.add(lblNewLabel_1);

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
				
				table.filterByKeyword(keyWords);

			}
		});

		search.add(textArea_1, BorderLayout.CENTER);

		loadingScreen = new LoadingScreen(this);
	}

	public void loadingEnded(List<Emploi> emplois) {
		System.out.println("Emplois : " + emplois.size());

		table.setTableData(emplois, false);

	}

}
