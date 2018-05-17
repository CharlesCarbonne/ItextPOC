package hello;

import java.io.File;
import java.io.IOException;

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
	public static final String DEST = String.format("%stest.pdf", TARGET);
	
    @Value("${test.value}")
    private String test;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		System.out.println("**********************"+this.test);
		File file = new File(TARGET);
		file.mkdirs();
		HtmlToPdf htpdf = new HtmlToPdf();
		try {
			htpdf.createPdf(BASEURI, SRC, DEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RepeatStatus.FINISHED;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
