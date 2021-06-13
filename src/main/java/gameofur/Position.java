package gameofur;

import java.util.ArrayList;
import java.util.List;

public class Position {
    private final List<Integer> listPosXwhite = new ArrayList<>(List.of(3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3));
    private final List<Integer> listPosXblack = new ArrayList<>(List.of(1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1));
    private final List<Integer> listPosY = new ArrayList<>(List.of(4, 5, 6, 7, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3));

    public double getPosX(int index) {
        double posX = listPosXwhite.get(index) * 92 + 19;
        return posX;
    }

    public double getPosY(int index) {
        double posY = (listPosY.get(index) - 1) * 92 + 19;
        return posY;
    }

    public List<Integer> getListPosXwhite() {
        return listPosXwhite;
    }

    public List<Integer> getListPosXblack() {
        return listPosXblack;
    }

    public List<Integer> getListPosY() {
        return listPosY;
    }
}
