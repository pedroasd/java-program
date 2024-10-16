package com.pedro.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.pedro.model.Ticket;
import com.pedro.facade.BookingFacade;
import com.pedro.model.User;
import com.pedro.oxm.TicketsXML;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class TicketController {

    private final Log log = LogFactory.getLog(TicketController.class);

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private Jaxb2Marshaller jaxb2Marshaller;

    // Display the form to book a ticket
    @GetMapping("/bookTicket")
    public String showBookingForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "bookTicket";
    }

    // Method to handle ticket booking submission
    @PostMapping("/bookTicket")
    public String bookTicket(@RequestParam("userId") Long userId,
                             @RequestParam("eventId") Long eventId,
                             @RequestParam("category") String category,
                             @RequestParam("place") Integer place,
                             Model model) {
        Ticket ticket = bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.valueOf(category));
        if (ticket != null) {
            model.addAttribute("message", "Ticket booked successfully!");
        } else {
            model.addAttribute("error", "Failed to book ticket. Please try again.");
        }

        return "bookTicketResult";
    }

    // Display the form to get booked tickets for a user
    @GetMapping("/tickets/search")
    public String showBookedTicketsForm() {
        return "searchTickets";
    }

    // Handle the search for booked tickets
    @GetMapping("/tickets")
    public String getBookedTickets(@RequestParam(value = "userId", required = false) Long userId, Model model) {
        if (userId != null ) {
            User user = bookingFacade.getUserById(userId);
            if (user == null) {
                model.addAttribute("message", "User doesn't exists.");
                return "searchTickets";
            }
            List<Ticket> tickets = bookingFacade.getBookedTickets(user, 100, 1);

            model.addAttribute("userId", userId);
            model.addAttribute("tickets", tickets);
            model.addAttribute("userName", user.getName());
            return "searchTickets";

        } else {
            model.addAttribute("tickets", null);
            model.addAttribute("userName", "");
            return "searchTickets";
        }
    }

    // Handle the search for booked tickets
    @GetMapping(value = "/tickets", params = "downloadPdf=true")
    public ResponseEntity<?> downloadTickets(@RequestParam(value = "userId", required = false) Long userId) {
        User user = bookingFacade.getUserById(userId);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user, 100, 1);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=tickets.pdf");
        return new ResponseEntity<>(generateTicketsPdf(tickets, user), headers, HttpStatus.OK);
    }

    @GetMapping("/uploadTickets")
    public String showUploadTickets() {
        return "uploadTickets";
    }

    @PostMapping("/uploadTickets")
    public String uploadTickets(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "uploadTickets";
        }

        try {
            File tempFile = File.createTempFile("tickets", ".xml");
            file.transferTo(tempFile);

            bookingFacade.preloadTickets(loadTicketsFile(tempFile.getAbsolutePath()));
            model.addAttribute("message", "Tickets uploaded successfully!");

            tempFile.deleteOnExit();
        } catch (Exception e) {
            model.addAttribute("message", "Error uploading file: " + e.getMessage());
        }

        return "uploadTickets";
    }

    @GetMapping("/testError")
    public String testException() {
        throw new RuntimeException("This is a test exception!");
    }

    private List<Ticket> loadTicketsFile(String filePath) {
        File xmlFile = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(xmlFile)) {
            TicketsXML ticketsXML = (TicketsXML) jaxb2Marshaller.unmarshal(new StreamSource(fileInputStream));

            return ticketsXML.getTickets().stream().map(tx -> {
                var users = bookingFacade.getUsersByName(tx.getUser(), 1, 1);
                if(users == null || users.isEmpty()) throw new RuntimeException("User: " + tx.getUser() + " doesn't exist.");
                var events = bookingFacade.getEventsByTitle(tx.getEvent(), 1, 1);
                if(events == null || events.isEmpty()) throw new RuntimeException("Event: " + tx.getEvent() + " doesn't exist.");
                return new Ticket(users.get(0), events.get(0), tx.getPlace(), Ticket.Category.valueOf(tx.getCategory().toUpperCase()));
            }).toList();
        } catch (IOException e) {
            log.error("Failed to read the XML file: " + e.getMessage(), e);
            throw new RuntimeException("Failed to read the XML file: " + e.getMessage(), e);
        }
    }

    private byte[] generateTicketsPdf(List<Ticket> tickets, User user) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Booked Tickets for: " + user.getName())
                    .setBold()
                    .setFontSize(18)
                    .setMarginBottom(10));

            Table table = new Table(3);
            table.addCell(new Cell().add(new Paragraph("Event Title")).setBold());
            table.addCell(new Cell().add(new Paragraph("Category")).setBold());
            table.addCell(new Cell().add(new Paragraph("Place")).setBold());

            for (Ticket ticket : tickets) {
                table.addCell(new Cell().add(new Paragraph(ticket.getEvent().getTitle())));
                table.addCell(new Cell().add(new Paragraph(ticket.getCategory().toString())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(ticket.getPlace()))));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
