package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import static indy.Constants.*;

/**
 * This is the Explosion class. It animates the Bomb Shooter's explosions with three overlapping
 * Polygons that rapidly expand and fade out. It contains an individual timeline for this animation.
 */
public class Explosion {

    private Polygon boom1;
    private Polygon boom2;
    private Polygon boom3;
    private Pane pane;
    private Timeline timeline;
    private double pct;

    /**
     * This is the Explosion class' Constructor. It takes in the dart pane and the DoublyLinkedList of
     * Bloons through association and calls methods to pop all bloons in the blast radius, set up the
     * explosion graphic, and set up the animation Timeline.
     */
    public Explosion (double x, double y, Pane myPane, DoublyLinkedList myBloons, int bigBoom){
        this.pane = myPane;

        this.popBloons(myBloons, x, y, bigBoom);
        this.setUpExplode(x, y);
        this.setUpTimeline(bigBoom*RANGE_MULTIPLIER);
    }

    /**
     * This method is called from the Constructor. It takes in a position for the center of the blast
     * radius. It then loops through the DoublyLinkedList of Bloons from last to first, checking at each
     * Node whether the Bloon is within poppable and within the blast radius. If so, the Bloon pops...
     */
    private void popBloons(DoublyLinkedList bloonList, double x, double y, int boomSize){
        if (bloonList.getSize() > 0){
            Node temp = bloonList.getTail();
            Node prev = null;
            do {
                if (prev != null){
                    temp = prev;
                }
                // Black Bloons are immune to explosive attacks.
                if (temp.getBloon().getStrength() != BLACK_STRENGTH){
                    // Remove the Bloon if the pop method returns true, meaning its strength is 0.
                    if (temp.getDist(x, y)[0] <= boomSize && temp.getBloon().pop(false)){
                        bloonList.remove(temp);
                    }
                }
                prev = temp.getPrev();
            }
            while (prev != null);
        }
    }

    /**
     * This method is called from the Constructor. It instantiates the boom Polygons and calls the
     * setUpBoom helped method below to set up their stylistic properties.
     */
    private void setUpExplode(double x, double y){
        this.boom1 = new Polygon(POP_SHAPES);
        this.setUpBoom(this.boom1, x, y);

        this.boom2 = new Polygon(POP_SHAPES);
        this.setUpBoom(this.boom2, x, y);

        this.boom3 = new Polygon(POP_SHAPES);
        this.setUpBoom(this.boom3, x, y);
    }

    /**
     * This helper method is called from the above setUpExplode method. It eliminates repetitive code
     * in the set-up of the three boom Polygons, which are all identical in the underlying shape.
     */
    private void setUpBoom(Polygon boom, double x, double y){
        boom.setTranslateY(y);
        boom.setTranslateX(x);
        boom.setScaleX(0);
        boom.setScaleY(0);
        boom.setRotate(Math.random()*CIRCLE);
        this.pane.getChildren().add(boom);
    }

    /**
     * This method is called from the Constructor, and it sets up the animation Timeline with the
     * appropriate KeyFrame speed defined in the Constants class. It then starts the Timeline.
     */
    private void setUpTimeline(double boomSize){
        KeyFrame frame = new KeyFrame(Duration.millis(POP_TIMELINE),
                (ActionEvent e) -> this.grow(boomSize));
        this.timeline = new Timeline(frame);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * This method is called from the Timeline, and it increments an instance variable that controls
     * the scale and opacity of the three booms. Once the timer variable (pct) reaches a certain
     * predefined threshold, the timeline is stopped and the boom Polygons are removed from the pane.
     */
    private void grow(double boomSize){
        this.pct += EXPLODE_INCREMENT;
        if (this.pct <= (EXPLODE_SIZE*boomSize)) {
            this.growFade(boomSize);
        }
        else {
            this.timeline.stop();
            this.pane.getChildren().removeAll(this.boom1, this.boom2, this.boom3);
        }
    }

    /**
     * This method is called from the above grow method, and it adjusts the scale and color of the
     * three layered boom Polygons, such that they nearly fade out completely before being removed.
     */
    private void growFade(double boomSize){

        this.boom1.setScaleX(YELLOW_X_SCALE * this.pct);
        this.boom1.setScaleY(YELLOW_Y_SCALE * this.pct);
        this.boom1.setFill(new Color(YELLOW_COLOR_VALUES[0], YELLOW_COLOR_VALUES[1], YELLOW_COLOR_VALUES[2],
                1 - EXPLODE_SCALAR*(this.pct/(EXPLODE_SIZE*boomSize))));

        this.boom2.setScaleX(ORANGE_X_SCALE * this.pct);
        this.boom2.setScaleY(ORANGE_Y_SCALE * this.pct);
        this.boom2.setFill(new Color(ORANGE_COLOR_VALUES[0], ORANGE_COLOR_VALUES[1], ORANGE_COLOR_VALUES[2],
                1 - EXPLODE_SCALAR*(this.pct/(EXPLODE_SIZE*boomSize))));

        this.boom3.setScaleX(RED_X_SCALE * this.pct);
        this.boom3.setScaleY(RED_Y_SCALE * this.pct);
        this.boom3.setFill(new Color(RED_COLOR_VALUES[0], RED_COLOR_VALUES[1], RED_COLOR_VALUES[2],
                1 - EXPLODE_SCALAR*(this.pct/(EXPLODE_SIZE*boomSize))));
    }
}