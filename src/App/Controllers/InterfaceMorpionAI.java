package App.Controllers;

import javafx.animation.Animation;
import  App.Controllers.InterfacePrincipaleController.*;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.TextField;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ai.MultiLayerPerceptron;


public class InterfaceMorpionAI {
	@FXML
	private Pane panebtn;
	@FXML
	private Button c1, c2, c3, c4, c5, c6, c7, c8, c9, RePlay, change_nom;
	@FXML
	private Label nom, gang;
	@FXML
	private ImageView imageview1;
	private InterfacePrincipaleController InterfacePrincipaleController;

	
    boolean gangne = false;
	// liste de boutton pour pouvoir les manipuler facilement
	ArrayList<Button> liste = new ArrayList<Button>();

	// Ajout des joueurs à une liste pour pouvoir les manipuler plus facilement
	ArrayList<Joueur> players = new ArrayList<Joueur>(2);

	Table Table;
	Table game;
	int indice;

	double[] inputs;
	double[] output;
	boolean[] visited = new boolean[9];

	public void initialize()  {
		nom.setText(NomJouerController.NomJoueur1);
		Image image = new Image(new File("./resources/images/ManVsCpu.jpg").toURI().toString());
		imageview1.setImage(image);

		// Ajout des boutons à une liste pour pouvoir les manipuler plus facilement
		liste.add(c1);
		liste.add(c2);
		liste.add(c3);
		liste.add(c4);
		liste.add(c5);
		liste.add(c6);
		liste.add(c7);
		liste.add(c8);
		liste.add(c9);

		// Initialisation d'un tableau pour suivre les cases déjà visitées
		for (int i = 0; i < 9; i++) {
			visited[i] = false;
		}
		// Affectation des identifiants à chaque bouton
		// Cet identifiant est important car il est utilisé pour identifier le bouton
		// qui a été cliqué par l'utilisateur lorsqu'il joue son coup.
		for (int i = 0; i < liste.size(); i++) {
			Button button = liste.get(i);
			button.setId(String.valueOf(i));
		}

		// Création des deux joueurs, humain et ordinateur, avec leur symbole respectif
		Joueur humain1 = new Joueur();
		Joueur computer = new Joueur();
		humain1.setSymbole(0);
		computer.setSymbole(1);

		// Le premier coup est effectué par le joueur humain
		humain1.myTour = true;

		players.add(humain1);
		players.add(computer);

		// Création de la table de jeu avec les deux joueurs
		game = new Table(players);

		// Initialisation de la fin de la partie à "false"
		game.finish = false;

		// Initialisation des entrées à un tableau de 9 zéros
		inputs = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

		// Calcul de la sortie du réseau de neurones avec les entrées données
		output = InterfacePrincipaleController.Mult.forwardPropagation(inputs);

	}

