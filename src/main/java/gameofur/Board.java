package gameofur;

import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gameofur.GameOfUr.numberOfPawns;

public class Board {
    enum Color {
        EMPTY, BLACK, WHITE
    }

    private final Map<Coordinates, Color> boardMap = new HashMap<>();
    private final List<Pawn> whitePawnsList;
    private final List<Pawn> blackPawnsList;
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

    public Board.Color getWinner() {
        if (blackScore == numberOfPawns) {
            return Color.BLACK;
        } else if (whiteScore == numberOfPawns) {
            return Color.WHITE;
        } else {
            return Color.EMPTY;
        }
    }

    public Color getTileState(Coordinates coordinates) {
        return boardMap.get(coordinates);
    }

    public List<Pawn> getWhitePawnsList() {
        return whitePawnsList;
    }

    public List<Pawn> getBlackPawnsList() {
        return blackPawnsList;
    }

    public int getScore(Color color) {
        if (color == Color.WHITE) {
            return whiteScore;
        }
        return blackScore;
    }

    public void emptyBoard() {
        for (int x = 1; x <= xSize; x++) {
            for (int y = 1; y <= ySize; y++) {
                this.boardMap.put(new Coordinates(x, y), Color.EMPTY);
            }
        }
    }

    public void incrementScore(Color color) {
        if (color == Color.WHITE) {
            this.whiteScore++;
        } else if (color == Color.BLACK) {
            this.blackScore++;
        }
    }

    public void setTileState(Coordinates coordinates, Color newState) {
        boardMap.replace(coordinates, newState);
    }

    public void resetScore() {
        this.whiteScore = 0;
        this.blackScore = 0;
    }

    public boolean checkIfEmptyTile(Coordinates coordinates) {
        return getTileState(coordinates) == (Color.EMPTY);
    }
}
