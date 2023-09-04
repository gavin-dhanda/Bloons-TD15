package indy;

import indy.MoneyTracker;
import indy.Monkey;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import static indy.Constants.*;

/**
 * This is the UpgradePanel class. It sets up and modifies the upgrade panel that show up whenever any
 * monkey is selected during gameplay. It contains instance variables for the different icons and types
 * of upgrades that correspond to each type of monkey, and depending on which monkey is selected, it
 * shows the corresponding upgrade panel, which allows the user to upgrade their monkeys.
 */
public class UpgradePanel {

    private BorderPane root;
    private BorderPane gameRight;
    private BorderPane gameRoot;
    private Monkey monkey;
    private ImageView dartPic;
    private ImageView ninjaPic;
    private ImageView bombPic;
    private VBox dartMonkeyUpgrades;
    private VBox ninjaMonkeyUpgrades;
    private VBox bombShooterUpgrades;
    private VBox top;
    private Text typeText;
    private MoneyTracker bank;

    /**
     * This is the UpgradePanel class' Constructor. It sets up the BorderPanes for the local root and the
     * ones that correspond to the game screen itself, and calls an array of methods to set up the visual
     * and functional aspects of the upgrade panel, its buttons, and the specific monkeys.
     */
    public UpgradePanel(BorderPane myGameRoot, BorderPane myGameRight, MoneyTracker myBank){
        this.root = new BorderPane();
        this.gameRight = myGameRight;
        this.gameRoot = myGameRoot;
        this.bank = myBank;

        this.setUpPanel();
        this.setUpBackButton();
        this.setUpDartMonkeyUpgrades();
        this.setUpNinjaMonkeyUpgrades();
        this.setUpBombShooterUpgrades();
    }

    /**
     * This method is called from the MonkeyOrganizer whenever a new monkey is selected. It passes in the
     * new monkey, to be stored the 'monkey' instance variable, and shows the appropriate upgrade panel,
     * depending on the type of monkey that is passed in.
     */
    public void setMonkey(Monkey newMonkey){
        this.monkey = newMonkey;

        this.top.getChildren().removeAll(this.ninjaPic, this.dartPic, this.bombPic);
        this.root.setCenter(null);

        switch (this.monkey.getMonkeyType()){
            case DART_MONKEY_TYPE:
                this.typeText.setText("Dart Monkey");
                this.top.getChildren().add(this.dartPic);
                this.root.setCenter(this.dartMonkeyUpgrades);
                break;
            case NINJA_MONKEY_TYPE:
                this.typeText.setText("Ninja Monkey");
                this.top.getChildren().add((this.ninjaPic));
                this.root.setCenter(this.ninjaMonkeyUpgrades);
                break;
            case BOMB_SHOOTER_TYPE:
                this.typeText.setText("Bomb Shooter");
                this.top.getChildren().add((this.bombPic));
                this.root.setCenter(this.bombShooterUpgrades);
                break;
            default:
                break;
        }
    }

    /**
     * This method is called from the Constructor, and it styles and sets the mouse functionality of
     * a 'Back' button at the bottom of the upgrade panel that returns the user to the monkey shop.
     */
    private void setUpBackButton(){
        Text back = new Text("Back");
        back.setFill(Color.WHITE);
        back.setStroke(BROWN_3);
        back.setFont(MID_GAME);
        back.setTranslateY(BACK_BUTTON_OFFSET);
        back.setOnMouseEntered((MouseEvent a) -> back.setFont(GAME));
        back.setOnMouseExited((MouseEvent a) -> back.setFont(MID_GAME));
        back.setOnMouseClicked((MouseEvent a) -> this.unselect());

        VBox bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(back);
        this.root.setBottom(bottom);
    }


    /**
     * This method is called from the Constructor, and it sets up the rectangle that visually represents
     * the upgrade panel, along with the text at the top of the panel that indicates the type of monkey
     * that is selected at any given point.
     */
    private void setUpPanel(){
        Rectangle rectangle = new Rectangle(0, 0, PANEL_RECT_WIDTH, PANEL_RECT_HEIGHT);
        rectangle.setFill(MENU_COLOR);
        rectangle.setStroke(MENU_BORDER);
        rectangle.setStrokeWidth(MENU_BORDER_WIDTH);
        rectangle.setTranslateY(MENU_OFFSET_Y);

        this.typeText = new Text();
        this.typeText.setFill(Color.WHITE);
        this.typeText.setStroke(BROWN_3);
        this.typeText.setFont(MID_GAME);
        this.typeText.setTranslateY(MONKEY_TYPE_OFFSET);

        this.top = new VBox();
        this.top.setAlignment(Pos.CENTER);
        this.top.getChildren().addAll(this.typeText);

        this.root.getChildren().add(rectangle);
        this.root.setTop(top);
        this.root.setMinWidth(UPGRADE_PANEL_WIDTH);
    }

