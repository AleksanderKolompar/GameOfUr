package gameofur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


public class Dice {
    private final int repositionXWhite = 400;
    private final int repositionY = 650;

    private final Image diceRoll0Image;
    private final Image diceRoll1Image;
    private final List<ImageView> listOfDice = new ArrayList<>();
    private final int diceCount;
    private final Button rollButton = new Button("Roll");

    private int whiteRollResult;
    private int blackRollResult;

    public Dice(int diceCount, Image diceRoll0Image, Image diceRoll1Image) {
        this.diceCount = diceCount;
        rollButton.setTranslateX(repositionXWhite + 30);
        rollButton.setTranslateY(330);
        this.diceRoll0Image = diceRoll0Image;
        this.diceRoll1Image = diceRoll1Image;
        for (int i = 0; i < diceCount; i++) {
            this.listOfDice.add(new ImageView(diceRoll0Image));
            this.listOfDice.get(i).setTranslateX(repositionXWhite);
            this.listOfDice.get(i).setTranslateY(repositionY - (100 * i));
        }
    }


    public int getRollResult(Board.Color color) {
        if (color == Board.Color.WHITE) {
            return whiteRollResult;
        } else {
            return blackRollResult;
        }
    }

    public void setOnAction(EventHandler<ActionEvent> value) {
        this.rollButton.setOnAction(value);
    }

    public void setRollResult(int rollResult, Board.Color color) {
        if (color == Board.Color.WHITE) {
            this.whiteRollResult = rollResult;
            for (int i = 0; i < diceCount; i++) {
                listOfDice.get(i).setTranslateX(repositionXWhite);
            }
        } else {
            this.blackRollResult = rollResult;
            for (int i = 0; i < diceCount; i++) {
                int repositionXBlack = 0;
                listOfDice.get(i).setTranslateX(repositionXBlack);
            }
        }
    }

    public void resetWhiteRollResult() {
        this.whiteRollResult = 0;
    }

    public void setDiceImage(int index, Image image) {
        listOfDice.get(index).setImage(image);
    }

    public void resetDice() {
        for (int i = 0; i < diceCount; i++) {
            setDiceImage(i, diceRoll0Image);
        }
    }

    public int rollDice() {
        int result = 0;
        for (int i = 0; i < diceCount; i++) {
            if ((int) Math.round(Math.random() * 1) == 0) {
                result++;
                setDiceImage(i, diceRoll1Image);
            } else {
                setDiceImage(i, diceRoll0Image);
            }
        }
        return result;
    }

    public void enableButton() {
        rollButton.setDisable(false);
    }

    public void disableButton() {
        rollButton.setDisable(true);
    }

    public void addToGrid(GridPane grid) {
        grid.add(rollButton, 0, 0);
        for (int i = 0; i < diceCount; i++) {
            this.listOfDice.add(new ImageView(diceRoll0Image));
            grid.add(listOfDice.get(i), 0, 0);
        }
    }
}
