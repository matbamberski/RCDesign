package GUI.ReinforcementDesignLibraryControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import util.ResultsToPDF;


/*
 * Klasa zmieniaj¹ca nazwe pliku do zapisu
 */
public class PdfController {

	public static void addPropertiesToPdfNameTextField(TextField tf) {
		addListenerToPdfNameTF(tf);
		// setInitialPdfName(tf);
	}

	private static void addListenerToPdfNameTF(TextField tf) {
		tf.textProperty().addListener(new PdfInputListener());
	}

	private static class PdfInputListener implements ChangeListener<String> {

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			ResultsToPDF.setPdfName(arg2);

		}
	}

	private static void setInitialPdfName(TextField tf) {
		tf.setText("wyniki");
	}

}