	@FXML
	public void jouer(ActionEvent actionEvent) throws IOException, InterruptedException {
		// Ces deux lignes permettent de récupérer le bouton qui a déclenché l'événement
		// "jouer" et d'obtenir son index dans la liste de boutons "liste".
		Button c = (Button) actionEvent.getSource();
		int index = liste.indexOf(c);
		if (game.finish != true) {
			c.setText("X");
			c.setDisable(true);
			c.setStyle("-fx-font: 50 arial;-fx-text-fill: red;");
			game.setChoice(index, 0);
			visited[index] = true;
			double maxOutput = -Double.MAX_VALUE;
			if (indice < visited.length) {

				for (int i = 0; i < output.length; i++) {
					if (!visited[i] && output[i] > maxOutput) {
						maxOutput = output[i];
						indice = i;
					}
				}
			}
			if (indice < visited.length && gangne == false) {
				for (Button c0 : liste) {
					if ((Integer.parseInt(c0.getId())) == indice) {
						c0.setText("O");
						c0.setDisable(true);
						c0.setStyle("-fx-font: 50 arial;-fx-text-fill: green;");
						game.setChoice(indice, 1);
						visited[indice] = true;
					}
				}
			}
			if (game.checkBoard()) {
				game.finish = true;
			}
			String b1 = c1.getText();
			String b2 = c2.getText();
			String b3 = c3.getText();
			String b4 = c4.getText();
			String b5 = c5.getText();
			String b6 = c6.getText();
			String b7 = c7.getText();
			String b8 = c8.getText();
			String b9 = c9.getText();
		
	

			if (b1.equals("X") && b4.equals("X") && b7.equals("X")) {
				c1.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c4.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c7.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerX();	
				return;
			}else if (b2.equals("X") && b5.equals("X") && b8.equals("X")) {
				c2.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c5.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c8.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerX();
				return;
			}else if (b3.equals("X") && b6.equals("X") && b9.equals("X")) {
				c3.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c6.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c9.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerX();
				return;
			}else if (b1.equals("X") && b5.equals("X") && b9.equals("X")) {
				c1.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c5.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c9.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerX();
				return;
			}else if (b3.equals("X") && b5.equals("X") && b7.equals("X")) {
				c3.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c5.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c7.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerX();
				return;
			}else if (b1.equals("X") && b2.equals("X") && b3.equals("X")) {
				c1.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c2.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c3.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerX();
				return;
			}else if (b4.equals("X") && b5.equals("X") && b6.equals("X")) {
				c4.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c5.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c6.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				// Créer une image à afficher
				animationWinnerX();
				return;
			}else if (b7.equals("X") && b8.equals("X") && b9.equals("X")) {
				c7.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c8.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c9.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerX();
				return;
			}
			if (b1.equals("O") && b4.equals("O") && b7.equals("O")) {
				c1.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c4.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c7.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerO();
			}else if (b2.equals("O") && b5.equals("O") && b8.equals("O")) {
				c2.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c5.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c8.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerO();

			}else if (b3.equals("O") && b6.equals("O") && b9.equals("O")) {
				c3.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c6.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c9.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerO();
			}else if (b1.equals("O") && b2.equals("O") && b3.equals("O")) {
				c1.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c2.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c3.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerO();
			}else if (b4.equals("O") && b5.equals("O") && b6.equals("O")) {
				c4.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c5.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c6.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerO();
			}else if (b7.equals("O") && b8.equals("O") && b9.equals("O")) {
				c7.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c8.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c9.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerO();
			}else if (b1.equals("O") && b5.equals("O") && b9.equals("O")) {
				c1.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c5.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c9.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerO();
			}else if (b5.equals("O") && b3.equals("O") && b7.equals("O")) {
				c5.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c3.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				c7.setBackground(new Background(new BackgroundFill(Color.web("#9036c6"), CornerRadii.EMPTY, Insets.EMPTY)));
				animationWinnerO();
			}else if(!b1.isEmpty() && !b2.isEmpty() && !b3.isEmpty() && !b4.isEmpty() && !b5.isEmpty() && !b6.isEmpty() && !b7.isEmpty() && !b8.isEmpty() && !b9.isEmpty() && gangne == false){
				panebtn.setOpacity(100);
				ImageView imageView = new ImageView(new Image(new File("./resources/images/TicTacToe/egal.png").toURI().toString()));

				imageView.setFitWidth(400);
				imageView.setFitHeight(400);
				// Positionner l'image au centre du cadre
				double x = c9.getLayoutX() + (c9.getWidth() - imageView.getFitWidth()) / 2;
				double y = c9.getLayoutY() + (c9.getHeight() - imageView.getFitHeight()) / 2;
				imageView.setLayoutX(x);
				imageView.setLayoutY(y);
				// Créer une transition pour l'animation
				FadeTransition ft = new FadeTransition(Duration.seconds(3), imageView);
				ft.setFromValue(0.0);
				ft.setToValue(1.0);
				ft.setAutoReverse(true);
				ft.setCycleCount(2);

				// Ajouter l'image à la scène
				Scene scene = c9.getScene();
				Pane pane = (Pane) scene.getRoot();
				pane.getChildren().add(imageView);

				// Lancer l'animation
				ft.play();

				panebtn.setOpacity(1.0);
			}
		}

	}

	
	
