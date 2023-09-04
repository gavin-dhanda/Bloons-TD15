package indy;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import static indy.Constants.*;

/**
 * This is the MonkeyOrganizer class. It deals with monkey selection, monkey placing, etc. and contains an
 * ArrayList which stores all the monkeys on the screen at any point. It has instance variables to track the
 * darts and bloons, along with the monkey hitBoxes and the MoneyTracker...
 */
public class MonkeyOrganizer {

    private ArrayList<Monkey> myMonkeys;
    private ArrayList<Shape> hitBoxes;
    private DoublyLinkedList bloonsList;
    private DartOrganizer dartOrg;
    private UpgradePanel upgradePanel;
    private Monkey selectedMonkey;
    private MoneyTracker bank;
    private BorderPane gameRoot;
    private BorderPane rightPane;
    private Pane monkeyPane;
    private Pane hitBoxPane;
    private Pane rangePane;
    private Pane primary;

    /**
     * This is the MonkeyOrganizer class' Constructor. It is associated with the DoublyLinkedList of bloons,
     * the root and right side BorderPanes, the MoneyTracker, the DartOrganizer, and a variety of layered Panes.
     */
    public MonkeyOrganizer (DoublyLinkedList bloons, Pane myMonkeyPane, Pane myHitBoxPane,
                            Pane myPrimaryPane, Pane myRangePane, BorderPane myRoot, BorderPane myRightPane,
                            MoneyTracker myBank, DartOrganizer myDartOrg){
        this.myMonkeys = new ArrayList<>();
        this.hitBoxes = new ArrayList<>();
        this.selectedMonkey = null;
        this.bank = myBank;
        this.bloonsList = bloons;
        this.gameRoot = myRoot;
        this.rightPane = myRightPane;

        this.monkeyPane = myMonkeyPane;
        this.hitBoxPane = myHitBoxPane;
        this.rangePane = myRangePane;
        this.primary = myPrimaryPane;

        this.dartOrg = myDartOrg;
        this.upgradePanel = new UpgradePanel(this.gameRoot, this.rightPane, this.bank);
    }

    /**
     * This method is called when the game is initially started and again whenever the user returns to
     * the home screen to restart the game. It clears the game screen graphically and logically of all
     * elements contained within or related to the MonkeyOrganizer, and it sets up the track.
     */
    public void clear(){
        this.primary.setOnMouseMoved(null);
        this.primary.setOnMouseClicked(null);
        this.selectedMonkey = null;
        this.bloonsList.clear();
        this.hitBoxes.clear();
        this.myMonkeys.clear();
        this.hitBoxPane.getChildren().clear();
        this.rangePane.getChildren().clear();
        this.monkeyPane.getChildren().clear();
        this.addTrack();
    }

    /**
     * This method is called from the clear method above. It adds the shapes that are defined as the track
     * in the constants to the hitBoxPane so that any monkeys cannot be placed on the track. It also slightly
     * modifies two of the ellipses which must be rotated, and sets the shapes to the 'INVISIBLE' color.
     */
    private void addTrack(){
        Shape[] shapes = MAP_SHAPES;
        shapes[ELLIPSE_A].setRotate(ELLIPSE_A_DEG);
        shapes[ELLIPSE_B].setRotate(ELLIPSE_B_DEG);

        for (Shape shape : shapes){
            shape.setFill(INVISIBLE);
            this.hitBoxPane.getChildren().add(shape);
            shape.setOnMouseEntered((MouseEvent a) -> this.cantPlace(true));
            shape.setOnMouseExited((MouseEvent a) -> this.cantPlace(false));
        }
    }

    /**
     * This method is called when the mouse is moved in or out of the track, and—if the user is trying to
     * place a monkey—it tells the monkey that it cannot be placed, as it is currently on the track.
     */
    private void cantPlace(boolean bool){
        if (this.selectedMonkey != null){
            this.selectedMonkey.setOnTrack(bool);
        }
    }

    /**
     * This method is called from the Timeline in the BloonsGame class, and it calls the shoot
     * method for each Monkey in the myMonkeys ArrayList.
     */
    public void aimAndShoot(){
        for (Monkey monkey : this.myMonkeys){
            monkey.shoot();
        }
    }

