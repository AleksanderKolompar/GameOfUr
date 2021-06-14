package gameofur;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        this.pawnButton.setOnAction(e -> pawnEvent());
        move(position.getPosX(0), position.getPosY(0));
    }

    public Button getPawnButton() {
        return pawnButton;
    }

    private void pawnEvent() {
        int newTileNumber = tileNumber + dice.getRollResult();
        if (newTileNumber <= 15) {
            int posX = position.getListPosX().get(tileNumber);
            int posY = position.getListPosY().get(tileNumber);
            int newPosX = position.getListPosX().get(newTileNumber);
            int newPosY = position.getListPosY().get(newTileNumber);

            if (isValidMove(newTileNumber, newPosX, newPosY)) {
                System.out.println(newPosX + " " + newPosY + " " + newTileNumber);
                board.setTileState(posX, posY, Board.TileState.EMPTY);
                move(position.getPosX(newTileNumber), position.getPosY(newTileNumber));
                tileNumber = newTileNumber;
                if (tileNumber == 15) {
                    //score++
                    this.pawnButton.setOnAction(null);
                } else {
                    board.setTileState(newPosX, newPosY, color);
                }
                //board.displayBoard();


                dice.resetRollResult();
            }
            dice.reactivateButton();
        }

    }

    private void move(double x, double y) {
        this.pawnButton.setTranslateX(x);
        this.pawnButton.setTranslateY(y);
    }

    private boolean isValidMove(int newTileNumber, int newPosX, int newPosY) {
        if (board.checkIfEmptyTile(newPosX, newPosY)) {
            return true;
        } else if (board.getTileState(newPosX, newPosY) != color) {
            capture(newTileNumber);
            return true;
        }
        System.out.println("Invalid Move. Try Again");
        return false;
    }

    private void capture(int newTileNumber) {
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
