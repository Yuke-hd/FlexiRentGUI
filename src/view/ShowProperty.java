package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import controller.FlexiRentSystem;
import controller.RentHandler;
import controller.SQL;
import controller.Utility;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Property;

public class ShowProperty {

	public String propId;
	public FlexiRentSystem admin = new FlexiRentSystem();
	public StringProperty text = new SimpleStringProperty();
	public FlowPane fp;


	public FlowPane show() {
		ArrayList<VBox> array = new ArrayList<VBox>();
		ArrayList<String> id = new ArrayList<>();
		ArrayList<Button> but = new ArrayList<>();

		fp = new FlowPane();
		// gp.setPadding(new Insets(0, 50, 50, 50));
		fp.setAlignment(Pos.CENTER);
		fp.setHgap(50);
		fp.setVgap(50);
		ArrayList<String> result = SQL.ViewData();
		System.out.println(result.size());
		
		for (int i = 0; i < result.size(); i = i + 2) {
			VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			id.add(result.get(i + 1));
			Text textBox = new Text(Utility.trimString(4, result.get(i + 1)));
			
			
			
			ImageView imageBox = Utility.drawImg(result.get(i), 200);

			Button rentButton = new Button("More details");
			but.add(rentButton);

			vb.getChildren().addAll(imageBox, textBox, rentButton);
			array.add(vb);
		}
		importProp(result);
		for (int j = 0; j < but.size(); j++) {
			String g;
			String[] k = id.get(j).split("-");
			g = k[0];
			but.get(j).setUserData(j);//pass the no. of this button to event handler.
			but.get(j).setOnAction(new RentHandler(this));
		}
		
		fp.getChildren().addAll(array);
		fp.getChildren().add(placeHolderBox());
		return fp;
	}

	private void importProp(ArrayList<String> result) {
		for (int i = 0; i < result.size(); i=i+2) {
			System.out.println(result.get(i+1));
			String[] propInfo = result.get(i+1).split("-");
			//id, snum, sname, suburb, bednum,imgpath
			String id = propInfo[0];
			String snum = propInfo[1];
			String sname = propInfo[2];
			String suburb = propInfo[3];
			int bednum = Integer.parseInt(propInfo[4]);
			String imgpath = result.get(i);
			boolean isApt = Boolean.parseBoolean( propInfo[5]);
			if (isApt) {
				admin.addProp(id, snum, sname, suburb, bednum, imgpath);
			}else {
				admin.addProp(id, snum, sname, suburb, imgpath);
			}
			
		}
		
	}
	


	private Node placeHolderBox() {
		VBox placeHolder = new VBox();
		placeHolder.getChildren().add(Utility.drawImg("res/pic/COMING-SOON.gif", 200));
		return placeHolder;
	}
	

}
