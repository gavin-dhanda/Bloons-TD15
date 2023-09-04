package indy;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import java.util.ArrayList;
import static indy.Constants.*;

/**
 * This is the abstract Monkey class. It is the superclass for all monkey types, and defines all of their
 * shared functionality, such as hit-box and range visuals, and Bloon targeting, among other things. It
 * contains instance variables for the DoublyLinkedList of bloons, along with the ArrayLists of the other
 * monkeys and their hit-boxes to check collision when placing, and a variety of doubles and booleans to
 * track the state of the monkey at any point.
 */
abstract class Monkey {

    private DoublyLinkedList myBloons;
    private ArrayList<Monkey> monkeys;
    private ArrayList<Shape> hitBoxes;
    private Ellipse hitBox;
    private Circle rangeCircle;
    private Rotate rotate;
    private Rotate hitBoxRotate;
    private Pane hitBoxPane;
    private Pane rangePane;
    private int range;
    private double x;
    private double y;
    private double xDiff;
    private double yDiff;
    private double shotClock;
    private double shotSpeed;
    private boolean selectable;
    private boolean onTrack;
    private boolean chosen;
    private boolean camo;

    /**
     * This is the Monkey class' Constructor. It is associated with the DoublyLinkedList, the
     * hit-box and range panes, and the ArrayLists for monkeys and hit-boxes, and it sets up all
     * instance variables, then calling the setUpMonkey method.
     */
    public Monkey(DoublyLinkedList bloons, Pane myHitBoxPane, Pane myRangePane,
                  ArrayList<Shape> myHitBoxes, int range, ArrayList<Monkey> myMonkeys,
                  boolean canSeeCamo){
        this.x = -range;
        this.y = -range;
        this.range = range;
        this.xDiff = 0;
        this.yDiff = 0;
        this.shotClock = 0;
        this.shotSpeed = 0;

        this.onTrack = false;
        this.chosen = false;
        this.camo = canSeeCamo;

        this.myBloons = bloons;
        this.monkeys = myMonkeys;
        this.hitBoxes = myHitBoxes;

        this.hitBoxPane = myHitBoxPane;
        this.rangePane = myRangePane;

        this.setUpMonkey();
    }

    /**
     * This is the Monkey class' Constructor. It is associated with the DoublyLinkedList, the
     * hit-box and range panes, and the ArrayLists for monkeys and hit-boxes, and it sets up all
     * instance variables, then calling the setUpMonkey method.
     */
    private void setUpMonkey(){
        this.rotate = new Rotate(0, this.x, this.y);
        this.rangeCircle = new Circle(this.x, this.y, this.range, RANGE_INDICATOR);
        this.hitBox = new Ellipse(this.x, this.y, SMALL_HITBOX_RAD, SMALL_HITBOX_RAD);
        this.hitBox.setFill(INVISIBLE);
        this.rangePane.getChildren().add(this.rangeCircle);
    }

