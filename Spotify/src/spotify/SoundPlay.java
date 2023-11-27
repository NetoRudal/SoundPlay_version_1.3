/*
 * Este es un programa que simula el reproductor de musica Spotify, solamente tiene la funcion de
   la seccion donde ya tengamos nuestra playlist.
   
    Contendra las siguientes funciones:
    
    1.- Un menu de inicio donde el usuario podra agregar su nombre
    2.- Un boton que reproducira la musica
    3.- Un boton que pausara la musica(se hara el cambio en el boton de reproducir, sera intercalado)
    4.- Un boton que cambiara la siguiente cancion
    5.- Un boton que retrocedera a la cancion anterior
    6.- Podremos dar click a la cancion en la lista y de igual manera se podra reproducir
 */
package spotify;


import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author Neto
 */
public class SoundPlay extends Application {
    
    private final List<String> playlist = new ArrayList<>(); //Creamos una lista que contendra las canciones
    private final List<String> nombreCancion = new ArrayList<>();
    private MediaPlayer reproductorMusica; //creamos un objeto llamado mediaplayer, que sera el reproductor de musica
    private int IndiceCancionActual = 0; //indica la posicion de la cancion actual
    private final HBox botonEstatus = new HBox(); //Creamos un HBox que sera el boton del estatus de la cancion, pausada o reproducir
    private final VBox contenedorTitulos = new VBox(); //Creamos el contenedor que ira en el contenedorSuperiorDerecha en la parte derecha, contiene los titulos
    private final VBox PanelCentral = new VBox(); //Creamos un contenedor que ira al centro del panel derecho  
    private final VBox contenedorPrincipal = new VBox(); //Este es el contenedor principal
    private final HBox contenedorSupeior = new HBox(10); //Este es el contenedor Superior
    private final VBox contenedorDerechoSuperior = new VBox();  
    private final HBox contenedorSuperiorDerecha = new HBox(); //Creamos el contenedor que ira arriba del panel derecho, checar documentacion.
    private final VBox contenedorInferiorDerecha = new VBox(); //Creamos el contenedor que ira abajo del panel derecho, checar documentacion.
    private final VBox contenedorIzquierdoSuperior = new VBox();                     
    private final HBox contenedorInferior = new HBox();   //Este es el contenedor inferior     
    private final HBox panelinferiorIzq = new HBox();
    private final HBox contenedorControladores = new HBox();
    private final HBox contenedorLineaVerde = new HBox();  //Este es el contenedor inferior 2      
    private final HBox contenedorLogoApp = new HBox();
    private final Image logoApp = new Image("Image/LogoApp.png");
    private final ImageView Logo = new ImageView(logoApp);    
    private final Image iconoApp = new Image("/Image/icono2.png"); //Creamos un objeto image y el parametro de la ruta donde se encuentra alojado la imagen     
   //Creamos un imageviwe con la imagen del icono de Pausar y uno con el icono de Reproducir
    private Image imgPausa = new Image("Image/pausa.png"); //Objeto image que contiene la ruta de la imagen del icono de pausa
    private Image imgPlay = new Image("Image/reproducir.png");//Objeto image que contiene la ruta de la imagen del icono de reproducir
    private final ImageView ImgPausa = new ImageView(imgPausa);
    private final ImageView ImgPlay = new ImageView(imgPlay);
    private final  Image image = new Image("Image/imagenDiscoNegro.png"); // Reemplaza con la ruta de tu imagen
    private final ImageView imageView = new ImageView(image);     
    private final  Slider slider_time = new Slider();    
    private final Slider volumen = new Slider(0,100, 50);
    private final  Label actual_time = new Label("0:00");
    private final  Label total_time = new Label("0:00");
    private HBox contenedorSliderTime = new HBox(slider_time);
    private HBox contenedorSliderVolumen = new HBox(volumen);
    private HBox contenedorLabelTiempoActual = new HBox(actual_time);
    private HBox contenedorLabelTiempoTotal = new HBox(total_time);
    private HBox contenedorCentralBotones = new HBox(botonEstatus);     
     Image imageRegresar = new Image("Image/regresar.png");
     Image imagenSiguiente = new Image("Image/siguiente.png");
     ImageView imgRegresarSong = new ImageView(imageRegresar);
     ImageView imgSiguienteSong = new ImageView(imagenSiguiente);
     HBox botonSiguiente = new HBox(imgSiguienteSong);
     HBox botonRegresar = new HBox(imgRegresarSong);
     Image icoVolumen = new Image("Image/volumen.png");
     ImageView imagenVolumen = new ImageView(icoVolumen);
     private List<HBox> listaDeCanciones = new ArrayList<>();
     private int indiceCancionActual = -1;
     private ScrollPane scrollPane = new ScrollPane(contenedorInferiorDerecha); 
     private Image agregar = new Image("Image/agregar1.png");
     private Image agregar_Secundario = new Image("Image/agregar2.png");
     private ImageView imgAgregarSecundario = new ImageView(agregar_Secundario);
     private ImageView imgAgregar = new ImageView(agregar);
     private VBox btnAgregarCanciones = new VBox(imgAgregar);
     private Label etiqueta = new Label();
     private  String rutaAplicacion = System.getProperty("user.dir");
    @Override

    
    @SuppressWarnings("empty-statement")
    public void start(Stage primaryStage) throws IOException {
        
        /*
         Establecer el ícono en la ventana, esto aparecera en el icono superior izquierda alado del titulo, tambien aparecera
         en el icono de aplicacion
         */
         primaryStage.setTitle("Sound Play"); //Nombramos a l ventana con el titulo de la app
         primaryStage.getIcons().add(iconoApp);//Agregamos el icono a la ventana
         configuracionUI();   //Mandamos a llamar al metodo configuracionUI que contiene toda la configuracion de la Interfaz
         Scene scene = new Scene(contenedorPrincipal, 1200, 700);  // Crear la escena
         primaryStage.setScene(scene);  // Configurar y mostrar el escenario
         primaryStage.setResizable(false);       
      //   cargarCancionesDesdeArchivo(rutaAplicacion);

         scrollPane.getStyleClass().clear();
         primaryStage.show();
      
    }
    
      
  
