package view;

import java.util.ArrayList;

import controller.FlexiRentSystem;
import controller.RentHandler;
import controller.SQL;
import controller.Utility;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.DateTime;
import model.Property;
import model.Record;

public class ShowProperty {

	public String propId;
	public FlexiRentSystem admin;
	public FlowPane fp;

	public ShowProperty() {

	}

	public FlowPane show(FlexiRentSystem admin, String type) {
		admin = new FlexiRentSystem();
		ArrayList<VBox> array = new ArrayList<VBox>();
		ArrayList<String> id = new ArrayList<>();
		ArrayList<Button> but = new ArrayList<>();
		ArrayList<String> result;
		this.admin = admin;

		fp = new FlowPane();
		fp.setAlignment(Pos.CENTER);
		fp.setHgap(80);
		fp.setVgap(50);
		if (type.equals("all")) {
			result = SQL.ViewData();
		} else {
			result = SQL.ViewData(type);
		}

		System.out.println(result.size() + " SQL result size");

		for (int i = 0; i < result.size(); i++) {
			VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			id.add(result.get(i));
			Text textBox = new Text(Utility.trimString(1, 4, result.get(i)));
			textBox.setTextAlignment(TextAlignment.CENTER);
			textBox.setWrappingWidth(200);

			String imgPath = Utility.trimString(8, result.get(i));
			System.out.println(Utility.trimString(1, 4, result.get(i)));
			ImageView imageBox = Utility.drawImg(imgPath, 200);

			Button rentButton = new Button("More details");
			but.add(rentButton);

			vb.getChildren().addAll(imageBox, textBox, rentButton);
			array.add(vb);
		}
		importProp(result);

		for (int j = 0; j < but.size(); j++) {
			but.get(j).setUserData(j);// pass the no. of this button to event handler.
			but.get(j).setOnAction(new RentHandler(this));
		}

		fp.getChildren().addAll(array);
		fp.getChildren().add(placeHolderBox());
		return fp;
	}

	private void importProp(ArrayList<String> result) {
		for (int i = 0; i < result.size(); i++) {
			String[] propInfo = result.get(i).split("-");
			// id, snum, sname, suburb, bednum,imgpath
			String id = propInfo[1];
			String snum = propInfo[2];
			String sname = propInfo[3];
			String suburb = propInfo[4];
			int bednum = Integer.parseInt(propInfo[5]);
			String imgpath = Utility.trimString(8, result.get(i));
			boolean isApt = Boolean.parseBoolean(propInfo[6]);
			boolean isRented = Boolean.parseBoolean(propInfo[7]);
			String mntdate = propInfo[9];
			if (isApt) {
				admin.addProp(id, snum, sname, suburb, bednum, isRented, imgpath);
			} else {
				System.out.println(mntdate);
				admin.addProp(id, snum, sname, suburb, isRented, imgpath, new DateTime(mntdate));
			}
			System.out.println("PROPERTY LIST SIZE NOW IS " + admin.getPropList().size());
			importRecrods(id);
		}

	}

	private void importRecrods(String id) {
		System.out.println("Importing all records for " + id);
		ArrayList<String> result = SQL.viewRecords(id);
		for (int k = 0; k < result.size(); k++) {
			Property prop = admin.getPropList().get(admin.inputPropID(id));
			String[] recInfo = result.get(k).split("/");
			Record[] propRecord = prop.getRecordList();
			Record record;
			String recid = recInfo[2];
			DateTime sdate = Utility.reverseDate(recInfo[3]);
			DateTime edate = Utility.reverseDate(recInfo[4]);
			DateTime rdate = null;
			double rentfee;
			double latefee;
			try {
				rentfee = Double.valueOf(recInfo[6]);
				latefee = Double.valueOf(recInfo[7]);
			} catch (NumberFormatException e) {
				rentfee = 0.0;
				latefee = 0.0;
			}
			if (rentfee > 0.0) {
				rdate = Utility.reverseDate(recInfo[5]);
				latefee = Double.valueOf(recInfo[7]);
			}
			if (rentfee > 0.0) {
				for (int j = 9; j > 0; j--) {
					propRecord[j] = propRecord[j - 1];
				}
				record = new Record(recid, sdate, edate, rdate, rentfee, latefee);
				propRecord[0] = record;
			} else {
				for (int j = 9; j > 0; j--) {
					propRecord[j] = propRecord[j - 1];
				}
				record = new Record(recid, sdate, edate);
				propRecord[0] = record;
			}
		}
	}

	private Node placeHolderBox() {
		VBox placeHolder = new VBox();
		placeHolder.getChildren().add(Utility.drawImg("res/pic/COMING-SOON.gif", 200));
		return placeHolder;
	}

}
