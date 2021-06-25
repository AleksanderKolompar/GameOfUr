package gameofur;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.Optional;

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

            dice.getRollButton().setDisable(true);
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
                    pawn.positionOutside(board.getScore(pawn.getColor()));
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
        if (pawn.getColor().equals(Board.Color.WHITE)) {
            disableWhitePawns();
            computerMove();
        }
        dice.reactivateButton();
    }

    public void checkEnd() {
        Board.Color winner = board.getWinner();
        if ((winner != Board.Color.EMPTY) && (pawn.getColor().equals(Board.Color.WHITE))) {
            System.out.println(winner);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game finished");
            alert.setHeaderText("And the winner is: " + winner);
            alert.setContentText("Do you want to play again?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                resetGame();
            } else {
                Stage stage = (Stage) grid.getScene().getWindow();
                stage.close();
            }
        }
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
        dice.reactivateButton();
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
        checkEnd();
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
                dice.reactivateButton();
            });
            pauseTransition.play();
        });
        pauseTransition.play();
        checkEnd();
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
                System.out.println("index: " + start);
                return start;
            }
        }
        return findValidBlackMove(start + 1, end, color);
    }

    public void resetGame() {
        for (int i = 0; i < numberOfPawns; i++) {
            Pawn whitePawn = board.getWhitePawnsList().get(i);
            Pawn blackPawn = board.getBlackPawnsList().get(i);

            whitePawn.setTileNumber(0);
            blackPawn.setTileNumber(0);

            whitePawn.reposition(0);
            blackPawn.reposition(0);
        }
        board.emptyBoard();
        board.resetScore();
        dice.resetDice();
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