  /**
   * METODO QUE MANDA A LLAMAR A OTROS METODOS QUE CONTIENEN LAS CONFIGURACUIONES DE CADA CONTENEDOR (HBox)
   */
    private void configuracionUI(){
        configuracionContenedorSuperior();
        configuracionContenedorInferior();
        configuracionContenedorPrincipal();
        agregarCancionesUI();
      
      
    }

    /**
     * Metodo que contiene la configuracion del contenedor superior, elementos que se le agregaran, diseño, etc.
     */
    private void configuracionContenedorSuperior(){

       
          imageView.setFitHeight(110); //Le damos medidas a la imagen, 110 de alto y 110 de ancho
          imageView.setFitWidth(110);
          
          HBox contenedorDiscoNegro = new HBox(imageView);
               contenedorDiscoNegro.setMargin(imageView, new Insets(0, 10, 0, 40));
          
          Label tituloSeccion = createLabel("Musica Relajante", "Roboto", FontWeight.BOLD, 35, Color.WHITE);
          Label antetitulo = createLabel("PlayList", "Roboto", FontWeight.NORMAL, 18, Color.WHITE);

          
          contenedorTitulos.getChildren().add(antetitulo);     
          contenedorTitulos.getChildren().add(tituloSeccion);
           
          contenedorDerechoSuperior.getChildren().add(contenedorSuperiorDerecha);
          
          contenedorSuperiorDerecha.getChildren().add(contenedorDiscoNegro);
          contenedorSuperiorDerecha.getChildren().add(contenedorTitulos);
          
          contenedorTitulos.setAlignment(Pos.CENTER_LEFT);

          Label etiquetaTitulo = createLabel("Titulo", "Roboto", FontWeight.BOLD, 15, Color.GREY); //Creamos un label que contendra la palabra Titulo, le damos formato Roboto, con bold 15 y que sea color blanco
          Label lineaDiv = new Label("_______________________________________________________________________________________________________________________________________________________________");

          PanelCentral.getChildren().add(etiquetaTitulo);
          PanelCentral.getChildren().add(lineaDiv);
          PanelCentral.setMargin(lineaDiv, new Insets(10, 10, 10, 60));
          PanelCentral.setMargin(etiquetaTitulo, new Insets(50, 10, 10, 130));
          
         contenedorDerechoSuperior.getChildren().add(PanelCentral);         

         contenedorDerechoSuperior.getChildren().add(contenedorInferiorDerecha);

         contenedorInferiorDerecha.setPadding(new Insets(20, 200, 100, 50));
         
             
        //ELEMENTOS AGREGADOS AL CONTENEDOR IZQUIERDO DEL PANEL SUPERIOR
        
       
          Logo.setFitHeight(50); //Le damos medidas a la imagen, 50 de alto y 150 de ancho
          Logo.setFitWidth(150);
          imagenVolumen.setFitHeight(15);
          imagenVolumen.setFitWidth(15);
         
         
          contenedorLogoApp.getChildren().add(Logo);
          contenedorIzquierdoSuperior.getChildren().add(contenedorLogoApp);
          contenedorLogoApp.setMargin(Logo, new Insets(10, 0, 0, 10));
          
             
          contenedorSupeior.getChildren().add(contenedorIzquierdoSuperior); //Agregamos el panel izquierdo
          contenedorSupeior.getChildren().add(contenedorDerechoSuperior);   //Agregamos el pandel derecho

       
       //DISEÑO DE PANEL SUPERIOR
       //  contenedorSupeior.setStyle("-fx-background-color: rgb(" + 27 + "," + 26 + "," + 26 + ");");
        contenedorSupeior.setStyle("-fx-background-color: rgb(" + 0 + "," + 0 + "," + 0 + ");");
       //CONTENEDOR IZQUIERDO(CONTENEDOR SUPERIOR)
        
       //Aqui agregamos bordes al contenedor
        contenedorIzquierdoSuperior.setStyle("-fx-background-radius: 20;" + "-fx-border-radius: 100;" + "-fx-background-color: rgb(" + 32 + "," + 32 + "," + 32 + ");"); //Color negro
        
        //CONTENEDOR DERECHO(PANEL SUPERIOR)

        contenedorSupeior.setPrefHeight(620);//Ajustamos en 100 pixeles el alto del contenedor superior
        contenedorSupeior.setPrefWidth(1200);//Ajustamos en 1200 pixeles el ancho del contenedor superior
        contenedorDerechoSuperior.setPrefHeight(600);//Ajustamos en 600 el alto del panel derecho del contenedor superior
        contenedorDerechoSuperior.setPrefWidth(900);//Ajustamos en 900 el ancho del panel derecho del contenedor superior
        contenedorIzquierdoSuperior.setPrefHeight(600);//Ajustamos en 600 el alto del panel izquierdo del contenedor superior
        contenedorIzquierdoSuperior.setPrefWidth(300);//Ajustamos en 300 el ancho del panel izquierdo del contenedor superior
       
         
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        


        // scrollPane.lookup(".scroll-bar:horizontal").setStyle("-fx-background-color: red;");
         scrollPane.setStyle("-fx-background-radius: 20;" + "-fx-border-radius: 100;" +"-fx-background-color: transparent;");
         contenedorDerechoSuperior.getChildren().add(scrollPane);
         contenedorDerechoSuperior.setStyle("-fx-background-radius: 20;" + "-fx-border-radius: 100;" + "-fx-background-color: linear-gradient(to bottom, rgb(11,45,64) 1%, rgb(32,32,32) 51%);");
    }
    
