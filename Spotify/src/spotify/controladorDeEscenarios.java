/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotify;




import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author USUARIO
 */
public class controladorDeEscenarios {

   
       // abre el frame donde se muestra el reproductor de Musica
       public void abrirTelonReproductor(Stage primaryStage, HBox contenido){

       Scene escena = new Scene(contenido, 900, 650);
       primaryStage.setResizable(false);
       primaryStage.setScene(escena);
       primaryStage.show();
   }
      
   
}
