package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

import emplois.Emploi;
import javax.swing.JScrollPane;

public class JobVisualiser extends JPanel {

	JEditorPane edit1;

	public JobVisualiser() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		edit1 = new JEditorPane("text/html", "Select a value");
		edit1.setEditable(false);
		edit1.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		scrollPane.setViewportView(edit1);

	}

	public void setJob(Emploi e) {

		String modifiedHTML = "<font face=\"verdana\">" + e.html + "</font>";

		edit1.setText(modifiedHTML);

	}

}