    //Metodo que contiene toda la configuracion del contenedor inferior
    private void configuracionContenedorInferior(){
        //ELEMENTOS AGREGADOS AL PANEL INFERIOR
         contenedorInferior.setAlignment(Pos.CENTER_RIGHT);
         contenedorControladores.setPrefHeight(70); 
         contenedorControladores.setPrefWidth(900);

         contenedorInferior.setAlignment(Pos.CENTER_LEFT);
         panelinferiorIzq.setPrefHeight(70); 
         panelinferiorIzq.setPrefWidth(300);
         panelinferiorIzq.setPadding(new Insets(10, 20, 20, 10));
         

         //**********Elementos agregados al panel inferior derecho*********//
        
 
         //Damos tamaño a los iconos de reproducir y de pausar
         ImgPausa.setFitHeight(35);
         ImgPausa.setFitWidth(60);
         ImgPlay.setFitHeight(35);
         ImgPlay.setFitWidth(60);
         imgRegresarSong.setFitHeight(35);
         imgRegresarSong.setFitWidth(60);
         imgSiguienteSong.setFitHeight(35);
         imgSiguienteSong.setFitWidth(60);
         //Creamos un HBox que sera el que contenga los iconos de pausar y reproducir, y sera un boton
          botonEstatus.getChildren().add(ImgPlay); //Por defecto vendra el estatus en pausa
            
         contenedorControladores.getChildren().add(botonEstatus);
       
         contenedorControladores.setAlignment(Pos.CENTER);
         botonEstatus.setPadding(new Insets(10));
         botonRegresar.setPadding(new Insets(10));
         botonSiguiente.setPadding(new Insets(10));
        //DISEÑO PANEL INFERIOR
        
       // contenedorInferior.setStyle("-fx-background-color: rgb(" + 27 + "," + 26 + "," + 26 + ");");
         contenedorInferior.setStyle("-fx-background-color: rgb(" + 0 + "," + 0 + "," + 0 + ");");
        //DISEÑO PANEL INFERIOR 2
        contenedorLineaVerde.setStyle("-fx-background-color: rgb(" + 43 + "," + 216 + "," + 0 + ");");
              
        contenedorInferior.setPrefHeight(50); //Ajustamos en 100 pixeles el alto del contenedor inferior
        contenedorInferior.setPrefWidth(1100); //Ajustamos en 1200 pixeles el ancho del contenedor inferior
        contenedorLineaVerde.setPrefHeight(10); //Ajustamos en 10 el alto del panel inferior 2
        contenedorLineaVerde.setPrefWidth(1200); //Ajustamos en 1200 el ancho del panel inferior 2
        
        
        if(reproductorMusica != null){
        controladorReproductor(reproductorMusica);
        interfazControladores();
        }else{
            interfazControladores();
        }

    }
    
 
    
