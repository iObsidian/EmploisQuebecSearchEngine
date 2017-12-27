package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import emplois.Emploi;

public class UI extends JFrame {

	AdvancedSearch as = new AdvancedSearch();
	Table table = new Table();

	private JPanel contentPane;
	private JPanel search;

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

		JPanel searchPanel = new JPanel();
		table.add(searchPanel, BorderLayout.SOUTH);
		searchPanel.setLayout(new BorderLayout(0, 0));

		//

		searchPanel.add(as.getSimpleSearchPanel(), BorderLayout.NORTH);
		searchPanel.add(as, BorderLayout.SOUTH);

	}

	public void loadingEnded(List<Emploi> emplois) {
		setVisible(true);

		System.out.println("Emplois : " + emplois.size());

		table.setTableData(emplois, false);

	}

}
