package gameofur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;


public class GameOfUr extends Application {

    private final Image boardImage = loadImage("UrBoard.jpg");
    private final Image whitePawnImage = loadImage("WhitePawnT.png");
    private final Image blackPawnImage = loadImage("BlackPawnT.png");
    private final Image diceRoll0Image = loadImage("DiceRoll0T.png");
    private final Image diceRoll1Image = loadImage("DiceRoll1T.png");
    private final List<Pawn> whitePawnsList = new LinkedList<>();
    private final List<Pawn> blackPawnsList = new LinkedList<>();

    private final Board board = new Board(3, 8, whitePawnsList, blackPawnsList);
    private final Dice dice = new Dice(3, diceRoll0Image, diceRoll1Image);
    private final GridPane grid = new GridPane();
    private final GameEventHandler gameEventHandler = new GameEventHandler(board, dice);


    public static final int numberOfPawns = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(boardImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);


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

    private Image loadImage(String fileName) {
        try {
            return new Image(GameOfUr.class.getClassLoader().getResourceAsStream(fileName));
        }
        catch (NullPointerException e) {
            System.out.println("Could not find: " + fileName);
            e.printStackTrace();

            WritableImage img = new WritableImage(1, 1);
            PixelWriter pw = img.getPixelWriter();
            Color color = Color.color(0, 0, 0, 1);

            pw.setColor(0, 0, color);
            return img;
        }
    }


}