    /**
     * This method is called whenever the user attempts to buy a new monkey. If the user is already placing
     * a monkey, then that monkey is removed, but if there is no monkey already selected and the user has
     * enough cash to buy the monkey, then a monkey—that corresponds to the cost—is created. The monkey is
     * then set to follow the mouse until placed down or removed by the user.
     */
    public void newMonkey(int cost){
        if (this.selectedMonkey == null && this.bank.getCash() >= cost) {

            // Unselect all other monkeys.
            for (Monkey monkey : this.myMonkeys) {
                monkey.setSelectable(false);
                monkey.setHitBox(true);
                monkey.deselect();
            }

            // Use factory pattern with the 'cost' passed in to determine which monkey to create.
            switch (cost){
                case DART_MONKEY_COST:
                    this.selectedMonkey = new DartMonkey(this.monkeyPane, this.bloonsList, this.dartOrg,
                            this.hitBoxPane, this.rangePane, this.hitBoxes, this.myMonkeys);
                    break;
                case NINJA_MONKEY_COST:
                    this.selectedMonkey = new NinjaMonkey(this.monkeyPane, this.bloonsList, this.dartOrg,
                            this.hitBoxPane, this.rangePane, this.hitBoxes, this.myMonkeys);
                    break;
                case BOMB_SHOOTER_COST:
                    this.selectedMonkey = new BombShooter(this.monkeyPane, this.bloonsList, this.dartOrg,
                            this.hitBoxPane, this.rangePane, this.hitBoxes, this.myMonkeys);
                    break;
                default:
                    break;
            }

            // The new monkey will follow the mouse while selected.
            this.primary.setOnMouseMoved((MouseEvent a) ->
                    this.selectedMonkey.mouseFollow(a));
            this.primary.setOnMouseClicked((MouseEvent a) ->
                    this.placeMonkey(cost));
        }

        // Remove old monkey if one is already selected.
        else if (this.selectedMonkey != null){
            this.selectedMonkey.eraseGraphically();
            this.removePlacingMonkey();
        }
        else {
            // Un-select other monkeys if necessary.
            for (Monkey monkey : this.myMonkeys){
                monkey.deselect();
            }
        }
    }

    /**
     * This method is called whenever a monkey's hitBox is clicked, and it sets the UpgradePanel to that
     * monkey and shows the UpgradePanel in place of the monkey shop on the right side of the screen.
     */
    public void showUpgrade(){
        this.gameRoot.setRight(null);
        this.gameRoot.setRight(this.upgradePanel.getRoot());

        Monkey temp = null;
        for (Monkey monkey : this.myMonkeys){
            if (monkey.getChosen()){
                temp = monkey;
            }
        }
        if (temp != null){
            this.upgradePanel.setMonkey(temp);
        }
    }

    /**
     * This method is called whenever the user clicks the mouse while a new monkey is being created.
     * It checks if the monkey is able to be placed, and if so, it adds it to the ArrayList, takes away
     * its cost from the total cash, and shows the new monkey's upgrade screen.
     */
    private void placeMonkey(int cost){
        if (this.selectedMonkey.placeDown()){
            this.myMonkeys.add(this.selectedMonkey);
            this.hitBoxes.add(this.selectedMonkey.getHitBox());
            this.bank.modifyCash(-cost);
            this.removePlacingMonkey();
            this.showUpgrade();
        }
    }

    /**
     * This method is called when the user places down a new monkey, or when the user attempts to buy a
     * new monkey while another monkey is already being created. It resets the mouse functionality of the
     * primary pane and allows other monkeys to be selected once again.
     */
    private void removePlacingMonkey(){
        this.primary.setOnMouseMoved(null);
        this.primary.setOnMouseClicked((MouseEvent a) -> this.removeSelection(a));
        this.selectedMonkey = null;
        for (Monkey monkey : this.myMonkeys){
            monkey.setSelectable(true);
            monkey.setHitBox(false);
        }
    }

    /**
     * This method is called when the user is not attempting to create a new monkey, and the screen is
     * clicked. It shows the monkey shop, and deselects all monkeys. However, if the mouse was clicked
     * on any particular monkey, then that monkey is reselected and the upgrade panel is shown again.
     */
    private void removeSelection(MouseEvent a){
        // Removes the upgrade panel from the screen.
        this.gameRoot.setRight(null);
        this.gameRoot.setRight(this.rightPane);

        boolean removeMe = true;
        for (Monkey monkey : this.myMonkeys){
            if (monkey.getChosen()){
                removeMe = false;
                break;
            }
        }
        if (!removeMe){
            for (Monkey monkey : this.myMonkeys){
                monkey.deselect();
                if (monkey.getHitBox().intersects(a.getX(), a.getY(), 0, 0)){
                    monkey.selectMe();
                    this.showUpgrade();
                }
            }
        }
    }
}