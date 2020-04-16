package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.*;
import model.Client;
import model.OrderItem;
import model.OrderTotal;
import model.Product;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

/**
 * Creates PDF Documents that show a table's Data
 */
public class Report {

    /**
     * Variable used to create the name of the PDF file
     */
    private static int client = 0;
    /**
     * Variable used to create the name of the PDF file
     */
    private static int product = 0;
    /**
     * Variable used to create the name of the PDF file
     */
    private static int order = 0;
    private String table;
    private ClientDAO clientDAO = new ClientDAO();
    private ProductDAO productDAO = new ProductDAO();
    private OrderTotalDAO orderTotalDAO = new OrderTotalDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    /**
     * Creates a PDF Document for a table with the name for a Client table e.g. : reportclient_0.pdf
     * @param table Can be "client" or "product" or "order"
     */
    public Report(String table) {
        this.table = table;

        if (table.equalsIgnoreCase("client"))
            clientTable();
        else if (table.equalsIgnoreCase("product"))
            productTable();
        else if (table.equalsIgnoreCase("order"))
            orderTable();
    }

    /**
     *  Creates the Client table that will be put in the PDF file
     */
    private void clientTable(){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("PDF/report" + table +"_" + client++ + ".pdf"));
            document.open();
            Paragraph paragraph = new Paragraph("Client Table\n\n");
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);
            PdfPTable table = new PdfPTable(3);
            addTableHeaderClient(table);
            List<Client> clients = clientDAO.findAll();
            for (Client c : clients) {
                addClientRows(table, c);
            }
            document.add(table);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Creates the Product table that will be put in the PDF file
     */
    private void productTable() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("PDF/report" + table +"_" + product++ + ".pdf"));
            document.open();
            Paragraph paragraph = new Paragraph("Product Table\n\n");
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);
            PdfPTable table = new PdfPTable(4);
            addTableHeaderProduct(table);
            List<Product> products = productDAO.findAll();
            for (Product p : products) {
                addProductRows(table, p);
            }
            document.add(table);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Creates the Order table that will be put in the PDF file
     */
    private void orderTable() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("PDF/report" + table +"_" + order++ + ".pdf"));
            document.open();
            Paragraph paragraph = new Paragraph("Order Table\n\n");
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);
            PdfPTable table = new PdfPTable(5);
            addTableHeaderOrder(table);
            List<OrderTotal> orders = orderTotalDAO.findAll();
            for (OrderTotal o : orders) {
                List<OrderItem> items = orderItemDAO.findByOrderId(o.getID());
                for (OrderItem i : items) {
                    addOrderRows(table, o, i);
                }
            }
            document.add(table);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * Adds the header to the client table
     * @param table Client table
     */
    private void addTableHeaderClient(PdfPTable table) {
        Stream.of("ID", "Name", "Address")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Adds the header to the Product table
     * @param table Product table
     */
    private void addTableHeaderProduct(PdfPTable table) {
        Stream.of("ID", "Name", "Stock", "Price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Adds the header to the Order table
     * @param table Order table
     */
    private void addTableHeaderOrder(PdfPTable table) {
        Stream.of("ID", "Client ID", "Product id", "Quantity", "Total price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * @param table Client Table
     * @param c Client
     */
    private void addClientRows(PdfPTable table, Client c) {
        table.addCell(String.valueOf(c.getID()));
        table.addCell(c.getName());
        table.addCell(c.getAddress());
    }

    /**
     * Add the rows to the Product table
     * @param table Product table
     * @param p Product
     */
    private void addProductRows(PdfPTable table, Product p) {
        table.addCell(String.valueOf(p.getID()));
        table.addCell(p.getName());
        table.addCell(String.valueOf(p.getStock()));
        table.addCell(String.valueOf(p.getPrice()));
    }

    /**
     * Add the rows to the Order table
     * @param table Order table
     * @param o Order
     * @param i Order item
     */
    private void addOrderRows(PdfPTable table, OrderTotal o, OrderItem i) {
        table.addCell(String.valueOf(o.getID()));
        table.addCell(String.valueOf(o.getIDClient()));
        table.addCell(String.valueOf(i.getIDProduct()));
        table.addCell(String.valueOf(i.getQuantity()));
        table.addCell(String.valueOf(o.getTotalPrice()));
    }

}
