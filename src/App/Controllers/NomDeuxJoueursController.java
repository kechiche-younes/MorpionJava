package App.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NomDeuxJoueursController {
	 @FXML
	    private TextField joueur1;
	    @FXML
	    private TextField joueur2;
	    @FXML
	    private Button start1;
	    public static String NomJoueur1;
	    public static String NomJoueur2;



	    
	    public void setName(String name1 ,String name2) {
	        this.NomJoueur1 = name1;
	        this.NomJoueur2 = name2;
	    }
	    @FXML
	    
	    
	    void onStart(ActionEvent event)  throws IOException {
	 
	        
	        // fonction apelé pour difinr le nom du jouer
	        setName(joueur1.getText(),joueur2.getText());
	        
	        URL url =new File("src/App/vues/interfaceMorpionJoueur.fxml").toURI().toURL(); 
	        
	        Parent root = FXMLLoader.load(url);
	        Stage stage = new Stage();
	        
	        stage.setTitle("Play");
	        Scene scene=new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	        // désactiver la possibilité de redimensionner la fenêtre.
	        stage.setResizable(false);
	        // cacher la fenêtre de la saisie du nom du joueur avant d'afficher la fenêtre de jeu principale
			((Node)(event.getSource())).getScene().getWindow().hide();
	    }
	    @FXML
	    void onRetour(ActionEvent event)throws IOException  {
	        URL url =new File("src/App/vues/interfaceMorpionJoueur.fxml").toURI().toURL(); 
	        
	        Parent root = FXMLLoader.load(url);
	        Stage stage = new Stage();
	        
	        stage.setTitle("Tic Tac Toe");
	        Scene scene=new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	        // désactiver la possibilité de redimensionner la fenêtre.
	        stage.setResizable(false);
	        // cacher la fenêtre de la saisie du nom du joueur avant d'afficher la fenêtre de jeu principale
			((Node)(event.getSource())).getScene().getWindow().hide();
	    }
	    
	    
}
