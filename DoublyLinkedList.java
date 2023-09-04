package indy;

/**
 * This is the DoublyLinkedList class. It represents a double-linked-list of Nodes, and it includes
 * instance variables for the head Node, tail Node, and size of the list. It deals with modifying some
 * aspects of the list and re-ordering the list when necessary.
 */
public class DoublyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    /**
     * This is the DoublyLinkedList class' Constructor. It initializes the head, tail, and size.
     */
    public DoublyLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * This method runs the same code as the constructor whenever the game is reset.
     */
    public void clear(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * This method adds the node passed in to the head position in the list and increments the
     * size accordingly, also checking for any edge cases.
     */
    public void addFirst(Node node){
        this.size++;
        if (this.head != null){
            node.setNext(this.head);
            this.head.setPrev(node);
        }
        else{
            this.tail = node;
        }
        this.head = node;
    }

    /**
     * This method removes the last node from the list, checking for any edge cases,
     * and decrements the size of the list.
     */
    public void removeLast(){
        if (this.size > 1) {
            this.tail = this.tail.getPrev();
            this.tail.setNext(null);
        }
        else if (this.size == 1){
            this.tail = null;
            this.head = null;
        }
        this.size--;
    }

    /**
     * This method removes the first node from the list, checking for any edge cases,
     * and decrements the size of the list.
     */
    public void removeFirst(){
        if (this.size > 1) {
            this.head = this.head.getNext();
            this.head.setPrev(null);
        }
        else if (this.size == 1){
            this.tail = null;
            this.head = null;
        }
        this.size--;
    }

    /**
     * This method removes the node passed in from the list, checking for any edge cases,
     * and decrements the size of the list.
     */
    public void remove(Node node){
        if (node == this.tail){
            this.removeLast();
        }
        else if (node == this.head){
            this.removeFirst();
        }
        else {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
            this.size--;
        }
    }

    /**
     * This method moves the node passed in to the next position in the linked list,
     * checking for the many possible situations in which this could happen...
     */
    public void moveForward(Node node){
        // The method is only called if the next node is not null...
        if (node.getPrev() != null){
            // If there is a node to the left...
            node.getNext().setPrev(node.getPrev());
            node.getPrev().setNext(node.getNext());
            node.setPrev(node.getNext());

            // If the next node is not the tail...
            if (node.getNext().getNext() != null){
                node.setNext(node.getNext().getNext());
                node.getPrev().setNext(node);
                node.getNext().setPrev(node);
            }
            else {
                // If the next node is the tail...
                node.setNext(null);
                node.getPrev().setNext(node);
                this.tail = node;
            }
        }
        else {
            // If this node is the head...
            node.setPrev(node.getNext());
            node.getPrev().setPrev(null);
            this.head = node.getPrev();

            // If the next node is not the tail...
            if (node.getNext().getNext() != null){
                node.setNext(node.getNext().getNext());
                node.getNext().setPrev(node);
                this.head.setNext(node);
            }
            else {
                // If the next node is the tail...
                node.getPrev().setNext(node);
                node.setNext(null);
                this.tail = node;
            }
        }
    }

    /**
     * Accessor method to get the Tail of the list.
     */
    public Node getTail(){
        return this.tail;
    }

    /**
     * Accessor method to get the size of the list.
     */
    public int getSize(){
        return this.size;
    }
}