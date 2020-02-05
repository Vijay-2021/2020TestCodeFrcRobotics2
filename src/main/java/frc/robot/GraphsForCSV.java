/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vshah-21
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.event.HyperlinkEvent;
import frc.robot.LoadCSV;
import frc.robot.CSVClient;

/**
 *
 * @author vshah-21
 */
public class GraphsForCSV extends Application {
    ArrayList<String[]> points = new ArrayList<String[]>();

    ArrayList<CheckBox> boxes;
    String[] headings;
    double[] scaleFactor;
    double[][] pointArrays;
    int WIDTH = 800; 
    int HEIGHT = 700;
    int currentColumn = 2;

    // ArrayList<Boolean> graphBooleans = new ArrayList<Boolean>();
    boolean wasRan = false;
    double setpoint = 0;

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Canvas background = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(background);
        Button runGraphServer = new Button("graphScene");
        Button runGraphDefault = new Button("default Graph");
        Button startVideoPlayer = new Button("Video Player");
        final FileChooser fileChooser = new FileChooser();

        final Button openButton = new Button("Open a Picture...");
        final Button openMultipleButton = new Button("Open Pictures...");

        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                fileChooser.setInitialDirectory(new File("D:\\MyProfile\\Documents\\NetBeansProjects\\GraphsForCSV\\"));

                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {

                    graphCSVScene(primaryStage, file.getAbsolutePath());

                }
            }
        });
        EventHandler<ActionEvent> drawFromServer = (ActionEvent e) -> {
            CSVClient csvCreator = new CSVClient("10.58.95.2", 5800);/// thread will run until connection made
            graphCSVScene(primaryStage, csvCreator.fileName());

        };
        EventHandler<ActionEvent> graphLatest = (ActionEvent e) -> {
            graphCSVScene(primaryStage, "D:\\MyProfile\\Documents\\NetBeansProjects\\GraphsForCSV\\DefaultGraph.csv");// default
                                                                                                                      // graph
                                                                                                                      // loc
        };
        System.out.println("client graphessssssr");
        EventHandler<ActionEvent> videoButtonPressed = (ActionEvent es) -> {
            System.out.println("ran");
            videoScene(primaryStage);
        };

        startVideoPlayer.addEventHandler(ActionEvent.ACTION, videoButtonPressed);
        runGraphServer.addEventHandler(ActionEvent.ACTION, drawFromServer);
        runGraphDefault.addEventHandler(ActionEvent.ACTION, graphLatest);
        root.getChildren().add(openButton);
        openButton.relocate(200, 600);
        root.getChildren().add(startVideoPlayer);
        root.getChildren().add(runGraphDefault);
        startVideoPlayer.relocate(200, 300);
        root.getChildren().add(runGraphServer);
        runGraphServer.relocate(200, 200);
        runGraphDefault.relocate(200, 400);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("running and not breaking?");
    }

    public void videoScene(Stage primaryStage) {

        Group root = new Group();
        Canvas mainCanvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(mainCanvas);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void graphCSVScene(Stage primaryStage, String filename) {
        System.out.println("filename " + filename);
        LoadCSV load = new LoadCSV();

        System.out.println("file name");
        pointArrays = load.loadCSV(filename);
        headings = load.getHeading();
        String[] variables;
        variables = load.getHeading();
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        primaryStage.setTitle("This is really running");
        boxes = new ArrayList<CheckBox>();

        EventHandler<ActionEvent> eventHandlerTextFields = (ActionEvent e) -> {
            System.out.println("happening");
        };

        EventHandler<ActionEvent> eventHandlerTextField;
        eventHandlerTextField = (ActionEvent event) -> {
            // Playing the animation
            try {
                System.out.println("running this");
                gc.clearRect(0, 0, WIDTH, HEIGHT);
                printVars(gc, variables);
                gc.setFill(Color.BLACK);
                gc.setStroke(Color.BLUE);

                calcAndDrawAxes(pointArrays, HEIGHT, (HEIGHT/2), gc);
                drawGraph(gc, pointArrays);

            } catch (Exception e) {

            }

        };
        EventHandler<MouseEvent> drawLineAndPoints = (MouseEvent dragged) -> {
            if(dragged.getSceneX()%2==0){
            gc.clearRect(0, 0, WIDTH, HEIGHT);
            calcIntersections(gc,pointArrays,dragged.getSceneX());
            calcAndDrawAxes(pointArrays,HEIGHT,(HEIGHT/2),gc);
            drawGraph(gc,pointArrays);
            }
        };
       
        Button but = new Button("Testing?");
        but.relocate(200, 200);
       
        but.addEventHandler(ActionEvent.ACTION, eventHandlerTextField);
       // System.out.println("here?");
        //canvas.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerTextField);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, drawLineAndPoints);
    
       
        
        VBox layout= new VBox(5);
        System.out.println("can print from here? ");
        for(int i =0; i < pointArrays[0].length;i++){
                try{
                CheckBox checky = new CheckBox(headings[i]);
                
                boxes.add(checky);
                boxes.get(i).relocate(20,50+ ((double)HEIGHT/2)*((double)i/pointArrays[0].length));
                //boxes.get(i).addEventHandler(ActionEvent.ACTION, eventHandlerTextFields);
                layout.getChildren().add(boxes.get(i));
                }catch(Exception e){
                    
                }
        }
        
        layout.getChildren().add(but);
        root.getChildren().add(canvas);
        
        root.getChildren().add(layout);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        System.out.println("running and not breaking?");
    
    }
    public void printVars(GraphicsContext gc, String[] s){
        for(int i =0; i < s.length;i++){
            gc.fillText(s[i], 200+i*s.length*10, 200);
        }
    }
    public double findLength(int min, int max, int scale){
        return ((double)HEIGHT/scale);
    }
    
    public void calcAndDrawAxes(double[][] pointArray, int maxLen, int minLen,GraphicsContext gc){
        
        double[] max = new double[pointArray[2].length];
        double[] min = new double[pointArray[2].length];
        scaleFactor = new double[pointArray[2].length];
        for(int i =0; i < max.length;i++){
            max[i] = Double.MIN_VALUE;
            min[i] = Double.MAX_VALUE;
        }
        for(int i =0; i < pointArray[0].length;i++){
            for(int j = 0; j < pointArray.length;j++){
                if(pointArray[j][i]>max[i]){
                    max[i]=pointArray[j][i];
                    }
                if(pointArray[j][i]<min[i]){
                    min[i]=pointArray[j][i];
                    
                }
            }
        }
        gc.setStroke(Color.GREEN);
        gc.strokeLine(5,0,5,WIDTH);
        gc.strokeLine(0,(double)HEIGHT/2,WIDTH,(double)HEIGHT/2);
        Line lines;
        
        for(int i =0; i < min.length;i++){
            scaleFactor[i] = (maxLen-minLen)/(max[i]-min[i]);   
        }
        
        
    }
    public void drawAxes(GraphicsContext gc,double factor){
        for(int i =0; i < 20; i++){
            gc.setFont(new Font(10));
           String formated = String.format("%.2f",(((double)1/factor)*(-(HEIGHT/2)+HEIGHT*((double)i/20))));
            gc.strokeText(""+ formated,20,WIDTH-(WIDTH*((double)i/20)));
            
             //System.out.println("Scale factor" + scaleFactor[5]);
        }
    
    }
    public void createPoints(ArrayList<String[]> points){
        pointArrays = new double[points.size()][points.get(0).length];
        for(int i =0; i <points.size();i++){
            for(int j =0; j < points.get(i).length;j++){
                
                pointArrays[i][j] = Double.parseDouble(points.get(i)[j]);
            }
           
        }
    }
    boolean hasPrintedScaleFactors = false;
    public void calcIntersections(GraphicsContext gc, double pointList[][], double mouseX){
        double currentScale = Integer.MAX_VALUE;
        for(int j =0; j < pointList[0].length;j++){
            if(!hasPrintedScaleFactors){
                System.out.println("scale factor " +  j + " is " + scaleFactor[j]);
                hasPrintedScaleFactors = true;
            }
            if(boxes.get(j).isSelected()){
                if(currentScale>scaleFactor[j]){
                    currentScale=scaleFactor[j];
                }
            }
        }
        drawAxes(gc,currentScale);
        int offset = 0;
        for(int i =0; i < pointList.length; i++){
            for(int j = 0; j < pointList[i].length;j++){
                if(boxes.get(j).isSelected()){
                    if(mouseX==2*i||mouseX==2*i + 1){
                        double pY = (HEIGHT/2)+(pointList[i-1][j]*currentScale);
                        
                        gc.setStroke(Color.BLUE);
                        gc.strokeLine(mouseX, 0,mouseX, WIDTH);
                        gc.setFont(new Font(20));
                        String formattedPList = String.format("%.2f",pointList[i][j]);
                         gc.strokeText(headings[j]+ " Intersect At", mouseX, (HEIGHT/2)+offset);
                         gc.strokeText( "MOUSE X: " + mouseX + " Y LOC: " + formattedPList,mouseX, (HEIGHT/2)+40+offset);
                         offset+=80;
                    }
                }
            }
        }
    }
    private void drawGraph(GraphicsContext gc, double pointList[][]) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        double currentScale = Integer.MAX_VALUE;
        for(int j =0; j < pointList[0].length;j++){
            if(boxes.get(j).isSelected()){
                if(currentScale>scaleFactor[j]){
                    currentScale=scaleFactor[j];
                    
                }
            }
        }
        for(int i =1; i <pointList.length;i++){
            for(int j =0; j < pointList[i].length;j++){
                if(boxes.get(j).isSelected()){
                    gc.strokeLine(2*i - 1,(HEIGHT/2)+(pointList[i-1][j]*currentScale),i*2,(HEIGHT/2)+(pointList[i][j]*currentScale));                          
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String... args) {
        launch(args);
    }
    
}

