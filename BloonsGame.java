package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import static indy.Constants.*;

/**
 * This is the BloonsGame class. It contains a variety of instance variables to track game states,
 * along with the MonkeyOrganizer and the BloonOrganizer. It deals with all pause/play and timeline
 * functionalities, as well as restarts, win & loss screen visuals, and other general game logic.
 */
public class BloonsGame {

    private BloonOrganizer bloonOrg;
    private MonkeyOrganizer monkeyOrg;
    private DartOrganizer dartOrg;
    private MoneyTracker bank;
    private DoublyLinkedList bloons;
    private Timeline timeline;
    private KeyFrame fast;
    private KeyFrame slow;
    private BorderPane root;
    private Pane loss;
    private Pane win;
    private Text playPause;
    private Label livesText;
    private Label levelText;
    private boolean paused;
    private boolean speedy;

    /**
     * This is the BloonsGame class' Constructor. It takes in the Lives, Cash, and Level labels; the
     * root, loss, win, primary, and rightSide panes; and the playPause text through association. It
     * sets up the timeline and MoneyTracker, and calls the setUpPanes method.
     */
    public BloonsGame(Pane primary, Label myLives, Label myCash, Label myLevel,
                      BorderPane myRoot, Pane myLoss, Pane myWin, Text myPlayPause, BorderPane rightSide){
        this.livesText = myLives;
        this.levelText = myLevel;
        this.playPause = myPlayPause;

        this.paused = true;
        this.speedy = false;

        this.root = myRoot;
        this.loss = myLoss;
        this.win = myWin;

        this.setUpTimeline();
        this.bank = new MoneyTracker(myCash);
        this.setUpPanes(primary, rightSide);
    }

    /**
     * This method is called from the Constructor, and it layers a variety of Panes to logically set up the
     * structure on which the visual aspects will be displayed. It then creates a DoublyLinkedList for the
     * bloons and instantiates the BloonOrganizer and MonkeyOrganizer, passing in all relevant arguments...
     */
    private void setUpPanes(Pane primaryPane, BorderPane rightSide){
        Pane dartPane = new Pane();
        Pane bloonPane = new Pane();
        Pane popPane = new Pane();
        Pane rangePane = new Pane();
        Pane monkeyPane = new Pane();
        Pane hitBoxPane = new Pane();
        Pane andyPane = new Pane();
        primaryPane.getChildren().addAll(dartPane, bloonPane, popPane,
                rangePane, monkeyPane, andyPane, hitBoxPane);

        this.bloons = new DoublyLinkedList();
        this.bloonOrg = new BloonOrganizer(bloonPane, popPane, andyPane, this.bloons, this.bank);
        this.dartOrg = new DartOrganizer(dartPane, this.bloons, this.bloonOrg);
        this.monkeyOrg = new MonkeyOrganizer(this.bloons, monkeyPane, hitBoxPane,
                primaryPane, rangePane, this.root, rightSide, this.bank, this.dartOrg);
    }

    /**
     * This method is called from the Constructor, and it sets up the timeline, including both the slow
     * and fast KeyFrames, which will be switched in and out as the user speeds-up / slows-down the game.
     */
    private void setUpTimeline(){
        this.slow = new KeyFrame(Duration.millis(SLOW_TIMELINE),
                (ActionEvent e) -> this.updatePositions());
        this.fast = new KeyFrame(Duration.millis(FAST_TIMELINE),
                (ActionEvent e) -> this.updatePositions());
        this.timeline = new Timeline(this.slow);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * This method is called repeated from the timeline. It moves all bloons, adds any new bloons,
     * checks for a game over, victory, or new level, and updates the labels in the top-left.
     */
    private void updatePositions(){
        this.bloonOrg.moveAllBloons(this.bloons.getTail());
        this.bloonOrg.addBloons();

        // Update lives and aim/shoot at bloons.
        this.livesText.setText("Lives  " + this.bloonOrg.getLives());
        this.monkeyOrg.aimAndShoot();
        this.dartOrg.moveDarts();

        // Check both win and loss conditions...
        if (this.bloonOrg.getLives() <= 0){
            this.livesText.setText("Lives  0");
            this.gameOver();
        }
        else if (this.bloonOrg.getVictory() && this.bloons.getSize() < 1){
            this.win();
        }

        // Check if the level is over.
        if (this.bloons.getSize() < 1 && !this.bloonOrg.getInLevel()){
            this.newLevel();
        }
    }

    /**
     * This method is called from the updatePositions method above whenever a level ends. It stops
     * the timeline and makes necessary adjustments in preparation for the next level.
     */
    private void newLevel(){
        this.timeline.stop();
        this.dartOrg.clear();
        this.playPause.setText("Play");
        this.bank.modifyCash(LEVEL_CASH);
        this.paused = true;
    }

    /**
     * This method is called from the updatePositions method above whenever lives drop to 0,
     * and it simply stops the timeline and displays the loss pane (passed in from PaneOrganizer).
     */
    private void gameOver(){
        this.timeline.stop();
        this.root.getChildren().add(this.loss);
    }

    /**
     * This method is called from the updatePositions method when the final level is over,
     * and it simply stops the timeline and displays the win pane (passed in from PaneOrganizer).
     */
    private void win(){
        this.timeline.stop();
        this.root.getChildren().add(this.win);
    }

    /**
     * This method is called when the play button is pressed. If a level is not active, it starts
     * the next level. If a level is active, it either speeds up or slows down the timeline...
     */
    public void play(){
        if (this.paused){

            // START THE GAME IF ALREADY PAUSED
            this.timeline.play();
            this.bloonOrg.setInLevel(true);
            this.levelText.setText("Level  " + this.bloonOrg.getLevel() + "/30");
            this.paused = false;
            if (this.speedy){
                this.playPause.setText("Slow");
            }
            else{
                this.playPause.setText("Fast");
            }
        }
        else if (!this.speedy){

            // IF NOT PAUSED AND ALREADY SLOW, MAKE IT FAST
            this.timeline.stop();
            this.timeline = new Timeline(this.fast);
            this.timeline.setCycleCount(Animation.INDEFINITE);
            this.timeline.play();
            this.playPause.setText("Slow");
            this.speedy = true;
        }
        else {

            // IF NOT PAUSED AND ALREADY FAST, MAKE IT SLOW
            this.timeline.stop();
            this.timeline = new Timeline(this.slow);
            this.timeline.setCycleCount(Animation.INDEFINITE);
            this.timeline.play();
            this.playPause.setText("Fast");
            this.speedy = false;
        }
    }

    /**
     * This method is called when the game is first started and then again whenever it is restarted.
     * It stops the timeline, clears the screen graphically and logically of all monkeys, darts, and
     * bloons, and resets the cash and all the labels in the upper-left corner to default.
     */
    public void restart(){
        this.timeline.stop();
        this.timeline = new Timeline(this.slow);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.speedy = false;
        this.paused = true;

        this.monkeyOrg.clear();
        this.bloonOrg.clear();
        this.dartOrg.clear();

        this.root.getChildren().remove(this.loss);
        this.root.getChildren().remove(this.win);

        this.bank.resetCash();
        this.livesText.setText("Lives  " + this.bloonOrg.getLives());
        this.levelText.setText("Level  " + this.bloonOrg.getLevel() + "/30");
    }

    /**
     * This method is called when any of the monkey icons are clicked in the shop, and it calls
     * the MonkeyOrganizer's newMonkey method, passing in the cost of the specific monkey.
     */
    public void newMonkey(int cost){
        this.monkeyOrg.newMonkey(cost);
    }
}