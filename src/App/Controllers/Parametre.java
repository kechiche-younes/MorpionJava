package App.Controllers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Parametre {

	    @FXML
	    private TextField L1;

	    @FXML
	    private TextField L2;

	    @FXML
	    private TextField L3;

	    @FXML
	    private TextField Lr1;

	    @FXML
	    private TextField Lr2;

	    @FXML
	    private TextField Lr3;

	    @FXML
	    private TextField h1;

	    @FXML
	    private TextField h2;

	    @FXML
	    private TextField h3;

	    @FXML
	    private AnchorPane pane;

	    @FXML
	    private Text text1;

	    @FXML
	    private Text text2;

	    @FXML
	    private Text text3;
	
    
	    String[] difficileSp ;

	    String[] facileSp;
    
	    String[] MoyenneSp;

	    String source = "resources/config.txt";

    public void initialize() throws IOException {
        
        //Ouvrir un flux de lecture pour lire les octets du fichier 
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
        // lire le contenu du fichier sous forme de texte en utilisant l'encodage de caractères UTF-8
        BufferedReader r = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8));

        //lire une ligne à la fois et  renvoyer la chaîne de caractères de fichier
        String facile = r.readLine();
        String Moyenne = r.readLine();
        String difficile = r.readLine();
        
        
        //separer la chaine de caractere avec : et afficher les valeurs de chaque champ
        facileSp = facile.split(":");
        Lr1.setText(""+facileSp[1]);
        h1.setText(""+facileSp[2]);
        L1.setText(""+facileSp[3]);
        
        MoyenneSp = Moyenne.split(":");
        Lr2.setText(""+MoyenneSp[1]);	
        h2.setText(""+MoyenneSp[2]);
        L2.setText(""+MoyenneSp[3]);

        difficileSp = difficile.split(":");
        Lr3.setText(""+difficileSp[1]);	
        h3.setText(""+difficileSp[2]);
        L3.setText(""+difficileSp[3]);
        
        
        
        bis.close();


    }

    public void onClickValide(ActionEvent actionEvent) throws IOException {

    	//ecrire dans le fichier 
        BufferedWriter sortie = new BufferedWriter(new FileWriter(source));

        sortie.write("facile:"+Lr1.getText()+":"+h1.getText()+":"+L1.getText());
        sortie.newLine();
        sortie.write("Moyenne:"+Lr2.getText()+":"+h2.getText()+":"+L2.getText());
        sortie.newLine();
        sortie.write("difficile:"+Lr3.getText()+":"+h3.getText()+":"+L3.getText());
       
        sortie.close();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

    }

}