    //Metodo que contiene la configuracion de la interfaz del contenedor principal, tambien que HBoxes se le agregaran
    private void configuracionContenedorPrincipal(){
       // contenedorPrincipal.setStyle("-fx-background-color: rgb(" + 43 + "," + 216 + "," + 0 + ");");
       contenedorPrincipal.setStyle("-fx-background-color: rgb(" + 0 + "," + 0 + "," + 0 + ");");
        contenedorPrincipal.getChildren().add(contenedorSupeior);
        contenedorSupeior.setAlignment(Pos.TOP_CENTER); //alineamos el panel superior en la parte sobrante de arriba en el centro
        contenedorSupeior.setPadding( new Insets(10));
        //Agregamos el panel inferior
        contenedorPrincipal.getChildren().add(contenedorInferior);
        contenedorInferior.getChildren().add(panelinferiorIzq);
        contenedorInferior.getChildren().add(contenedorControladores);
        contenedorControladores.setAlignment(Pos.CENTER_LEFT);
        contenedorInferior.setAlignment(Pos.BOTTOM_CENTER); //alineamos el panel inferior hasta abajo en el centro
        //Agregamos el panel inferior 2, este es la linea verde que esta hasta el final
      //  contenedorPrincipal.getChildren().add(contenedorLineaVerde);
        contenedorLineaVerde.setAlignment(Pos.BOTTOM_CENTER);

 
     
}
 
    
 //++++++++++++++++++++++++++++++ AQUI HAY METODOS QUE AYUDAN A SIMULAR UNA BASE DE DATOS DE LAS CANCIONES, LAS RUTAS SE ALMACENARAN EN UN ARCHIVO DE TEXTO
 //+++++++++++++++++++++++++++++++PARA POSTERIOR BUSCAR EN EL ARCHIVO LAS RUTAS Y MOSTRARLAS AL INICIAR LA APLICACION
    
/**
 * 
 * Metodo que agrega canciones a un archivo de texto
 * 
 */
    
private void agregarCanciones() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Archivos MP3", "*.mp3"),
            new ExtensionFilter("Todos los archivos", "*.*")
    );

    List<File> archivos = fileChooser.showOpenMultipleDialog(null);

    if (archivos != null) {
        for (File archivo : archivos) {
            String path = archivo.getAbsolutePath();
            playlist.add(path);

            // Obtén el nombre del archivo sin la extensión
            String nombre = archivo.getName();
            int pos = nombre.lastIndexOf(".");
            if (pos > 0) {
                nombre = nombre.substring(0, pos);
            }

            nombreCancion.add(nombre);

            // Crea el HBox correspondiente para la nueva canción
            HBox nuevaCancionHBox = createSongHBox(nombre, contenedorInferiorDerecha, playlist.size() - 1, panelinferiorIzq);
            contenedorInferiorDerecha.getChildren().add(nuevaCancionHBox);
            listaDeCanciones.add(nuevaCancionHBox);
        }

        // Después de agregar todas las canciones, escribe todas las rutas en el archivo
        escribirEnArchivo(playlist, rutaAplicacion);
    }
}

