package gameofur;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Dice {
    private final int numberOfDice;
    private final Button rollButton = new Button("Roll");
    private final Label testLabel = new Label("Test");
    private int rollResult;

    public Dice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
        rollButton.setTranslateX(420);
        rollButton.setOnAction(e -> {
            rollResult = rollDice();
            testLabel.setText(Integer.toString(rollResult));
            rollButton.setDisable(true);
        });
        testLabel.setTranslateX(400);
        testLabel.setTranslateY(40);
    }

    public int rollDice() {
        int result = 0;
        for (int i = 0; i < numberOfDice; i++) {
            result += Math.round(Math.random() * 1);
        }
        return result;
    }

    public Button getRollButton() {
        return rollButton;
    }

    public Label getTestLabel() {
        return testLabel;
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
}
