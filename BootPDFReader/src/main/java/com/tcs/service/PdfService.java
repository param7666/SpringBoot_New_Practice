package com.tcs.service;


import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfService {

	public String extractText(MultipartFile file) throws Exception{
		
		if(!"application/pdf".equals(file.getContentType())) {
			throw new IllegalArgumentException("Only PDF files are allowed.");
		}
		
		try(PDDocument pdf=Loader.loadPDF(file.getBytes())) {
			if(pdf.isEncrypted()) {
				throw new RuntimeException("Can not read Document PDF is Encrypted");
			}
			
			PDFTextStripper striper=new PDFTextStripper();
			striper.setSortByPosition(true);
			//System.out.println(striper.getText(pdf));
			return striper.getText(pdf);
		}
	}
}
