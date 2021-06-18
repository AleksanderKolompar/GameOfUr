package gameofur;

import java.util.*;

public class Board {
    enum TileState {
        EMPTY, BLACK, WHITE
    }

    private final Map<Coordinates, TileState> boardMap = new HashMap<>();
    private final List<Pawn> whitePawnsList;
    private final List<Pawn> blackPawnsList;

    public Board(int xSize, int ySize, List<Pawn> whitePawnsList, List<Pawn> blackPawnsList) {
        for (int x = 1; x <= xSize; x++) {
            for (int y = 1; y <= ySize; y++) {
                this.boardMap.put(new Coordinates(x, y), TileState.EMPTY);
            }
        }
        this.whitePawnsList = whitePawnsList;
        this.blackPawnsList = blackPawnsList;
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
}
