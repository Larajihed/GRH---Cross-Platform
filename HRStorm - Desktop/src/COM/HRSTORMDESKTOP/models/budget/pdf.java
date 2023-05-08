/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.models.budget;

import COM.HRSTORMDESKTOP.models.budget.budget;
import COM.HRSTORMDESKTOP.services.budget.BudgetService;
import com.itextpdf.text.Document;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author ihebt
 */
public class pdf {
     public void GeneratePdf(String filename, budget b, int id) throws FileNotFoundException, DocumentException, BadElementException, IOException, InterruptedException, SQLException {

        Document document = new Document() {
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
        document.open();

        BudgetService bs = new BudgetService();
        document.add(new Paragraph("            Date  :"+LocalDateTime.now()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------"));

        document.add(new Paragraph("budget :" + b.getBudget()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("date :" + b.getDate()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("prime :" + b.getPrime()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Budget_materiel:" + b.getBudget_materiel()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Budget_salaire :" + b.getBudget_salaire()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Budget_service :" + b.getBudget_service()));
        document.add(new Paragraph("                      "));
        

        document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------------- "));
        document.add(new Paragraph("                              Code Worries                     "));
        document.close();
        Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
    }

}
