package services.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import models.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.pdf.PdfWriter;
import services.tools.timeTools;

/**
 * Created by nicolas on 07/11/16.
 */
public class planningPrinter {

    private static final Font titleFont = FontFactory.getFont(FontFactory.COURIER, 30, Font.BOLD, new CMYKColor(255, 255, 255, 0));
    private static final Font chapterFont = FontFactory.getFont(FontFactory.COURIER, 23, Font.BOLD, new CMYKColor(255, 255, 255, 0));
    private static final Font sectionFont = FontFactory.getFont(FontFactory.COURIER, 15, Font.BOLD, new CMYKColor(180, 180, 180, 0));
    private static final Font instructionFont = FontFactory.getFont(FontFactory.COURIER, 10, Font.NORMAL, new CMYKColor(255, 255, 255, 0));
    private static final float instructionLeftMargin = 10;

    public static void generatePdfFromPlanning(Planning planning, String pathOfWrotePdfFIle){

        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathOfWrotePdfFIle));
            document.open();
            Paragraph title = new Paragraph("Planning",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph chapterTitle = new Paragraph("Path",chapterFont);
            Chapter chapter = new Chapter(chapterTitle,1);
            chapter.setNumberDepth(0);

            Integer currentTime = planning.getWarehouse().getTimeStart();
            for(Route route : planning.getRoutes()){
                currentTime = printRoute(chapter,route,planning.getWaitingTimeAtWaypoint(route.getEndWaypoint()),currentTime);
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

    /**
     * Print the route into the given chapter
     * @param chapter the chapter where the route will be described
     * @param route the route to describe
     * @param waitingTime the waiting time before the delivery
     */
    private static int printRoute(Chapter chapter, Route route, int waitingTime, Integer time){
        String titleText;
        if(route.getStartWaypoint() instanceof Warehouse){
            titleText = "From warehouse to delivery address "+route.getEndWaypoint().getId();
        }
        else if(route.getEndWaypoint() instanceof Warehouse){
            titleText = "From delivery address "+route.getStartWaypoint().getId()+" to warehouse";
        }
        else{
            titleText = "From delivery address "+route.getStartWaypoint().getId()+" to delivery address "+route.getEndWaypoint().getId();
        }
        Paragraph title = new Paragraph(titleText,sectionFont);
        Section section = chapter.addSection(title);
        //add leaving instruction
        {
            String instruction = "[" + timeTools.printTime(time) + "]";;
            if (route.getStartWaypoint() instanceof Warehouse) {
                instruction += " leave the warehouse";
            } else {
                instruction += " leave delivery address "+route.getStartWaypoint().getId();
            }
            Paragraph instructionParagraph = new Paragraph(instruction, instructionFont);
            instructionParagraph.setIndentationLeft(instructionLeftMargin);
            section.add(instructionParagraph);
        }
        //road instructions
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
            Paragraph instructionParagraph = new Paragraph(instruction, instructionFont);
            instructionParagraph.setIndentationLeft(instructionLeftMargin);
            section.add(instructionParagraph);
            //update time
            time += durationValue;
        }
        //waiting instruction
        if(waitingTime>60){ //only display waiting if wait more than one minute
            int openingTimeValue = route.getEndWaypoint().getTimeStart();
            String openingTime = timeTools.printTime(openingTimeValue);
            String instruction = "Wait "+openingTime;
            Paragraph instructionParagraph = new Paragraph(instruction, instructionFont);
            instructionParagraph.setIndentationLeft(instructionLeftMargin);
            section.add(instructionParagraph);
            //update time
            time += waitingTime;
        }
        //deliver instruction
        {
            String instruction = "[" + timeTools.printTime(time) + "]";
            if(route.getEndWaypoint() instanceof Warehouse){
                instruction += " Enter back to the warehouse";
            }
            else {
                instruction += " Deliver " + route.getEndWaypoint().getId() + " (" + route.getEndWaypoint().getDuration() / 60 + " min)";
                time += route.getEndWaypoint().getDuration();
            }
            Paragraph instructionParagraph = new Paragraph(instruction, instructionFont);
            instructionParagraph.setIndentationLeft(instructionLeftMargin);
            section.add(instructionParagraph);
        }
        return time;
    }
}