    /**
     * This method is called from the Constructor, and it sets up the DartMonkey icon and the different
     * texts that indicate the dart monkey's possible upgrades, descriptions, and costs.
     */
    private void setUpDartMonkeyUpgrades(){
        this.dartMonkeyUpgrades = new VBox();
        this.styleVBox(this.dartMonkeyUpgrades);

        this.dartPic = new ImageView(new Image("indy/MonkeyPic.png"));
        this.setUpImages(this.dartPic, true);
        this.dartPic.setTranslateY(DART_PIC_OFFSET);

        Text range = new Text("Range");
        this.setUpUpgrade(range, "Increase Dart Monkey's", "Range by 25%.", DM_RANGE_COST,
                TOP_UPGRADE_MONKEYS_OFFSET, TOP_UPGRADE, this.dartMonkeyUpgrades);
        range.setOnMouseClicked((MouseEvent a) -> this.buyUpgrade(TOP_UPGRADE, DM_RANGE_COST, range));

        Text piercing = new Text("Piercing");
        this.setUpUpgrade(piercing, "Darts pop 2 Layers of", "Bloon instead of 1.", DM_PIERCING_COST,
                MID_UPGRADE_MONKEYS_OFFSET, MID_UPGRADE, this.dartMonkeyUpgrades);
        piercing.setOnMouseClicked((MouseEvent a) -> this.buyUpgrade(MID_UPGRADE, DM_PIERCING_COST, piercing));

        Text speed = new Text("Speed");
        this.setUpUpgrade(speed, "Increases Dart Monkey's", "Attack Speed.", DM_SPEED_COST,
                BOT_UPGRADE_MONKEYS_OFFSET, BOT_UPGRADE, this.dartMonkeyUpgrades);
        speed.setOnMouseClicked((MouseEvent a) -> this.buyUpgrade(BOT_UPGRADE, DM_SPEED_COST, speed));
    }

    /**
     * This method is called from the Constructor, and it sets up the NinjaMonkey icon and the different
     * texts that indicate the ninja monkey's possible upgrades, descriptions, and costs.
     */
    private void setUpNinjaMonkeyUpgrades(){
        this.ninjaMonkeyUpgrades = new VBox();
        this.styleVBox(this.ninjaMonkeyUpgrades);

        this.ninjaPic = new ImageView(new Image("indy/Ninja.png"));
        this.setUpImages(this.ninjaPic, true);

        Text targeting = new Text("Targeting");
        this.setUpUpgrade(targeting, "Ninja Monkey's Darts", "Track Down Bloons.", NM_TARGETING_COST,
                TOP_UPGRADE_MONKEYS_OFFSET, TOP_UPGRADE, this.ninjaMonkeyUpgrades);
        targeting.setOnMouseClicked((MouseEvent a) -> this.buyUpgrade(TOP_UPGRADE, NM_TARGETING_COST, targeting));

        Text sharpness = new Text("Sharpness");
        this.setUpUpgrade(sharpness, "Darts Can Now Pop", "Up to 6 Bloons.", NM_SHARPNESS_COST,
                MID_UPGRADE_MONKEYS_OFFSET, MID_UPGRADE, this.ninjaMonkeyUpgrades);
        sharpness.setOnMouseClicked((MouseEvent a) -> this.buyUpgrade(MID_UPGRADE, NM_SHARPNESS_COST, sharpness));

        Text speed = new Text("Speed");
        this.setUpUpgrade(speed, "Increase Ninja Monkey's", "Attack Speed.", NM_SPEED_COST,
                BOT_UPGRADE_MONKEYS_OFFSET, BOT_UPGRADE, this.ninjaMonkeyUpgrades);
        speed.setOnMouseClicked((MouseEvent a) -> this.buyUpgrade(BOT_UPGRADE, NM_SPEED_COST, speed));
    }

    /**
     * This method is called from the Constructor, and it sets up the BombShooter icon and the different
     * texts that indicate the bomb shooter's possible upgrades, descriptions, and costs.
     */
    private void setUpBombShooterUpgrades(){
        this.bombShooterUpgrades = new VBox();
        this.styleVBox(this.bombShooterUpgrades);

        this.bombPic = new ImageView(new Image("indy/Bomber.png"));
        this.setUpImages(this.bombPic, false);
        this.bombPic.setFitWidth(BOMB_UPGRADE_WIDTH);
        this.bombPic.setFitHeight(BOMB_UPGRADE_HEIGHT);

        Text speed = new Text("Speed");
        this.setUpUpgrade(speed, "Increase Bomb Shooter's", "Attack Speed.", BS_SPEED_COST,
                TOP_UPGRADE_MONKEYS_OFFSET, TOP_UPGRADE, this.bombShooterUpgrades);
        speed.setOnMouseClicked((MouseEvent a) -> this.buyUpgrade(TOP_UPGRADE, BS_SPEED_COST, speed));

        Text explosive = new Text("Explosive");
        this.setUpUpgrade(explosive, "Increases Cannonball", "Blast Radius.", BS_EXPLOSIVE_COST,
                MID_UPGRADE_BOMBER_OFFSET, MID_UPGRADE, this.bombShooterUpgrades);
        explosive.setOnMouseClicked((MouseEvent a) -> this.buyUpgrade(MID_UPGRADE, BS_EXPLOSIVE_COST, explosive));

        Text range = new Text("Range");
        this.setUpUpgrade(range, "Bomb Shooter Can", "Shoot Further.", BS_RANGE_COST,
                BOT_UPGRADE_BOMBER_OFFSET, BOT_UPGRADE, this.bombShooterUpgrades);
        range.setOnMouseClicked((MouseEvent a) -> this.buyUpgrade(BOT_UPGRADE, BS_RANGE_COST, range));
    }

