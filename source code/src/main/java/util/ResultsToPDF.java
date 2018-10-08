package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import SLS.Sls;
import SLS.cracks.Scratch;
import SLS.creepCoeficent.CreepCoeficent;
import SLS.deflection.DeflectionControl;
import diagnosis.DiagnosisMainAlgorithm;
import javafx.scene.control.CheckBox;
import mainalgorithm.ForcesCombination;
import mainalgorithm.InternalForces;
import mainalgorithm.NominalStiffness;
import mainalgorithm.Reinforcement;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.shearing.ShearingReinforcementAnalizer;

public class ResultsToPDF {

	/** Path to the resulting PDF file. */

	private static String GetExecutionPathDesign() {
		ifPdfNameIsEmptyReturnNewName();
		String absolutePath = CompyPdf.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		absolutePath = absolutePath.replaceAll("%20", " "); // Surely need to do
		// this here
		absolutePath = absolutePath + "/" + pdfName + "_p.pdf";
		return absolutePath;
	}

	private static String GetExecutionPathDiagnosis() {
		ifPdfNameIsEmptyReturnNewName();
		String absolutePath = CompyPdf.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		absolutePath = absolutePath.replaceAll("%20", " "); // Surely need to do
		// this here
		absolutePath = absolutePath + "/" + pdfName + "_d.pdf";
		return absolutePath;
	}

	private static String GetExecutionPathForSecondFile() {
		ifPdfNameIsEmptyReturnNewName();
		String absolutePath = CompyPdf.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		absolutePath = absolutePath.replaceAll("%20", " "); // Surely need to do
		// this here
		absolutePath = absolutePath + "/" + pdfName + "WynikiPosrednie.pdf";
		return absolutePath;
	}

	private static String DEST = "C:/Users/Mieciac/Desktop/testresult.pdf";
	private static String pdfName = "Wyniki";

	public static void setPdfName(String name) {
		pdfName = name;
	}

	private static void ifPdfNameIsEmptyReturnNewName() {
		if (pdfName.equals("")) {
			pdfName = "Wyniki";
		}
	}

	public static void saveDesingResultsToPDF(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, Scratch scratch,
			CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox, NominalStiffness stiffness,
			Cement cement)
			throws IOException, DocumentException {
		if (dimensions.getIsColumn()) {
			saveRectangularBeamReinforcementManyForces(withTable, concrete, steel, reinforcement, forces, 
					dimensions, sls, scratch, creep, deflection, nominalCheckBox, stiffness, cement);
		} else if (dimensions.getisBeamRectangular()) {
			if (forces.getnEd() == 0) {
				nominalCheckBox.setSelected(false);
				saveRectangularBeamReinforcement(withTable, concrete, steel, reinforcement, forces, dimensions, sls, scratch,
						creep, deflection, nominalCheckBox, stiffness, cement);
			} else {
				saveBeamAxisLoadResulsts(withTable, concrete, steel, reinforcement, forces, dimensions, sls, scratch, creep,
						deflection, nominalCheckBox, stiffness, cement);
			}
		} else {
			nominalCheckBox.setSelected(false);
			saveTShapedBeamResulsts(withTable, concrete, steel, reinforcement, forces, dimensions, sls, scratch, deflection, creep,
					nominalCheckBox, stiffness, cement);
		}
		// printResultsForJacekIntoSecondFile();
	}
	
	public static void saveRectangularBeamReinforcementManyForces(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, Scratch scratch,
			CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement)
			throws IOException, DocumentException {
		String SRC = "plates/BeamAxisLoadManyForces.pdf";
		File file = new File(SRC);
		file.getParentFile().mkdirs();
		printRectangularBeamResultsManyForces(withTable, SRC, GetExecutionPathDesign(), concrete, steel, reinforcement, forces, dimensions,
				sls, scratch, creep, deflection, nominalCheckBox, stiffness, cement);
	}
	
	public static void printRectangularBeamResultsManyForces(boolean withTable, String src, String dest, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			Scratch scratch, CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox,
			NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
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
		Paragraph paragraph2 = new Paragraph("Nazwa zadania: " + pdfName);
		paragraph2.setAlignment(Element.ALIGN_LEFT);

		document.add(paragraph1);
		document.add(paragraph2);
		
		addParamGeom(cb, dimensions, forces);
		addParamMaterials(cb, concrete, steel, cement);
		addReinforcementDesign(cb, reinforcement, 55, true);
		addManyForces(cb, forces, 0);
		if(withTable)
		additionalParametersAxisLoad(src, dest, concrete, steel, reinforcement, forces, dimensions,
				sls, scratch, creep, deflection, nominalCheckBox, stiffness, cb,
				forces.getvRdCdesign(), forces.getvRdSdesign(), forces.getvRdMaxdesign(), 350, 279, forces.getCombinations());
		
		document.close();
	}
	
	public static PdfContentByte addManyForces(PdfContentByte cb, InternalForces forces, float offset) throws DocumentException, IOException {
		float lineWidth = (float) 12.5;
		int fontSize = 11;
		float startY = (float) 585 - offset;
		float startX = 215;
		int step = 150;
		
		ArrayList<ForcesCombination> combinations = forces.getForcesCombinations();
		for (ForcesCombination combination : combinations) {
			// Med
			cb.beginText();
			cb.moveText(startX, startY);
			cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(String.format("%.2f", combination.getM()));
			cb.endText();

			// Ned
			cb.beginText();
			cb.moveText(290, startY);
			cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(String.format("%.2f", combination.getN()));
			cb.endText();

			startY -= 13;
		}
		startX = 150;
		startY-=8;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.forcesFormatter(forces.getvEd()));
		cb.endText();
		
