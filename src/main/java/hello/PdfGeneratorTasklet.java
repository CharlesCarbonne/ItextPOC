package hello;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public class PdfGeneratorTasklet implements Tasklet, InitializingBean {

	/** The Base URI of the HTML page. */
	public static final String BASEURI = "src/main/resources/html/";
	/** The path to the source HTML file. */
	public static final String SRC = String.format("%shello.html", BASEURI);
	/** The target folder for the result. */
	public static final String TARGET = "src/main/resources/results/generatedPdf/";
	/** The path to the resulting PDF file. */
	
	File file = new File(BASEURI);

	public String HTML = this.getHtmlFileToString(file);
	
	File folder = new File("/home/user/LEGIFRANCE/itextPoc/src/main/resources/html/");
	File[] listOfFiles = folder.listFiles();
	
	List<String> listOfFilesNames = this.getListofFilesNames(listOfFiles);
	
	
public List<String> getListofFilesNames(File[] files) {
	List<String> filesNames = new ArrayList<String>();
	for (File file : files) {
	    if (file.isFile() && file.getName().contains(".html")) {
	        System.out.println(file.getName());
	        filesNames.add(file.getName());
	    }
	}
	return filesNames;
}



//TODO créer liste de Strings HTML pour conversion.
//modifier le repeatStatus pour qu'il traite la liste de strings html
	

	@Value("${test.value}")
	private String test;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		this.getListofFilesNames(listOfFiles);

		for (String file : listOfFilesNames) {
			File target = new File(TARGET);
			target.mkdirs();
			HtmlToPdf htpdf = new HtmlToPdf();
			String srcPath = BASEURI+file;
			String destPath = TARGET+file.replace(".html", ".pdf");
			try {
				htpdf.createPdf(BASEURI, srcPath, destPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		

return RepeatStatus.FINISHED;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	public String getHtmlFileToString(File path) {
		List<String> lines;
		String contents = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/html/hello.html"));
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				if (sb.length() > 0) {
					sb.append("\n");
				}
				sb.append(line);
			}
			contents = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	}
}
