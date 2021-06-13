package gameofur;

import java.util.*;

public class Board {
    enum TileState {
        EMPTY, BLACK, WHITE
    }

    class Tile {
        private final int xCoordinate;
        private final int yCoordinate;

        private Tile(int xCoordinate, int yCoordinate) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return xCoordinate == tile.xCoordinate && yCoordinate == tile.yCoordinate;
        }

        @Override
        public int hashCode() {
            return Objects.hash(xCoordinate, yCoordinate);
        }
    }

    private final Map<Tile, TileState> boardMap = new HashMap<>();
    private final List<Pawn> whitePawnsList;
    private final List<Pawn> blackPawnsList;

    public Board(int xSize, int ySize, List<Pawn> whitePawnsList, List<Pawn> blackPawnsList) {
        for (int x = 1; x <= xSize; x++) {
            for (int y = 1; y <= ySize; y++) {
                this.boardMap.put(new Tile(x, y), TileState.EMPTY);
                //System.out.println(this.boardMap.get(new Tile(x, y)) == TileState.EMPTY);
            }
        }
        this.whitePawnsList = whitePawnsList;
        this.blackPawnsList = blackPawnsList;
    }

    public TileState getTileState(int x, int y) {
        //System.out.println("getTileState" + boardMap.get(new Tile(x, y)));
        return boardMap.get(new Tile(x, y));
    }

    public List<Pawn> getWhitePawnsList() {
        return whitePawnsList;
    }

    public List<Pawn> getBlackPawnsList() {
        return blackPawnsList;
    }

    public void setTileState(int x, int y, TileState newState) {
        boardMap.replace(new Tile(x, y), newState);
    }

    public boolean checkIfEmptyTile(int x, int y) {
        //System.out.println("checkIfEmptyTile" + getTileState(x, y).equals(TileState.EMPTY));
        return getTileState(x, y) == (TileState.EMPTY);
    }

    public void displayBoard() {
        for (int i = 1; i <= 8; i++) {
            System.out.print("|");
            for (int j = 1; j <= 3; j++) {
                System.out.print(" " + getTileState(j, i) + " |");
            }
            System.out.print("\n");
        }
    }
}