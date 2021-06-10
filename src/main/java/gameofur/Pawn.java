package gameofur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawn {
    private final Button pawnButton;
    private final String name;
    private int tilenumber = 0;
    private final Position position  = new Position();

    EventHandler<ActionEvent> event = e -> {
        move(500,500);
        System.out.println("moved");
    };

    public Pawn(Image pawnImage, String name) {
        this.name = name;
        this.pawnButton = new Button("", new ImageView(pawnImage));
        this.pawnButton.setStyle("-fx-background-color: transparent;");
        this.pawnButton.setOnAction(e -> {
            tilenumber++;
            if (tilenumber>=16) {
                tilenumber = 0;
            }
            move(position.getPosX(tilenumber),position.getPosY(tilenumber));
        });
        move(position.getPosX(tilenumber),position.getPosY(tilenumber));
    }

    public void move(double x, double y){
        this.pawnButton.setTranslateX(x);
        this.pawnButton.setTranslateY(y);
    }

    public Button getPawnButton() {
        return pawnButton;
    }

    public void setTilenumber(int tilenumber) {
        this.tilenumber = tilenumber;
    }
}
