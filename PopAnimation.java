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
 * This is the PopAnimation class. It is used whenever a bloon is popped, and it creates a short visual
 * pop animation at the bloon's location before disappearing.
 */
public class PopAnimation {

    private Polygon pop;
    private Timeline timeline;
    private Pane popPane;
    private int growths;

    /**
     * This is the PopAnimation class' Constructor. It is associated with the pop pane and takes
     * in the x and y coordinates for the bloon that was just popped. It calls methods to set up
     * the pop Polygon and the Timeline.
     */
    public PopAnimation(double x, double y, Pane myPopPane){
        this.growths = 0;
        this.popPane = myPopPane;

        this.setUpPop(x, y);
        this.setUpTimeline();
    }

    /**
     * This method is called from the Constructor. It sets up the pop Polygon in the correct
     * position and color, and adds it to the pop pane.
     */
    private void setUpPop(double x, double y){
        this.pop = new Polygon(POP_SHAPES);
        this.pop.setTranslateY(y);
        this.pop.setTranslateX(x);
        this.pop.setFill(Color.WHITE);
        this.popPane.getChildren().add(this.pop);
    }

    /**
     * This method is called from the Constructor. It sets up the pop animation's Timeline
     * with its corresponding KeyFrame speed and starts the Timeline.
     */
    private void setUpTimeline(){
        KeyFrame frame = new KeyFrame(Duration.millis(POP_TIMELINE),
                (ActionEvent e) -> this.grow());
        this.timeline = new Timeline(frame);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * This method is called from the Timeline above. It increases the scale of the pop animation a set
     * number of times, simultaneously decreasing the opacity of the pop Polygon. Once it has reached the
     * intended size and disappeared visually, the timeline is stopped and the Polygon is removed.
     */
    private void grow(){
        this.growths++;

        this.pop.setScaleX(this.pop.getScaleX() + POP_X_SCALE);
        this.pop.setScaleY(this.pop.getScaleY() + POP_Y_SCALE);
        this.pop.setFill(new Color(1, 1, 1,
                1 - ((double) this.growths)/NUM_GROWTHS));

        if (this.growths > (NUM_GROWTHS - 1)){
            this.timeline.stop();
            this.popPane.getChildren().remove(this.pop);
        }
    }
}