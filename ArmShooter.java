package indy;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.util.ArrayList;
import static indy.Constants.*;

/**
 * This is the abstract ArmShooter class. It is the superclass for the NinjaMonkey and DartMonkey to
 * account for much of their shared functionality with animation and appearance. It contains instance
 * variables for monkey properties and animation, along with the DartOrganizer and the monkeys' shapes.
 */
abstract class ArmShooter extends Monkey {

    private DartOrganizer dartOrg;
    private Shape[] shapes;
    private Rectangle arm;
    private Circle hand;
    private Pane monkeyPane;
    private Node target;
    private double animationClock;
    private double xMove;
    private double yMove;
    private double baseX;
    private double baseY;
    private int sharpness;
    private int range;
    private int type;
    private boolean piercing;
    private boolean shooting;
    private boolean camo;

    /**
     * This is the ArmShooter class' Constructor. It calls the superclass' Constructor, passing in all
     * relevant parameters, and sets up the rest of the ArmShooter's unique instance variables. The
     * ArmShooter is uniquely associated with the DartOrganizer, and takes in arguments for the range,
     * dart-sharpness, and camo properties.
     */
    public ArmShooter(Pane myMonkeyPane, DoublyLinkedList bloons, DartOrganizer darts,
                      Pane myHitBoxPane, Pane myRangePane, ArrayList<Shape> myHitBoxes, int myRange,
                      ArrayList<Monkey> monkeys, int mySharpness, boolean canSeeCamo){
        super(bloons, myHitBoxPane, myRangePane, myHitBoxes, myRange, monkeys, canSeeCamo);

        this.dartOrg = darts;
        this.monkeyPane = myMonkeyPane;

        this.shooting = false;
        this.piercing = false;
        this.camo = canSeeCamo;

        this.range = myRange;
        this.sharpness = mySharpness;
        this.animationClock = 0;
        this.xMove = 0;
        this.yMove = 0;
        this.type = 0;

        this.target = null;
    }

