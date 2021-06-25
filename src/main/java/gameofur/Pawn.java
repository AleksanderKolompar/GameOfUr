package gameofur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import static gameofur.Position.START_TILE;

public class Pawn {
    private final Button pawnButton;
    private final Board.Color color;
    private final Position position;
    private int tileNumber = START_TILE;


    public Pawn(Image pawnImage, Board.Color color) {
        this.color = color;
        this.position = new Position(color);
        this.pawnButton = new Button("", new ImageView(pawnImage));
        this.pawnButton.setStyle("-fx-background-color: transparent;");
        reposition(START_TILE);
    }

    public Board.Color getColor() {
        return color;
    }

    public int getTileNumber() {
        return tileNumber;
    }

    public void setOnAction(EventHandler<ActionEvent> value) {
        this.pawnButton.setOnAction(value);
    }

    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    public void reposition(int tileNumber) {
        this.pawnButton.setTranslateX(position.getPosX(tileNumber));
        this.pawnButton.setTranslateY(position.getPosY(tileNumber));
    }

    public void positionOutside(int score) {
        if (color == Board.Color.WHITE) {
            this.pawnButton.setTranslateX(405);
        } else {
            this.pawnButton.setTranslateX(5);
        }
        this.pawnButton.setTranslateY(10 + (score * 30));
    }

    public void addToGrid(GridPane grid) {
        grid.add(pawnButton, 0, 0);
    }
}
