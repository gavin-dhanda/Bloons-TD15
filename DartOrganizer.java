package indy;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import static indy.Constants.*;

/**
 * This is the DartOrganizer class, which inherits from the Dart class. It contains all the Program's
 * darts in an ArrayList, and deals with adding/removing darts, as well as moving darts and collision
 * checking with the Bloons. It contains this program's major algorithm—the targeting dart...
 */
public class DartOrganizer {

    private Pane dartPane;
    private ArrayList<Dart> myDarts;
    private DoublyLinkedList myBloons;
    private BloonOrganizer bloonOrg;

    /**
     * This is the DartOrganizer class' Constructor. The DartOrganizer is associated with the dart
     * pane, the DoublyLinkedList, and the BloonOrganizer, and this method initializes these instance
     * variables along with the ArrayList of darts.
     */
    public DartOrganizer(Pane pane, DoublyLinkedList bloons, BloonOrganizer bloonOrganizer){
        this.dartPane = pane;
        this.myDarts = new ArrayList<>();
        this.bloonOrg = bloonOrganizer;
        this.myBloons = bloons;
    }

    /**
     * This method is called at the start of the game and whenever the game is restarted. It graphically
     * and logically clears the screen or all remaining darts.
     */
    public void clear(){
        this.myDarts.clear();
        this.dartPane.getChildren().clear();
    }

    /**
     * This method is called from the different Monkey classes when they shoot at Bloons. It uses
     * Factory Pattern to generate different types of darts (normal, targeting, cannonball), and
     * takes in a variety of properties that apply to each dart...
     */
    public void newDart(double x, double y, double angle, int range, int type,
                        boolean piercing, int sharpness, boolean camo, int bigBoom){
        switch (type){
            case NORMAL_DART:
                this.myDarts.add(new Dart(this.dartPane, angle, x, y, range,
                        false, sharpness, new ArrayList<>(), piercing, camo));
                break;
            case TARGETING_DART:
                this.myDarts.add(new Dart(this.dartPane, angle, x, y, range,
                        true, sharpness, new ArrayList<>(), false, camo));
                break;
            case CANNONBALL:
                this.myDarts.add(new Cannonball(this.dartPane, angle, x, y, range, false,
                        1, new ArrayList<>(), false, camo, this.myBloons, bigBoom));
                break;
            default:
                break;
        }
    }

    /**
     * This method is called repeatedly from the Timeline in the BloonsGame class. It moves the darts and
     * then checks their collision against every Bloon. It is thoroughly commented given that it is quite
     * lengthy, and also includes this program's main algorithm, which is mostly separated out into the
     * targetNewBloon method below. The main component of this move method is that is uses a do-While loop
     * to iterate through the DoublyLinkedList of Bloons.
     */
    public void moveDarts(){
        // Iterate through each dart in the ArrayList...
        for (int i = 0; i < this.myDarts.size(); i++){

            // Store the dart as a temporary variable to save time/space.
            Dart dart = this.myDarts.get(i);

            // Move the dart. If it has reached the end of its range, remove it.
            if (!dart.move()){
                this.myDarts.remove(dart);
            }

            // If there are any Bloons, check for collisions...
            if (this.myBloons.getTail() != null){

                // Store the tail Node in a temp variable to save time/space.
                Node temp = this.myBloons.getTail();

                // Store whether the selected dart is a piercing dart or not.
                boolean hasPierce = dart.getPiercing();

                // Initialize a 'popped' boolean which will be the condition for this do-while loop.
                boolean popped = false;

                do {
                    // Call the Dart's checkCollision method, passing in the temp Node. Returns true if
                    // the dart is colliding with the Bloon's body and the Bloon and Dart are compatible.
                    if (dart.checkCollision(temp.getBloon())) {

                        // If the dart has reached the limit of its pops, remove it graphically and logically.
                        if (dart.getPopCount() <= 0){
                            this.myDarts.remove(dart);
                            this.dartPane.getChildren().remove(dart.getDart());
                        }

                        // If the Bloon that was popped is either Lead or Black, create a duplicate Bloon.
                        if (temp.getBloon().getStrength() > PINK_STRENGTH &&
                                temp.getBloon().getStrength() <= ANDY_POP){
                            this.bloonOrg.splitBloon(temp, dart);
                        }

                        // The dart is a targeting dart and the remaining pops > 0, target a new Bloon.
                        if (dart.getTargetability() && dart.getPopCount() > 0) {
                            this.targetNewBloon(temp, dart);
                        }

                        // Pop the Bloon, and if the pop method returns true (the Bloon's strength = 0),
                        // then remove the Bloon from the DoublyLinkedList.
                        if (temp.getBloon().pop(hasPierce)){
                            this.myBloons.remove(temp);
                        }

                        // A Bloon has been popped, so the while loop will stop.
                        popped = true;
                    }
                    // If the dart's collision method returns false but its pop count drops to 0, then it
                    // must have hit a Lead Bloon, which means it must be immediately removed.
                    else if (dart.getPopCount() <= 0){
                        this.myDarts.remove(dart);
                        this.dartPane.getChildren().remove(dart.getDart());

                        // The dart is deleted, so the while loop will stop.
                        popped = true;
                    }

                    // If there is a Bloon to behind the current one, set the temp Node to the previous Node
                    // and repeat the while loop until a Bloon has been popped or the dart is deleted.
                    if (temp.getPrev() != null){
                        temp = temp.getPrev();
                    }
                    else{
                        break;
                    }
                } while (!popped);
            }
        }
    }

