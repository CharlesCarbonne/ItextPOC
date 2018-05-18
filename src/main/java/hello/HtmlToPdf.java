package hello;

import java.io.File;
import java.io.IOException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;

public class HtmlToPdf {

	// public void createPdf(String baseUri, String src, String dest) throws
	// IOException{
	// HtmlConverter.convertToPdf(new File(src), new File(dest));
	// }

	public void createPdf(String baseUri, String src, String dest) throws IOException {
		System.out.println("Début conversion pdf");
		ConverterProperties properties = new ConverterProperties();
		properties.setBaseUri(baseUri);

		// Le OutlineHandler sert à créer les bookmarks, on le passe ensuite dans les
		// propriétés. Par défaut il crée les bookmarks avec les titres <h1><h2>etc...
		OutlineHandler outlineHandler = OutlineHandler.createStandardHandler();
		properties.setOutlineHandler(outlineHandler);

		HtmlConverter.convertToPdf(new File(src), new File(dest), properties);
		System.out.println("pdf généré!");
	}

}
