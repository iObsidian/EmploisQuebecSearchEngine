package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JobVisualiser extends JPanel {

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

	}

	public void setJob(Job e) {

		String modifiedHTML = "<font face=\"Arial\">" + e.html + "</font>";

		edit1.setText(modifiedHTML);
		edit1.setCaretPosition(0); //scroll back to top

		edit1.revalidate();
		edit1.repaint();

	}

}