    /**
     * This method is called from the subclasses in the abstract shoot method defined below. It aims
     * the monkey at the further Bloon along the track that is still within the monkey's range, and
     * returns the Node for the Bloon that it aims at, so that the subclasses know when/where to shoot.
     */
    public Node aim(){
        this.shotClock += SHOT_TIMER_INCREMENT;
        double[] dist;

        // When the shot clock reaches the shotSpeed value...
        if (this.myBloons.getTail() != null && this.shotClock >= this.shotSpeed){
            Node temp = this.myBloons.getTail();

            // Loop through the list of Bloons, starting from furthest along, until one is within range.
            do {
                dist = temp.getDist(this.x, this.y);
                if (temp.getPrev() != null) {temp = temp.getPrev();
                }
                else{
                    break;
                }
            } while (dist[0] > this.range);

            // If there is any Bloon within range and the monkey can target it, then aim at the bloon.
            if (dist[0] <= this.range){
                if (!temp.getBloon().getCamo() || this.camo){
                    this.hitBoxRotate.setAngle(dist[1] - QUART_CIRCLE);
                    this.rotate.setAngle(dist[1] - QUART_CIRCLE);
                    this.shotClock = 0;

                    // Return the targeted Bloon if there is one, otherwise return null...
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * This method is called when a monkey is being placed, and it tells the Monkey to set its location
     * to follow the position of the Mouse, assuming the mouse is somewhere on the game screen. If the
     * monkey is moved either onto the track or onto the hit-box of another monkey, the color of the
     * monkey's range circle is changed from a dark grey to a dark red to indicate that the monkey cannot
     * be placed in that location. It is overridden by the subclasses to move their additional shapes.
     */
    public void mouseFollow(MouseEvent a){
        this.xDiff = a.getX() - this.x;
        this.yDiff = a.getY() - this.y;

        this.x = a.getX();
        this.y = a.getY();
        a.consume();

        this.relocate(this.hitBox);
        this.relocate(this.rangeCircle);

        if (this.canPlace()){
            this.rangeCircle.setFill(RANGE_INDICATOR);
        }
        else {
            this.rangeCircle.setFill(RED_RANGE);
        }
    }

    /**
     * This helper method eliminates some repeated code in moving the monkey's shapes, and is called
     * both from the mouseFollow method above, and the overridden mouseFollow methods in the subclasses.
     */
    public void relocate(javafx.scene.Node shape){
        shape.setTranslateX(shape.getTranslateX() + this.xDiff);
        shape.setTranslateY(shape.getTranslateY() + this.yDiff);
    }

    /**
     * This method is called from the MonkeyOrganier, and is overridden in the subclasses. If
     * the monkey is able to be placed in its current location, then the hit-box is reset and given
     * mouse hover functionality, and the monkey is selected.
     */
    public boolean placeDown(){
        if (this.canPlace()){
            this.hitBox = new Ellipse(this.x, this.y, HIT_BOX_X, HIT_BOX_Y);
            this.hitBox.setFill(INVISIBLE);
            this.hitBoxRotate = new Rotate(0, this.x, this.y);
            this.hitBox.getTransforms().add(this.hitBoxRotate);
            this.hitBoxPane.getChildren().add(this.hitBox);

            this.hitBox.setOnMouseEntered((MouseEvent a) -> this.hoverFill());
            this.hitBox.setOnMouseExited((MouseEvent a) -> this.hoverEmpty());
            this.hitBox.setOnMouseClicked((MouseEvent a) -> this.selectMe());

            this.selectable = true;
            this.selectMe();
            return true;
        }
        return false;
    }

    /**
     * This helper method checks if the monkey is on the track and if it is intersecting any other monkeys,
     * and if neither of those conditions are true, then the monkey can be placed in its current location
     * so it returns true. It is called both from the placeDown and mouseFollow methods.
     */
    private boolean canPlace(){
        boolean intersecting = false;
        for (Shape shape : this.hitBoxes){
            if (shape.intersects(this.hitBox.getBoundsInParent())){
                intersecting = true;
            }
        }
        if (!intersecting && !this.onTrack){
            return true;
        }
        return false;
    }

    /**
     * This method is called when the mouse hovers over a monkey's hit-box. It checks if any monkey is
     * already selected, and if not, it shows the hovered monkey's range circle.
     */
    private void hoverFill(){
        if (this.selectable){
            boolean anyMonkeySelected = false;
            for (Monkey monkey : this.monkeys){
                if (monkey.getChosen()){
                    anyMonkeySelected = true;
                    break;
                }
            }
            if (!anyMonkeySelected){
                this.rangeCircle.setFill(RANGE_INDICATOR);
            }
        }
    }

    /**
     * This method is called when the mouse exits a monkey's hit-box, and it sets the fill to INVISIBLE
     * if the monkey is not already selected by the user.
     */
    private void hoverEmpty(){
        if (!this.chosen){
            this.rangeCircle.setFill(INVISIBLE);
        }
    }

    /**
     * This method is called from the MonkeyOrganizer when a new monkey (other than this one) is created,
     * and it either resets the size of the hit-box, or makes it smaller so that monkeys can be placed
     * closer together than would otherwise be possible.
     */
    public void setHitBox(boolean small){
        if (small){
            this.hitBox.setRadiusX(SMALL_HITBOX_RAD);
            this.hitBox.setRadiusY(SMALL_HITBOX_RAD);
        }
        else{
            this.hitBox.setRadiusX(HIT_BOX_X);
            this.hitBox.setRadiusY(HIT_BOX_Y);
        }
    }

    /**
     * This method is called when the hit-box of a monkey is clicked and another monkey is not currently
     * being placed down (when a new monkey is created, all other monkeys are no longer selectable). It
     * deselects any other monkeys, and it is overridden by the subclasses to put an outline/highlight the
     * monkey in some way.
     */
    public boolean selectMe(){
        if (this.selectable){
            for (Monkey monkey : this.monkeys){
                monkey.deselect();
            }
            this.rangeCircle.setFill(RANGE_INDICATOR);
            this.chosen = true;
            return true;
        }
        return false;
    }

    /**
     * This method is called when the user has selected the monkey but then clicks elsewhere on the map.
     * It hides the range circle, and is overridden by the subclasses to return the visual to its original
     * state (no longer highlighted).
     */
    public void deselect(){
        this.rangeCircle.setFill(INVISIBLE);
        this.chosen = false;
    }

    /**
     * This method is called when the user attempts to buy a new monkey while already in the process of
     * buying one. It graphically removes the hit-box and range circle and is overridden by the subclasses
     * to remove the rest of the monkey's visual components.
     */
    public void eraseGraphically(){
        this.hitBoxPane.getChildren().remove(this.hitBox);
        this.rangePane.getChildren().remove(this.rangeCircle);
    }

    /**
     * Mutator method called by the subclasses if they have a range upgrade, to increase the
     * range, and update the rangeCircle to reflect that change.
     */
    public void upgradeRange(){
        this.range += RANGE_UPGRADE;
        this.rangeCircle.setRadius(this.range);
    }

    /**
     * Mutator method called by the subclasses, used both to initialize the shot speed and then
     * to modify the shot speed if/when it is upgraded by the user.
     */
    public void setShotSpeed(double speed){
        this.shotSpeed = speed;
    }

    /**
     * Mutator method to tell the monkey that it cannot be selected when a new monkey is being placed.
     */
    public void setSelectable(boolean bool){
        this.selectable = bool;
    }

    /**
     * Mutator method to tell the monkey whether it is currently intersecting with the track.
     */
    public void setOnTrack(boolean bool){
        this.onTrack = bool;
    }

    /**
     * Accessor method to get the monkey's hit-box.
     */
    public Ellipse getHitBox(){
        return this.hitBox;
    }

    /**
     * Accessor method to get the monkey's current rotation.
     */
    public Rotate getRotate(){
        return this.rotate;
    }

    /**
     * Accessor method to get whether the monkey is currently selected.
     */
    public boolean getChosen(){
        return this.chosen;
    }

    /**
     * Abstract method to shoot darts towards the Bloon that the monkey is targeting.
     */
    abstract void shoot();

    /**
     * Abstract accessor method to get the monkey's type (Dart = 0, Ninja = 1, Bomber = 2).
     */
    abstract int getMonkeyType();

    /**
     * Abstract method called whenever the user attempts to upgrade the monkey.
     */
    abstract boolean upgrade(int upgradeNum, int cash, int cost);

    /**
     * Abstract accessor method to get whether a monkey has its specific upgrades.
     */
    abstract boolean[] getUpgradeStatus();
}