    /**
     * This method is called from the constructors of the subclasses to factor out the majority of repeated
     * code in the visual design of the Dart and Ninja monkeys. It takes in variables corresponding to the
     * initial position of the monkey, the subclass' array of shapes, and the color scheme of the monkey.
     */
    public void setShapes(Shape[] shapeArray, int pos, Color inA, Color outA, Color inB, Color outB){
        this.shapes = shapeArray;

        // Create the shapes.
        this.shapes[LEFT_PUPIL] = new Circle(pos-EYE_OFFSET_X, pos-PUPIL_OFFSET_Y,
                PUPIL_RAD, Color.BLACK);
        this.shapes[RIGHT_PUPIL] = new Circle(pos+EYE_OFFSET_X, pos-PUPIL_OFFSET_Y,
                PUPIL_RAD, Color.BLACK);
        this.shapes[LEFT_GLINT] = new Circle(pos-EYE_OFFSET_X-1, pos-GLINT_OFFSET_Y,
                GLINT_RAD, Color.BLACK);
        this.shapes[RIGHT_GLINT] = new Circle(pos+EYE_OFFSET_X-1, pos-GLINT_OFFSET_Y,
                GLINT_RAD, Color.BLACK);
        this.shapes[LEFT_EYE] = new Ellipse(pos-EYE_OFFSET_X, pos-EYE_OFFSET_Y,
                EYE_RAD_X, EYE_RAD_Y);
        this.shapes[RIGHT_EYE] = new Ellipse(pos+EYE_OFFSET_X, pos-EYE_OFFSET_Y,
                EYE_RAD_X, EYE_RAD_Y);
        this.shapes[LEFT_EAR] = new Ellipse(pos-EAR_OFFSET_Y, pos, EAR_RAD_X, EAR_RAD_Y);
        this.shapes[RIGHT_EAR] = new Ellipse(pos+EAR_OFFSET_Y, pos, EAR_RAD_X, EAR_RAD_Y);
        this.shapes[BODY] = new Ellipse(pos, pos, HEAD_RAD_X, HEAD_RAD_Y);
        this.shapes[ARM] = new Rectangle(pos+ARM_X, pos-ARM_Y, ARM_WIDTH, ARM_LENGTH);
        this.shapes[HAND] = new Circle(pos+ARM_X+ARM_WIDTH/2, pos-ARM_Y, HAND_RAD);
        this.shapes[FACE] = new Rectangle(pos-EYE_OFFSET_X, pos-FACE_Y, EYE_OFFSET_X*2, FACE_HEIGHT);
        this.shapes[HEAD_BORDER] = new Ellipse(pos, pos, HEAD_RAD_X, HEAD_RAD_Y);

        // Set the arm and hand instance variables to the corresponding shapes in the array.
        this.arm = (Rectangle) this.shapes[ARM];
        this.hand = (Circle) this.shapes[HAND];

        // Tail coordinates are unique depending on the type of monkey.
        if (this.getMonkeyType() == 0){
            this.shapes[TAIL] = new Polygon(DM_TAIL_COORDS);
        }
        else {
            this.shapes[TAIL] = new Polygon(NM_TAIL_COORDS);
        }

        // Color the shapes depending on the color scheme passed into the method.
        this.colorShape(this.shapes[LEFT_EAR], inA, outA);
        this.colorShape(this.shapes[RIGHT_EAR], inA, outA);
        this.colorShape(this.shapes[HAND], inA, outA);
        this.colorShape(this.shapes[TAIL], inB, outB);
        this.colorShape(this.shapes[BODY], inB, outB);
        this.colorShape(this.shapes[ARM], inB, outB);
        this.colorShape(this.shapes[HEAD_BORDER], INVISIBLE, outB);
        this.colorShape(this.shapes[LEFT_EYE], Color.WHITE, BROWN_1);
        this.colorShape(this.shapes[RIGHT_EYE], Color.WHITE, BROWN_1);
        this.shapes[LEFT_GLINT].setFill(Color.WHITE);
        this.shapes[RIGHT_GLINT].setFill(Color.WHITE);
        this.shapes[FACE].setFill(BROWN_1);
    }

    /**
     * This helper method takes in a Shape and fill/border colors, and sets up the shape visually,
     * eliminating a large amount of repetitive code in the process.
     */
    public void colorShape(Shape shape, Color fill, Color border){
        shape.setFill(fill);
        shape.setStroke(border);
        shape.setStrokeWidth(MONKEY_BORDER_WIDTH);
    }

    /**
     * This method overrides the superclass' selectMe method, and if it returns true—meaning that the
     * monkey can be selected—it sets the border of the head, ears, and tail to black to indicate that
     * the monkey has been selected.
     */
    @Override
    public boolean selectMe(){
        boolean prev = super.selectMe();
        if (prev){
            this.shapes[HEAD_BORDER].setStroke(Color.BLACK);
            this.shapes[LEFT_EAR].setStroke(Color.BLACK);
            this.shapes[RIGHT_EAR].setStroke(Color.BLACK);
            this.shapes[TAIL].setStroke(Color.BLACK);
        }
        return prev;
    }

    /**
     * This method overrides the superclass' placeDown method, and if it returns true—meaning that the
     * monkey can be placed—it tracks the base location for the arm, which will be animated...
     */
    @Override
    public boolean placeDown(){
        boolean result = super.placeDown();
        if (result){
            this.baseX = this.arm.getTranslateX();
            this.baseY = this.arm.getTranslateY();
        }
        return result;
    }

