package presentation;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.OrderItemDAO;
import dao.ProductDAO;
import model.Client;
import model.OrderItem;
import model.OrderTotal;
import model.Product;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * This Class creates a PDF document representing a Bill for each order
 */

public class Bill {
    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private ProductDAO productDAO = new ProductDAO();


    /**
     * Makes a PDF Documents consisting of 3 paragraphs and a table
     * @param c Client that made the order
     * @param ot Order instance
     * @param d Date when the order was placed
     */
    public Bill(Client c, OrderTotal ot, Date d) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("PDF/order"+ot.getID()+"_bill.pdf"));

            document.open();
            String title = "BILL : Order #" + ot.getID() + "\n\n";
            Paragraph paragraph = new Paragraph(title);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);

            String msg1 = ("This is the bill for the order placed at " + d.toString() + " by " + c.getName() + ".\n\n");
            Paragraph paragraph1 = new Paragraph(msg1);
            document.add(paragraph1);

            List<OrderItem> items = orderItemDAO.findByOrderId(ot.getID());
            PdfPTable table = new PdfPTable(4);
            addTableHeader(table);

            for (OrderItem i : items) {
                addRows(table, i);
            }
            document.add(table);

            Paragraph paragraph2 = new Paragraph("Total price : " + ot.getTotalPrice());
            document.add(paragraph2);
            document.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Adds the table header
     * @param table Table to be modified
     */
    private void addTableHeader(PdfPTable table) {
        Stream.of("Product id", "Product", "Quantity", "Price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Adds the rows to the table with information about the order item
     * @param table Table to be modified
     * @param i Item of the order
     */
    private void addRows(PdfPTable table, OrderItem i) {
        Product p = productDAO.findById(i.getIDProduct());
        table.addCell(String.valueOf(p.getID()));
        table.addCell(p.getName());
        table.addCell(String.valueOf(i.getQuantity()));
        table.addCell(String.valueOf(p.getPrice()));
    }
}
