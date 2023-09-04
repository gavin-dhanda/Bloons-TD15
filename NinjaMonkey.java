package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.util.ArrayList;
import static indy.Constants.*;

/**
 * This is the Ninja class. It deals mainly with the unique appearance and upgrade system of the
 * ninja monkeys, and is one of two subclassed of the abstract ArmShooter class.
 */
public class NinjaMonkey extends ArmShooter {

    private Shape[] shapes;
    private boolean[] upgradeStatus;

    /**
     * This is the NinjaMonkey class' Constructor. It calls the ArmShooter's Constructor, and sets up the
     * shapes array with the corresponding number of shapes, and calls the setShapes method in the superclass
     * and the setUpMonkey method below to fully create the visual shape of the ninja monkey.
     */
    public NinjaMonkey(Pane myMonkeyPane, DoublyLinkedList bloons, DartOrganizer darts,
                       Pane myHitBoxPane, Pane myRangePane, ArrayList<Shape> myHitBoxes, ArrayList<Monkey> monkeys){
        super(myMonkeyPane, bloons, darts, myHitBoxPane, myRangePane, myHitBoxes,
                NINJA_MONKEY_RANGE, monkeys, NM_SHARPNESS_START, true);

        this.shapes = new Shape[NM_SHAPES];
        this.upgradeStatus = new boolean[NUM_UPGRADES];

        super.setShapes(this.shapes, -NINJA_MONKEY_RANGE, BROWN_1, BROWN_2, RED_1, RED_2);
        this.setUpMonkey(myMonkeyPane, -NINJA_MONKEY_RANGE);
        super.setShotSpeed(NM_START_SPEED);
    }

    /**
     * This method creates the shapes for the stripe on the ninja monkey's tail and the tail-border, and
     * colors them appropriately. It then adds the superclass' rotation to all the ninja monkey's shapes and
     * adds the shapes to the monkeyPane with the correct layering to properly represent the monkey...
     */
    private void setUpMonkey(Pane pane, int pos){
        this.shapes[STRIPE] = new Polygon(STRIPE_COORDS);
        this.shapes[TAIL_BORDER] = new Polygon(NM_TAIL_COORDS);

        super.colorShape(this.shapes[TAIL_BORDER], INVISIBLE, INVISIBLE);
        this.shapes[STRIPE].setFill(Color.WHITE);

        for (Shape shape : this.shapes){
            shape.getTransforms().add(super.getRotate());
        }

        pane.getChildren().addAll(this.shapes[LEFT_EAR], this.shapes[RIGHT_EAR], this.shapes[ARM],
                this.shapes[HAND], this.shapes[TAIL], this.shapes[STRIPE], this.shapes[TAIL_BORDER],
                this.shapes[BODY], this.shapes[FACE], this.shapes[LEFT_EYE], this.shapes[RIGHT_EYE],
                this.shapes[LEFT_PUPIL], this.shapes[RIGHT_PUPIL], this.shapes[LEFT_GLINT],
                this.shapes[RIGHT_GLINT], this.shapes[HEAD_BORDER]);
    }

    /**
     * This method overrides the superclass' selectMe method, it sets the tail-border, which is unique
     * to the ninja monkey, to Black, so that it covers the small white stripe on the monkey's tail.
     */
    @Override
    public boolean selectMe(){
        if (super.selectMe()){
            this.shapes[TAIL_BORDER].setStroke(Color.BLACK);
        }
        return true;
    }

    /**
     * This method overrides the superclass' deselect method, and it returns the shapes back to normal.
     */
    @Override
    public void deselect(){
        super.deselect();
        this.shapes[HEAD_BORDER].setStroke(RED_2);
        this.shapes[TAIL_BORDER].setStroke(INVISIBLE);
        this.shapes[LEFT_EAR].setStroke(BROWN_2);
        this.shapes[RIGHT_EAR].setStroke(BROWN_2);
        this.shapes[TAIL].setStroke(RED_2);
    }

    /**
     * This accessor method returns the monkey's type so the UpgradePanel knows which monkey it represents.
     */
    @Override
    public int getMonkeyType(){
        return  NINJA_MONKEY_TYPE;
    }

    /**
     * Accessor method to get the array of boolean values that tracks which upgrades the monkey has bought.
     */
    @Override
    public boolean[] getUpgradeStatus() {
        return this.upgradeStatus;
    }

    /**
     * This method is called whenever the user clicks on an upgrade button to try and buy an upgrade. It
     * returns true if the user has enough cash to buy the upgrade, and if the upgrade has not already been
     * bought for the given monkey. In this case, it uses a switch statement to do whatever is necessary
     * to implement the upgrade that corresponds with the 'upgradeNum' passed in.
     */
    @Override
    public boolean upgrade(int upgradeNum, int cash, int cost){
        if (cash >= cost){
            if (!this.upgradeStatus[upgradeNum]){
                switch(upgradeNum){
                    case TOP_UPGRADE:
                        super.setDartType(TARGETING_DART);
                        break;
                    case MID_UPGRADE:
                        super.setSharpness(NM_SHARPNESS_NEW);
                        break;
                    default:
                        super.setShotSpeed(NM_NEW_SPEED);
                        break;
                }
                this.upgradeStatus[upgradeNum] = true;
                return true;
            }
        }
        return false;
    }
}