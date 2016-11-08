package services.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import models.DeliveryAddress;
import models.Planning;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;

import com.itextpdf.text.pdf.PdfWriter;
import models.Route;
import models.StreetSection;

/**
 * Created by nicolas on 07/11/16.
 */
public class planningPrinter {

    public static void generatePdfFromPlanning(Planning planning, String pathOfWrotePdfFIle){
        Font titleFont = FontFactory.getFont(FontFactory.COURIER, 23, Font.BOLD, new CMYKColor(255, 255, 255, 0));
        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathOfWrotePdfFIle));
            document.open();
            Paragraph title = new Paragraph("Planning",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph chapterTitle = new Paragraph("Path");
            Chapter chapter = new Chapter(chapterTitle,1);
            chapter.setNumberDepth(0);

            for(Route route : planning.getRoutes()){
                printRoute(chapter,route,planning.getWaitingTimeAtWaypoint(route.getEndWaypoint()));
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
        String titleText = "From way point "+route.getStartWaypoint().getId()+" to way point "+route.getEndWaypoint().getId();
        Paragraph title = new Paragraph(titleText);
        Section section = chapter.addSection(title);
        List<StreetSection> streets = route.getStreetSections();
        for(int i=0 ; i<streets.size() ; i++){
            StreetSection street = streets.get(i);
            String streetName = street.getStreetName();
            int durationValue = street.getDuration();
            int distanceValue = street.getLength();
            while (i+1<streets.size() && streetName.equals(streets.get(i+1).getStreetName())){
                i++;
                street = streets.get(i);
                durationValue += street.getDuration();
                distanceValue += street.getLength();
            }
            String distance;
            if(distanceValue > 10000){
                distance = Math.round(distanceValue/1000.0)/10.0 + " km";
            }else{
                distance = Math.round(distanceValue/100)*10 + " m";
            }
            String instruction = "Follow road "+streetName+" for "+distance+" ("+durationValue/60+" min)";
            Paragraph instructionParagraph = new Paragraph(instruction);
            section.add(instructionParagraph);
        }
        if(waitingTime>60){ //only display waiting if wait more than one minute
            int openingTimeValue = route.getEndWaypoint().getTimeStart();
            String openingTime = openingTimeValue/3600+"h"+(openingTimeValue%3600)/60;
            String instruction = "Wait "+openingTime;
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
