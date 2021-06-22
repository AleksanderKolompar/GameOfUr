package gameofur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Dice {
    private final int diceCount;
    private final Button rollButton = new Button("Roll");
    private final Label testLabel = new Label("Test");
    private int rollResult;
    private final Image diceRoll0Image;
    private final Image diceRoll1Image;
    private final int repositionXWhite = 400;
    private final int repositionXBlack = 0;
    private final int repositionY = 650; //+92
    private final List<ImageView> listOfDice = new ArrayList<>();

    public Dice(int diceCount, Image diceRoll0Image, Image diceRoll1Image) {
        this.diceCount = diceCount;
        rollButton.setTranslateX(430);
        rollButton.setTranslateY(330);
        testLabel.setTranslateX(400);
        testLabel.setTranslateY(40);
        this.diceRoll0Image = diceRoll0Image;
        this.diceRoll1Image = diceRoll1Image;
        for (int i = 0; i < diceCount; i++) {
            this.listOfDice.add(new ImageView(diceRoll0Image));
            this.listOfDice.get(i).setTranslateX(repositionXWhite);
            this.listOfDice.get(i).setTranslateY(repositionY - (100 * i));
        }
    }

    public void setOnAction(EventHandler<ActionEvent> value) {
        this.rollButton.setOnAction(value);
    }

    public void setTestLabel(String text) {
        testLabel.setText(text);
    }

    public void setRollResult(int rollResult) {
        this.rollResult = rollResult;
    }

    public int rollDice() {
        int result = 0;
        for (int i = 0; i < diceCount; i++) {
            if ((int) Math.round(Math.random() * 1) == 0) {
                result++;
                setDiceImage(i, diceRoll1Image);
            }
            else {
                setDiceImage(i, diceRoll0Image);
            }

        }
        return result;
    }

    public Button getRollButton() {
        return rollButton;
    }

    public int getRollResult() {
        return rollResult;
    }

    public void resetRollResult() {
        this.rollResult = 0;
    }

    public void reactivateButton() {
        rollButton.setDisable(false);
    }

    public void addToGrid(GridPane grid) {
        grid.add(rollButton, 0, 0);
        grid.add(testLabel, 0, 0);
        for (int i = 0; i < diceCount; i++) {
            this.listOfDice.add(new ImageView(diceRoll0Image));
            grid.add(listOfDice.get(i), 0, 0);
        }
    }

    public void setDiceImage(int index, Image image) {
        listOfDice.get(index).setImage(image);
    }

    public void resetDice(){
        for (int i = 0; i < diceCount; i++) {
            this.listOfDice.get(i).setImage(diceRoll0Image);
        }
    }

}
