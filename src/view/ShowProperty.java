package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.SQL;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShowProperty {
	
	public static FlowPane show() {
		ArrayList<VBox> array = new ArrayList<VBox>();
		
		FlowPane fp = new FlowPane();
		//gp.setPadding(new Insets(0, 50, 50, 50));
		fp.setAlignment(Pos.CENTER);
		fp.setHgap(50);
		fp.setVgap(50);
		ArrayList<String> result=SQL.ViewData();
		System.out.println(result.size());
		for (int i=0;i<result.size();i=i+2) {
			try {
				VBox vb = new VBox();
				Text textBox = new Text(result.get(i+1));
				FileInputStream input;
				input = new FileInputStream(result.get(i));
				System.out.println("kk");
				Image image = new Image(input);
				ImageView imageBox = new ImageView(image);
				imageBox.setFitWidth(200);
				imageBox.setPreserveRatio(true);
				vb.getChildren().addAll(imageBox,textBox);
				array.add(vb);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
			
			
			
	/*
		try {
			input = new FileInputStream("./res/Horde.png");
			Image image = new Image(input);
			ImageView imageBox = new ImageView(image);
			imageBox.setFitWidth(200);
			imageBox.setPreserveRatio(true);
			vb.getChildren().addAll(imageBox,textBox);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			input = new FileInputStream("./res/Horde.png");
			Image image = new Image(input);
			ImageView imageBox = new ImageView(image);
			imageBox.setFitWidth(200);
			imageBox.setPreserveRatio(true);
			vb2.getChildren().addAll(imageBox,textBox);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			input = new FileInputStream("./res/Horde.png");
			Image image = new Image(input);
			ImageView imageBox = new ImageView(image);
			imageBox.setFitWidth(200);
			imageBox.setPreserveRatio(true);
			vb3.getChildren().addAll(imageBox,textBox);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			input = new FileInputStream("./res/Horde.png");
			Image image = new Image(input);
			ImageView imageBox = new ImageView(image);
			imageBox.setFitWidth(200);
			imageBox.setPreserveRatio(true);
			vb4.getChildren().addAll(imageBox,textBox);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		

		fp.getChildren().addAll(array);
		return fp;
	}
}