/**
 * Metodo que obtiene el nombre del archivo
 * @param archivo
 * @return 
 */
    private String obtenerNombreArchivo(File archivo) {
        String nombre = archivo.getName();
        int pos = nombre.lastIndexOf(".");
        if (pos > 0) {
            nombre = nombre.substring(0, pos);
        }
        return nombre;
    }

    /**
     * Metodo que carga las rutas desde el archivo que se creo
     * @param nombreArchivo
     * @throws IOException 
     */
    private void cargarCancionesDesdeArchivo(String nombreArchivo) throws IOException {
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                // Agregar la canción al playlist y a la interfaz
                playlist.add(linea);
                String nombre = obtenerNombreArchivo(new File(linea));
                nombreCancion.add(nombre);
                HBox nuevaCancionHBox = createSongHBox(nombre, contenedorInferiorDerecha, playlist.size() - 1, panelinferiorIzq);
                contenedorInferiorDerecha.getChildren().add(nuevaCancionHBox);
                listaDeCanciones.add(nuevaCancionHBox);
            }
        }    }

    
/**
 * Metodo que escribe las rutas de las canciones en un archivo de texto
 * @param lineas
 * @param nombreArchivo 
 */
  private void escribirEnArchivo(List<String> lineas,String rutaAplicacion) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaAplicacion))) {
        for (String linea : lineas) {
            writer.write(linea);
            writer.newLine();  // Agrega una nueva línea después de cada ruta
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
  
   
    
 /**
  * Metodo que contiene la configuracion de Interfaz Grafica del boton de agregar canciones
  */  
    
private void agregarCancionesUI(){
        HBox contenedorAgregar = new HBox(); 
        imgAgregar.setFitHeight(40);
        imgAgregar.setFitWidth(40);
        imgAgregarSecundario.setFitHeight(40);
        imgAgregarSecundario.setFitWidth(40);      
        etiqueta=createLabel("Agregar a playlist","Roboto", FontWeight.NORMAL, 16, Color.GRAY); 
        etiqueta.setAlignment(Pos.CENTER);
        contenedorAgregar.setAlignment(Pos.CENTER_RIGHT);
        contenedorAgregar.setPrefWidth(700);
        contenedorAgregar.getChildren().addAll(btnAgregarCanciones, etiqueta);
        contenedorAgregar.setSpacing(5);
        
        contenedorInferiorDerecha.getChildren().add(contenedorAgregar);
        btnAgregarCanciones.setOnMouseClicked(e -> agregarCanciones());
        btnAgregarCanciones.setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            btnAgregarCanciones.getChildren().clear();
            btnAgregarCanciones.getChildren().add(imgAgregarSecundario);
        }
        }
        );     
      btnAgregarCanciones.setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            btnAgregarCanciones.getChildren().clear();
            btnAgregarCanciones.getChildren().add(imgAgregar);
        }
    }
        );
    }
         
     /**
      * Metodo que contiene los controles de:
      * 
      * Boton estatus: si el usuario desea pausar o reproducir la cancion actual
      * Boton siguiente: es el encargado de pasar a la siguiente cancion respecto a la actual
      * Boton Regresar: es el encargado de regresar a la cancion anterior respecto a la actual
      * 
      */
private void controles(){
       /**
        * Al darle click, este evento lo que hara es verificar si la cancion esta en pausa o reproduciendose para intercalar 
        * las imagenes correspondiente a cada estado
        */
        
        
        botonEstatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /*
                Creamos una condiconal que verifica el estatus del mediaplayer}
                si la cancion esta pausada, va reproducir la cancion, caso contrario la pausara
                */
                try{
                    if (reproductorMusica.getStatus() == MediaPlayer.Status.PAUSED) {
                        botonEstatus.getChildren().clear();
                        botonEstatus.getChildren().add(ImgPausa);
                         reproductorMusica.play();
            
                    } else {
                      botonEstatus.getChildren().clear();
                      botonEstatus.getChildren().add(ImgPlay);
                      reproductorMusica.pause();
                    }
                }catch(Exception ex){
                     Logger.getLogger(SoundPlay.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
            }
            });  
         
        
        /**
         * El objetivo de este evento es que cuando se le de click al icono de siguiente, cambiara la cancion a la que le sigue
         */
        botonSiguiente.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
               playNextSong();
            }
            }); 
      
        /**
         * Este evento regresara a la cancion anterior con respecto a la actual
         */
       botonRegresar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               
               previousSong();
            }
            });   
}

