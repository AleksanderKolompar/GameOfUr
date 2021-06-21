package gameofur;

import javafx.event.ActionEvent;

import java.util.List;

import static gameofur.GameOfUr.numberOfPawns;
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

            if (isValidMove(newTileNumber, newCoordinates, pawn.getColor())) {
                board.setTileState(oldCoordinates, Board.TileState.EMPTY);
                pawn.reposition(newTileNumber);
                pawn.setTileNumber(newTileNumber);
                if (newTileNumber == END_TILE) {
                    if (dice.getRollResult() != 0) {
                        board.incrementScore(pawn.getColor());
                    }
                    pawn.setOnAction(null);
                    board.setTileState(newCoordinates, Board.TileState.EMPTY);
                } else {
                    board.setTileState(newCoordinates, pawn.getColor());
                }
                board.displayBoard();
                dice.resetRollResult();
            }
        }
        if (board.checkGameEnd()) {
            dice.setTestLabel("0");
            board.resetScoreLabel();
            resetGame();
        }
        if (pawn.getColor().equals(Board.TileState.WHITE)) {
            computerMove();
        }
        dice.reactivateButton();
    }

    private boolean isValidMove(int newTileNumber, Coordinates coordinates, Board.TileState color) {
        if (board.checkIfEmptyTile(coordinates) || newTileNumber == END_TILE || pawn.getTileNumber() == newTileNumber) {
            return true;
        } else if (board.getTileState(coordinates) != color) {
            capture(newTileNumber);
            return true;
        }
        System.out.println("Invalid Move.");
        dice.setRollResult(0);
        dice.reactivateButton();
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
                capturedPawn.reposition(START_TILE);
            }
        }
    }

    private void computerMove() {
        diceEvent();
        if (dice.getRollResult() == 0) {
            return;
        }
        int pawnNumber = findValidBlackMove(0, numberOfPawns, Board.TileState.BLACK);
        int newTileNumber = board.getBlackPawnsList().get(pawnNumber).getTileNumber();
        Pawn pawn = board.getBlackPawnsList().get(pawnNumber);
        pawn.setTileNumber(newTileNumber);
        pawnEvent(pawn);
        dice.reactivateButton();
    }

    public int findValidBlackMove(int start, int end, Board.TileState color) {
        int validIndex = start;
        if (validIndex >= end) {
            System.out.println("no pawn to move");
            dice.setRollResult(0);
            return numberOfPawns - 1;
        }
        int newTileNumber = board.getBlackPawnsList().get(validIndex).getTileNumber() + dice.getRollResult();
        if (newTileNumber <= END_TILE) {
            Coordinates coordinates = new Coordinates(newTileNumber, color);
            if (isValidMove(newTileNumber, coordinates, color)) {
                System.out.println("index: " + validIndex);
                return validIndex;
            }
        }
        return findValidBlackMove(validIndex + 1, end, color);
    }

    public void resetGame() {
        for (int i = 0; i < numberOfPawns; i++) {
            Pawn whitePawn = board.getWhitePawnsList().get(i);
            Pawn blackPawn = board.getBlackPawnsList().get(i);

            whitePawn.setTileNumber(0);
            blackPawn.setTileNumber(0);

            whitePawn.setOnAction(e -> pawnEvent(whitePawn));

            whitePawn.reposition(0);
            blackPawn.reposition(0);
        }
        board.emptyBoard();
        board.resetScore();
    }


}
