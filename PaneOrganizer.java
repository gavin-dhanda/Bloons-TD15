package indy;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import static indy.Constants.*;

/**
 * This is the PaneOrganizer class. It sets up the root borderpane, and deals with organizing
 * the panes and buttons for the start screen, as well as the game screen. It contains an instance
 * of BloonsGame, which deals with the logical aspects of the gameplay.
 */
public class PaneOrganizer {

    private BorderPane root;
    private BorderPane topStart;
    private BorderPane bottomStart;
    private BorderPane gameRight;
    private ImageView startImage;
    private ImageView mapImage;
    private Pane primary;
    private VBox cashLives;
    private Label lives;
    private Label cash;
    private Label level;
    private Text startText;
    private Text playPause;
    private BloonsGame game;

    /**
     * This is the PaneOrganizer class' Constructor, which initializes all relevant panes and the BloonsGame
     * and calls all relevant methods to graphically set up the basic elements of the game and start screens.
     */
    public PaneOrganizer(){
        this.root = new BorderPane();

        // Start Screen Panes
        this.startImage = new ImageView();
        this.topStart = new BorderPane();
        this.bottomStart = new BorderPane();
        this.startText = new Text("Start");

        // Game Screen Panes
        this.mapImage = new ImageView();
        this.lives = new Label();
        this.cash = new Label();
        this.level = new Label();
        this.gameRight = new BorderPane();
        this.cashLives = new VBox();
        this.primary = new Pane();
        this.playPause = new Text();
        BorderPane loss = new BorderPane();
        BorderPane win = new BorderPane();

        this.makeImages();
        this.setUpStartScreen();
        this.setUpStartButton();
        this.setUpEndScreen(win, "Victory!", VICTORY);
        this.setUpEndScreen(loss, "Game Over", LOSS);

        this.game = new BloonsGame(this.primary, this.lives, this.cash, this.level,
                this.root, loss, win, this.playPause, this.gameRight);

        this.setUpGameRight();
        this.setUpGameLeft();
        this.setUpMonkeyIcons();
        this.reset();
    }

    /**
     * This method is called from the Constructor, and it visually sets up the two
     * background images, which I found on the web, to use for the game and start screen.
     */
    private void makeImages(){
        this.setUpImage(this.startImage, "indy/Start.png");
        this.startImage.setFitHeight(SCENE_HEIGHT);
        this.startImage.setTranslateX(START_IMAGE_OFFSET);
        this.setUpImage(this.mapImage, "indy/Map.png");
        this.mapImage.setFitWidth(SCENE_WIDTH);
        this.mapImage.setTranslateY(MAP_IMAGE_OFFSET);
    }

