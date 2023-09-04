package indy;

import static indy.Constants.*;

/**
 * This is the Node class. It is used in the structure of the DoublyLinkedList, and it includes instance
 * variables for the next and previous Nodes, along with the Bloon that it contains. It has little real
 * functionality, but performs some logical tasks.
 */
public class Node {

    private Node next;
    private Node prev;
    private Bloon bloon;

    /**
     * This is the Node class' Constructor. It initializes the Bloon and the next/prev Nodes.
     */
    public Node(Bloon myBloon){
        this.bloon = myBloon;
        this.next = null;
        this.prev = null;
    }

    /**
     * This method takes in x and y coordinates and finds the distance and angle between the node's
     * Bloon and those coordinates. It adjusts the angle so that it is somewhere from 0 to 360 degrees
     * and returns a double array with the angle and distance values.
     */
    public double[] getDist(double myX, double myY){
        double X = myX - this.bloon.getX();
        double Y = myY - this.bloon.getY();
        double[] angleDist = {Math.sqrt(X*X + Y*Y), (Math.atan(Y/X)  * HALF_CIRCLE / Math.PI)};

        // Adjust the angle so that it points in the correct quadrant...
        if (X < 0){
            angleDist[1] += HALF_CIRCLE;
        }
        else if (Y < 0){
            angleDist[1] += CIRCLE;
        }
        return angleDist;
    }

    /**
     * Accessor method to get the Bloon that is contained in this Node.
     */
    public Bloon getBloon(){
        return this.bloon;
    }

    /**
     * Mutator method to set the next Node to the passed in Node.
     */
    public void setNext(Node node){
        this.next = node;
    }

    /**
     * Accessor method to get the next Node of this Node.
     */
    public Node getNext(){
        return this.next;
    }

    /**
     * Mutator method to set the prev Node to the passed in Node.
     */
    public void setPrev(Node node){
        this.prev = node;
    }

    /**
     * Accessor method to get the prev Node of this Node.
     */
    public Node getPrev(){
        return this.prev;
    }
}