	@FXML
	void onNom(ActionEvent event) throws IOException {
		URL url = new File("src/App/vues/NomJouer.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Stage stage = new Stage();
		stage.setTitle("Tic Tac Toe");
		Scene scene = new Scene(root);
		stage.setScene(scene);
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
		stage.show();

	}

	private void resetGame() {
		// Réinitialiser les tableaux visited et game
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		game.finish = false;

		// Mettre à jour les boutons pour qu'ils soient à nouveau actifs
		for (Button button : liste) {
			button.setText("");
			button.setDisable(false);
			button.setStyle("-fx-font: 50 arial; -fx-background-color: #CECCCC");
		}
	}

	@FXML
	void onRePlay(ActionEvent event) throws IOException {
		resetGame(); 
		// réinitialiser l'état du jeu
		URL url = new File("src/App/vues/InterfaceMorpionAI.fxml").toURI().toURL();
		Parent view2 = FXMLLoader.load(url);

		// Création d'une liste contenant tous les boutons
		List<Button> buttons = new ArrayList<>();
		for (Node node : view2.getChildrenUnmodifiable()) {
			if (node instanceof Button) {
				buttons.add((Button) node);
			}
		}

		Scene scene2 = new Scene(view2);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene2);
		window.setTitle("Morpion");

		// Création de la transition de rotation
		SequentialTransition rotationTransition = new SequentialTransition();
		for (Button button : buttons) {
			RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), button);
			rotateTransition.setFromAngle(0);
			rotateTransition.setToAngle(360);
			rotationTransition.getChildren().add(rotateTransition);
		}

		// Création de la transition de retour à l'état initial
		SequentialTransition resetTransition = new SequentialTransition();
		for (Button button : buttons) {
			RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0), button);
			rotateTransition.setToAngle(0);
			resetTransition.getChildren().add(rotateTransition);
		}

		// Ajout des deux transitions à la séquence de transition
		SequentialTransition transition = new SequentialTransition(rotationTransition, resetTransition);

		// Lancement de la transition
		transition.play();
		// Affichage de la nouvelle fenêtre
		window.show();

		// Cacher la fenêtre actuelle
		if (event != null && event.getSource() != null && ((Node) event.getSource()).getScene() != null
				&& ((Node) event.getSource()).getScene().getWindow() != null) {
			((Node) event.getSource()).getScene().getWindow().hide();
		}
	}

	@FXML
	void onRetour(ActionEvent event) throws IOException {
		// Récupération de la scène et de la racine de la vue actuelle

		Scene currentScene = ((Node) event.getSource()).getScene();
		Parent root = currentScene.getRoot();

		// Création d'une nouvelle scène et d'une nouvelle racine pour la vue suivante

		URL url = new File("src/App/vues/InterfacePrincipale.fxml").toURI().toURL();
		Parent nextRoot = FXMLLoader.load(url);
		Scene nextScene = new Scene(nextRoot);

		// Mise à jour de la taille de la nouvelle racine
		nextRoot.prefWidth(currentScene.getWidth());
		nextRoot.prefHeight(currentScene.getHeight());

		// Création de la transition de fondu
		FadeTransition fadeOut = new FadeTransition(Duration.millis(500), root);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);

		// Action à exécuter une fois la transition terminée
		fadeOut.setOnFinished((e) -> {

			// Affichage de la nouvelle scène avec une transition de fondu
			Stage stage = (Stage) currentScene.getWindow();
			stage.setScene(nextScene);

			// Création de la transition de fondu pour la nouvelle vue
			FadeTransition fadeIn = new FadeTransition(Duration.millis(500), nextRoot);
			fadeIn.setFromValue(0.0);fadeIn.setToValue(1.0);fadeIn.play();});

		// Lancement de la transition de fondu
		fadeOut.play();
	}

	InterfacePrincipaleController principal = new InterfacePrincipaleController();
	public static int niveau = 0;
	@FXML
	void facile(ActionEvent event) throws IOException {
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
		niveau = 1;
		principal.changeNiveau(event);


	}
	@FXML
	void moyen(ActionEvent event) throws IOException {
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
		niveau = 2;
		principal.changeNiveau(event);

	}


	@FXML
	void difficile(ActionEvent event) throws IOException {
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
		niveau = 3;
		principal.changeNiveau(event);

	}

	private void animationWinnerO(){
		panebtn.setOpacity(100);
		ImageView imageView = new ImageView(new Image(new File("./resources/images/TicTacToe/winCircle.png").toURI().toString()));

		imageView.setFitWidth(400);
		imageView.setFitHeight(400);
		// Positionner l'image au centre du cadre
		double x = c9.getLayoutX() + (c9.getWidth() - imageView.getFitWidth()) / 2;
		double y = c9.getLayoutY() + (c9.getHeight() - imageView.getFitHeight()) / 2;
		imageView.setLayoutX(x);
		imageView.setLayoutY(y);
		// Créer une transition pour l'animation
		FadeTransition ft = new FadeTransition(Duration.seconds(3), imageView);
		ft.setFromValue(0.0); ft.setToValue(1.0); ft.setAutoReverse(true); ft.setCycleCount(2);


		// Ajouter l'image à la scène
		Scene scene = c9.getScene();
		Pane pane = (Pane) scene.getRoot();
		pane.getChildren().add(imageView);

		// Afficher le nom du gagnant avec une transition de fade-in
		gang.setText("Ordinateur a gagné!!!");

		// Créer une animation de zoom-in / zoom-out pour l'objet "gang"
		ScaleTransition st = new ScaleTransition(Duration.seconds(1), gang);
		st.setFromX(1.0);
		st.setFromY(1.0);
		st.setToX(1.5);
		st.setToY(1.5);
		st.setAutoReverse(true);
		st.setCycleCount(Animation.INDEFINITE);

		// Ajouter une animation de fade-in pour l'objet "gang"
		FadeTransition ft1 = new FadeTransition(Duration.seconds(2), gang);
		ft1.setFromValue(0.0);
		ft1.setToValue(1.0);

		// Créer une séquence d'animation pour combiner l'animation de zoom-in / zoom-out et l'animation de fade-in
		SequentialTransition seq = new SequentialTransition(ft1, st);

		// Démarrer la séquence d'animation
		seq.play();



		// Lancer l'animation
		ft.play();

		panebtn.setOpacity(1.0);
		gangne = true;
		return;
	}
	
	
	private void animationWinnerX()
	{
		// Créer une image à afficher
		panebtn.setOpacity(100);
		ImageView imageView = new ImageView(new Image(new File("./resources/images/TicTacToe/wincross.png").toURI().toString()));

		imageView.setFitWidth(400);
		imageView.setFitHeight(400);
		// Positionner l'image au centre du cadre
		double x = c9.getLayoutX() + (c9.getWidth() - imageView.getFitWidth()) / 2;
		double y = c9.getLayoutY() + (c9.getHeight() - imageView.getFitHeight()) / 2;
		imageView.setLayoutX(x);
		imageView.setLayoutY(y);
		// Créer une transition pour l'animation
		FadeTransition ft = new FadeTransition(Duration.seconds(3), imageView);
		ft.setFromValue(0.0);ft.setToValue(1.0);ft.setAutoReverse(true);ft.setCycleCount(2);

		// Ajouter l'image à la scène
		Scene scene = c9.getScene();
		Pane pane = (Pane) scene.getRoot();
		pane.getChildren().add(imageView);
 if(NomJouerController.NomJoueur1 == null)
 {
		// Afficher le nom du gagnant avec une transition de fade-in
		gang.setText("vous avez gagné"); 
 }else {
		// Afficher le nom du gagnant avec une transition de fade-in
		gang.setText(NomJouerController.NomJoueur1+"a gagné");
 }

		// Créer une animation de zoom-in / zoom-out pour l'objet "gang"
		ScaleTransition st = new ScaleTransition(Duration.seconds(1), gang);
		st.setFromX(1.0);
		st.setFromY(1.0);
		st.setToX(1.5);
		st.setToY(1.5);
		st.setAutoReverse(true);
		st.setCycleCount(Animation.INDEFINITE);

		// Ajouter une animation de fade-in pour l'objet "gang"
		FadeTransition ft1 = new FadeTransition(Duration.seconds(2), gang);
		ft1.setFromValue(0.0);
		ft1.setToValue(1.0);

		// Créer une séquence d'animation pour combiner l'animation de zoom-in / zoom-out et l'animation de fade-in
		SequentialTransition seq = new SequentialTransition(ft1, st);

		// Démarrer la séquence d'animation
		seq.play();
		// Lancer l'animation
		ft.play();

		panebtn.setOpacity(1.0);
		gangne = true;
		return;
	}
	
}
