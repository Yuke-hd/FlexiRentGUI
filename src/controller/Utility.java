package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utility {

	public static String trimString(int size, String info) {
		String string="";
		String[] sArray = info.split("-");
		for (int i = 1; i < size; i++) {
			string=string.concat(" "+sArray[i-1]);
		}
		return string;
	}

	public static ImageView drawImg(String filePath, int imgWidth) {
		FileInputStream input;
		ImageView imageBox = null;
		try {
			input = new FileInputStream(filePath);
			Image image = new Image(input);
			imageBox = new ImageView(image);
			imageBox.setFitWidth(imgWidth);
			imageBox.setPreserveRatio(true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imageBox;
		
	}
}
