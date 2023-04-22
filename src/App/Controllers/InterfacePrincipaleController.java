package App.Controllers;

import ai.MultiLayerPerceptron;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.text.Text;

import javafx.stage.Stage;

import java.io.*;

import java.net.URL;
import java.nio.charset.StandardCharsets;

public class InterfacePrincipaleController {
	public static double h1, lr;
	public static int h, l;

	public static double getLr() {
		return lr;
	}

	public void setLr(double lr) {
		this.lr = lr;
	}

	public static int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public static int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}
	

	@FXML
	private ToggleButton Computer, Friend;

	@FXML
	private RadioButton Difficile, Facile, Moyen;

	@FXML
	private ImageView ImageView, ImageView1;

	@FXML
	private Button start;

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Text text;

	String source = "resources/config.txt";

	public static MultiLayerPerceptron Mult;

	String[] difficileSp;
	String[] MoyenneSp;
	String[] facileSp;
	


	public void initialize() {
		Friend.setOnAction(e -> {
			if (Friend.isSelected()) {
				Difficile.setDisable(true);
				Facile.setDisable(true);
				Moyen.setDisable(true);
			} else if (Computer.isSelected()) {
				Difficile.setDisable(false);
				Facile.setDisable(false);
				Moyen.setDisable(false);
			}
		});
		Computer.setOnAction(e -> {
			if (Computer.isSelected()) {
				Difficile.setDisable(false);
				Facile.setDisable(false);
				Moyen.setDisable(false);
			} else {
				Difficile.setDisable(true);
				Facile.setDisable(true);
				Moyen.setDisable(true);
			}
		});
		text.setId("fancytext");
		ToggleGroup group = new ToggleGroup();
		ToggleGroup group2 = new ToggleGroup();
		Facile.setToggleGroup(group);
		Difficile.setToggleGroup(group);
		Moyen.setToggleGroup(group);
		Friend.setToggleGroup(group2);
		Computer.setToggleGroup(group2);
	}

	@FXML
	void supprime(ActionEvent event) throws IOException {
		URL url = new File("src/App/vues/ListeModel.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Stage stage = new Stage();
		stage.setTitle("Play time");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}

	@FXML
	void change(ActionEvent event) throws IOException {
		URL url = new File("src/App/vues/Parametre.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Stage stage = new Stage();
		stage.setTitle("Play time");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}

	@FXML
	void help(ActionEvent event) throws IOException {
		URL url = new File("src/App/vues/Help.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Stage stage = new Stage();
		stage.setTitle("help");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}
	String ModeFacile;
	String ModeDifficile;
	String ModeMoyen;


	@FXML
	public void onClickStart(ActionEvent event) throws IOException {

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
		BufferedReader r = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8));
		String facile = r.readLine();
		String moyen = r.readLine();
		String difficile = r.readLine();

		if (Computer.isSelected()) {
			if (Facile.isSelected()) {
				facileSp = facile.split(":");
				int hf = Integer.parseInt(facileSp[1]);
				double lrf = Double.parseDouble(facileSp[2]);
				int lf = Integer.parseInt(facileSp[3]);
				setH(hf);
				setL(lf);
				setLr(lrf);
				ModeFacile = "model" + lrf + "_" + hf + "_" + lf + ".srl";
				String path = "resources/models/" + ModeFacile;
				if (new File(path).exists()) {
					Mult = MultiLayerPerceptron.load(path);
					startGame();
					((Node) event.getSource()).getScene().getWindow().hide();
				} else {
					lanceApprentssage();
				}
			} else if (Difficile.isSelected()) {

				difficileSp = difficile.split(":");
				int hd = Integer.parseInt(difficileSp[1]);
				double lrd = Double.parseDouble(difficileSp[2]);
				int ld = Integer.parseInt(difficileSp[3]);
				setH(hd);
				setL(ld);
				setLr(lrd);

				ModeDifficile = "model" + lrd + "_" + hd + "_" + ld + ".srl";
				String path = "resources/models/" + ModeDifficile;
				if (new File(path).exists()) {
					Mult = MultiLayerPerceptron.load(path);
					startGame();
					((Node) event.getSource()).getScene().getWindow().hide();

				} else {
					lanceApprentssage();
				}
			}

			else if (Moyen.isSelected()) {

				MoyenneSp = moyen.split(":");

				double lrm = Double.parseDouble(MoyenneSp[2]);
				int hm = Integer.parseInt(MoyenneSp[1]);
				int lm = Integer.parseInt(MoyenneSp[3]);
				setH(hm);
				setL(lm);
				setLr(lrm);

				ModeMoyen = "model" + lrm + "_" + hm + "_" + lm + ".srl";
				String path = "resources/models/" + ModeMoyen;
				if (new File(path).exists()) {
					Mult = MultiLayerPerceptron.load(path);
					startGame();
					((Node) event.getSource()).getScene().getWindow().hide();

				} else {
					lanceApprentssage();
				}
			}

			bis.close();
		}

		if (Friend.isSelected()) {
			URL url = new File("src/App/vues/InterfaceMorpionJoueur.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Stage stage = new Stage();
			stage.setTitle("Play time");
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			stage.setResizable(false);
			((Node) event.getSource()).getScene().getWindow().hide();
		}
	}
	
	
	
	public void changeNiveau(ActionEvent event) throws IOException
	{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
		BufferedReader r = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8));
		String facile = r.readLine();
		String moyen = r.readLine();
		String difficile = r.readLine();
		if (InterfaceMorpionAI.niveau == 1) {

			facileSp = facile.split(":");
			int hf = Integer.parseInt(facileSp[1]);
			double lrf = Double.parseDouble(facileSp[2]);
			int lf = Integer.parseInt(facileSp[3]);
			setH(hf);
			setL(lf);
			setLr(lrf);
			ModeFacile = "model" + lrf + "_" + hf + "_" + lf + ".srl";
			String path = "resources/models/" + ModeFacile;
			if (new File(path).exists()) {
				Mult = MultiLayerPerceptron.load(path);
		        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
				startGame();

			} else {
				lanceApprentssage();
			}
		} else if (InterfaceMorpionAI.niveau == 3) {

			difficileSp = difficile.split(":");
			int hd = Integer.parseInt(difficileSp[1]);
			double lrd = Double.parseDouble(difficileSp[2]);
			int ld = Integer.parseInt(difficileSp[3]);
			setH(hd);
			setL(ld);
			setLr(lrd);

			ModeDifficile = "model" + lrd + "_" + hd + "_" + ld + ".srl";
			String path = "resources/models/" + ModeDifficile;
			if (new File(path).exists()) {
				Mult = MultiLayerPerceptron.load(path);
		        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
				startGame();


			} else {
				lanceApprentssage();
			}
		}

		else if (InterfaceMorpionAI.niveau == 2) {

			MoyenneSp = moyen.split(":");

			double lrm = Double.parseDouble(MoyenneSp[2]);
			int hm = Integer.parseInt(MoyenneSp[1]);
			int lm = Integer.parseInt(MoyenneSp[3]);
			setH(hm);
			setL(lm);
			setLr(lrm);

			ModeMoyen = "model" + lrm + "_" + hm + "_" + lm + ".srl";
			String path = "resources/models/" + ModeMoyen;
			if (new File(path).exists()) {
				Mult = MultiLayerPerceptron.load(path);
		        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
				startGame();


			} else {
				lanceApprentssage();
			}
		}

		bis.close();
	}
	
//    
	public void lanceApprentssage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/App/vues/Apprentissage.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Apprentissage");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	    // Ajouter un EventHandler pour détecter lorsque la fenêtre est fermée
	    stage.setOnHidden(event -> {
			try {
				startGame();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void startGame() throws IOException {
		URL url = new File("src/App/vues/InterfaceMorpionAI.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Stage stage = new Stage();
		stage.setTitle("Tic Tac Toe");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	}

	public void listeModele(ActionEvent actionEvent) throws IOException {
		URL url = new File("src/App/vues/InterfaceListeModel.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Stage stage = new Stage();
		stage.setTitle("Model's list");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}

}
