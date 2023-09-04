package indy;

import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import java.util.ArrayList;
import static indy.Constants.*;

/**
 * This is the DartMonkey class. It deals mainly with the unique appearance and upgrade system of the
 * dart monkeys, and is one of two subclassed of the abstract ArmShooter class.
 */
public class DartMonkey extends ArmShooter {

    private Shape[] shapes;
    private boolean[] upgradeStatus;

    /**
     * This is the DartMonkey class' Constructor. It calls the ArmShooter's Constructor, and sets up the
     * shapes array with the corresponding number of shapes, and calls the setShapes method in the superclass
     * and the setUpMonkey method below to fully create the visual shape of the dart monkey.
     */
    public DartMonkey(Pane myMonkeyPane, DoublyLinkedList bloons, DartOrganizer darts,
                      Pane myHitBoxPane, Pane myRangePane, ArrayList<Shape> myHitBoxes,
                      ArrayList<Monkey> monkeys){
        super(myMonkeyPane, bloons, darts, myHitBoxPane, myRangePane, myHitBoxes,
                DART_MONKEY_RANGE, monkeys, DM_SHARPNESS, false);

        this.shapes = new Shape[NUM_SHAPES];
        this.upgradeStatus = new boolean[NUM_UPGRADES];

        super.setShapes(this.shapes, -DART_MONKEY_RANGE, BROWN_2, BROWN_3, BROWN_2, BROWN_3);
        this.setUpMonkey(myMonkeyPane);
        super.setShotSpeed(DM_START_SPEED);
    }

    /**
     * This method adds the superclass' rotation to all the dart monkey's shapes, and then adds
     * the shapes to the monkeyPane with the correct layering to properly represent the monkey...
     */
    private void setUpMonkey(Pane pane){
        for (Shape shape : this.shapes){
            shape.getTransforms().add(super.getRotate());
        }

        pane.getChildren().addAll(this.shapes[LEFT_EAR], this.shapes[ARM], this.shapes[HAND],
                this.shapes[RIGHT_EAR], this.shapes[TAIL], this.shapes[BODY], this.shapes[FACE],
                this.shapes[LEFT_EYE], this.shapes[RIGHT_EYE], this.shapes[LEFT_PUPIL], this.shapes[RIGHT_PUPIL],
                this.shapes[LEFT_GLINT], this.shapes[RIGHT_GLINT], this.shapes[HEAD_BORDER]);
    }

    /**
     * This method overrides the superclass' deselect method, and it returns the shapes back to normal.
     */
    @Override
    public void deselect(){
        super.deselect();
        this.shapes[HEAD_BORDER].setStroke(BROWN_3);
        this.shapes[LEFT_EAR].setStroke(BROWN_3);
        this.shapes[RIGHT_EAR].setStroke(BROWN_3);
        this.shapes[TAIL].setStroke(BROWN_3);
    }

    /**
     * This accessor method returns the monkey's type so the UpgradePanel knows which monkey it represents.
     */
    @Override
    public int getMonkeyType(){
        return  DART_MONKEY_TYPE;
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
                        super.upgradeRange();
                        break;
                    case MID_UPGRADE:
                        super.upgradePiercing();
                        break;
                    case BOT_UPGRADE:
                        super.setShotSpeed(DM_NEW_SPEED);
                        break;
                    default:
                        break;
                }
                this.upgradeStatus[upgradeNum] = true;
                return true;
            }
        }
        return false;
    }
}