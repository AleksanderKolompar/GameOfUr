package gameofur;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
    enum TileState {
        EMPTY, BLACK, WHITE
    }

    class Tile {
        private final int xCoordinate;
        private final int yCoordinate;
        private TileState tileState;

        public Tile(int xCoordinate, int yCoordinate) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
            this.tileState = TileState.EMPTY;
        }

        public void setTileState(TileState tileState) {
            this.tileState = tileState;
        }
    }

    private final List<Tile> board = new ArrayList<>();

    public Board(int xSize, int ySize) {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                this.board.add(new Tile(x, y));
            }
        }
    }

    public List<Tile> getBoard() {
        return board;
    }

    public TileState getTileState(int x, int y) {
        return board.stream()
                .filter(e -> (e.xCoordinate == x) && (e.yCoordinate == y))
                .collect(Collectors.toList()).get(0).tileState;
    }

    public void setTileState(int x, int y, TileState newState) {
        int index = board.indexOf(board.stream()
                .filter(e -> (e.xCoordinate == x) && (e.yCoordinate == y))
                .collect(Collectors.toList()));
        board.get(index).setTileState(newState);
    }
}
