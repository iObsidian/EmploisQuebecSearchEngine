package ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JobVisualiser extends JPanel {

	Job job;

	Rectangle size = new Rectangle();

	JEditorPane edit1;

	public JobVisualiser() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		edit1 = new JEditorPane("text/html", "Waiting...");
		edit1.setEditable(false);
		edit1.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE); //The magic line of code
		edit1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		scrollPane.setViewportView(edit1);

		JButton btnVisiterSurEmplois_1 = new JButton("Visiter sur Emplois Quebec");
		btnVisiterSurEmplois_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (job != null) {
					try {
						Desktop.getDesktop().browse(new URI(job.url));
					} catch (IOException error) {
						error.printStackTrace();
					} catch (URISyntaxException error) {
						error.printStackTrace();
					}
				}
			}
		});
		add(btnVisiterSurEmplois_1, BorderLayout.SOUTH);

	}

	public void setJob(Job e) {

		job = e;

		String modifiedHTML = "<font face=\"Arial\">" + e.html + "</font>";

		edit1.setText(modifiedHTML);
		edit1.setCaretPosition(0); //scroll back to top

		edit1.revalidate();
		edit1.repaint();

	}

}
