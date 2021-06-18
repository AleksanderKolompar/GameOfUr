package gameofur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Dice {
    private final int diceCount;
    private final Button rollButton = new Button("Roll");
    private final Label testLabel = new Label("Test");
    private int rollResult;

    public Dice(int diceCount) {
        this.diceCount = diceCount;
        rollButton.setTranslateX(420);
        testLabel.setTranslateX(400);
        testLabel.setTranslateY(40);
    }

    public void setOnAction(EventHandler<ActionEvent> value) {
        this.rollButton.setOnAction(value);
    }

    public void setTestLabel(String text){
        testLabel.setText(text);
    }

    public void setRollResult(int rollResult) {
        this.rollResult = rollResult;
    }

    public int rollDice() {
        int result = 0;
        for (int i = 0; i < diceCount; i++) {
            result += Math.round(Math.random() * 1);
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

    public void addToGrid(GridPane grid){
        grid.add(rollButton, 0, 0);
        grid.add(testLabel, 0, 0);
    }


}
