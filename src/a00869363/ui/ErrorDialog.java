package a00869363.ui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ErrorDialog extends JDialog {

	private static final long serialVersionUID = 1L;


	/**
	 * Create the dialog.
	 */
	public ErrorDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

}
