package autoCompletion;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;

/**
 * An example on how to use the {@link AutoCompleteDocument}.
 * 
 * @see AutoCompleteDocument
 * @see CompletionService
 * 
 * @author Samuel Sjoberg, http://samuelsjoberg.com
 * @version 1.0.0
 */
public class CompletionSample implements Runnable {

	/**
	 * Create the GUI and display it to the world.
	 */
	public void run() {
		// Create the completion service.
		NameService nameService = new NameService();
		nameService.setValues(Arrays.asList("Jacob", "Emma", "Michael", "Isabella", "Ethan", "Emily", "Joshua", "Madison", "Daniel", "Ava", "Alexander", "Olivia", "Anthony", "Sophia", "William", "Abigail", "Christopher", "Elizabeth", "Matthew", "Chloe"));

		// Create the input field.
		JTextField input = new JTextField();
		Document autoCompleteDocument = new AutoCompleteDocument(nameService, input);
		input.setDocument(autoCompleteDocument);

		// Below we just create a frame and display the GUI.

		JFrame frame = new JFrame("Autocompletion sample");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel description = new JLabel();
		description.setText("<html><b>Autocompletion sample for the most<br>" + " popular baby names (2008)</b><br><br>" + nameService.toString().replace("\n", "<br>") + "<br>");

		JComponent contentPane = (JComponent) frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(input, BorderLayout.SOUTH);
		contentPane.add(description, BorderLayout.CENTER);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Application entry point.
	 * 
	 * @param args
	 *            not in use
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new CompletionSample());
	}

	/**
	 * A simple {@link CompletionService} providing completion for most popular
	 * baby names 2008.
	 */
	private static class NameService implements CompletionService<String> {

		/** Our name data. */
		private List<String> data;

		public void setValues(List<String> data) {
			this.data = data;
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			for (String o : data) {
				b.append(o).append("\n");
			}
			return b.toString();
		}

		/** {@inheritDoc} */
		public String autoComplete(String startsWith) {
			// Naive implementation, but good enough for the sample
			String hit = null;
			for (String o : data) {
				if (o.startsWith(startsWith)) {
					// CompletionService contract states that we only
					// should return completion for unique hits.
					if (hit == null) {
						hit = o;
					} else {
						hit = null;
						break;
					}
				}
			}
			return hit;
		}

	}
}
