package gameofur;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

import static gameofur.GameOfUr.numberOfPawns;
import static gameofur.Position.END_TILE;
import static gameofur.Position.START_TILE;

public class GameEventHandler {

    private final Board board;
    private final Dice dice;
    private final GridPane grid;
    private Pawn pawn;

    public final PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));

    public GameEventHandler(Board board, Dice dice, GridPane grid) {
        this.board = board;
        this.dice = dice;
        this.grid = grid;
    }

    public void diceEvent(Board.Color color) {
        if (color == Board.Color.WHITE) {
            enableWhitePawns();
            dice.disableButton();
        }
        dice.setRollResult(dice.rollDice(), color);
        System.out.println(dice.getRollResult(color));
    }

    public void pawnEvent(Pawn pawn) {
        this.pawn = pawn;
        int tileNumber = pawn.getTileNumber();
        int newTileNumber = tileNumber + dice.getRollResult(pawn.getColor());
        if (newTileNumber <= END_TILE && dice.getRollResult(pawn.getColor()) != 0) {
            Coordinates oldCoordinates = new Coordinates(tileNumber, pawn.getColor());
            Coordinates newCoordinates = new Coordinates(newTileNumber, pawn.getColor());

            if (isValidMove(newTileNumber, newCoordinates, pawn.getColor())) {
                board.setTileState(oldCoordinates, Board.Color.EMPTY);
                pawn.setTileNumber(newTileNumber);
                if (newTileNumber == END_TILE) {
                    pawn.positionOutside(board);
                    if (dice.getRollResult(pawn.getColor()) != 0) {
                        board.incrementScore(pawn.getColor());
                    }
                    pawn.setOnAction(null);
                    board.setTileState(newCoordinates, Board.Color.EMPTY);
                } else {
                    pawn.reposition(newTileNumber);
                    board.setTileState(newCoordinates, pawn.getColor());
                }
                dice.resetWhiteRollResult();
            }
        }
        disableWhitePawns();
        if (checkEnd()) {
            dice.setOnAction(null);
        } else {
            if (pawn.getColor().equals(Board.Color.WHITE)) {
                computerMove();
            }
            dice.enableButton();
        }
    }

    public boolean checkEnd() {
        Board.Color winner = board.getWinner();

        if ((winner != Board.Color.EMPTY)) {

            Button resetButton = new Button("Reset");
            Button exitButton = new Button("Exit");
            Label winnerLabel = new Label("Winner is: " + winner + ". \nWould you like to play again?");

            resetButton.setTranslateX(400);
            resetButton.setTranslateY(300);
            exitButton.setTranslateX(460);
            exitButton.setTranslateY(300);
            winnerLabel.setTranslateX(320);
            winnerLabel.setTranslateY(270);

            resetButton.setOnAction(e -> {
                resetGame();
                grid.getChildren().remove(resetButton);
                grid.getChildren().remove(exitButton);
                grid.getChildren().remove(winnerLabel);
                dice.enableButton();
            });

            exitButton.setOnAction(e -> {
                Stage stage = (Stage) grid.getScene().getWindow();
                stage.close();
            });

            grid.add(resetButton, 0, 0);
            grid.add(exitButton, 0, 0);
            grid.add(winnerLabel, 0, 0);
            return true;
        }
        return false;
    }

    private boolean isValidMove(int newTileNumber, Coordinates coordinates, Board.Color color) {
        if (board.checkIfEmptyTile(coordinates) || newTileNumber == END_TILE || pawn.getTileNumber() == newTileNumber) {
            return true;
        } else if (board.getTileState(coordinates) != color) {
            capture(newTileNumber);
            return true;
        }
        System.out.println("Invalid Move.");
        dice.setRollResult(0, color);
        dice.enableButton();
        return false;
    }

    private void capture(int newTileNumber) {
        List<Pawn> pawnsList;

        if (pawn.getColor() == Board.Color.WHITE) {
            pawnsList = board.getBlackPawnsList();
        } else {
            pawnsList = board.getWhitePawnsList();
        }
        for (Pawn capturedPawn : pawnsList) {
            if (capturedPawn.getTileNumber() == newTileNumber) {
                System.out.println("Pawning");
                capturedPawn.setTileNumber(START_TILE);
                capturedPawn.reposition(START_TILE);
            }
        }
    }

    private void computerMove() {
        pauseTransition.setOnFinished(event1 ->
        {
            diceEvent(Board.Color.BLACK);
            if (dice.getRollResult(Board.Color.BLACK) == 0) {
                return;
            }
            pauseTransition.setOnFinished(event2 ->
            {
                int pawnNumber = findValidBlackMove(0, numberOfPawns, Board.Color.BLACK);
                int newTileNumber = board.getBlackPawnsList().get(pawnNumber).getTileNumber();
                Pawn pawn = board.getBlackPawnsList().get(pawnNumber);
                pawn.setTileNumber(newTileNumber);
                pawnEvent(pawn);
                dice.enableButton();
            });
            pauseTransition.play();
        });
        pauseTransition.play();
    }

    public int findValidBlackMove(int start, int end, Board.Color color) {
        if (start >= end) {
            System.out.println("no pawn to move");
            dice.setRollResult(0, Board.Color.BLACK);
            return numberOfPawns - 1;
        }
        int newTileNumber = board.getBlackPawnsList().get(start).getTileNumber() + dice.getRollResult(Board.Color.BLACK);
        if (newTileNumber <= END_TILE) {
            Coordinates coordinates = new Coordinates(newTileNumber, color);
            if (isValidMove(newTileNumber, coordinates, color)) {
                System.out.println("Black pawn index: " + start);
                return start;
            }
        }
        return findValidBlackMove(start + 1, end, color);
    }

    public void resetGame() {
        for (int i = 0; i < numberOfPawns; i++) {
            Pawn whitePawn = board.getWhitePawnsList().get(i);
            Pawn blackPawn = board.getBlackPawnsList().get(i);

            whitePawn.setTileNumber(START_TILE);
            blackPawn.setTileNumber(START_TILE);

            whitePawn.reposition(START_TILE);
            blackPawn.reposition(START_TILE);
        }
        board.emptyBoard();
        board.resetScore();
        dice.resetDice();
        dice.setOnAction(e -> diceEvent(Board.Color.WHITE));
    }

    private void disableWhitePawns() {
        System.out.println("disabled");
        for (Pawn whitePawn : board.getWhitePawnsList()) {
            whitePawn.setOnAction(null);
        }
    }

    private void enableWhitePawns() {
        System.out.println("enabled");
        for (Pawn whitePawn : board.getWhitePawnsList()) {
            whitePawn.setOnAction(e -> pawnEvent(whitePawn));
        }
    }
}