package gameofur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GameOfUr extends Application {

    private final Image imageback = new Image("file:src/main/resources/UrBoard.jpg");
    private final Image whitePawn = new Image("file:src/main/resources/WhitePawnT.png");
    private final Image blackPawn = new Image("file:src/main/resources/BlackPawnT.png");
    private Label testLabel = new Label("Test");



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);


        GridPane grid = new GridPane();

        grid.setBackground(background);

        testLabel.setTranslateY(300);

        grid.add(testLabel,1,1,100,100);

        Pawn white1 = new Pawn(whitePawn,"white1");

        grid.add(white1.getPawnButton(),0,0,392,392);

        Scene scene = new Scene(grid, 500, 760, Color.WHITE);

        primaryStage.setTitle("Game of Ur");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