    /**
     * This is a helper method called from the above makeImages method to eliminate
     * some repetitive code in setting up thew two background images.
     */
    private void setUpImage(ImageView image, String path){
        image.setImage(new Image(path));
        image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);
    }

    /**
     * This method is called from the Constructor, and it sets up the top and bottom transparent
     * rectangles in the start screen and the text that sits within them.
     */
    private void setUpStartScreen(){
        // Set up the Game Title.
        Text title = new Text("Bloons Tower Defense 15");
        title.setFont(BIG_BUBBLY);
        title.setFill(Color.WHITE);
        title.setTranslateY(TITLE_OFFSET_Y);

        // Set up the top pane
        Rectangle rectangle = new Rectangle(0, 0, SCENE_WIDTH, TITLE_RECT_HEIGHT);
        rectangle.setFill(TRANSPARENT);
        this.topStart.getChildren().add(rectangle);
        this.topStart.setCenter(title);

        // Set up the SubTitle.
        Text subtitle = new Text("A Recreation of the Bloons Tower Defense Series");
        subtitle.setFont(BABY_BUBBLY);
        subtitle.setFill(Color.WHITE);
        subtitle.setTranslateY(SUBTITLE_OFFSET_Y);

        // Set up the bottom pane
        Rectangle botRectangle = new Rectangle(SCENE_WIDTH, TITLE_RECT_HEIGHT);
        botRectangle.setFill(TRANSPARENT);
        botRectangle.setTranslateY(SUBTITLE_RECT_OFFSET);
        this.bottomStart.getChildren().add(botRectangle);
        this.bottomStart.setCenter(subtitle);
    }

    /**
     * This method is called from the above setUpStartScreen method, and it sets up the central
     * button that is pressed to start the gameplay.
     */
    private void setUpStartButton(){
        this.startText.setFont(LARGE_GAME);
        this.startText.setFill(Color.WHITE);

        this.startText.setOnMouseEntered((MouseEvent a) -> this.startText.setFont(HUGE_GAME));
        this.startText.setOnMouseExited((MouseEvent a) -> this.startText.setFont(LARGE_GAME));
        this.startText.setOnMouseClicked((MouseEvent a) -> this.startGame());
    }

    /**
     * This method is called from the Constructor, and it sets up the menu on the right side
     * of the screen during gameplay, and the Start, Home, and Quit buttons.
     */
    private void setUpGameRight(){
        // Set up the rectangle on the right side.
        Rectangle rectangle = new Rectangle(0, 0, MENU_WIDTH, MENU_HEIGHT);
        rectangle.setFill(MENU_COLOR);
        rectangle.setStroke(MENU_BORDER);
        rectangle.setStrokeWidth(MENU_BORDER_WIDTH);
        rectangle.setTranslateY(MENU_OFFSET_Y);

        // Set up the buttons on the bottom right.
        Text home = new Text("Home");
        Text quit = new Text("Quit");

        this.setUpButton(home, HOME_BUTTON_OFFSET, MENU_BUTTON_BORDER);
        this.setUpButton(quit, 0, MENU_BUTTON_BORDER);
        this.setUpButton(this.playPause, PLAY_BUTTON_OFFSET, MENU_BUTTON_BORDER);

        home.setOnMouseClicked((MouseEvent a) -> this.reset());
        quit.setOnMouseClicked((MouseEvent a) -> System.exit(0));
        this.playPause.setOnMouseClicked((MouseEvent a) -> this.game.play());

        VBox rightButtons = new VBox();
        rightButtons.setAlignment(Pos.CENTER);
        rightButtons.setMaxHeight(0);
        rightButtons.setTranslateY(MENU_BUTTONS_OFFSET);
        rightButtons.getChildren().addAll(this.playPause, home, quit);

        this.gameRight.setMinWidth(MENU_PANE_WIDTH);
        this.gameRight.getChildren().add(rectangle);
        this.gameRight.setBottom(rightButtons);
    }

    /**
     * This helper method is called from the above setUpGameRight method to create the Play,
     * Home, and Quit buttons in the monkey menu, and also called when setting up the buttons
     * that show in the game over screens.
     */
    private void setUpButton(Text text, int y, double stroke){
        text.setFont(GAME);
        text.setFill(Color.WHITE);
        text.setStroke(MENU_BORDER);
        text.setStrokeWidth(stroke);
        text.setOnMouseEntered((MouseEvent a) -> text.setFont(BIG_GAME));
        text.setOnMouseExited((MouseEvent a) -> text.setFont(GAME));
        text.setTranslateY(y);
    }

    /**
     * This method is called from the setUpGameRight method, and it sets up the different monkey icons
     * and corresponding texts, arranging them accordingly in the menu area on the right side.
     */
    private void setUpMonkeyIcons(){
        // Set up the Dart Monkey
        Text dartMonkey = new Text("Dart Monkey");
        Text cost = new Text("$225");
        this.setUpTexts(dartMonkey, cost, DART_ICON_OFFSET);
        ImageView monkey = new ImageView(new Image("indy/MonkeyPic.png"));
        this.setUpMonkeyIcon(monkey, DART_MONKEY_COST, true);

        // Set up the Ninja Monkey
        Text ninjaMonkey = new Text("Ninja Monkey");
        Text ninjaCost = new Text("$550");
        this.setUpTexts(ninjaMonkey, ninjaCost, NINJA_ICON_OFFSET);
        ImageView ninja = new ImageView(new Image("indy/Ninja.png"));
        this.setUpMonkeyIcon(ninja, NINJA_MONKEY_COST, true);

        // Set up the Bomb Shooter
        Text bombShooter = new Text("Bomb Shooter");
        Text bombCost = new Text("$400");
        this.setUpTexts(bombShooter, bombCost, BOMB_ICON_OFFSET);
        ImageView bomber = new ImageView(new Image("indy/Bomber.png"));
        this.setUpMonkeyIcon(bomber, BOMB_SHOOTER_COST, false);
        bomber.setFitWidth(BOMB_ICON_WIDTH);
        bomber.setFitHeight(BOMB_ICON_HEIGHT);

        // Add to the VBox
        VBox monkeyShop = new VBox();
        monkeyShop.setAlignment(Pos.CENTER);
        monkeyShop.getChildren().addAll(bomber, bombShooter, bombCost,
                                        monkey, dartMonkey, cost,
                                        ninja, ninjaMonkey, ninjaCost);
        monkeyShop.setMaxHeight(0);
        monkeyShop.setTranslateY(MONKEY_SHOP_OFFSET);
        this.gameRight.setCenter(monkeyShop);

        // Set Up Menu Title
        Text menuTitle = new Text("MONKEYS");
        menuTitle.setTranslateY(MENU_TITLE_OFFSET);
        menuTitle.setFont(GAME);
        menuTitle.setFill(Color.WHITE);
        menuTitle.setStroke(MENU_BORDER);
        menuTitle.setStrokeWidth(MENU_BUTTON_BORDER);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(menuTitle);
        this.gameRight.setTop(vBox);
    }

    /**
     * This helper method eliminates repeated code in the above setUpMonkeyIcons method, and it
     * visually sets of the individual monkey icons and sets up their Mouse hover and mouse click
     * functionality, passing along the cost of the particular monkey whenever a monkey is bought.
     */
    private void setUpMonkeyIcon(ImageView image,  int cost, boolean preserve){
        image.setPreserveRatio(preserve);
        image.setSmooth(true);
        image.setCache(true);
        this.sizeMonkey(image, SMALL_ICON);
        image.setOnMouseEntered((MouseEvent a) -> this.sizeMonkey(image, BIG_ICON));
        image.setOnMouseExited((MouseEvent a) -> this.sizeMonkey(image, SMALL_ICON));
        image.setOnMouseClicked((MouseEvent a) -> this.game.newMonkey(cost));
    }

    /**
     * This helper method eliminates repeated code in the above setUpMonkeyIcons method, and
     * it sets up the font and position of the text labels below the monkey icons.
     */
    private void setUpTexts(Text name, Text cost, int y){
        name.setFont(LIL_GAME);
        cost.setFont(LIL_GAME);
        name.setTranslateY(y);
        cost.setTranslateY(y+2);
    }

    /**
     * This method is called whenever the mouse hovers over a monkey icon, and it
     * either increases or decreases the scale of that particular icon.
     */
    private void sizeMonkey(ImageView image,  double xy){
        image.setScaleX(xy);
        image.setScaleY(xy);
    }

    /**
     * Sets up the three Cash, Lives, and Level labels in the upper-left corner
     * of the game screen and adds them to a VBox...
     */
    private void setUpGameLeft(){
        this.setUpLabel(this.lives, "Lives  100");
        this.setUpLabel(this.cash, "Cash  $500");
        this.setUpLabel(this.level, "Level  1/30");
        this.cashLives.getChildren().addAll(this.cash, this.lives, this.level);
    }

    /**
     * This helper method is called from the above setUpGameLeft method, to reduce
     * repetitive code when styling and positioning the three labels in the top-left.
     */
    private void setUpLabel(Label label, String text){
        label.setText(text);
        label.setFont(GAME);
        label.setTextFill(Color.WHITE);
        label.setTranslateY(Y_OFFSET_LABEL);
        label.setTranslateX(X_OFFSET_LABEL);
        label.setMinWidth(Region.USE_PREF_SIZE);
    }

    /**
     * This method is called when the start button is pressed in the start screen, and it
     * reassigns the different positions in the root pane to show the game screen.
     */
    private void startGame(){
        this.root.getChildren().remove(this.startImage);
        this.root.getChildren().addAll(this.mapImage, this.cashLives);
        this.root.setTop(null);
        this.root.setBottom(null);
        this.root.setCenter(this.primary);
        this.root.setRight(this.gameRight);
    }

    /**
     * This method is called whenever the game is first started and when it is restarted, and it
     * replaces the game screen with the start screen and calls the BloonsGame's restart method.
     */
    private void reset(){
        this.root.getChildren().removeAll(this.mapImage, this.cashLives);
        this.root.getChildren().add(this.startImage);
        this.root.setRight(null);
        this.root.setTop(this.topStart);
        this.root.setBottom(this.bottomStart);
        this.root.setCenter(this.startText);
        this.playPause.setText("Play");
        this.game.restart();
    }

    /**
     * This method is called from the Constructor to set up both the win and loss panes, which displays
     * a win/loss message and  the home and quit buttons that were previously in the menu on the
     * right side. The loss and win panes are then displayed from within the BloonsGame when needed.
     */
    private void setUpEndScreen(BorderPane pane,  String text, Color color){
        Rectangle colorScreen = new Rectangle(0, 0, SCENE_WIDTH, SCENE_HEIGHT);
        colorScreen.setFill(color);

        Text gameOverText = new Text(text);
        gameOverText.setFont(HUGE_GAME);
        gameOverText.setFill(Color.WHITE);
        gameOverText.setTextAlignment(TextAlignment.CENTER);

        // Same style buttons as in the menu, but centered...
        Text quit = new Text("Quit");
        this.setUpButton(quit, QUIT_OFFSET, 0);
        quit.setOnMouseClicked((MouseEvent a) -> System.exit(0));

        Text home = new Text("Home");
        this.setUpButton(home, HOME_OFFSET, 0);
        home.setOnMouseClicked((MouseEvent a) -> this.reset());

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(gameOverText, home, quit);

        pane.setMinWidth(SCENE_WIDTH);
        pane.setMinHeight(SCENE_HEIGHT);
        pane.getChildren().addAll(colorScreen);
        pane.setCenter(vBox);
    }

    /**
     * This accessor method returns the root pane used to set up the Scene in the App class.
     */
    public BorderPane getRoot(){
        return this.root;
    }
}