    /**
     * This method is called when one of the upgrade texts is clicked for a given monkey. It calls the
     * monkey's upgrade method, and if it returns true, then it updates the money to adjust for the cost
     * and removes the button's visual animation.
     */
    private void buyUpgrade(int upgradeNum, int cost, Text text){
        if (this.monkey.upgrade(upgradeNum, this.bank.getCash(), cost)){
            this.bank.modifyCash(-cost);
            text.setFont(GAME);
            text.setFill(Color.WHITE);
        }
    }

    /**
     * This helper method is called from the set-up methods above that correspond to each monkey,
     * and it sets up the image icons with the correct scale and position.
     */
    private void setUpImages(ImageView image, boolean preservable){
        image.setPreserveRatio(preservable);
        image.setSmooth(true);
        image.setCache(true);
        image.setScaleX(MONKEY_UPGRADE_SCALE);
        image.setScaleY(MONKEY_UPGRADE_SCALE);
        image.setTranslateY(UPGRADE_ICON_OFFSET);
    }

    /**
     * This helper method is called from the set-up methods above that correspond to each monkey,
     * and it sets up the VBox in which the upgrades and descriptions will be situated.
     */
    private void styleVBox(VBox vBox){
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(UPGRADE_ITEM_SPACING);
        vBox.setMaxHeight(0);
    }

    /**
     * This helper method is called from the set-up methods above that correspond to each monkey,
     * and it sets up the three text labels that correspond to each individual upgrade. It then sets
     * up the mouse functionality such that the buttons grow and change color when hovered over.
     */
    private void setUpUpgrade(Text main, String desc1, String desc2, int cost, int y, int upgradeNum, Pane pane){
        Text description1 = new Text(desc1);
        Text description2 = new Text(desc2);
        Text price = new Text("$" + cost);
        this.setText(GAME, main, y, Color.WHITE, BROWN_3);
        this.setText(LIL_GAME, description1, y, Color.BLACK, INVISIBLE);
        this.setText(LIL_GAME, description2, y, Color.BLACK, INVISIBLE);
        this.setText(MID_GAME, price, y, Color.WHITE, BROWN_3);

        main.setOnMouseEntered((MouseEvent a) -> this.grow(main, upgradeNum, cost));
        main.setOnMouseExited((MouseEvent a) -> this.shrink(main));
        pane.getChildren().addAll(main, description1, description2, price);
    }

    /**
     * This helper method is called from the setUpUpgrade method above, and it styles the different
     * Texts according to the four parameters passed in.
     */
    private void setText(Font font, Text text, int y, Color fill, Color stroke){
        text.setFont(font);
        text.setFill(fill);
        text.setTranslateY(y);
        text.setStroke(stroke);
    }

    /**
     * This method is called whenever the mouse moves off of one of the upgrade buttons, and
     * it shrinks it back to normal size and color...
     */
    private void shrink(Text text){
        text.setFont(GAME);
        text.setFill(Color.WHITE);
    }

    /**
     * This method is called whenever the mouse moves onto of one of the upgrade buttons.
     * It increases the size, and sets the color depending on whether the user has enough
     * money to buy the upgrade that corresponds to the text...
     */
    private void grow(Text text, int upgrade, int cost){
        if (!this.monkey.getUpgradeStatus()[upgrade]){
            text.setFont(BIG_GAME);
            if (cost <= this.bank.getCash()){
                text.setFill(Color.LIGHTGREEN);
            }
            else {
                text.setFill(Color.PALEVIOLETRED);
            }
        }
    }

    /**
     * This method is called by the 'Back' button to unselect the current monkey and return the user
     * to the normal monkey shop on the right side of the screen.
     */
    public void unselect(){
        this.monkey.deselect();
        this.gameRoot.setRight(null);
        this.gameRoot.setRight(this.gameRight);
    }

    /**
     * Accessor method to get the root pane that is used to represent the upgrade panel.
     */
    public BorderPane getRoot() {
        return root;
    }
}