    /**
     * This helper method is called from the above move method, to factor out what is the main section of
     * this program's main algorithm. As described further in the in-line comments, this method takes in a
     * targeting dart and a Bloon with which it has collided, and re-orients the dart such that it aims at
     * the closest Bloon and creates a new Dart, sending it towards that new target Bloon...
     */
    private void targetNewBloon(Node temp, Dart dart){
        // These arrays will track the distance and angle to the Bloons on either side
        // of the Bloon that the dart has just hit.
        double[] distNext = {};
        double[] distPrev = {};

        // These booleans track whether there is a Bloon in either direction.
        boolean next = false;
        boolean prev = false;

        if (temp.getNext() != null){
            // If there is a Bloon further along, check if the Bloon is in the dart's
            // ArrayList of Bloons that it has already hit.
            boolean hasHit = false;
            for (Bloon bloon : dart.getBloonsHit()) {
                if (bloon == temp.getNext().getBloon()) {
                    hasHit = true;
                    break;
                }
            }

            // If the dart has not yet hit the Bloon, then get the distance to it.
            if (!hasHit){
                distNext = temp.getNext().getDist(temp.getBloon().getX(), temp.getBloon().getY());
                next = true;
            }
        }

        if (temp.getPrev() != null){
            // If there is a Bloon further back, check if the Bloon is in the dart's
            // ArrayList of Bloons that it has already hit.
            boolean hasHit = false;
            for (Bloon bloon : dart.getBloonsHit()) {
                if (bloon == temp.getPrev().getBloon()) {
                    hasHit = true;
                    break;
                }
            }

            // If the dart has not yet hit the Bloon, then get the distance to it.
            if (!hasHit) {
                distPrev = temp.getPrev().getDist(temp.getBloon().getX(), temp.getBloon().getY());
                prev = true;
            }
        }

        // If there is an unhit Bloon both behind and ahead of the dart...
        if (prev && next){

            // Check which Bloon is closer to the dart, and make a new dart angled towards it.
            if (distNext[0] <= distPrev[0]){
                this.myDarts.add(new Dart(this.dartPane, distNext[1] - QUART_CIRCLE,
                        temp.getBloon().getX(), temp.getBloon().getY(), (int) distNext[0], true,
                        dart.getPopCount(), dart.getBloonsHit(), false, true));
            }
            else {
                this.myDarts.add(new Dart(this.dartPane, distPrev[1] - QUART_CIRCLE,
                        temp.getBloon().getX(), temp.getBloon().getY(), (int) distPrev[0], true,
                        dart.getPopCount(), dart.getBloonsHit(), false, true));
            }
        }
        // If there is an unhit Bloon ahead of the dart, make a new dart angled towards it.
        else if (next){
            this.myDarts.add(new Dart(this.dartPane, distNext[1] - QUART_CIRCLE,
                    temp.getBloon().getX(), temp.getBloon().getY(), (int) distNext[0], true,
                    dart.getPopCount(), dart.getBloonsHit(), false, true));
        }
        // If there is an unhit Bloon behind the dart, make a new dart angled towards it.
        else if (prev){
            this.myDarts.add(new Dart(this.dartPane, distPrev[1] - QUART_CIRCLE,
                    temp.getBloon().getX(), temp.getBloon().getY(), (int) distPrev[0], true,
                    dart.getPopCount(), dart.getBloonsHit(), false, true));
        }

        // Remove the old dart now that a new one has been made.
        this.myDarts.remove(dart);
        this.dartPane.getChildren().remove(dart.getDart());
    }
}