package indy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import static indy.Constants.*;

/**
 * This is the BombShooter class. It contains instance variables for monkey properties, along with
 * the DartOrganizer and ImageViews used to visually represent the monkey. This monkey was created last,
 * and I went with a different approach where I created the monkey in an outside software and used the
 * imported images. This was easier since the BombShooter has no animation (while the ArmShooters do),
 * and it reduced a lot of code required to set up the shapes for the other monkeys...
 */
public class BombShooter extends Monkey {

    private DartOrganizer dartOrg;
    private ImageView bomber;
    private ImageView selected;
    private Pane monkeyPane;
    private boolean[] upgradeStatus;
    private int range;
    private int boom;

    /**
     * This is the BombShooter class' Constructor. It calls the superclass' constructor and sets up the
     * instance variables unique to the BombShooter, and calls the setUpBomber method to visually set it up.
     */
    public BombShooter(Pane myMonkeyPane, DoublyLinkedList bloons, DartOrganizer darts, Pane myHitBoxPane,
                       Pane myRangePane, ArrayList<Shape> myHitBoxes, ArrayList<Monkey> monkeys){
        super(bloons, myHitBoxPane, myRangePane, myHitBoxes,
                BOMB_SHOOTER_RANGE, monkeys, false);

        this.dartOrg = darts;
        this.monkeyPane = myMonkeyPane;

        this.upgradeStatus = new boolean[NUM_UPGRADES];

        this.boom = START_EXPLOSION_RADIUS;
        this.range = BOMB_SHOOTER_RANGE;

        this.setUpBomber();
        super.setShotSpeed(START_BOMB_SPEED);
    }

    /**
     * This method is called from the Constructor, and it sets up the two images that represent the
     * BombShooter (one normal image and one for when the BombShooter is selected by the user).
     */
    private void setUpBomber(){
        this.bomber = new ImageView(new Image("indy/Bomber.png"));
        this.setUpGraphic(this.bomber);

        this.selected = new ImageView(new Image("indy/Selected.png"));
        this.setUpGraphic(this.selected);

        super.getRotate().setPivotX(super.getRotate().getPivotX() + BOMB_SHOOTER_RANGE + BOMB_SHOOTER_WIDTH/2);
        super.getRotate().setPivotY(super.getRotate().getPivotY() + BOMB_SHOOTER_RANGE + BOMB_SHOOTER_HEIGHT/2);

        this.monkeyPane.getChildren().add(this.bomber);
    }

    /**
     * This helper method is called from the above setUpBomber method to eliminate a bunch of
     * repetitive code used to set up the images.
     */
    private void setUpGraphic(ImageView image){
        image.setPreserveRatio(false);
        image.setSmooth(true);
        image.setCache(true);
        image.setFitWidth(BOMB_SHOOTER_WIDTH);
        image.setFitHeight(BOMB_SHOOTER_HEIGHT);
        image.setTranslateY(-BOMB_SHOOTER_RANGE - BOMB_SHOOTER_HEIGHT/2);
        image.setTranslateX(-BOMB_SHOOTER_RANGE - BOMB_SHOOTER_WIDTH/2);
        image.getTransforms().add(super.getRotate());
    }

    /**
     * This accessor method returns the monkey's type so the UpgradePanel knows which monkey it represents.
     */
    @Override
    public int getMonkeyType(){
        return BOMB_SHOOTER_TYPE;
    }

    /**
     * This method overrides the superclass' selectMe method, and if it returns true—meaning that the
     * monkey can be selected—it sets the image to the 'selected' image, which is slightly different from
     * the BombShooter's original image.
     */
    @Override
    public boolean selectMe(){
        boolean prev = super.selectMe();
        if (prev){
            this.monkeyPane.getChildren().removeAll(this.bomber, this.selected);
            this.monkeyPane.getChildren().add(this.selected);
        }
        return prev;
    }

    /**
     * This method overrides the superclass' deselect method, and it returns the image back to normal.
     */
    @Override
    public void deselect(){
        super.deselect();
        this.monkeyPane.getChildren().removeAll(this.bomber, this.selected);
        this.monkeyPane.getChildren().add(this.bomber);
    }

    /**
     * This method overrides the superclass' setHitBox method, since the BombShooter's hit-box is,
     * in both cases, larger than that of the ArmShooters...
     */
    @Override
    public void setHitBox(boolean small){
        if (small){
            super.getHitBox().setRadiusX(BS_HITBOX_SMALL);
            super.getHitBox().setRadiusY(BS_HITBOX_SMALL);
        }
        else{
            super.getHitBox().setRadiusX(BS_HITBOX_X);
            super.getHitBox().setRadiusY(BS_HITBOX_Y);
        }
    }

    /**
     * This method overrides the superclass' placeDown method, and if it returns true—meaning that the
     * monkey can be placed—it moves the selected image to the correct position and offsets the bomber's
     * hit-box so that it more accurately represents the 'body' of the monkey.
     */
    @Override
    public boolean placeDown(){
        boolean result = super.placeDown();
        if (result){
            super.getHitBox().setOnMouseClicked((MouseEvent a) -> this.selectMe());
            this.selected.setTranslateX(this.bomber.getTranslateX());
            this.selected.setTranslateY(this.bomber.getTranslateY());
            super.getHitBox().setCenterY(super.getHitBox().getCenterY() + BS_HITBOX_OFFSET);
        }
        return result;
    }

    /**
     * This method defines the abstract shoot method from the superclass. It calls the superclass' aim
     * method, and if it doesn't return null, then it shoots a dart in the direction that the superclass
     * is facing. The BombShooter does not have a shooting animation.
     */
    @Override
    public void shoot(){
        if (super.aim() != null){
            this.dartOrg.newDart(this.bomber.getTranslateX() + BOMB_SHOOTER_WIDTH/2,
                    this.bomber.getTranslateY() + BOMB_SHOOTER_HEIGHT/2, super.getRotate().getAngle(),
                    this.range + DART_RANGE_INCREASE, BOMB_SHOOTER_TYPE, false, 1,
                    false, this.boom);
        }
    }

    /**
     * This method overrides the superclass' method, in order to additionally remove the monkey's images.
     */
    @Override
    public void eraseGraphically(){
        super.eraseGraphically();
        this.monkeyPane.getChildren().removeAll(this.bomber, this.selected);
    }

    /**
     * This method overrides the superclass' method, in order to additionally move the monkey's image.
     */
    @Override
    public void mouseFollow(MouseEvent a){
        super.mouseFollow(a);
        super.relocate(this.bomber);
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
                        super.setShotSpeed(NEW_BOMB_SPEED);
                        break;
                    case MID_UPGRADE:
                        this.boom = NEW_EXPLOSION_RADIUS;
                        break;
                    case BOT_UPGRADE:
                        super.upgradeRange();
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