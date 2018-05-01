package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class CompyPdf {
	// public static final String DEST =
	// "C:/Users/Mieciac/Desktop/inzynier/wyniki.pdf";
	public static final String SRC = "D:/Magisterka/Results/Projektowanie.pdf";
	public static final String DEST = "D:/Magisterka/Results/loled.pdf";

	private static String GetExecutionPath() {
		ifPdfNameIsEmptyReturnNewName();
		String absolutePath = CompyPdf.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		absolutePath = absolutePath.replaceAll("%20", " "); // Surely need to do
		// this here
		absolutePath = absolutePath + "/" + pdfName + ".pdf";
		return absolutePath;
	}

	private static ArrayList<String> resultsList = new ArrayList();
	private static ArrayList<String> variableList = new ArrayList();
	private static String pdfName = "wyniki";

	public static void setPdfName(String name) {
		pdfName = name;
	}

	private static void ifPdfNameIsEmptyReturnNewName() {
		if (pdfName.equals("")) {
			pdfName = "wyniki";
		}

	}

	public static void addResults(String variable, String result) {
		variableList.add(variable);
		resultsList.add(result);
	}

	public static void saveResultsToPDF() throws IOException {
		File file = new File(GetExecutionPath());
		file.getParentFile().mkdirs();
		createPdf(GetExecutionPath());
	}

	public static void clearResults() {
		resultsList.clear();
		variableList.clear();
	}

	private static void createPdf(String dest) throws IOException {
		// Initialize PDF writer
		PdfWriter writer = new PdfWriter(dest);
		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		// Initialize document
		Document document = new Document(pdf);
		// Add paragraph to the document
		for (int i = 0; i < resultsList.size(); i++) {
			document.add(new Paragraph(variableList.get(i) + " " + resultsList.get(i)));
		}
		// Close document
		document.close();
	}
}