package indy;

import javafx.scene.layout.Pane;
import static indy.Constants.*;

/**
 * This is the BloonOrganizer class. It deals with the DoublyLinkedList of Nodes
 * (and consequently Bloons),and contains instance variables to track the specific Bloon
 * properties of a given level as the player moves through the game.
 */
public class BloonOrganizer {

    private DoublyLinkedList myBloons;
    private int[][][] bloonLevels;
    private MoneyTracker bank;
    private Pane andyPane;
    private Pane bloonPane;
    private Pane popPane;
    private double bloonTimer;
    private double bloonSpacing;
    private int lives;
    private int level;
    private int wave;
    private int bloonStrength;
    private int bloonCount;
    private boolean camo;
    private boolean inLevel;
    private boolean victory;

    /**
     * This is the BloonOrganizer class' Constructor. It is associated with the balloon, pop, and andy
     * panes, the DoublyLinkedList for the Bloons and the MonkeyTracker. It initializes all necessary
     * instance variables and calls the clear method to prepare for gameplay.
     */
    public BloonOrganizer(Pane balloonPane, Pane myPopPane, Pane myAndyPane,
                          DoublyLinkedList bloons, MoneyTracker myBank){
        this.bloonPane = balloonPane;
        this.popPane = myPopPane;
        this.andyPane = myAndyPane;
        this.myBloons = bloons;
        this.bloonLevels = LEVELS;
        this.bank = myBank;
        this.clear();
    }

    /**
     * This method is called when the game is started and then when it is subsequently restarted, and
     * it resets all relevant level-tracking variables and clears all major panes of their game objects.
     */
    public void clear(){
        this.bloonPane.getChildren().clear();
        this.andyPane.getChildren().clear();
        this.myBloons.clear();

        this.lives = STARTING_LIVES;
        this.level = STARTING_LEVEL;
        this.wave = 0;
        this.updateLevelProperties();

        this.inLevel = false;
        this.victory = false;
    }

    /**
     * This method is called from the timeline to move the tail bloon (which is the furthest along
     * the track). It then is recursively called for all Nodes in the DoublyLinkedList. It also calls
     * the moveForward method when necessary to re-order the linked list by distance traveled.
     */
    public void moveAllBloons(Node node){
        if (node != null) {

            // Move the bloon and check if it has finished the map.
            if (!node.getBloon().move()) {
                int strength = node.getBloon().getStrength();
                if (strength > LEAD_STRENGTH){
                    // Game Over if AndyBloon makes it through.
                    this.lives = 0;
                }
                else if (strength == LEAD_STRENGTH || strength == BLACK_STRENGTH){
                    // Lead and Black Bloons take away twice the number of lives.
                    this.lives -= strength * 2;
                }
                else{
                    this.lives -= node.getBloon().getStrength();
                }
                this.myBloons.removeLast();
            }

            // If there is a bloon ahead, check if the current bloon has passed it and swap them.
            if (node.getNext() != null){
                if (node.getBloon().getDistanceTraveled() >
                        node.getNext().getBloon().getDistanceTraveled()){
                    this.myBloons.moveForward(node);
                }
            }

            // If there is a bloon behind, recursively call this method to move it...
            if (node.getPrev() != null) {
                this.moveAllBloons(node.getPrev());
            }
        }
    }

    /**
     * This method is called from the timeline to add new Bloons to the screen while a level
     * is active. It adds Bloons to the linked list until a particular wave is complete, then
     * either moves onto the next wave or the next level of the game.
     */
    public void addBloons(){
        if (this.inLevel && this.level < this.bloonLevels.length && !this.victory){

            // Repeat this loop until all the bloons have been spawned for a given wave.
            if (this.bloonCount < this.bloonLevels[this.level][this.wave][COUNT_INDEX]){
                this.bloonTimer += BLOON_TIMER_INCREMENT;

                // Whenever the timer reaches the wave's spacing value, spawn a bloon.
                if (this.bloonTimer >= this.bloonSpacing){
                    this.bloonTimer = 0;
                    this.bloonCount++;

                    // If bloon strength is 0 then there is a pause between waves...
                    if (this.bloonStrength > 0){
                        this.myBloons.addFirst(new Node(
                                new Bloon(this.bloonPane, this.popPane, this.bloonStrength, this.camo, X_START,
                                        Y_START, 0, 0, this.bank, this.andyPane)));
                    }
                }
            }

            // At the end of a wave/level, increment the wave/level counter and update bloon properties.
            else if (this.wave < (this.bloonLevels[this.level].length - 1)){
                this.wave++;
                this.updateLevelProperties();
            } else if (this.wave == (this.bloonLevels[this.level].length - 1)){
                this.level++;
                if (this.level < this.bloonLevels.length){
                    this.wave = 0;
                    this.updateLevelProperties();
                    this.inLevel = false;
                }
                else {
                    this.victory = true;
                }
            }
        }
    }

    /**
     * This method is called when the game is restarted, when a wave ends, or when a level ends,
     * and it updates the properties of the Bloons for a particular wave.
     */
    private void updateLevelProperties(){
        this.bloonTimer = 0;
        this.bloonCount = 0;
        this.bloonSpacing = this.bloonLevels[this.level][this.wave][SPACING_INDEX];
        this.bloonStrength = this.bloonLevels[this.level][this.wave][STRENGTH_INDEX];

        // If the camo index == 1, the bloons are camo; if it's 0, they're normal.
        if (this.bloonLevels[this.level][this.wave][CAMO_INDEX] == 1){
            this.camo = true;
        }
        else {
            this.camo = false;
        }
    }

    /**
     * This method is called when a Bloon is popped that is supposed to split into two. It creates a
     * new Bloon with all the old Bloon's properties and adds it to the list. The new Bloon does not have
     * to be inserted in the middle of the list because it will be very quickly sorted as the Bloons move.
     */
    public void splitBloon(Node node, Dart dart){
        Bloon old = node.getBloon();
        Bloon newBloon = new Bloon(this.bloonPane, this.popPane,
                old.getStrength() - 1, old.getCamo(), old.getX(), old.getY(),
                old.getDistanceTraveled(), old.getCheckpoint(), this.bank, this.andyPane);
        this.myBloons.addFirst(new Node(newBloon));
        dart.getBloonsHit().add(newBloon);
    }

    /**
     * Mutator method to tell the BloonsGame that a new level has started.
     */
    public void setInLevel(boolean bool){
        this.inLevel = bool;
    }

    /**
     * Accessor method that tells whether a level is active.
     */
    public boolean getInLevel(){
        return this.inLevel;
    }

    /**
     * Accessor method that tells whether the game has been won.
     */
    public boolean getVictory(){
        return this.victory;
    }

    /**
     * Accessor method that returns the number of lives remaining.
     */
    public int getLives(){
        return this.lives;
    }

    /**
     * Accessor method that returns the level that the player is at.
     */
    public int getLevel() {
         return (this.level + 1);
    }
}