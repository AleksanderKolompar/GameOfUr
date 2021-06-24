package gameofur;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.List;

import static gameofur.GameOfUr.numberOfPawns;
import static gameofur.Position.END_TILE;
import static gameofur.Position.START_TILE;

public class GameEventHandler {

    private final Board board;
    private final Dice dice;
    private Pawn pawn;

    public final PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));

    public GameEventHandler(Board board, Dice dice) {
        this.board = board;
        this.dice = dice;
    }


    public void diceEvent(Board.Color color) {
        if (color == Board.Color.WHITE){
            enableWhitePawns();
        }
        dice.setRollResult(dice.rollDice(), color) ;
        System.out.println(dice.getRollResult(color));
        //this.dice.setTestLabel(Integer.toString(dice.getRollResult(color)));
    }

    public void pawnEvent(Pawn pawn) {
        //pauseTransition.setOnFinished( e -> {
            this.pawn = pawn;
            int tileNumber = pawn.getTileNumber();
            int newTileNumber = tileNumber + dice.getRollResult(pawn.getColor());
            if (newTileNumber <= END_TILE && dice.getRollResult(pawn.getColor()) != 0) {
                Coordinates oldCoordinates = new Coordinates(tileNumber, pawn.getColor());
                Coordinates newCoordinates = new Coordinates(newTileNumber, pawn.getColor());

                if (isValidMove(newTileNumber, newCoordinates, pawn.getColor())) {
                    board.setTileState(oldCoordinates, Board.Color.EMPTY);
                    pawn.reposition(newTileNumber);
                    pawn.setTileNumber(newTileNumber);
                    if (newTileNumber == END_TILE) {
                        if (dice.getRollResult(pawn.getColor()) != 0) {
                            board.incrementScore(pawn.getColor());
                        }
                        pawn.setOnAction(null);
                        board.setTileState(newCoordinates, Board.Color.EMPTY);
                    } else {
                        board.setTileState(newCoordinates, pawn.getColor());
                    }
                    //board.displayBoard();
                    dice.resetRollResult();
                }
            }
            if (board.checkGameEnd()) {
                //dice.setTestLabel("0");
                //board.resetScoreLabel();
                resetGame();
            }
            if (pawn.getColor().equals(Board.Color.WHITE)) {
                disableWhitePawns();
                computerMove();
            }
            dice.reactivateButton();
        //});
        //pauseTransition.play();
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

            whitePawn.setOnAction(e -> pawnEvent(whitePawn));

            whitePawn.reposition(0);
            blackPawn.reposition(0);
        }
        board.emptyBoard();
        board.resetScore();
        dice.resetDice();
    }

    private void disableWhitePawns(){
        System.out.println("disabled");
        for (Pawn whitePawn : board.getWhitePawnsList()) {
            whitePawn.setOnAction(null);
        }
    }

    private void enableWhitePawns(){
        System.out.println("enabled");
        for (Pawn whitePawn : board.getWhitePawnsList()) {
            whitePawn.setOnAction(e -> pawnEvent(whitePawn));
        }
    }
}