		startX+= 10;
		startY-=lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.forcesFormatter(forces.getvEdRed()));
		cb.endText();
	
		return cb;
	}
	

	public static void saveRectangularBeamReinforcement(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, Scratch scratch,
			CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox, NominalStiffness stiffness, 
			Cement cement)
			throws IOException, DocumentException {
		String SRC = "plates/RectangularBeam.pdf";
		File file = new File(SRC);
		file.getParentFile().mkdirs();
		printRectangularBeamResults(withTable, SRC, GetExecutionPathDesign(), concrete, steel, reinforcement, forces, dimensions,
				sls, scratch, creep, deflection, nominalCheckBox, stiffness, cement);
	}

	public static void printRectangularBeamResults(boolean withTable, String src, String dest, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			Scratch scratch, CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox,
			NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
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
		Paragraph paragraph2 = new Paragraph("Nazwa zadania: " + pdfName);
		paragraph2.setAlignment(Element.ALIGN_LEFT);

		document.add(paragraph1);
		document.add(paragraph2);

		// forces
		
		addForces(cb, forces);
		addParamGeom(cb, dimensions, forces);
		addParamMaterials(cb, concrete, steel, cement);
		addReinforcementDesignClearBending(cb, reinforcement, 2, true);
		addWFtoTable(cb, sls, 8);
		if (withTable)
		addTableDesign(cb, concrete, steel, reinforcement, forces, dimensions,
				sls, scratch, creep, deflection, nominalCheckBox, stiffness,
				forces.getvRdCdesign(), forces.getvRdSdesign(), forces.getvRdMaxdesign(), forces.getVrds1design(), forces.getVrds2design(),
				125, 287);

		///////////// PARAMETRY DODATKOWE /////////////
		//additionalParameters(src, dest, concrete, steel, reinforcement, forces, dimensions, sls, scratch, creep,
		//		deflection, nominalCheckBox, stiffness, cb, forces.getvRdCdesign(), 123456, forces.getvRdMaxdesign());

		document.close();
	}

	public static void saveTShapedBeamResulsts(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, Scratch scratch,
			DeflectionControl deflection, CreepCoeficent creep, CheckBox nominalCheckBox, NominalStiffness stiffness,
			Cement cement)
			throws IOException, DocumentException {
		String SRC = "plates/TShapedBeam.pdf";
		File file = new File(SRC);
		file.getParentFile().mkdirs();
		printTShapedBeamResults(withTable, SRC, GetExecutionPathDesign(), concrete, steel, reinforcement, forces, dimensions, sls,
				scratch, deflection, creep, nominalCheckBox, stiffness, cement);
	}

	private static void printTShapedBeamResults(boolean withTable, String src, String dest, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			Scratch scratch, DeflectionControl deflection, CreepCoeficent creep, CheckBox nominalCheckBox,
			NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {

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
		Paragraph paragraph2 = new Paragraph("Nazwa zadania: " + pdfName);
		paragraph2.setAlignment(Element.ALIGN_LEFT);

		document.add(paragraph1);
		document.add(paragraph2);
		// forces
		
		addForces(cb, forces);
		addParamGeomTshaped(cb, dimensions, forces);
		addParamMaterials(cb, concrete, steel, cement);
		addReinforcementDesignClearBending(cb, reinforcement, 1, true);
		addWFtoTable(cb, sls, 8);
		if(withTable)
		addTableDesign(cb, concrete, steel, reinforcement, forces, dimensions, sls, scratch,
				creep, deflection, nominalCheckBox, stiffness,
				forces.getvRdCdesign(), forces.getvRdSdesign(), forces.getvRdMaxdesign(), forces.getVrds1design(), forces.getVrds2design(),
				125, 298);

		///////////// PARAMETRY DODATKOWE /////////////
		//additionalParameters(src, dest, concrete, steel, reinforcement, forces, dimensions, sls, scratch, creep,
		//		deflection, nominalCheckBox, stiffness, cb, forces.getvRdCdesign(), 123456, forces.getvRdMaxdesign());

		document.close();
	}

	public static void saveBeamAxisLoadResulsts(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, Scratch scratch,
			CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox, NominalStiffness stiffness, 
			Cement cement)
			throws IOException, DocumentException {
		String SRC = "plates/BeamAxisLoad.pdf";
		File file = new File(SRC);
		file.getParentFile().mkdirs();
		printBeamAxisLoadBeamResults(withTable, SRC, GetExecutionPathDesign(), concrete, steel, reinforcement, forces, dimensions,
				sls, scratch, creep, deflection, nominalCheckBox, stiffness, cement);
	}

	private static void printBeamAxisLoadBeamResults(boolean withTable, String src, String dest, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			Scratch scratch, CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox,
			NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {

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
		Paragraph paragraph2 = new Paragraph("Nazwa zadania: " + pdfName);
		paragraph2.setAlignment(Element.ALIGN_LEFT);

		document.add(paragraph1);
		document.add(paragraph2);
		// forces

		addForces(cb, forces);
		addParamGeom(cb, dimensions, forces);
		addParamMaterials(cb, concrete, steel, cement);
		addReinforcementDesign(cb, reinforcement, 2, forces.getmEd()>0 ? true : false);
		if(withTable)
		additionalParametersAxisLoad(src, dest, concrete, steel, reinforcement, forces,
				dimensions, sls, scratch, creep, deflection, nominalCheckBox, stiffness, cb,
				forces.getvRdCdesign(), forces.getvRdSdesign(), forces.getvRdMaxdesign(), 350, 296, forces.getPdfCombinations());
		
	
		///////////// PARAMETRY DODATKOWE /////////////
		//additionalParameters(src, dest, concrete, steel, reinforcement, forces, dimensions, sls, scratch, creep,
		//		deflection, nominalCheckBox, stiffness, cb, forces.getvRdCdesign(), 123456, forces.getvRdMaxdesign());

		//
		document.close();
	}
	
	private static void additionalParametersAxisLoad(String src, String dest, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			Scratch scratch, CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox,
			NominalStiffness stiffness, PdfContentByte cb, double vRdC, double vRdS, double vRdMax, float x1, float y1,
			ArrayList<ForcesCombination> combinations)
			throws IOException, DocumentException {
		
		float ymin = 13.1f;
		float marginX = x1 - 125;
		float marginY = y1 - 68;
		/*
		 * double vRdC; double vRdS; double vRdMax;
		 */
		String accuracy = "%.04f";
		//ArrayList<ForcesCombination> combinations = forces.getPdfCombinations();
		if (combinations != null)
		for (int i = 0; i< combinations.size(); i++) {
			if (combinations.get(i).getN()==0 && combinations.get(i).getM()==0) {
				combinations.remove(i);
				i--;
			}
		}
		
		cb.beginText();
		cb.moveText(x1, y1 - (0 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.5f", dimensions.getxC()));
		cb.endText();
		
		cb.beginText();
		cb.moveText(x1, y1 - (1 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.6f", dimensions.getAc()));
		cb.endText();
		
		
		cb.beginText();
		cb.moveText(x1, y1 - (2 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.12f", dimensions.getIc()));
		cb.endText();
		
		cb.beginText();
		cb.moveText(x1, y1 - (3 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, creep.getCreepCoeficent()));
		cb.endText();
		
		cb.beginText();
		cb.moveText(x1, y1 - (4 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (nominalCheckBox.isSelected() && stiffness.getFiEf()!=0.0) {
			cb.showText(String.format(accuracy, stiffness.getFiEf()));
		} else if (stiffness.getFiEf()!=0.0)
			cb.showText(String.format(accuracy, stiffness.getFiEf()));
		else cb.showText(String.format(accuracy, creep.getCreepCoeficent()));
		cb.endText();
		if (combinations != null && combinations.size()>0)
			for (int i = 0; i<combinations.size(); i++) {
				printOneCombination(cb, vRdC, vRdS, vRdMax, i, 85, marginX, marginY,
						combinations.get(i).getRs().getNbsym(), combinations.get(i).getRs().getXsym(), combinations.get(i).getRs().getEIcsym(), combinations.get(i).getRs().getEIssym(),
						combinations.get(i).getRs().getXunsym(), combinations.get(i).getRs().getNbunsym(), combinations.get(i).getRs().getEIcunsym(), combinations.get(i).getRs().getEIsunsym());
			}
		else {
			
			printOneCombination(cb, vRdC, vRdS, vRdMax, 0, 85, marginX, marginY,
					forces.getnCritSymmetrical(), forces.getAxisLoadToPdf().getRs().getXsym(), forces.getAxisLoadToPdf().getRs().getEIcsym(), forces.getAxisLoadToPdf().getRs().getEIssym(),
					forces.getAxisLoadToPdf().getRs().getXunsym(), forces.getnCritUnsymmetrical(), forces.getAxisLoadToPdf().getRs().getEIcunsym(), forces.getAxisLoadToPdf().getRs().getEIsunsym());		
		}
		
		

		
	}

	private static void additionalParameters(String src, String dest, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			Scratch scratch, CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox,
			NominalStiffness stiffness, PdfContentByte cb, double vRdC, double vRdS, double vRdMax)
			throws IOException, DocumentException {
		int x1 = 155;
		int x2 = 397;
		int y1 = 216;
		float ymin = 12.7f;
		/*
		 * double vRdC; double vRdS; double vRdMax;
		 */
		String accuracy = "%.07f";
		ArrayList<ForcesCombination> combinations = forces.getForcesCombinations();
		for (int i = 0; i< combinations.size(); i++) {
			if (combinations.get(i).getN()==0 && combinations.get(i).getM()==0) {
				combinations.remove(i);
				i--;
			}
		}
		
		cb.beginText();
		cb.moveText(x1, y1 - (0 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, dimensions.getxC()));
		cb.endText();
		
		cb.beginText();
		cb.moveText(x1, y1 - (1 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, dimensions.getAc()));
		cb.endText();
		
		
		cb.beginText();
		cb.moveText(x1, y1 - (2 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, dimensions.getIc() * Math.pow(100, 4)));
		cb.endText();
		
		cb.beginText();
		cb.moveText(x1, y1 - (3 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, creep.getCreepCoeficent()));
		cb.endText();
		
		cb.beginText();
		cb.moveText(x2, y1 - (5 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (nominalCheckBox.isSelected()) {
			cb.showText(String.format(accuracy, stiffness.getFiEf()));
		} else cb.showText(String.format(accuracy,creep.getCreepCoeficent()));
		cb.endText();
		
/*
		cb.beginText();
		cb.moveText(x1, y1 - (2 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, dimensions.getI1() * Math.pow(100, 4)));
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (3 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, dimensions.getX1()));
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (4 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, dimensions.getI2() * Math.pow(100, 4)));
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (5 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, dimensions.getX2()));
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (6 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);

		if (forces.getnEd() == 0) {
			if (!dimensions.getIsColumn()) {
				cb.showText(String.format(accuracy, scratch.getSigmaS()));
			} else {
				cb.showText("-,--");
			}
		} else {
			cb.showText("-,--");
		}

		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (6 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (forces.getnEd() == 0) {
			if (!dimensions.getIsColumn()) {
				cb.showText(String.format(accuracy, scratch.getsRMax()));
			} else {
				cb.showText("-,--");
			}
		} else {
			cb.showText("-,--");
		}
		cb.endText();
*/
		

		cb.beginText();
		cb.moveText(x1, y1 - (7 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (forces.getnEd() == 0) {
			if (!dimensions.getIsColumn()) {
				cb.showText(String.format(accuracy, deflection.getEpsilonCS()));
			} else {
				cb.showText("-,--");
			}
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (0 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (nominalCheckBox.isSelected()) {
			cb.showText(String.format(accuracy, forces.getnCritSymmetrical()));
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (2 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (nominalCheckBox.isSelected()) {
			cb.showText(String.format(accuracy, forces.getnCritUnsymmetrical()));
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (1 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, reinforcement.getDegreeOfDesignedSymmetricalReinforcement() * 100));
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (3 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement() * 100));
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (8 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (forces.getnEd() == 0) {
			if (!dimensions.getIsColumn()) {
				cb.showText(String.format(accuracy, deflection.getB1()));
			} else {
				cb.showText("-,--");
			}
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (9 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (forces.getnEd() == 0) {
			if (!dimensions.getIsColumn()) {
				cb.showText(String.format(accuracy, deflection.getB2()));
			} else {
				cb.showText("-,--");
			}
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (10 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (forces.getnEd() == 0) {
			if (!dimensions.getIsColumn()) {
				cb.showText(String.format(accuracy, scratch.getRoPEff() * 100));
			} else {
				cb.showText("-,--");
			}
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (11 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (forces.getnEd() == 0) {
			if (!dimensions.getIsColumn()) {
				cb.showText(String.format(accuracy, scratch.getEpsilonSmMinusEpsilonCm()));
			} else {
				cb.showText("-,--");
			}
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (12 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (forces.getnEd() == 0) {
			if (!dimensions.getIsColumn()) {
				cb.showText(String.format(accuracy, scratch.getaCEff()));
			} else {
				cb.showText("-,--");
			}
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (13 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (nominalCheckBox.isSelected()) {
			cb.showText(String.format(accuracy, stiffness.geteIc()));
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (12 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (nominalCheckBox.isSelected()) {
			cb.showText(String.format(accuracy, stiffness.geteIs()));
		} else {
			cb.showText("-,--");
		}
		cb.endText();

		

		cb.beginText();
		cb.moveText(x2, y1 - (7 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, vRdC));
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (8 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, vRdMax));
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (9 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (vRdS == 123456) {
			cb.showText("-,--");
		} else {
			cb.showText(String.format(accuracy, vRdS));
		}
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (10 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, dimensions.getS1() * Math.pow(100, 3)));
		cb.endText();

		cb.beginText();
		cb.moveText(x1, y1 - (11 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, dimensions.getS2() * Math.pow(100, 3)));
		cb.endText();

		cb.beginText();
		cb.moveText(x2, y1 - (13 * ymin));
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText("sigmaCp: " + String.format(accuracy, forces.getSigmaCP()) + " [MPa]");
		cb.endText();
	}
	
	public static PdfContentByte printOneCombination(PdfContentByte cb, double Vrdc, double Vrds,
			double Vrdmax, int column, float width, float margin, float startY,
			double Nbsym, double Xsym, double EIcsym, double EIssym, 
			double Xunsym, double Nbunsym, double EIcunsym, double EIsunsym) throws DocumentException, IOException {
		float lineWidth = (float) 13.1;
		float x = margin + column*width;
		String accuracy = "%.3f";
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, Vrdc));
		cb.endText();
		
		startY -= lineWidth;

		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		if (Vrds == 123456) {
			cb.showText("");
		} else {
			cb.showText(String.format(accuracy, Vrds));
		}
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, Vrdmax));
		cb.endText();

		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.2f", Nbsym));
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.5f", Xsym));
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.7f", EIcsym));
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.7f", EIssym));
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.5f", Xunsym));
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.2f", Nbunsym));
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.7f", EIcunsym));
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(x, startY);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format("%.7f", EIsunsym));
		cb.endText();
		
		return cb;
	}

	public static void saveDiagnosisResultsToPDF(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			DiagnosisMainAlgorithm diagnosis, Scratch scratch, CreepCoeficent creep, DeflectionControl deflection,
			CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
		if (dimensions.getIsColumn()) {
			int mAndN = forces.checkHowManyCombinationsWithMandN();
			if (mAndN == 0) {
				saveAxisLoadBeamDiagnosis(withTable, concrete, steel, reinforcement, forces, dimensions, sls, diagnosis, scratch,
						creep, deflection, nominalCheckBox, stiffness, cement);
			} else if (mAndN > 0) {
				saveAxisLoadColumnDiagnosis(withTable, concrete, steel, reinforcement, forces, dimensions, sls, diagnosis, scratch,
						creep, deflection, nominalCheckBox, stiffness, cement);
			} else {
				int mOrN = forces.checkHowManyCombinationsWithMorN();
				if (mOrN == 0 && forces.getnEd() == 0) {
					saveRectangularBeamDiagnosis(withTable, concrete, steel, reinforcement, forces, dimensions, sls, diagnosis,
							scratch, creep, deflection, nominalCheckBox, stiffness, cement);
				} else if (mOrN == 0 && forces.getnEd() != 0) {
					saveAxisLoadBeamDiagnosis(withTable, concrete, steel, reinforcement, forces, dimensions, sls, diagnosis,
							scratch, creep, deflection, nominalCheckBox, stiffness, cement);
				} else if (mOrN > 0) {
					saveAxisLoadColumnDiagnosis(withTable, concrete, steel, reinforcement, forces, dimensions, sls, diagnosis,
							scratch, creep, deflection, nominalCheckBox, stiffness, cement);
				}
			}
		} else if (dimensions.getisBeamRectangular()) {
			if (forces.getnEd() == 0) {
				saveRectangularBeamDiagnosis(withTable, concrete, steel, reinforcement, forces, dimensions, sls, diagnosis,
						scratch, creep, deflection, nominalCheckBox, stiffness, cement);
			} else {
				saveAxisLoadBeamDiagnosis(withTable, concrete, steel, reinforcement, forces, dimensions, sls, diagnosis, scratch,
						creep, deflection, nominalCheckBox, stiffness, cement);
			}
		} else {
			saveRectangularTShapedBeamDiagnosis(withTable, concrete, steel, reinforcement, forces, dimensions, sls, diagnosis,
					scratch, creep, deflection, nominalCheckBox, stiffness, cement);
		}
	}

	public static void saveRectangularBeamDiagnosis(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			DiagnosisMainAlgorithm diagnosis, Scratch scratch, CreepCoeficent creep, DeflectionControl deflection,
			CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
		String SRC = "plates/BeamBendingDiagnosis.pdf";
		File file = new File(SRC);
		file.getParentFile().mkdirs();
		printRectangularBeamResultsDiagnosis(withTable, SRC, GetExecutionPathDiagnosis(), concrete, steel, reinforcement, forces,
				dimensions, sls, diagnosis, scratch, creep, deflection, nominalCheckBox, stiffness, cement);
	}

	public static void printRectangularBeamResultsDiagnosis(boolean withTable, String src, String dest, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			DiagnosisMainAlgorithm diagnosis, Scratch scratch, CreepCoeficent creep, DeflectionControl deflection,
			CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
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
		Paragraph paragraph2 = new Paragraph("Nazwa zadania: " + pdfName);
		paragraph2.setAlignment(Element.ALIGN_LEFT);

		document.add(paragraph1);
		document.add(paragraph2);
		// forces
		
		
		cb.beginText();
		cb.moveText(215, 478);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(OutputFormatter.diagnosisMed(diagnosis.getmRdDesignedSymmetrical()));
		cb.endText();

		cb.beginText();
		cb.moveText(215, 436);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(OutputFormatter.diagnosisVedAndNed(diagnosis.getvRdDesigned()));
		cb.endText();
		
		addForces(cb, forces);
		addParamGeom(cb, dimensions, forces);
		addParamMaterials(cb, concrete, steel, cement);
		addReinforcementDiagnosis(cb, reinforcement, 0);
		addWFtoTable(cb, sls, 0);
		if (withTable)
		addTableDesign(cb, concrete, steel, reinforcement, forces, dimensions,
				sls, scratch, creep, deflection, nominalCheckBox, stiffness,
				forces.getvRdCdiagnosis(), forces.getvRdSdiagnosis(), forces.getvRdMaxdiagnosis(), forces.getVrds1diagnosis(), forces.getVrds2diagnosis(),
				125, 281);
		
		// sls
		///////////// PARAMETRY DODATKOWE /////////////
		//additionalParameters(src, dest, concrete, steel, reinforcement, forces, dimensions, sls, scratch, creep,
		//		deflection, nominalCheckBox, stiffness, cb, forces.getvRdCdiagnosis(), forces.getvRdSdiagnosis(),
		//		forces.getvRdMaxdiagnosis());

		document.close();
	}
	
	public static PdfContentByte addWFtoTable(PdfContentByte cb, Sls sls, float offset) throws DocumentException, IOException {
		float startY = 373 - offset;
		float lineWidth = (float)13;
		float startX = 275;
		float step = 125;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(String.format("%.03f", sls.getwSymmetricalRequired()));
		cb.endText();

		startX += step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(String.format("%.03f", sls.getwSymmetricalDesigned()));
		cb.endText();
		
		startX-=step;
		startY-=lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(String.format("%.03f", sls.getfSymmetricalRequiredWithoutShrinkage()*100));
		cb.endText();
		
		startX+= step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(String.format("%.03f", sls.getfSymmetricalDesignedWithoutShrinkage()*100));
		cb.endText();
		
		startY-=lineWidth;
		startX-=step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(String.format("%.03f", sls.getfSymmetricalRequired()*100));
		cb.endText();
		
		startX+= step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(String.format("%.03f", sls.getfSymmetricalDesigned()*100));
		cb.endText();
		
		return cb;
	}

	public static void saveRectangularTShapedBeamDiagnosis(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			DiagnosisMainAlgorithm diagnosis, Scratch scratch, CreepCoeficent creep, DeflectionControl deflection,
			CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
		String SRC = "plates/TBeamBendingDiagnosis.pdf";
		File file = new File(SRC);
		file.getParentFile().mkdirs();
		printRectangularTshapedBeamResultsDiagnosis(withTable, SRC, GetExecutionPathDiagnosis(), concrete, steel, reinforcement,
				forces, dimensions, sls, diagnosis, scratch, creep, deflection, nominalCheckBox, stiffness, cement);
	}

	public static void printRectangularTshapedBeamResultsDiagnosis(boolean withTable, String src, String dest, Concrete concrete,
			Steel steel, Reinforcement reinforcement, InternalForces forces,
			DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, DiagnosisMainAlgorithm diagnosis, Scratch scratch,
			CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement)
			throws IOException, DocumentException {
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
		Paragraph paragraph2 = new Paragraph("Nazwa zadania: " + pdfName);
		paragraph2.setAlignment(Element.ALIGN_LEFT);

		document.add(paragraph1);
		document.add(paragraph2);
		// forces

		addForces(cb, forces);
		addParamGeomTshaped(cb, dimensions, forces);
		addParamMaterials(cb, concrete, steel, cement);
		addWFtoTable(cb, sls, 26);
		addReinforcementDiagnosis(cb, reinforcement, 25);
		if(withTable)
		addTableDesign(cb, concrete, steel, reinforcement, forces, dimensions,
				sls, scratch, creep, deflection, nominalCheckBox, stiffness,
				forces.getvRdCdiagnosis(), forces.getvRdSdiagnosis(), forces.getvRdMaxdiagnosis(), forces.getVrds1diagnosis(), forces.getVrds2diagnosis(),
				125, 281);
		
		cb.beginText();
		cb.moveText(215, 453);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(OutputFormatter.diagnosisMed(diagnosis.getmRdDesignedSymmetrical()));
		cb.endText();

		cb.beginText();
		cb.moveText(215, 411);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(OutputFormatter.diagnosisVedAndNed(diagnosis.getvRdDesigned()));
		cb.endText();
		

		// sls

		///////////// PARAMETRY DODATKOWE /////////////
		//additionalParameters(src, dest, concrete, steel, reinforcement, forces, dimensions, sls, scratch, creep,
		//		deflection, nominalCheckBox, stiffness, cb, forces.getvRdCdiagnosis(), forces.getvRdSdiagnosis(),
		//		forces.getvRdMaxdiagnosis());
		
		document.close();
	}

	public static void saveAxisLoadBeamDiagnosis(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			DiagnosisMainAlgorithm diagnosis, Scratch scratch, CreepCoeficent creep, DeflectionControl deflection,
			CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
		String SRC = "plates/BeamAxisLoadDiagnosis.pdf";
		File file = new File(SRC);
		file.getParentFile().mkdirs();
		printAxisLoadResultsDiagnosis(withTable, SRC, GetExecutionPathDiagnosis(), concrete, steel, reinforcement, forces,
				dimensions, sls, diagnosis, scratch, creep, deflection, nominalCheckBox, stiffness, cement);
	}

	public static void printAxisLoadResultsDiagnosis(boolean withTable, String src, String dest, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			DiagnosisMainAlgorithm diagnosis, Scratch scratch, CreepCoeficent creep, DeflectionControl deflection,
			CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
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
		Paragraph paragraph2 = new Paragraph("Nazwa zadania: " + pdfName);
		paragraph2.setAlignment(Element.ALIGN_LEFT);

		document.add(paragraph1);
		document.add(paragraph2);
		// forces

		cb.beginText();
		cb.moveText(110, 478);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(OutputFormatter.diagnosisMed(diagnosis.getmRdDesignedSymmetrical()));
		cb.endText();

		cb.beginText();
		cb.moveText(110, 465);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(OutputFormatter.diagnosisVedAndNed(diagnosis.getnRdDesignedSymmetrical()));
		cb.endText();

		cb.beginText();
		cb.moveText(110, 424);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(OutputFormatter.diagnosisVedAndNed(diagnosis.getvRdDesigned()));
		cb.endText();

		addParamMaterials(cb, concrete, steel, cement);
		// geometry parameters
		
		addParamGeom(cb, dimensions, forces);
		
		addReinforcementDiagnosis(cb, reinforcement, 0);
		
		addForces(cb, forces);
		if (withTable)
		additionalParametersAxisLoad(src, dest, concrete, steel, reinforcement, forces, dimensions,
				sls, scratch, creep, deflection, nominalCheckBox, stiffness, cb, forces.getvRdCdiagnosis(), forces.getvRdSdiagnosis(), forces.getvRdMaxdiagnosis(),
				350f, 295f, forces.getPdfCombinations());

		// sls
		///////////// PARAMETRY DODATKOWE /////////////
		//additionalParameters(src, dest, concrete, steel, reinforcement, forces, dimensions, sls, scratch, creep,
		//		deflection, nominalCheckBox, stiffness, cb, forces.getvRdCdiagnosis(), forces.getvRdSdiagnosis(),
		//		forces.getvRdMaxdiagnosis());

		document.close();
	}

	public static PdfContentByte addParamMaterials(PdfContentByte cb, Concrete concrete, Steel steel, Cement cement)
			throws DocumentException, IOException {
		float lineWidth = (float) 12.5;
		int fontSize = 11;
		float startY = (float) 694.5;
		cb.beginText();
		cb.moveText(85, startY);
		cb.setFontAndSize(BaseFont.createFont(), fontSize);
		cb.showText(concrete.getConcreteClass());
		cb.endText();

		startY -= lineWidth;

		cb.beginText();
		cb.moveText(95, startY);
		cb.setFontAndSize(BaseFont.createFont(), fontSize);
		cb.showText(Integer.toString(steel.getFYk()));
		cb.endText();

		startY -= lineWidth;

		cb.beginText();
		cb.moveText(93, startY);
		cb.setFontAndSize(BaseFont.createFont(), fontSize);
		cb.showText(cement.getCementTypeName(cement.getCementType()));
		cb.endText();

		cb.beginText();
		cb.moveText(133, startY);
		cb.setFontAndSize(BaseFont.createFont(), fontSize);
		Double rh = new Double(concrete.getrH());
		int RH = rh.intValue();
		cb.showText(String.format("%02d", RH));
		cb.endText();

		cb.beginText();
		cb.moveText(180, startY);
		cb.setFontAndSize(BaseFont.createFont(), fontSize);
		cb.showText(Integer.toString(concrete.getT0()));
		cb.endText();
		return cb;
	}
	
	public static PdfContentByte addParamGeom(PdfContentByte cb, DimensionsOfCrossSectionOfConcrete dimensions, InternalForces forces) throws DocumentException, IOException {
		float lineWidth = (float) 12.5;
		int fontSize = 11;
		float startY = (float)694.5;
		float startX = 265;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.dimensionsPrintformatter(dimensions.getB()));
		cb.endText();

		startY-=lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.getH()));
		cb.endText();
		
		startY-=lineWidth;
		startX+=3;

		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.getA1()));
		cb.endText();
		
		startX+=70;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.getA2()));
		cb.endText();

		startY-=lineWidth;
		startX-=60;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.cNomPrintFormatter(dimensions.getcNom()*1000));
		cb.endText();
		
		startY-=lineWidth;
		startX-=5;

		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.dimensionsPrintformatter(dimensions.getlEff()));
		cb.endText();
		
		startY-=lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.alfaMFormatter(forces.getAlfaM()));
		cb.endText();
		
		return cb;
	}

	public static PdfContentByte addParamGeomTshaped(PdfContentByte cb, DimensionsOfCrossSectionOfConcrete dimensions, InternalForces forces) throws DocumentException, IOException {
		float lineWidth = (float) 12.5;
		int fontSize = 11;
		float startY = (float)694.5;
		float startX = 265;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.dimensionsPrintformatter(dimensions.getB()));
		cb.endText();

		startY-=lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.getH()));
		cb.endText();
		
		startY-=lineWidth;
		startX+=7;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.getbEff()));
		cb.endText();
		
		startX+=70;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.gettW()));
		cb.endText();

		startY-=lineWidth;
		startX-=70;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.getBefft()));
		cb.endText();
		
		startX+=70;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.getHft()));
		cb.endText();
		
		startY-=lineWidth;
		startX-=72;

		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.getA1()));
		cb.endText();
		
		startX+=70;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.a1a2printformatter(dimensions.getA2()));
		cb.endText();

		startY-=lineWidth;
		startX-=60;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.cNomPrintFormatter(dimensions.getcNom()));
		cb.endText();
		
		startY-=lineWidth;
		startX-=5;

		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.dimensionsPrintformatter(dimensions.getlEff()));
		cb.endText();
		
		startY-=lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.alfaMFormatter(forces.getAlfaM()));
		cb.endText();
		
		return cb;
	}
	
	public static PdfContentByte addReinforcementDiagnosis(PdfContentByte cb, Reinforcement reinforcement, float offset) throws DocumentException, IOException {
		float lineWidth = (float) 12.5;
		int fontSize = 11;
		float startY = (float) 589 - offset;
		float startX = 280;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(String.format("%.03f", reinforcement.getDesingedUnsymmetricalAS1()*10000) + " cm" + "\u00B2" 
		+ "  (" + reinforcement.getRequiredNumberOfUnsymmetricalRodsAS1() + "    \u03A6   " 
				+ PrintFormatter.doubletoIntPrintFormatter(reinforcement.getDesignedDiameterSymmetricalAS1()) + ")");
		cb.endText();
		
		startY-= lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(String.format("%.03f", reinforcement.getDesignedUnsymmetricalAS2()*10000) + " cm" + "\u00B2" 
		+ "  (" + reinforcement.getRequiredNumberOfUnsymmetricalRodsAS2() + "    \u03D5   " 
				+ PrintFormatter.doubletoIntPrintFormatter(reinforcement.getDesignedDiameterSymmetricalAS2()) + ")");
		cb.endText();
		
		startY -= lineWidth;
		startY -= 8;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.doubletoIntPrintFormatter(reinforcement.getnS1()) 
				+ "    \u03C6" + PrintFormatter.doubletoIntPrintFormatter(reinforcement.getaSW1Diameter()) + " /"
				+ " s1" +"\u2081" + " = " 
				+ PrintFormatter.s1s2PrintFormatter(reinforcement.getS1Designed()));

		cb.endText();
		
		startY-=lineWidth;
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.doubletoIntPrintFormatter(reinforcement.getnS2Designed())
				+ "    \u0278" + 
				PrintFormatter.doubletoIntPrintFormatter(reinforcement.getaSW2Diameter()) + " /" 
				+ " s2 = " + PrintFormatter.s1s2PrintFormatter(reinforcement.getS2Designed()) + " /"
				+ " " + "\u0251" + "     = " + PrintFormatter.doubletoIntPrintFormatter(reinforcement.getAlfa()) + "\u00B0");
		cb.endText();

		return cb;
	}
	
	
	public static PdfContentByte addForces(PdfContentByte cb, InternalForces forces) throws DocumentException, IOException {
		float lineWidth = (float) 12.7;
		int fontSize = 11;
		float startY = (float) 628;
		float startX = (float) 85;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.momentFormatter(forces.getmEd()));
		cb.endText();
		
		startY -= lineWidth;
		startX +=22;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.momentFormatter(forces.getCharacteristicMEdCalkowita()));
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.momentFormatter(forces.getCharacteristicMEdDlugotrwale()));
		cb.endText();
		
		startY -= lineWidth;
		startX -= 22;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.forcesFormatter(forces.getnEd()));
		cb.endText();
		
		startY -= lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.forcesFormatter(forces.getvEd()));
		cb.endText();
		
		
		startY -= lineWidth;
		startX += 27;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.forcesFormatter(forces.getvEdRed()));
		cb.endText();
		return cb;
	}
	
	public static PdfContentByte addReinforcementDesignClearBending(PdfContentByte cb, Reinforcement reinforcement, float offset, boolean as1Tensile) throws DocumentException, IOException {
		float lineWidth = (float) 13;
		int fontSize = 11;
		float startY = (float) 498 - offset;
		float startX = 190;
		int step = 150;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getRequiredSymmetricalAS1()) 
				+ " cm" + "\u00B2");
		cb.endText();
		
		startX += step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getDesignedSymmetricalAS1()) 
					+ " cm" + "\u00B2" 
					+ " (" + reinforcement.getRequiredNumberOfSymmetricalRodsAS1() + 
					"       " + reinforcement.getDesignedDiameterSymmetricalAS1() + ")");
		cb.endText();
		
		startY-=lineWidth;
		startX -= step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getRequiredSymmetricalAS2()) 
				+ " cm" + "\u00B2");
		cb.endText();
		
		startX += step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getDesignedSymmetricalAS2()) 
					+ " cm" + "\u00B2" 
					+ " (" + reinforcement.getRequiredNumberOfSymmetricalRodsAS2() + 
					"       " + reinforcement.getDesignedDiameterSymmetricalAS2() + ")");
		cb.endText();
		
		startY-=3*lineWidth;
		startY-=4;
		startX-=step;
		startX-=30;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.doubletoIntPrintFormatter(reinforcement.getnS1()) 
				+ "    \u03C6 " + PrintFormatter.doubletoIntPrintFormatter(reinforcement.getaSW1Diameter()) + " /"
				+ " s1" +"\u2081" + " = " 
				+ PrintFormatter.s1s2PrintFormatter(reinforcement.getS1Designed()));
		cb.endText();
		
		startY-=lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.doubletoIntPrintFormatter(reinforcement.getnS2Designed())
				+ "    \u0278 " + 
				PrintFormatter.doubletoIntPrintFormatter(reinforcement.getaSW2Diameter()) + " /" 
				+ " s2 = " + PrintFormatter.s1s2PrintFormatter(reinforcement.getS2Designed()) + " /"
				+ " " + "\u0251" + "     = " + PrintFormatter.doubletoIntPrintFormatter(reinforcement.getAlfa()) + "\u00B0");
		cb.endText();
		
		return cb;
	}
	
	
	public static PdfContentByte addReinforcementDesign(PdfContentByte cb, Reinforcement reinforcement, float offset, boolean as1Tensile) throws DocumentException, IOException {
		float lineWidth = (float) 13;
		int fontSize = 11;
		float startY = (float) 498 - offset;
		float startX = 190;
		int step = 150;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		if (as1Tensile)
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getRequiredSymmetricalAS1()) 
				+ " cm" + "\u00B2");
		else
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getRequiredSymmetricalAS2()) 
					+ " cm" + "\u00B2");
		cb.endText();
		
		startX += step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		if (as1Tensile)
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getDesignedSymmetricalAS1()) 
					+ " cm" + "\u00B2" 
					+ " (" + reinforcement.getRequiredNumberOfSymmetricalRodsAS1() + 
					"      " + reinforcement.getDesignedDiameterSymmetricalAS1() + ")");
		else
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getDesignedSymmetricalAS2()) 
						+ " cm" + "\u00B2" 
						+ " (" + reinforcement.getRequiredNumberOfSymmetricalRodsAS2() + 
						"      " + reinforcement.getDesignedDiameterSymmetricalAS2() + ")");
		cb.endText();
		
		startY-=lineWidth;
		startX -= step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		if (as1Tensile)
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getRequiredSymmetricalAS2()) 
				+ " cm" + "\u00B2");
		else
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getRequiredSymmetricalAS1()) 
					+ " cm" + "\u00B2");
		cb.endText();
		
		startX += step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		if (as1Tensile)
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getDesignedSymmetricalAS2()) 
					+ " cm" + "\u00B2" 
					+ " (" + reinforcement.getRequiredNumberOfSymmetricalRodsAS2() + 
					"      " + reinforcement.getDesignedDiameterSymmetricalAS2() + ")");
		else
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getDesignedSymmetricalAS1()) 
						+ " cm" + "\u00B2" 
						+ " (" + reinforcement.getRequiredNumberOfSymmetricalRodsAS1() + 
						"      " + reinforcement.getDesignedDiameterSymmetricalAS1() + ")");
		cb.endText();
		
		
		
		
		startY-=3*lineWidth;
		startY-=4;
		startX-=step;
		
		
		
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getRequiredUnsymmetricalAS1()) 
				+ " cm" + "\u00B2");
		cb.endText();
		
		startX += step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getDesingedUnsymmetricalAS1()) 
					+ " cm" + "\u00B2" 
					+ " (" + reinforcement.getRequiredNumberOfUnsymmetricalRodsAS1() + 
					"      " + reinforcement.getDesignedDiameterSymmetricalAS1() + ")");
		cb.endText();
		
		startY-=lineWidth;
		startX -= step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getRequiredUnsymmetricalAS2()) 
				+ " cm" + "\u00B2");
		cb.endText();
		
		startX += step;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(PrintFormatter.as1as2PrintFormatter(reinforcement.getDesignedUnsymmetricalAS2()) 
					+ " cm" + "\u00B2" 
					+ " (" + reinforcement.getRequiredNumberOfUnsymmetricalRodsAS2() + 
					"      " + reinforcement.getDesignedDiameterSymmetricalAS2() + ")");
		cb.endText();
		
		startY-=3*lineWidth;
		startY-=4;
		startX-=step;
		startX-=30;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.doubletoIntPrintFormatter(reinforcement.getnS1()) 
				+ "    \u03C6" + PrintFormatter.doubletoIntPrintFormatter(reinforcement.getaSW1Diameter()) + " /"
				+ " s1" +"\u2081" + " = " 
				+ PrintFormatter.s1s2PrintFormatter(reinforcement.getS1Designed()));
		cb.endText();
		
		startY-=lineWidth;
		
		cb.beginText();
		cb.moveText(startX, startY);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(PrintFormatter.doubletoIntPrintFormatter(reinforcement.getnS2Designed())
				+ "    \u0278" + 
				PrintFormatter.doubletoIntPrintFormatter(reinforcement.getaSW2Diameter()) + " /" 
				+ " s2 = " + PrintFormatter.s1s2PrintFormatter(reinforcement.getS2Designed()) + " /"
				+ " " + "\u0251" + "     = " + PrintFormatter.doubletoIntPrintFormatter(reinforcement.getAlfa()) + "\u00B0");
		cb.endText();
		
		return cb;
	}
	
	public static void saveAxisLoadColumnDiagnosis(boolean withTable, Concrete concrete, Steel steel, Reinforcement reinforcement,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			DiagnosisMainAlgorithm diagnosis, Scratch scratch, CreepCoeficent creep, DeflectionControl deflection,
			CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
		String SRC = "plates/BeamAxisLoadDiagnosisManyForces.pdf";
		File file = new File(SRC);
		file.getParentFile().mkdirs();
		printAxisLoadResultsColumnDiagnosis(withTable ,SRC, GetExecutionPathDiagnosis(), concrete, steel, reinforcement, forces,
				dimensions, sls, diagnosis, scratch, creep, deflection, nominalCheckBox, stiffness, cement);
	}

	public static void printAxisLoadResultsColumnDiagnosis(boolean withTable, String src, String dest, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			DiagnosisMainAlgorithm diagnosis, Scratch scratch, CreepCoeficent creep, DeflectionControl deflection,
			CheckBox nominalCheckBox, NominalStiffness stiffness, Cement cement) throws IOException, DocumentException {
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
		Paragraph paragraph2 = new Paragraph("Nazwa zadania: " + pdfName);
		paragraph2.setAlignment(Element.ALIGN_LEFT);

		document.add(paragraph1);
		document.add(paragraph2);

		addForces(cb, forces);
		addParamGeom(cb, dimensions, forces);
		addParamMaterials(cb, concrete, steel, cement);
		addReinforcementDiagnosis(cb, reinforcement, 0);
		
		ArrayList<ForcesCombination> combinations = forces.getPdfCombinations();
		int height = 450;
		for (ForcesCombination combination : combinations) {
			// Med
			cb.beginText();
			cb.moveText(170, height);
			cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(String.format("%.2f", combination.getmStiff()));
			cb.endText();

			// Ned
			cb.beginText();
			cb.moveText(275, height);
			cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(String.format("%.2f", combination.getN()));
			cb.endText();

			// Mrd
			cb.beginText();
			cb.moveText(375, height);
			cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(String.format("%.2f", combination.getmRd()));
			cb.endText();

			// Nrd
			cb.beginText();
			cb.moveText(480, height);
			cb.setFontAndSize(BaseFont.createFont(), 11);
			cb.showText(String.format("%.2f", combination.getnRd()));
			cb.endText();

			height -= 12.7;
		}

		
		cb.beginText();
		cb.moveText(110, 370);
		cb.setFontAndSize(BaseFont.createFont(), 11);
		cb.showText(OutputFormatter.diagnosisVedAndNed(diagnosis.getvRdDesigned()));
		cb.endText();
		
		if (withTable)
		additionalParametersAxisLoad(src, dest, concrete, steel, reinforcement, forces, dimensions, sls, scratch, creep, deflection,
				nominalCheckBox, stiffness, cb, forces.getvRdCdiagnosis(), forces.getvRdSdiagnosis(),
				forces.getvRdMaxdiagnosis(), 350, 306, forces.getPdfCombinations());
		
		
		///////////// PARAMETRY DODATKOWE /////////////
		//additionalParameters(src, dest, concrete, steel, reinforcement, forces, dimensions, sls, scratch, creep,
		//		deflection, nominalCheckBox, stiffness, cb, forces.getvRdCdiagnosis(), forces.getvRdSdiagnosis(),
		//		forces.getvRdMaxdiagnosis());

		document.close();
	}

	private static ArrayList<String> resultsList = new ArrayList();
	private static ArrayList<String> variableList = new ArrayList();

	private static void printResultsForJacekIntoSecondFile() throws IOException, DocumentException {

		File file = new File(GetExecutionPathForSecondFile());
		file.getParentFile().mkdirs();
		createPdfForSecondaryResults(GetExecutionPathForSecondFile());
	}

	public static void clearResults() {
		resultsList.clear();
		variableList.clear();
	}

	public static void addResults(String variable, String result) {
		variableList.add(variable);
		resultsList.add(result);
	}

	private static void createPdfForSecondaryResults(String dest) throws IOException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(dest));
		document.open();
		PdfPTable table = new PdfPTable(2);
		for (int i = 0; i < resultsList.size(); i++) {
			table.addCell(variableList.get(i));
			table.addCell(resultsList.get(i));

		}
		document.add(table);
		document.close();
	}
	
	public static PdfContentByte addTableDesign(PdfContentByte cb, Concrete concrete, Steel steel,
			Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
			Scratch scratch, CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox,
			NominalStiffness stiffness, double vRdC, double vRdS, double vRdMax, double vrds1, double vrds2, float x1, float y1)
			throws IOException, DocumentException {
		
		float ymin = 13.5f;
		float marginX = x1 - 125;
		float marginY = y1 - 68; 
		float lineWidth = 13.5f;
		float step = 170;
		
		printOneValue(cb, x1, y1, dimensions.getX(), "%.5f");
		printOneValue(cb, x1, y1-2*lineWidth, vRdC, "%.3f");
		printOneValue(cb, x1, y1-3*lineWidth, vrds1, "%.3f");
		printOneValue(cb, x1, y1-4*lineWidth, vrds2, "%.3f");
		printOneValue(cb, x1, y1-5*lineWidth, vRdS, "%.3f");
		printOneValue(cb, x1, y1-6*lineWidth, vRdMax, "%.3f");
		
		printOneValue(cb, x1, y1-8*lineWidth, stiffness.getFiEf(), "%.4f");
		printOneValue(cb, x1, y1-9*lineWidth, stiffness.geteIc(), "%.7f");
		printOneValue(cb, x1, y1-10*lineWidth, stiffness.geteIs(), "%.7f");
		printOneValue(cb, x1, y1-11*lineWidth, stiffness.getnB(), "%.2f");
		printOneValue(cb, x1, y1-12*lineWidth, stiffness.geteIc(), "%.7f");
		printOneValue(cb, x1, y1-13*lineWidth, stiffness.geteIs(), "%.7f");
		printOneValue(cb, x1, y1-14*lineWidth, stiffness.getnB(), "%.2f");
		
		x1 += step;
		
		printOneValue(cb, x1, y1, dimensions.getxC(),"%.5f");
		printOneValue(cb, x1, y1-lineWidth, dimensions.getIc(), "%.12f");
		printOneValue(cb, x1, y1-2*lineWidth, scratch.getMCr(), "%.3f");
		
		printOneValue(cb, x1, y1-4*lineWidth, dimensions.getX1(), "%.5f");
		printOneValue(cb, x1, y1-5*lineWidth, dimensions.getI1(), "%.12f");
		printOneValue(cb, x1, y1-6*lineWidth, dimensions.getS1(), "%.10f");
		printOneValue(cb, x1, y1-7*lineWidth, deflection.getB1(), "%.7f");
		
		printOneValue(cb, x1, y1-9*lineWidth, dimensions.getX2(), "%.5f");
		printOneValue(cb, x1, y1-10*lineWidth, dimensions.getI2(), "%.12f");
		printOneValue(cb, x1, y1-11*lineWidth, dimensions.getS2(), "%.10f");
		printOneValue(cb, x1, y1-12*lineWidth, deflection.getB2(), "%.7f");
		
		
		x1 += step + 15;
		
		printOneValue(cb, x1, y1, scratch.getSigmaS(), "%.3f");
		printOneValue(cb, x1, y1-lineWidth, scratch.getHcef(), "%.5f");
		printOneValue(cb, x1, y1-2*lineWidth, scratch.getaCEff(), "%.7f");
		printOneValue(cb, x1, y1-3*lineWidth, scratch.getRoPEff(), "%.7f");
		printOneValue(cb, x1, y1-4*lineWidth, scratch.getEpsilonSmMinusEpsilonCm(), "%.8f");
		printOneValue(cb, x1, y1-5*lineWidth, scratch.getsRMax(), "%.5f");
		
		printOneValue(cb, x1, y1-9*lineWidth, creep.getCreepCoeficent(), "%.4f");
		printOneValue(cb, x1, y1-10*lineWidth, sls.getAlfae(), "%.4f");
		printOneValue(cb, x1, y1-11*lineWidth, sls.getAlfaEeff(), "%.4f");
		printOneValue(cb, x1, y1-12*lineWidth, deflection.getEpsilonCS(), "%.8f");
		
		
		return cb;
	}

	private static PdfContentByte printOneValue(PdfContentByte cb, float x1, float y1, double value, String accuracy) throws DocumentException, IOException {
		cb.beginText();
		cb.moveText(x1, y1);
		cb.setFontAndSize(BaseFont.createFont(), 12);
		cb.showText(String.format(accuracy, value));
		cb.endText();
		return cb;
	}
	

}
