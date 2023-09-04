package indy;

import javafx.scene.control.Label;
import static indy.Constants.*;

/**
 * This is the MoneyTracker class. It stores the value of the game's total cash at any point and
 * a label that is updated to display the changing cash value.
 */
public class MoneyTracker {

    private int cash;
    private Label cashText;

    /**
     * This is the MoneyTracker class' Constructor, which initializes the starting cash value and
     * the cashText label, which is passed in through association from the BloonsGame...
     */
    public MoneyTracker (Label myCashText){
        this.cash = STARTING_CASH;
        this.cashText = myCashText;
    }

    /**
     * This mutator method adjusts the cash value by the integer passed into the method.
     * It then updates the label to reflect this change.
     */
    public void modifyCash(int change){
        this.cash += change;
        this.cashText.setText("Cash  $" + this.cash);
    }

    /**
     * This mutator method adjusts the cash value by the integer passed into the method.
     */
    public int getCash(){
        return this.cash;
    }

    /**
     * This method resets the cash to the starting value and updates the label
     * whenever the game is restarted or when the game first begins.
     */
    public void resetCash(){
        this.cash = STARTING_CASH;
        this.cashText.setText("Cash  $" + this.cash);    }
}