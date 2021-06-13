package gameofur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;


public class GameOfUr extends Application {

    private final Image imageback = new Image("file:src/main/resources/UrBoard.jpg");
    private final Image whitePawn = new Image("file:src/main/resources/WhitePawnT.png");
    private final Image blackPawn = new Image("file:src/main/resources/BlackPawnT.png");
    private final Label testLabel = new Label("Test");
    private final List<Pawn> whitePawnsList = new LinkedList<>();
    private final List<Pawn> blackPawnsList = new LinkedList<>();

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

        Board board = new Board(3, 8, whitePawnsList, blackPawnsList);

        grid.add(testLabel, 0, 0);


        for (int i = 0; i < 2; i++) {
            whitePawnsList.add(new Pawn(whitePawn, Board.TileState.WHITE, dice, board));
            blackPawnsList.add(new Pawn(blackPawn, Board.TileState.BLACK, dice, board));
            //Pawn white1 = new Pawn(whitePawn, Board.TileState.WHITE, dice, board);
            grid.add(whitePawnsList.get(i).getPawnButton(), 0, 0);
            grid.add(blackPawnsList.get(i).getPawnButton(), 0, 0);
        }
        Scene scene = new Scene(grid, 500, 760, Color.WHITE);

        primaryStage.setTitle("Game of Ur");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
