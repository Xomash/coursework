package com.khomandiak.courseWork;

import java.awt.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        PDDocument pdDoc = null;
        PDFTextStripper pdfStripper;


        String parsedText;
        String fileName = "target/Boyd-and-Ellison.pdf";
        File file = new File(fileName);
        Rectangle rect = new Rectangle(60, 80, 510, 660);
        RenderFilter filter = new RegionTextRenderFilter(rect);
        try {
            PdfReader reader = new PdfReader(fileName);
            String pattern = "(?<=References|Bibliography|References and Notes|Literature cited)";
            String pdf = "";
            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
                pdf = pdf + (PdfTextExtractor.getTextFromPage(reader, i, strategy));
            }

            String incompleteReferences = pdf.split(pattern)[1];
            pattern = "(?>=Acknowledgement|Autobiographical|Table|Appendix|Exhibit|Notes|About the Authors|$)";
            String references = incompleteReferences.split(pattern)[0];
            System.out.println(references);
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}