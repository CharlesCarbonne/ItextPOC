package hello;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

public class PdfGeneratorTasklet implements Tasklet, InitializingBean {

	/** The Base URI of the HTML page. */
	public static final String BASEURI = "src/main/resources/html/";
	/** The target folder for the result. */
	public static final String TARGET = "src/main/resources/results/generatedPdf/";

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

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		File folder = new File("/home/user/LEGIFRANCE/itextPoc/src/main/resources/html/");
		File[] listOfFiles = folder.listFiles();
		// List<String> listOfFilesNames = this.getListofFilesNames(listOfFiles);
		for (File src : listOfFiles) {
			File target = new File(TARGET);
			target.mkdirs();
			HtmlToPdf htpdf = new HtmlToPdf();
			String srcPath = BASEURI + src.getName();
			String destPath = TARGET + src.getName().replace(".html", ".pdf");
			try {
				htpdf.createPdf(BASEURI, srcPath, destPath);
				throw new Exception("test");
			} catch (Exception e) {
				// Fichier d'erreur
				PrintWriter writer = new PrintWriter(BASEURI+"error.txt");
				e.printStackTrace(writer);
				writer.close();
			}
		}
		return RepeatStatus.FINISHED;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}
}
