package presentation;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import model.OrderTotal;
import model.Product;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * This Class creates a PDF document when an order is cancel because there is not enough stock
 */
public class Understock {
    /**
     * Creates a PDF file with the message that the order for the product was cancelled due to under-stock
     * @param o Order
     * @param p Product
     * @param d Date when the order was placed
     */
    public Understock(OrderTotal o, Product p, Date d){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("PDF/order" + o.getID() + "_understock.pdf"));

            document.open();
            String msg = "The order placed at " + d.toString() + " for the product with ID : " + p.getID();
            msg += " by the client with the ID : " + o.getIDClient() + " was cancelled due to under-stock";
            Paragraph paragraph = new Paragraph(msg);

            document.add(paragraph);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
