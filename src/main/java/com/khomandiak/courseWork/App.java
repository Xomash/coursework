package com.khomandiak.courseWork;

import java.io.*;


import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;



import javax.swing.*;
import javax.swing.filechooser.FileSystemView;


public class App {
    private static String fileName;

    public static void main(String[] args) {

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setCurrentDirectory(new File("E:\\sts-bundle\\workspace\\courseWork\\target"));
        int returnValue = jfc.showOpenDialog(null);


        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            fileName = selectedFile.getAbsolutePath();
        }
        Rectangle rect = new Rectangle(60, 60, 700, 700);
        RenderFilter filter = new RegionTextRenderFilter(rect);
        try {
            PdfReader reader = new PdfReader(fileName);
            String pattern = "(?<=References|Bibliography|References and Notes|Literature cited|REFERENCES)";
            String pdf = "";
            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
                pdf += "\n" + (PdfTextExtractor.getTextFromPage(reader, i, strategy));
            }
            String incompleteReferences = pdf.split(pattern)[1];
            pattern = "(?>=Acknowledgement|Autobiographical|Table|Appendix|Exhibit|Notes|About the Authors|Actual transaction|PAUL|$)";
            String references = incompleteReferences.split(pattern)[0];
            System.out.println(references);
            SimpleGUI gui = new SimpleGUI();
            gui.setLabel(new JTextArea(references));
            gui.setVisible(true);
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}