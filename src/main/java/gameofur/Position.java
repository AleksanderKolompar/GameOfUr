package gameofur;

import java.util.ArrayList;
import java.util.List;

public class Position {
    public static final int START_TILE = 0;
    public static final int END_TILE = 15;

    private final List<Integer> listPosXwhite = new ArrayList<>(List.of(3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3));
    private final List<Integer> listPosXblack = new ArrayList<>(List.of(1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1));
    private final List<Integer> listPosY = new ArrayList<>(List.of(4, 5, 6, 7, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3));
    private final Board.TileState color;

    public Position(Board.TileState color) {
        this.color = color;
    }

    public double getPosX(int index) {
        if (color == Board.TileState.WHITE) {
            return listPosXwhite.get(index) * 92 + 19;
        }
        if (color == Board.TileState.BLACK) {
            return listPosXblack.get(index) * 92 + 19;
        }
        return 0;
    }

    public double getPosY(int tileNumber) {
        return (listPosY.get(tileNumber) - 1) * 92 + 9;
    }

    private List<Integer> getListPosX() {
        if (color == Board.TileState.WHITE) {
            return listPosXwhite;
        }
        if (color == Board.TileState.BLACK) {
            return listPosXblack;
        }
        return new ArrayList<>(0);
    }

    public int getCoordinateX(int tileNumber) {
        return getListPosX().get(tileNumber);
    }

    public int getCoordinateY(int tileNumber) {
        return listPosY.get(tileNumber);
    }
}
