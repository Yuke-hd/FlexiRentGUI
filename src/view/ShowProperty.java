package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import controller.RentHandler;
import controller.SQL;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.FlexiRentSystem;

public class ShowProperty {

	public String propId;
	FlexiRentSystem admin = new FlexiRentSystem();

	public FlowPane show() {
		ArrayList<VBox> array = new ArrayList<VBox>();
		ArrayList<String> id = new ArrayList<>();
		ArrayList<Button> but = new ArrayList<>();

		FlowPane fp = new FlowPane();
		// gp.setPadding(new Insets(0, 50, 50, 50));
		fp.setAlignment(Pos.CENTER);
		fp.setHgap(50);
		fp.setVgap(50);
		ArrayList<String> result = SQL.ViewData();
		System.out.println(result.size());
		
		for (int i = 0; i < result.size(); i = i + 2) {
			try {
				VBox vb = new VBox();
				vb.setAlignment(Pos.CENTER);
				id.add(result.get(i + 1));
				Text textBox = new Text(trimString(4, result.get(i + 1)));
				
				FileInputStream input;
				input = new FileInputStream(result.get(i));
				Image image = new Image(input);
				ImageView imageBox = new ImageView(image);

				imageBox.setFitWidth(200);
				imageBox.setPreserveRatio(true);

				Button rentButton = new Button("Rent!");
				but.add(rentButton);

				vb.getChildren().addAll(imageBox, textBox, rentButton);
				array.add(vb);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		importProp(result);
		for (int j = 0; j < but.size(); j++) {
			String g;
			String[] k = id.get(j).split("-");
			g = k[0];
			System.out.println(g);
			but.get(j).setUserData(g);//pass the prop id to event handler.
			but.get(j).setOnAction(new RentHandler());
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
	
	private String trimString(int size, String info) {
		String string="";
		String[] sArray = info.split("-");
		for (int i = 1; i < size; i++) {
			string=string.concat(" "+sArray[i-1]);
		}
		return string;
	}

	private Node placeHolderBox() {
		VBox placeHolder = new VBox();
		FileInputStream input;
		try {
			input = new FileInputStream("res/pic/COMING-SOON.gif");
			Image image = new Image(input);
			ImageView imageBox = new ImageView(image);
			imageBox.setFitWidth(200);
			imageBox.setPreserveRatio(true);
			placeHolder.getChildren().add(imageBox);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return placeHolder;
	}
	

}
