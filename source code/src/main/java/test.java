import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class test {

	/** Path to the resulting PDF file. */
	// testresult
	public static final String SRC = "C:/Users/Mieciac/Desktop/RectangularBeam.pdf";
	public static final String DEST = "C:/Users/Mieciac/Desktop/aa.pdf";
	private static final String FONT = null;

	/**
	 * Creates a PDF file: hello.pdf
	 * 
	 * @param args
	 *            no arguments needed
	 */

	public static void main(String[] args) throws IOException, DocumentException {
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		new test().manipulatePdf(SRC, DEST);

	}

	public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		PdfReader reader = new PdfReader(src);
		PdfImportedPage page = writer.getImportedPage(reader, 1);
		document.newPage();
		cb.addTemplate(page, 0, 0);
		writer.setCompressionLevel(0);
		Paragraph paragraph1 = new Paragraph("");
		Paragraph paragraph2 = new Paragraph("           Nazwa zadania:");
		paragraph2.setAlignment(Element.ALIGN_LEFT);

		document.add(paragraph1);
		document.add(paragraph2);

		// as1
		cb.beginText();
		cb.moveText(290, 666);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("1");
		cb.endText();

		cb.beginText();
		cb.moveText(341, 665);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("11");
		cb.endText();

		cb.beginText();
		cb.moveText(360, 665);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("16");
		cb.endText();
		// as2
		cb.beginText();
		cb.moveText(290, 653);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("4");
		cb.endText();

		cb.beginText();
		cb.moveText(341, 652);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("11");
		cb.endText();

		cb.beginText();
		cb.moveText(360, 652);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("16");
		cb.endText();
		// stirrups
		cb.beginText();
		cb.moveText(290, 626);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("3");
		cb.endText();

		cb.beginText();
		cb.moveText(360, 640);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("8");
		cb.endText();

		cb.beginText();
		cb.moveText(325, 626);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("3");
		cb.endText();
		// bent rod
		cb.beginText();
		cb.moveText(360, 612);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("8");
		cb.endText();

		cb.beginText();
		cb.moveText(290, 598);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("5");
		cb.endText();

		cb.beginText();
		cb.moveText(325, 598);
		cb.setFontAndSize(BaseFont.createFont(), 10);
		cb.showText("5");
		cb.endText();

		// geometry parameters
		cb.beginText();
		cb.moveText(110, 611);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText("1");
		cb.endText();

		cb.beginText();
		cb.moveText(110, 598);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText("1");
		cb.endText();

		cb.beginText();
		cb.moveText(110, 584);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText("1");
		cb.endText();

		cb.beginText();
		cb.moveText(200, 611);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText("1");
		cb.endText();

		cb.beginText();
		cb.moveText(200, 598);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText("1");
		cb.endText();

		// forces
		cb.beginText();
		cb.moveText(110, 506);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText("123,45");
		cb.endText();

		cb.beginText();
		cb.moveText(110, 490);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText("123,45");
		cb.endText();

		cb.beginText();
		cb.moveText(110, 474);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText("123,45");
		cb.endText();

		document.close();

	}

}
