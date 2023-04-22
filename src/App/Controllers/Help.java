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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Help {

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    

    public void initialize() throws Exception {
    	    	
  
		Image image = new Image(new File("./resources/images/TicTacToe/circle.png").toURI().toString());
		imageView2.setImage(image);
		Image image2 = new Image(new File("./resources/images/TicTacToe/cross.png").toURI().toString());
		imageView1.setImage(image2);
    }
    @FXML
    void onStart(ActionEvent event)  throws IOException {
		URL url = new File("src/App/vues/InterfacePrincipale.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Stage stage = new Stage();
		stage.setTitle("Tic Tac Toe");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		((Node) event.getSource()).getScene().getWindow().hide();
		stage.show();
    }

}
