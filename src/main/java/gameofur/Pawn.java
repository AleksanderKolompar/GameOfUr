package gameofur;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.LinkedList;
import java.util.List;

public class Pawn {
    private final Button pawnButton;
    private final Board.TileState color;
    private int tileNumber = 0;
    private final Position position;
    private final Dice dice;
    private final Board board;


    public Pawn(Image pawnImage, Board.TileState color, Dice dice, Board board) {
        this.dice = dice;
        this.board = board;
        this.color = color;
        this.position = new Position(color);
        this.pawnButton = new Button("", new ImageView(pawnImage));
        this.pawnButton.setStyle("-fx-background-color: transparent;");
        this.pawnButton.setOnAction(e -> {
            int newTileNumber = tileNumber + dice.getRollResult();
            int posX = position.getListPosX().get(tileNumber);
            int posY = position.getListPosY().get(tileNumber);
            int newPosX = position.getListPosX().get(newTileNumber);
            int newPosY = position.getListPosY().get(newTileNumber);
            System.out.println(newPosX + " " + newPosY);

            if (isValidMove(newTileNumber, newPosX, newPosY)) {
                tileNumber = newTileNumber;

                if (tileNumber >= 16) {
                    tileNumber = 0;
                }
                move(position.getPosX(newTileNumber), position.getPosY(newTileNumber));
                board.setTileState(posX, posY, Board.TileState.EMPTY);
                board.setTileState(newPosX, newPosY, color);
                board.displayBoard();

                dice.resetRollResult();
            } else {
                System.out.println("Invalid Move. Try Again");
            }
            dice.reactivateButton();

        });
        move(position.getPosX(0), position.getPosY(0));
    }

    private void move(double x, double y) {
        this.pawnButton.setTranslateX(x);
        this.pawnButton.setTranslateY(y);
    }

    public Button getPawnButton() {
        return pawnButton;
    }

    private boolean isValidMove(int newTileNumber, int newPosX, int newPosY) {
        if (newTileNumber <= 16) {
            if (board.checkIfEmptyTile(newPosX, newPosY)) {
                return true;
            } else if (board.getTileState(newPosX, newPosY) != color) {
                capture(newTileNumber);
                return true;
            }
        }
        return false;
    }

    private void capture(int newTileNumber) {
        //find opposite color pawn occupying newx/newy tile
        List<Pawn> pawnsList;
        if (color == Board.TileState.WHITE) {
            pawnsList = board.getBlackPawnsList();
        } else {
            pawnsList = board.getWhitePawnsList();
        }
        for (Pawn capturedPawn : pawnsList) {
            if (capturedPawn.tileNumber == newTileNumber) {
                System.out.println("Pawning");
                capturedPawn.tileNumber = 0;
                capturedPawn.move(capturedPawn.position.getPosX(0), position.getPosY(0));
            }
        }
    }
}
