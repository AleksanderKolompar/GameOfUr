package gameofur;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gameofur.GameOfUr.numberOfPawns;

public class Board {
    enum TileState {
        EMPTY, BLACK, WHITE
    }

    private final Map<Coordinates, TileState> boardMap = new HashMap<>();
    private final List<Pawn> whitePawnsList;
    private final List<Pawn> blackPawnsList;
    private final Label scoreLabel = new Label("Score");
    private final int xSize;
    private final int ySize;
    private int whiteScore = 0;
    private int blackScore = 0;


    public Board(int xSize, int ySize, List<Pawn> whitePawnsList, List<Pawn> blackPawnsList) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.whitePawnsList = whitePawnsList;
        this.blackPawnsList = blackPawnsList;
        emptyBoard();
    }

    public void addToGrid(GridPane grid) {
        grid.add(scoreLabel, 0, 0);
    }

    public void incrementScore(TileState color) {
        if (color == TileState.WHITE) {
            this.whiteScore++;
        } else if (color == TileState.BLACK) {
            this.blackScore++;
        }
        updateScore();
    }

    public boolean checkGameEnd() {
        if ((blackScore == numberOfPawns) || (whiteScore == numberOfPawns)) {
            return true;
        }
        return false;
    }


    public void resetScore() {
        this.whiteScore = 0;
        this.blackScore = 0;
    }

    private void updateScore() {
        scoreLabel.setText(blackScore + " " + whiteScore);
    }

    public TileState getTileState(Coordinates coordinates) {
        return boardMap.get(coordinates);
    }

    public List<Pawn> getWhitePawnsList() {
        return whitePawnsList;
    }

    public List<Pawn> getBlackPawnsList() {
        return blackPawnsList;
    }

    public void setTileState(Coordinates coordinates, TileState newState) {
        boardMap.replace(coordinates, newState);
    }

    public void resetScoreLabel() {
        this.scoreLabel.setText("0 0");
    }

    public boolean checkIfEmptyTile(Coordinates coordinates) {
        return getTileState(coordinates) == (TileState.EMPTY);
    }

    public void displayBoard() {
        for (int i = 1; i <= 8; i++) {
            System.out.print("|");
            for (int j = 1; j <= 3; j++) {
                System.out.print(" " + getTileState(new Coordinates(j, i)) + " |");
            }
            System.out.print("\n");
        }
    }

    public void emptyBoard() {
        for (int x = 1; x <= xSize; x++) {
            for (int y = 1; y <= ySize; y++) {
                this.boardMap.put(new Coordinates(x, y), TileState.EMPTY);
            }
        }
    }


}
