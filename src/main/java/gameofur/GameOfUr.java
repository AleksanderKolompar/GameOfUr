package gameofur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;


public class GameOfUr extends Application {

    private final Image boardImage = new Image("file:src/main/resources/UrBoard.jpg");
    private final Image whitePawnImage = new Image("file:src/main/resources/WhitePawnT.png");
    private final Image blackPawnImage = new Image("file:src/main/resources/BlackPawnT.png");
    private final List<Pawn> whitePawnsList = new LinkedList<>();
    private final List<Pawn> blackPawnsList = new LinkedList<>();

    public static final int numberOfPawns = 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(boardImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Board board = new Board(3, 8, whitePawnsList, blackPawnsList);
        Dice dice = new Dice(3);
        GridPane grid = new GridPane();
        GameEventHandler gameEventHandler = new GameEventHandler(board, dice);

        grid.setBackground(background);
        board.addToGrid(grid);
        dice.addToGrid(grid);

        dice.setOnAction(e -> gameEventHandler.diceEvent());

        for (int i = 0; i < numberOfPawns; i++) {
            Pawn whitePawn = new Pawn(whitePawnImage, Board.TileState.WHITE);
            Pawn blackPawn = new Pawn(blackPawnImage, Board.TileState.BLACK);
            whitePawnsList.add(whitePawn);
            blackPawnsList.add(blackPawn);

            whitePawn.setOnAction(e -> gameEventHandler.pawnEvent(whitePawn));
            //blackPawn.setOnAction(e -> gameEventHandler.pawnEvent(blackPawn));

            whitePawn.addToGrid(grid);
            blackPawn.addToGrid(grid);
        }
        Scene scene = new Scene(grid, 500, 760, Color.WHITE);


        primaryStage.setTitle("Game of Ur");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
