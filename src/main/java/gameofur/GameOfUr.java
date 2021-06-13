package gameofur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


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
        grid.setBackground(background);

        Dice dice = new Dice(3);
        grid.add(dice.getRollButton(), 0, 0);
        grid.add(dice.getTestLabel(), 0, 0);

        Board board = new Board(3, 8);

        grid.add(testLabel, 0, 0);


        for (int i = 1; i <= 4; i++) {
            Pawn white1 = new Pawn(whitePawn, Board.TileState.WHITE, dice, board);
            grid.add(white1.getPawnButton(), 0, 0);
        }
        Scene scene = new Scene(grid, 500, 760, Color.WHITE);

        primaryStage.setTitle("Game of Ur");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
