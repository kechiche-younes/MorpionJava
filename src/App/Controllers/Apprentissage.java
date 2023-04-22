package App.Controllers;


import ai.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import static App.Controllers.InterfacePrincipaleController.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Apprentissage {



    @FXML
    public TextField textField;

    @FXML
    public ProgressBar progressBar;


    private Task<Double> task;
    MultiLayerPerceptron net;
    int epochs;
    public static Thread thread;
    URL url = null;

    /**
     * @throws InterruptedException
     * @throws IOException
     */
    @FXML
    public void initialize() throws InterruptedException, IOException {

        try {

            int size = 9;
            System.out.println();
            System.out.println("START TRAINING ...");
            System.out.println();

            int[] layers = new int[getL()+2];
            layers[0]=size;
            for (int i=0;i<getL();i++){
                layers[i+1]=getH();
            }
            layers[layers.length-1]=size;
            //
            double error = 0.0;
            this.net = new MultiLayerPerceptron(layers, getLr(), new SigmoidalTransferFunction());
            this.epochs = 10000;

            System.out.println("--- ");
            System.out.println("Load data ...");
            HashMap<Integer, Coup> mapTrain = Test.loadCoupsFromFile("./resources/train_dev_test/train.txt");
            HashMap<Integer, Coup> mapDev = Test.loadCoupsFromFile("./resources/train_dev_test/dev.txt");
            HashMap<Integer, Coup> mapTest = Test.loadCoupsFromFile("./resources/train_dev_test/test.txt");
            System.out.println("---");
            progressBar.setProgress(0);
            //on crie un nouveau task 
            task=getTask(mapTrain);
            //lie la propriété de progression de la barre de progression à la propriété de progression de la tâche 
            //Cela signifie que la barre de progression se mettra à jour automatiquement en fonction de l'état d'avancement
            //de la tâche, sans que vous ayez besoin de la mettre à jour manuellement.
            progressBar.progressProperty().bind(task.progressProperty());

            // on a ajouter un ecouteur  qui ecoute le changement de task et effectue le fonction changed qui metr a jour le textField 
            task.messageProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    textField.setText(t1);
                }
            });
            //lorsque la tâche associée se termine avec succès on cache la fenetre 
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    textField.getScene().getWindow().hide();
                }
            });
            //creer un nouveau thread qui sera utilisée pour exécuter la tâche de manière asynchrone
            //c'est-à-dire en arrière-plan, sans bloquer l'interface utilisateur.
            thread=new Thread(this.task);
            thread.start();
            //Cela permet de conserver le modèle entraîné pour une utilisation ultérieure, sans avoir à le ré-entraîner à chaque fois.
            String file="model"+getLr()+"_"+getH()+"_"+getL()+".srl";
            net.save("resources/models/"+file);


        } catch (Exception e) {
            System.out.println("Test.test()");
            e.printStackTrace();
            System.exit(-1);
        }




    }


	public Task<Double> getTask(HashMap<Integer, Coup> mapTrain) {
        return new Task<Double>() {
            @Override
            protected Double call() throws Exception {

                double error = 0.0;
                //unPour => contient le nombre d'iteration et pour limter la frequence de l'epoches
                int unPour = epochs / 100;
                for (int i = 0; i < epochs; i++) {
                    Coup c = null;
                    while (c == null)
                        c = mapTrain.get((int) (Math.round(Math.random() * mapTrain.size())));

                    error += net.backPropagate(c.in, c.out);

                    if (i % unPour == 0) {

                        System.out.println("Error at step " + i + " is " + (error / (double) i));
                        //pour mettre a jour le textField de l'interface
                        updateMessage("Error at step " + i + " is " + (error / (double) i));
                    }
                //pour mettre a jour la bare de progression de l'interface    
                updateProgress(i,epochs);
                }
                System.out.println("Learning completed!");

                //calcule l'erreur moyenne commise par le réseau sur l'ensemble de données d'apprentissage.
                error /= epochs;
                //on teste l'epoche(nombre d'iteration) si inferieur a 0 on retourne un message d'erreur
                if (epochs < 0){
                    updateMessage("Error is " + error);
                    }
                return error;
            }

        };
    }


}