/**
 * Metodo que obtiene texto de un HBox
 * 
 * @param hbox Es el HBox al que se le quiere extraer el texto
 * @return  retorna un Label que contiene el texto del HBox
 */
    private String obtenerTextoDelLabel(HBox hbox) {
        for (javafx.scene.Node node : hbox.getChildren()) {
            if (node instanceof Label) {
                return ((Label) node).getText(); // Devuelve el texto del Label
            }
        }
        return null; // Devuelve null si no se encuentra ningún Label
    }

    /**
     * Metodo que resetea los colores de los hbox que no se estan reproduciendo, por ejemplo al darle click a la cancion 1
     * se va a reproducir, y el texto se resaltara en verde, al darle click a otra cancion esta otra su texto se resaltara en verde
     * pero el texto de la cancion anterior seguira en verde, lo que hace este metodo es reiniciar el color del las etiquetas de los hboxes que no
     * se estan reproduciendo actualmente
     * 
     * @param targetHBox Es el contenedor objetivo, parametro del tipo HBox
     * @param compareHBoxes Son los otros contenedores que no se estan reproduciendo actualmente en el mediaplayer, parametro del tipo Hbox, deben ser varios
     */
   private void resetearColores(HBox targetHBox, HBox... compareHBoxes) {
    // Iterar sobre los HBox para comparar
    for (HBox hbox : compareHBoxes) {
        // Iterar sobre los nodos (assumiendo que son Label) dentro de cada HBox
        for (javafx.scene.Node node : hbox.getChildren()) {
            // Verificar si el nodo es un Label
            if (node instanceof Label) {
                Label label = (Label) node;
                // Si el HBox actual es igual al HBox objetivo, cambiar el color del Label a verde
                if (hbox == targetHBox) {
                    label.setStyle("-fx-text-fill: rgb(88, 233, 52);");
                } else {
                    // Si no es igual, reiniciar el color del Label a blanco
                    label.setStyle("-fx-text-fill: white;");
                }
            }
        }
    }

}
  /**
   * Metodo que crea un HBox que al darle click se reproducira la cancion
   * 
   * @param nombreCancion Parametro del tipo String, se le debe pasar un string con el nombre de la cancion
   * @param contenedorInferiorDerecho Parametro del tipo HBox, se le debe pasar el contenedor donde se agregara este otro contenedor, en este caso es una lista
   * @param songIndex Parametro del tipo int, corresponde al indice de la cancion que corresponde
   * @param panelinferiorIzq Parametro del tipo HBox que servira para que se muestre la informacion de la cancion que se esta reproduciendi
   * @return 
   */
   
   private HBox createSongHBox(String nombreCancion, VBox contenedorInferiorDerecho, int songIndex, HBox panelinferiorIzq) {
      
    HBox cancionReproducir = new HBox();
    cancionReproducir.setPadding(new Insets(10, 20, 20, 10));
    cancionReproducir.setAlignment(Pos.CENTER_LEFT);

    Label nombre = createLabel(nombreCancion, "Roboto",FontWeight.NORMAL, 15, Color.WHITE);
    Label copia = createLabel(nombreCancion, "Roboto", FontWeight.BOLD, 13, Color.WHITE);
    Label reproduciendo = createLabel("Reproduciendo:  ", "Roboto", FontWeight.BOLD, 13, Color.GREY);
    reproduciendo.setAlignment(Pos.CENTER_LEFT);

    contenedorInferiorDerecho.setMargin(cancionReproducir, new Insets(10, 10, 10, 60));

    ImageView pausaImg = new ImageView(imgPausa);
    pausaImg.setFitHeight(35);
    pausaImg.setFitWidth(60);
    
    //Evento para cuando el puntero pase por el hbox este parezca resaltado
    cancionReproducir.setOnMouseEntered(event -> cancionReproducir.setStyle("-fx-background-radius: 20; -fx-border-radius: 100 ; -fx-background-color: rgb(45, 45, 45);"));
    cancionReproducir.setOnMouseExited(event -> cancionReproducir.setStyle(""));

    cancionReproducir.setOnMouseClicked(event -> {
 
     boolean cancionReproducida = playSong(songIndex);
    if (cancionReproducida) {
        cancionReproducir.setStyle("-fx-background-radius: 20; -fx-border-radius: 100 ; -fx-background-color: rgb(50, 50, 50);");
        copia.setText(nombreCancion);

        panelinferiorIzq.getChildren().clear();
        panelinferiorIzq.getChildren().addAll(reproduciendo, copia);
        panelinferiorIzq.setAlignment(Pos.CENTER_LEFT);

        nombre.setTextFill(Color.GREEN);
        IndiceCancionActual = songIndex;

        obtenerTextoDelLabel(cancionReproducir);
        resetearColores(cancionReproducir, listaDeCanciones.toArray(new HBox[0]));

        botonEstatus.getChildren().clear();
        botonEstatus.getChildren().add(pausaImg);
        
        controladorReproductor(reproductorMusica);
        interfazControladores();
        controles();
    }else{
        
    }

 });
    cancionReproducir.getChildren().add(nombre);
    return cancionReproducir;
   }
   
   
      /**
       * Metodo que controla los slider de tiempo y volumen del reproductor
       * @param reproductorMusica Se le debe pasar el parametro del tipo Mediaplayer, que es el que se esta reproduciendo
       * actualmente
       */
   private void controladorReproductor(MediaPlayer reproductorMusica){

         volumen.valueProperty().addListener((observable, oldValue, newValue) -> {
            reproductorMusica.setVolume(newValue.doubleValue() / 100.0); // El volumen debe estar en el rango [0, 1]
           
        });

        reproductorMusica.setOnReady(() -> {
           total_time.setText(formatTime(reproductorMusica.getTotalDuration()));
           slider_time.setMax(reproductorMusica.getTotalDuration().toSeconds());
           
           
           slider_time.valueProperty().addListener((p, o, value) -> {
             if (slider_time.isPressed()) {
                 reproductorMusica.seek(Duration.seconds(value.doubleValue()));
                }
            });

           reproductorMusica.currentTimeProperty().addListener((p, o, value) -> {
                slider_time.setValue(value.toSeconds());
                actual_time.setText(formatTime(value));
             });
    

           });
  
   }
   
