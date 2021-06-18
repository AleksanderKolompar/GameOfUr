package gameofur;

import javafx.event.ActionEvent;

import java.util.List;

import static gameofur.Position.END_TILE;
import static gameofur.Position.START_TILE;

public class GameEventHandler implements javafx.event.EventHandler<ActionEvent> {

    private final Board board;
    private final Dice dice;
    private Pawn pawn;

    public GameEventHandler(Board board, Dice dice) {
        this.board = board;
        this.dice = dice;
    }

    @Override
    public void handle(ActionEvent event) {
    }

    public void diceEvent() {
        dice.setRollResult(dice.rollDice());
        System.out.println(dice.getRollResult());
        this.dice.setTestLabel(Integer.toString(dice.getRollResult()));
        dice.getRollButton().setDisable(true);
    }

    public void pawnEvent(Pawn pawn) {
        this.pawn = pawn;
        int tileNumber = pawn.getTileNumber();
        int newTileNumber = tileNumber + dice.getRollResult();
        if (newTileNumber <= END_TILE) {
            Coordinates oldCoordinates = new Coordinates(tileNumber, pawn.getColor());
            Coordinates newCoordinates = new Coordinates(newTileNumber, pawn.getColor());

            if (isValidMove(newTileNumber, newCoordinates)) {
                board.setTileState(oldCoordinates, Board.TileState.EMPTY);
                pawn.move(newTileNumber);
                pawn.setTileNumber(newTileNumber);
                if (tileNumber == END_TILE) {
                    //score++
                    pawn.setOnAction(null);
                    board.setTileState(newCoordinates, Board.TileState.EMPTY);
                } else {
                    board.setTileState(newCoordinates, pawn.getColor());
                }
                board.displayBoard();
                dice.resetRollResult();
            }
            dice.reactivateButton();
        }
    }

    private boolean isValidMove(int newTileNumber, Coordinates coordinates) {
        if (board.checkIfEmptyTile(coordinates) || newTileNumber == END_TILE || pawn.getTileNumber() == newTileNumber) {
            return true;
        } else if (board.getTileState(coordinates) != pawn.getColor()) {
            capture(newTileNumber);
            return true;
        }
        System.out.println("Invalid Move. Try Again");
        return false;
    }

    private void capture(int newTileNumber) {
        List<Pawn> pawnsList;
        if (pawn.getColor() == Board.TileState.WHITE) {
            pawnsList = board.getBlackPawnsList();
        } else {
            pawnsList = board.getWhitePawnsList();
        }
        for (Pawn capturedPawn : pawnsList) {
            if (capturedPawn.getTileNumber() == newTileNumber) {
                System.out.println("Pawning");
                capturedPawn.setTileNumber(START_TILE);
                capturedPawn.move(START_TILE);
            }
        }
    }
}
