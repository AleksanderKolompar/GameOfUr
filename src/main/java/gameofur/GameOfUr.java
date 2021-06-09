package gameofur;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;



public class GameOfUr extends Application {

    private final Image imageback = new Image("file:src/main/resources/UrBoard.jpg");
    private final Image whitePawn = new Image("file:src/main/resources/WhitePawnT.png");
    private final Image blackPawn = new Image("file:src/main/resources/BlackPawnT.png");
    private final Label testLabel = new Label("Test");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        grid.setGridLinesVisible(true);


        grid.setBackground(background);

        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                testLabel.setText(" white button selected ");
            }
        };
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                testLabel.setText(" black button selected ");
            }
        };

        grid.add(testLabel,1,1,100,100);

        //white button
        ImageView iw = new ImageView(whitePawn);
        Button whiteButton = new Button("",iw);
        whiteButton.setStyle("-fx-background-color: transparent;");
        whiteButton.setOnAction(event1);
        grid.add(whiteButton,3,3);

        //black button
        ImageView ib = new ImageView(blackPawn);
        Button blackButton = new Button("",ib);
        blackButton.setStyle("-fx-background-color: transparent;");
        blackButton.setOnAction(event2);
        grid.add(blackButton,5,5);


        Scene scene = new Scene(grid, 500, 760, Color.WHITE);

        primaryStage.setTitle("Game of Ur");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
