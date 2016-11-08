package components.mapcanvas;

import javafx.scene.control.Tooltip;
import models.Intersection;
import javafx.embed.swing.SwingNode;


public class IntersectionInfo{
	Intersection intersection;
	Tooltip tool;
	SwingNode swnode;
	public IntersectionInfo(Intersection inter){
		intersection = inter;
		tool = new Tooltip("something");
		System.out.println("Tooltip");
	}
	
	public void install(){
		swnode = new SwingNode();
		swnode.setLayoutX(intersection.getX());
		swnode.setLayoutY(intersection.getY()); 
		Tooltip.install(swnode,tool);
		
	}

   
}

