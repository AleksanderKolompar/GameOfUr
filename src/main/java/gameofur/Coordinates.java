package gameofur;

import java.util.Objects;

public final class Coordinates {
    private final int xCoordinate;
    private final int yCoordinate;

    Coordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    Coordinates(int tileNumber, Board.Color color) {
        Position position = new Position(color);
        this.xCoordinate = position.getCoordinateX(tileNumber);
        this.yCoordinate = position.getCoordinateY(tileNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coordinates = (Coordinates) o;
        return xCoordinate == coordinates.xCoordinate && yCoordinate == coordinates.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }
}
