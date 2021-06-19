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
    private final Board.TileState color;
    private final Position position;
    private int tileNumber = START_TILE;


    public Pawn(Image pawnImage, Board.TileState color) {
        this.color = color;
        this.position = new Position(color);
        this.pawnButton = new Button("", new ImageView(pawnImage));
        this.pawnButton.setStyle("-fx-background-color: transparent;");
        //this.pawnButton.setOnAction(e -> pawnEvent());
        reposition(START_TILE);
    }


    public void setOnAction(EventHandler<ActionEvent> value) {
        this.pawnButton.setOnAction(value);
    }

    public void addToGrid(GridPane grid) {
        grid.add(pawnButton, 0, 0);
    }

    public void reposition(int tileNumber) {
        this.pawnButton.setTranslateX(position.getPosX(tileNumber));
        this.pawnButton.setTranslateY(position.getPosY(tileNumber));
    }

    public Board.TileState getColor() {
        return color;
    }

    public int getTileNumber() {
        return tileNumber;
    }

    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }
}