    /**
     * An implementation of the abstract shoot method defined in the superclass... It calls the superclass'
     * aim method, and then if the monkey targets a Bloon, it creates a dart and begins an animation, which
     * moves the monkey's hand and arm forward in the direction of the Bloon and then back into its body.
     */
    @Override
    public void shoot(){

        // If the monkey is not in the act of shooting, re-assign the monkey's target Node...
        if (!this.shooting){
            this.target = super.aim();
        }
        // Otherwise, if the monkey is shooting, call the superclass' aim method and increment the timer.
        else {
            super.aim();
            this.animationClock += ARM_ANIMATION_INCREMENT;
        }

        // Find the x and y increments needed to move the monkey's arm in the correct direction...
        double angle = super.getRotate().getAngle() + QUART_CIRCLE;
        if (this.target != null){
            this.shooting = true;
            this.xMove = ARM_ANIMATION_MULT * Math.cos(angle * Math.PI / HALF_CIRCLE);
            this.yMove = ARM_ANIMATION_MULT * Math.sin(angle * Math.PI / HALF_CIRCLE);
        }

        // When the animation begins, create a dart at the position of the hand and continue the animation.
        if (this.animationClock > 0 && this.animationClock < ANIMATION_START){
            this.dartOrg.newDart(this.hand.getTranslateX() - this.range, this.hand.getTranslateY()
                            - this.range, angle - QUART_CIRCLE, this.range + DART_RANGE_INCREASE,
                            this.type, this.piercing, this.sharpness, this.camo, 0);
            this.animationClock = ANIMATION_START;
        }
        // While the clock is between ANIMATION_START and ANIMATION_MID, move the hand outward.
        else if (this.animationClock > ANIMATION_START && this.animationClock < ANIMATION_MID){
            this.moveArmHand(this.arm.getTranslateX(), this.arm.getTranslateY(),
                    this.hand.getTranslateX(), this.hand.getTranslateY(), -this.xMove, -this.yMove);
        }
        // While the clock is between ANIMATION_MID and ANIMATION_END, move the hand back inward.
        else if (this.animationClock > ANIMATION_MID && this.animationClock <= ANIMATION_END){
            this.moveArmHand(this.arm.getTranslateX(), this.arm.getTranslateY(),
                    this.hand.getTranslateX(), this.hand.getTranslateY(), this.xMove, this.yMove);
        }
        // When the animation ends, reset the position of the hand to account for any error and reset the clock.
        else if (this.animationClock > ANIMATION_END){
            this.animationClock = 0;
            this.moveArmHand(this.baseX, this.baseY, this.baseX, this.baseY, 0, 0);
            this.shooting = false;
        }
    }

    /**
     * This helper method eliminates repeated code in the movement of the monkey's hand and arm. Takes
     * in the base X and Y locations of the hand and arm, and the increments by which they should move.
     */
    private void moveArmHand(double armBaseX, double armBaseY, double handBaseX,
                             double handBaseY, double xInc, double yInc){
        this.arm.setTranslateX(armBaseX + xInc);
        this.arm.setTranslateY(armBaseY + yInc);
        this.hand.setTranslateX(handBaseX + xInc);
        this.hand.setTranslateY(handBaseY + yInc);
    }

    /**
     * This method overrides the superclass' method, in order to additionally remove the monkey's shapes.
     */
    @Override
    public void eraseGraphically(){
        super.eraseGraphically();
        for (Shape shape : this.shapes){
            this.monkeyPane.getChildren().remove(shape);
        }
    }

    /**
     * This method overrides the superclass' method, in order to additionally move the monkey's shapes.
     */
    @Override
    public void mouseFollow(MouseEvent a){
        super.mouseFollow(a);
        for (Shape shape : this.shapes){
            super.relocate(shape);
        }
    }

    /**
     * Mutator method to adjust the dart-type shot by the monkey, used to upgrade to targeting darts.
     */
    public void setDartType(int value){
        this.type = value;
    }

    /**
     * Mutator method to adjust the sharpness of the monkey's darts, used when upgrading sharpness.
     */
    public void setSharpness(int value){
        this.sharpness = value;
    }

    /**
     * Mutator method to set a monkey's darts to piercing, used when upgrading piercing.
     */
    public void upgradePiercing(){
        this.piercing = true;
    }
}