/**
 *  Metodo que contiene el diseño de la interfaz grafica de los controladores del reproductor
 */
  private void interfazControladores(){  
     volumen.setPrefWidth(100);
     slider_time.setPrefWidth(400);
     slider_time.setStyle("-fx-backgorund-color:DADADA;");
     actual_time.setTextFill(Color.GREY);
     actual_time.setFont(Font.font("Roboto", FontWeight.NORMAL, 12));
     total_time.setTextFill(Color.GREY);
     total_time.setFont(Font.font("Roboto", FontWeight.NORMAL, 12));
     contenedorSliderTime = new HBox(slider_time);
     contenedorSliderVolumen = new HBox(imagenVolumen,volumen);
    
     contenedorSliderVolumen.setPrefWidth(300);
     contenedorSliderVolumen.setAlignment(Pos.BOTTOM_RIGHT);
     contenedorLabelTiempoActual = new HBox(actual_time);
     contenedorLabelTiempoTotal = new HBox(total_time);
     contenedorCentralBotones = new HBox(botonRegresar,botonEstatus, botonSiguiente);  
     contenedorCentralBotones.setAlignment(Pos.CENTER);
     contenedorControladores.getChildren().clear();
     HBox contenedorSliderTimeLabels = new HBox(contenedorLabelTiempoActual, contenedorSliderTime, contenedorLabelTiempoTotal);
     VBox contenedorCentral = new VBox(contenedorCentralBotones, contenedorSliderTimeLabels);   
    contenedorControladores.getChildren().addAll(contenedorCentral, contenedorSliderVolumen);
    contenedorInferior.setMargin(contenedorCentral,new Insets(10));
    contenedorInferior.setMargin(contenedorSliderVolumen,new Insets(13));
  } 
   
  
 /**
  * Metodo que da formato al tiempo en "00:00"
  * @param duration se le debe pasar un objeto del tipo duration
  * @return retorna un string en formato "00:00"
  */
 
 private String formatTime(Duration duration) {
    int minutes = (int) duration.toMinutes();
    int seconds = (int) duration.toSeconds() % 60;
    return String.format("%d:%02d", minutes, seconds);
}
 
 
 /**
  * Metodo que crea labels, pide parametros como el texto, el tipo de letra, el fontweight, tamaño y color del texto 
  * @param text Se le debe dar un string con lo que quiera que contenga el label
  * @param font El tipo de letra que quiere para el label
  * @param fontWeight Peso o grueso de la letra
  * @param fontSize Tamaño de la letra
  * @param textColor Color de la letra
  * @return retorna un label con los parametros dados
  */
   private Label createLabel(String text, String font, FontWeight fontWeight, double fontSize, Color textColor) {
       Label label = new Label(text);
       label.setFont(Font.font(font, fontSize));
       label.setTextFill(textColor);
       label.setFont(Font.font(font, fontWeight, fontSize));
    return label;
  }
   
  
   /**
    * Metodo que reproduce una cancion, pide como parametro el indice de la cancion
    * 
    * @param songIndex //Indice de la cancion
    * @return #Retorna el media
    */
    private boolean playSong(int songIndex) {
        
        MediaPlayer newMediaPlayer = createMediaPlayer(playlist.get(songIndex));
        
        if (newMediaPlayer == null) {
      
         return false;
        } else {
            
          if (reproductorMusica != null) {
              reproductorMusica.stop();
        }
         reproductorMusica = newMediaPlayer;
         reproductorMusica.play();
         reproductorMusica.setOnEndOfMedia(() -> playNextSong());
         controladorReproductor(reproductorMusica);
        }

        return true;
      
    }
    /**
     * 
     * @param mediaPath La ruta del archivo
     * @return  retorna un mediaplayer con la ruta de la cancion deseada
     */
    
    private MediaPlayer createMediaPlayer(String mediaPath) {
      
           File file = new File(mediaPath);
           try{
           if (!file.exists()) {
           JOptionPane.showMessageDialog(null, "No se encuentra el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
           }
          }catch(HeadlessException ex){
            Logger.getLogger(SoundPlay.class.getName()).log(Level.SEVERE, null, ex);
            return null;
          }
           
          Media media = new Media(file.toURI().toString());
          return new MediaPlayer(media);      
}

    /**
     * Metodo que cambia la cancion, al terminar la cancion actual, le sima uno al indice de la cancion actual para que cambie
     * a la siguiente
     */
    public void playNextSong() {
    if (listaDeCanciones.isEmpty()) {
        System.out.println("La lista de canciones está vacía.");
        return;
    }

    IndiceCancionActual = (IndiceCancionActual + 1) % listaDeCanciones.size();

    HBox[] hboxes = listaDeCanciones.toArray(new HBox[0]);

    updateSongInfo();
    playSong(IndiceCancionActual);

    HBox hboxActual = hboxes[IndiceCancionActual];
    resetearColores(hboxActual, hboxes);
}
    
    /**
     * 
     * Este metodo regresa a la cancion anterior
     */
  public void previousSong() {
         reproductorMusica.stop();
        // Retrocede al índice anterior de la lista de reproducción
        IndiceCancionActual = (IndiceCancionActual - 1 + playlist.size()) % playlist.size();
        // Carga y reproduce la nueva canción
        reproductorMusica = new MediaPlayer(new Media(new File(playlist.get(IndiceCancionActual)).toURI().toString()));
        HBox[] hboxes = listaDeCanciones.toArray(new HBox[0]);; //creo un conjunto de hboxes que contiene todos los boxes de las canciones
        HBox hboxActual = hboxes[IndiceCancionActual]; //creo un hbox, que sera el que se reproduce actualmente, hara una busqueda en el conjunto de hboxes que definimos arriba mediante el indice de la cancion actual
        resetearColores(hboxActual, hboxes); //
        updateSongInfo();
        playSong(IndiceCancionActual);
    }
 
  
  /**
   * Este metodo cambia la informacion en el hbox de la cancion que se esta reproduciendo actualmente
   * Aparece algo como "Reproduciendo: Cancion"
   */
    private void updateSongInfo() {
        panelinferiorIzq.getChildren().clear(); 
        Label reproduciendo = createLabel("Reproduciendo: ", "Roboto", FontWeight.BOLD, 13, Color.GREY);
        Label nombreC = createLabel(nombreCancion.get(IndiceCancionActual), "Roboto", FontWeight.BOLD, 13, Color.WHITE);         
        panelinferiorIzq.getChildren().add(reproduciendo);
        panelinferiorIzq.setAlignment(Pos.CENTER_LEFT);
        panelinferiorIzq.getChildren().add(nombreC);
         
    }


    @Override
    
public void stop() {
        try {
            if (reproductorMusica != null) {
                reproductorMusica.stop();
                reproductorMusica.dispose(); // Liberar recursos del MediaPlayer
            }
            // Otros pasos de cierre si los necesitas
            
            super.stop(); // Llama al método stop de la clase base
        } catch (Exception ex) {
            Logger.getLogger(SoundPlay.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

    

}
