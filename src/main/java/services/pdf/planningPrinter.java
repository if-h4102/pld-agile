package services.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import models.DeliveryAddress;
import models.Planning;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.pdf.PdfWriter;
import models.Route;
import models.StreetSection;

/**
 * Created by nicolas on 07/11/16.
 */
public class planningPrinter {

    public static void generatePdfFromPlanning(Planning planning, String pathOfWritedPdfFIle){
        Font titleFont = FontFactory.getFont(FontFactory.COURIER, 23, Font.BOLD, new CMYKColor(255, 255, 255, 0));
        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Planning.pdf"));
            document.open();
            Paragraph title = new Paragraph("Planning",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph chapterTitle = new Paragraph("Path");
            Chapter chapter = new Chapter(chapterTitle,1);
            chapter.setNumberDepth(0);

            for(Route route : planning.getRoutes()){
                printRoute(chapter,route,planning.getWaitingTimeAtWayPoint(route.getEndWaypoint()));
            }

            document.add(chapter);

            document.close();
            writer.close();
        } catch (DocumentException e)
        {
            e.printStackTrace();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static void printRoute(Chapter chapter, Route route, int waitingTime){
        Paragraph title = new Paragraph("From way point "+route.getStartWaypoint().getId()+" to way point "+route.getEndWaypoint().getId());
        Section section = chapter.addSection(title);
        for(StreetSection street : route.getStreetSections()){
            String instruction = "Follow road "+street.getStreetName()+" during "+street.getDuration()/60+" min";
            Paragraph instructionParagraph = new Paragraph(instruction);
            section.add(instructionParagraph);
        }
        if(waitingTime>0){
            String instruction = "Wait for "+waitingTime/60+" min";
            Paragraph instructionParagraph = new Paragraph(instruction);
            section.add(instructionParagraph);
        }
        if( route.getEndWaypoint() instanceof DeliveryAddress) {
            String instruction = "Deliver " + route.getEndWaypoint().getId() + " (" + route.getEndWaypoint().getDuration() / 60 + " min)";
            Paragraph instructionParagraph = new Paragraph(instruction);
            section.add(instructionParagraph);
        }
    }
}
