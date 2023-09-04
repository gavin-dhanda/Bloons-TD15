package indy;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

public class Constants {

    // SCENE DIMENSIONS
    public static final int SCENE_WIDTH = 1180;
    public static final int SCENE_HEIGHT = 720;


    // IMAGE CONSTANTS
    public static final int START_IMAGE_OFFSET = -20;
    public static final int MAP_IMAGE_OFFSET = -10;


    // START SCREEN CONSTANTS
    public static final int TITLE_OFFSET_Y = 20;
    public static final int SUBTITLE_OFFSET_Y = -15;
    public static final int TITLE_RECT_HEIGHT = 120;
    public static final int SUBTITLE_RECT_OFFSET = -35;


    // MENU PANE CONSTANTS
    public static final int MENU_WIDTH = 160;
    public static final int MENU_HEIGHT = SCENE_HEIGHT + 20;
    public static final int MENU_BORDER_WIDTH = 3;
    public static final int MENU_OFFSET_Y = -10;
    public static final int HOME_BUTTON_OFFSET = -8;
    public static final int PLAY_BUTTON_OFFSET = -16;
    public static final double MENU_BUTTON_BORDER = 1.25;
    public static final int MENU_BUTTONS_OFFSET = -35;
    public static final int MENU_PANE_WIDTH = 140;
    public static final int DART_ICON_OFFSET = -23;
    public static final int NINJA_ICON_OFFSET = -25;
    public static final int BOMB_ICON_OFFSET = -29;
    public static final int BOMB_ICON_WIDTH = 100;
    public static final int BOMB_ICON_HEIGHT = 140;
    public static final int MONKEY_SHOP_OFFSET = -20;
    public static final int MENU_TITLE_OFFSET = 15;
    public static final double SMALL_ICON = 0.47;
    public static final double BIG_ICON = 0.52;


    // UPPER-LEFT LABELS
    public static final int X_OFFSET_LABEL = 9;
    public static final int Y_OFFSET_LABEL = 7;


    // END SCREEN CONSTANTS
    public static final int QUIT_OFFSET = 26;
    public static final int HOME_OFFSET = 16;


    // BLOON CONSTANTS
    public static final int X_START = -20;
    public static final int Y_START = 295;
    public static final double BLOON_BORDER_WIDTH = 1.5;
    public static final double BLOON_TIMER_INCREMENT = 0.01;
    public static final double SPAWN_DELAY_INCREMENT = 0.0175;
    public static final int CAMO_FILM_HEIGHT = 4;
    public static final int TAIL_RAD = 3;
    public static final int RED_STRENGTH = 1;
    public static final int BLUE_STRENGTH = 2;
    public static final int GREEN_STRENGTH = 3;
    public static final int YELLOW_STRENGTH = 4;
    public static final int PINK_STRENGTH = 5;
    public static final int BLACK_STRENGTH = 6;
    public static final int LEAD_STRENGTH = 7;


    // BLOON DIMENSIONS
    public static final double RED_XRAD = 14;
    public static final double RED_YRAD = 19;
    public static final double RED_Y1 = 16;
    public static final double RED_Y2 = 22.5;

    public static final double BLUE_XRAD = 15;
    public static final double BLUE_YRAD = 20.5;
    public static final double BLUE_Y1 = 17.5;
    public static final double BLUE_Y2 = 24;

    public static final double GREEN_XRAD = 16;
    public static final double GREEN_YRAD = 22;
    public static final double GREEN_Y1 = 19;
    public static final double GREEN_Y2 = 25.5;

    public static final double YELLOW_XRAD = 17;
    public static final double YELLOW_YRAD = 23.5;
    public static final double YELLOW_Y1 = 20.5;
    public static final double YELLOW_Y2 = 27;

    public static final double PINK_XRAD = 18;
    public static final double PINK_YRAD = 25;
    public static final double PINK_Y1 = 22;
    public static final double PINK_Y2 = 28.5;

    public static final double BLACK_XRAD = 12;
    public static final double BLACK_YRAD = 16;
    public static final double BLACK_Y1 = 13;
    public static final double BLACK_Y2 = 19;


    // ANDY BLOON CONSTANTS
    public static final int ANDY_STRENGTH = 101;
    public static final int ANDY_POP = 8;
    public static final int HEALTH_WIDTH = 600;
    public static final int HEALTH_HEIGHT = 24;
    public static final int HEALTH_X = 272;
    public static final int HEALTH_Y = 20;
    public static final int HEALTH_BORDER = 3;
    public static final int ANDY_DIMENSIONS = 170;
    public static final int ANDY_START = -500;
    public static final int ANDY_OFFSET = -100;
    public static final double ANDY_XRAD = 60;
    public static final double ANDY_YRAD = 70;


    // GAME VALUES
    public static final int STARTING_LIVES = 100;
    public static final int STARTING_CASH = 500;
    public static final int STARTING_LEVEL = 0;
    public static final int DART_MONKEY_COST = 225;
    public static final int NINJA_MONKEY_COST = 550;
    public static final int BOMB_SHOOTER_COST = 400;
    public static final int LEVEL_CASH = 100;


    // MONKEY RANGES
    public static final int DART_MONKEY_RANGE = 120;
    public static final int BOMB_SHOOTER_RANGE = 150;
    public static final int NINJA_MONKEY_RANGE = 170;


    // BLOON SPEEDS
    public static final double RED_SPEED = 0.095;
    public static final double BLUE_SPEED = 0.135;
    public static final double GREEN_SPEED = 0.175;
    public static final double YELLOW_SPEED = 0.215;
    public static final double PINK_SPEED = 0.255;
    public static final double ANDY_SPEED = 0.05;


    // EXPLOSION CONSTANTS
    public static final double EXPLODE_INCREMENT = 0.094;
    public static final double EXPLODE_SIZE = 2.6;
    public static final double EXPLODE_SCALAR = 0.85;
    public static final double RANGE_MULTIPLIER = 0.02;


    // MISCELLANEOUS STUFF
    public static final double FAST_TIMELINE = 0.55;
    public static final double SLOW_TIMELINE = 1.05;
    public static final double POP_TIMELINE = 5;
    public static final int QUART_CIRCLE = 90;
    public static final int HALF_CIRCLE = 180;
    public static final double CIRCLE = 360;


    // POP & EXPLOSION ANIMATION
    public static final double[] POP_SHAPES = {0, -4.5, 6, -6.5, 4, -1.5, 6.5, 2, 3.5, 4, 3.75, 7.5,
            0, 5, -4.5, 8, -4, 4, -8, 0, -4, -3, -3.25, -7.5};
    public static final double POP_X_SCALE = 0.165;
    public static final double POP_Y_SCALE = 0.2;
    public static final int NUM_GROWTHS = 14;
    public static final int EXPLOSION_OFFSET = 5;
    public static final double YELLOW_Y_SCALE = 2.5;
    public static final double YELLOW_X_SCALE = 3;
    public static final double ORANGE_Y_SCALE = 2;
    public static final double ORANGE_X_SCALE = 2.4;
    public static final double RED_Y_SCALE = 1.5;
    public static final double RED_X_SCALE = 1.8;
    public static final double[] YELLOW_COLOR_VALUES = {.9, .83, .25};
    public static final double[] ORANGE_COLOR_VALUES = {.9, .63, .25};
    public static final double[] RED_COLOR_VALUES = {1, .35, .35};


    // DART & CANNONBALL CONSTANTS
    public static final double CANNONBALL_SPEED = 0.85;
    public static final double CANNONBALL_RADIUS = 5.5;
    public static final double DART_SPEED = 1.7;
    public static final int DART_WIDTH = 2;
    public static final int DART_HEIGHT = 10;
    public static final int MAX_DART_RANGE = 260;


    // MONKEY CONSTANTS
    public static final int HIT_BOX_X = 24;
    public static final int HIT_BOX_Y = 20;
    public static final double SMALL_HITBOX_RAD = 17.5;
    public static final int RANGE_UPGRADE = 45;
    public static final double SHOT_TIMER_INCREMENT = 0.001;
    public static final int MONKEY_BORDER_WIDTH = 2;
    public static final double ARM_ANIMATION_INCREMENT = 0.085;
    public static final double ARM_ANIMATION_MULT = 0.1;
    public static final int DART_RANGE_INCREASE = 50;
    public static final int ANIMATION_START = 1;
    public static final int ANIMATION_MID = 11;
    public static final int ANIMATION_END = 21;


    // BOMB SHOOTER CONSTANTS
    public static final double START_BOMB_SPEED = 1.95;
    public static final double NEW_BOMB_SPEED = 1.2;
    public static final int START_EXPLOSION_RADIUS = 42;
    public static final int NEW_EXPLOSION_RADIUS = 80;
    public static final int BOMB_SHOOTER_WIDTH = 50;
    public static final int BOMB_SHOOTER_HEIGHT = 70;
    public static final int BS_HITBOX_SMALL = 18;
    public static final int BS_HITBOX_X = 25;
    public static final int BS_HITBOX_Y = 26;
    public static final int BS_HITBOX_OFFSET = 9;


    // DART MONKEY CONSTANTS
    public static final double DM_START_SPEED = 1.15;
    public static final double DM_NEW_SPEED = 0.88;
    public static final int DM_SHARPNESS = 2;


    // NINJA MONKEY CONSTANTS
    public static final double NM_START_SPEED = 1.75;
    public static final double NM_NEW_SPEED = 1.33;
    public static final int NM_SHARPNESS_START = 3;
    public static final int NM_SHARPNESS_NEW = 6;
    public static final int NM_SHAPES = 16;
    public static final double[] STRIPE_COORDS = {-NINJA_MONKEY_RANGE-7, -NINJA_MONKEY_RANGE+26,
            -NINJA_MONKEY_RANGE-1, -NINJA_MONKEY_RANGE+26.83, -NINJA_MONKEY_RANGE+2.5,
            -NINJA_MONKEY_RANGE+23.75, -NINJA_MONKEY_RANGE-2, -NINJA_MONKEY_RANGE+23};


    // ARM SHOOTER SHAPES
    public static final int LEFT_EAR = 0;
    public static final int RIGHT_EAR = 1;
    public static final int LEFT_PUPIL = 2;
    public static final int RIGHT_PUPIL = 3;
    public static final int LEFT_GLINT = 4;
    public static final int RIGHT_GLINT = 5;
    public static final int LEFT_EYE = 6;
    public static final int RIGHT_EYE = 7;
    public static final int TAIL = 8;
    public static final int BODY = 9;
    public static final int ARM = 10;
    public static final int HAND = 11;
    public static final int FACE = 12;
    public static final int HEAD_BORDER = 13;
    public static final int STRIPE = 14;
    public static final int TAIL_BORDER = 15;
    public static final int NUM_SHAPES = 14;
    public static final double[] DM_TAIL_COORDS = {-DART_MONKEY_RANGE-4, -DART_MONKEY_RANGE+10, -DART_MONKEY_RANGE-7,
            -DART_MONKEY_RANGE+26, -DART_MONKEY_RANGE+11, -DART_MONKEY_RANGE+28.5, -DART_MONKEY_RANGE+10,
            -DART_MONKEY_RANGE+21, -DART_MONKEY_RANGE+5, -DART_MONKEY_RANGE+21, -DART_MONKEY_RANGE+7,
            -DART_MONKEY_RANGE+24.5, -DART_MONKEY_RANGE-2, -DART_MONKEY_RANGE+23, -DART_MONKEY_RANGE+2,
            -DART_MONKEY_RANGE+10};
    public static final double[] NM_TAIL_COORDS = {-NINJA_MONKEY_RANGE-4, -NINJA_MONKEY_RANGE+10, -NINJA_MONKEY_RANGE-7,
            -NINJA_MONKEY_RANGE+26, -NINJA_MONKEY_RANGE+11, -NINJA_MONKEY_RANGE+28.5, -NINJA_MONKEY_RANGE+10,
            -NINJA_MONKEY_RANGE+21, -NINJA_MONKEY_RANGE+5, -NINJA_MONKEY_RANGE+21, -NINJA_MONKEY_RANGE+7,
            -NINJA_MONKEY_RANGE+24.5, -NINJA_MONKEY_RANGE-2, -NINJA_MONKEY_RANGE+23, -NINJA_MONKEY_RANGE+2,
            -NINJA_MONKEY_RANGE+10};
    public static final int EAR_RAD_X = 6;
    public static final int EAR_RAD_Y = 8;
    public static final int EAR_OFFSET_Y = 20;
    public static final int EYE_OFFSET_X = 10;
    public static final int EYE_OFFSET_Y = 10;
    public static final int PUPIL_OFFSET_Y = EYE_OFFSET_Y+1;
    public static final double GLINT_OFFSET_Y = EYE_OFFSET_Y+.5;
    public static final double EYE_RAD_X = 6.25;
    public static final double EYE_RAD_Y = 5.25;
    public static final double PUPIL_RAD = 3.5;
    public static final double GLINT_RAD = .75;
    public static final int HEAD_RAD_X = 22;
    public static final int HEAD_RAD_Y = 18;
    public static final int ARM_LENGTH = 14;
    public static final int ARM_WIDTH = 6;
    public static final int ARM_X = 11;
    public static final int ARM_Y = 10;
    public static final double HAND_RAD = 3.5;
    public static final int FACE_Y = 16;
    public static final double FACE_HEIGHT = 12.25;


    // DART TYPES
    public static final int NORMAL_DART = 0;
    public static final int TARGETING_DART = 1;
    public static final int CANNONBALL = 2;


    // UPGRADE PANEL CONSTANTS
    public static final int NUM_UPGRADES = 3;
    public static final double MONKEY_UPGRADE_SCALE = 0.65;
    public static final int UPGRADE_ICON_OFFSET = 15;
    public static final int DART_MONKEY_TYPE = 0;
    public static final int NINJA_MONKEY_TYPE = 1;
    public static final int BOMB_SHOOTER_TYPE = 2;
    public static final int PANEL_RECT_WIDTH = 180;
    public static final int PANEL_RECT_HEIGHT = SCENE_HEIGHT + 20;
    public static final int UPGRADE_PANEL_WIDTH = 170;
    public static final int MONKEY_TYPE_OFFSET = 22;
    public static final int BACK_BUTTON_OFFSET = -33;
    public static final int DART_PIC_OFFSET = 20;
    public static final int BOMB_UPGRADE_WIDTH = 105;
    public static final int BOMB_UPGRADE_HEIGHT = 147;
    public static final int UPGRADE_ITEM_SPACING = 5;
    public static final int TOP_UPGRADE = 0;
    public static final int MID_UPGRADE = 1;
    public static final int BOT_UPGRADE = 2;
    public static final int TOP_UPGRADE_MONKEYS_OFFSET = -75;
    public static final int MID_UPGRADE_MONKEYS_OFFSET = -20;
    public static final int BOT_UPGRADE_MONKEYS_OFFSET = 35;
    public static final int MID_UPGRADE_BOMBER_OFFSET = -30;
    public static final int BOT_UPGRADE_BOMBER_OFFSET = 15;


    // UPGRADE COSTS
    public static final int DM_RANGE_COST = 150;
    public static final int DM_PIERCING_COST = 325;
    public static final int DM_SPEED_COST = 200;
    public static final int NM_TARGETING_COST = 225;
    public static final int NM_SHARPNESS_COST = 500;
    public static final int NM_SPEED_COST = 350;
    public static final int BS_SPEED_COST = 350;
    public static final int BS_EXPLOSIVE_COST = 525;
    public static final int BS_RANGE_COST = 200;


    // LEVELS[level][wave][bloonCount, bloonStrength, bloonSpacing, camo)]
    // A bloonStrength of 0 creates a pause between waves...
    public static int COUNT_INDEX = 0;
    public static int STRENGTH_INDEX = 1;
    public static int SPACING_INDEX = 2;
    public static int CAMO_INDEX = 3;
    public static int[][][] LEVELS = {{{20, 1, 9, 0}},
            {{35, 1, 5, 0}},
            {{10, 1, 5, 0}, {5, 2, 5, 0}, {1, 0, 18, 0}, {15, 1, 5, 0}},
            {{16, 1, 4, 0}, {20, 2, 2, 0}, {1, 0, 14, 0}, {12, 1, 2, 0}},
            {{12, 2, 5, 0}, {5, 1, 5, 0}, {16, 2, 5, 0}},
            {{60, 1, 2, 0}},
            {{12, 3, 3, 0}, {10, 1, 2, 0}, {10, 2, 5, 0}},
            {{12, 2, 4, 0}, {4, 3, 6, 0}, {20, 1, 4, 0}, {10, 2, 5, 0}},
            {{20, 2, 4, 0}, {2, 3, 6, 0}, {10, 1, 3, 0}, {12, 3, 6, 0}},
            {{30, 3, 7, 0}, {1, 0, 8, 0}, {10, 3, 5, 0}},
            {{100, 2, 5, 0}},
            {{20, 1, 3, 0}, {15, 2, 4, 0}, {10, 3, 5, 0}, {5, 4, 6, 0}},
            {{20, 2, 3, 0}, {8, 4, 6, 0}, {1, 0, 10, 0}, {20, 2, 1, 0}},
            {{40, 2, 2, 0}, {8, 3, 1, 0}, {1, 0, 14, 0}, {8, 3, 1, 0}, {1, 0, 14, 0}, {8, 2, 1, 0}},
            {{18, 1, 2, 0}, {15, 2, 3, 0}, {12, 3, 4, 0}, {9, 4, 5, 0}, {5, 5, 6, 0}},
            {{120, 3, 4, 0}},
            {{15, 1, 3, 0}, {1, 0, 6, 0}, {10, 2, 2, 1}, {10, 3, 2, 0}},
            {{30, 5, 3, 0}, {12, 2, 5, 0}, {1, 0, 10, 0}, {5, 4, 1, 0}},
            {{90, 4, 7, 0}},
            {{20, 2, 3, 0}, {15, 1, 1, 0}, {1, 0, 20, 0}, {12, 5, 1, 0}},
            {{10, 1, 3, 0}, {16, 3, 2, 1}, {1, 0, 12, 0}, {8, 2, 1, 1}, {1, 0, 12, 0}, {10, 4, 2, 0}},
            {{30, 2, 5, 0}, {6, 6, 2, 0}},
            {{40, 1, 1, 0}, {1, 0, 35, 0}, {40, 2, 2, 0}, {1, 0, 35, 0}, {40, 3, 3, 0}},
            {{40, 4, 8, 0}, {1, 0, 15, 0}, {12, 1, 1, 0}},
            {{5, 5, 5, 0}, {1, 0, 5, 0}, {5, 7, 4, 0}, {1, 0, 15, 0}, {5, 3, 1, 1}},
            {{20, 1, 3, 0}, {5, 6, 3, 0}, {20, 2, 3, 0}, {5, 5, 3, 0}, {20, 3, 3, 0}, {5, 4, 3, 0}},
            {{18, 4, 1, 0}, {30, 5, 2, 0}, {1, 0, 20, 0}, {20, 6, 1, 0}},
            {{80, 5, 7, 0}},
            {{16, 7, 3, 0}, {12, 5, 2, 1}, {10, 6, 8, 0}},
            {{1, ANDY_STRENGTH, 1, 0}, {1, 0, 32, 0}, {40, 5, 6, 0}}};
    public static int[][] MAP_PATH = {{560, 295}, {587, 280}, {587, 140}, {560, 116}, {415, 111}, {385, 140},
            {386, 300}, {389, 558}, {360, 585}, {225, 590}, {192, 555}, {195, 450}, {220, 420}, {490, 421},
            {680, 416}, {715, 403}, {745, 373}, {750, 293}, {750, 238}, {820, 225}, {890, 240}, {887, 485},
            {845, 525}, {548, 532}, {525, 600}, {521, 800}};
    public static Shape[] MAP_SHAPES = {new Polygon(0, 270, 564, 270, 564, 347, 0, 347),
            new Ellipse(564, 302, 66, 45),
            new Polygon(540, 302, 540, 135, 630, 135, 630, 302),
            new Ellipse(570, 135, 60, 40),
            new Polygon(570, 95, 400, 95, 400, 161, 570, 161),
            new Ellipse(400, 135, 54, 40),
            new Polygon(346, 135, 338, 440, 435, 440, 425, 135),
            new Polygon(342, 440, 433, 440, 433, 565, 342, 565),
            new Ellipse(364.75, 584, 74, 46),
            new Polygon(340, 565, 218, 560, 218, 635, 340, 638.5),
            new Ellipse(205, 578.75, 45, 66),
            new Polygon(148.5, 559, 238, 580, 235, 450, 152, 440),
            new Ellipse(204, 440, 52, 48),
            new Polygon(204, 392, 638, 394, 680, 386, 700, 365, 703, 240, 805, 260, 801,
                    350, 795, 390, 781, 425, 745, 452, 710, 460, 640, 467, 360, 469, 204, 463),
            new Ellipse(818, 241, 115, 45),
            new Polygon(933, 241, 933, 481, 925, 531, 909, 552, 885, 571, 850, 580, 560,
                    585, 525, 508.5, 560, 505, 760, 500, 823, 504, 834, 490, 848, 341, 844, 241),
            new Ellipse(538, 562, 55, 55),
            new Polygon(483, 562, 465, 800, 562, 800, 580, 562)};
    public static final int ELLIPSE_A = 8;
    public static final int ELLIPSE_A_DEG = -30;
    public static final int ELLIPSE_B = 10;
    public static final int ELLIPSE_B_DEG = -45;


    // GAME AND START SCREEN FONTS
    public static final Font BIG_BUBBLY =
            Font.loadFont(Constants.class.getResource("StartFont.ttf").toExternalForm(), 95);
    public static final Font BABY_BUBBLY =
            Font.loadFont(Constants.class.getResource("StartFont.ttf").toExternalForm(), 44);
    public static final Font LIL_GAME =
            Font.loadFont(Constants.class.getResource("Game.ttf").toExternalForm(), 15);
    public static final Font MID_GAME =
            Font.loadFont(Constants.class.getResource("Game.ttf").toExternalForm(), 25);
    public static final Font GAME =
            Font.loadFont(Constants.class.getResource("Game.ttf").toExternalForm(), 30);
    public static final Font BIG_GAME =
            Font.loadFont(Constants.class.getResource("Game.ttf").toExternalForm(), 35);
    public static final Font LARGE_GAME =
            Font.loadFont(Constants.class.getResource("Game.ttf").toExternalForm(), 92);
    public static final Font HUGE_GAME =
            Font.loadFont(Constants.class.getResource("Game.ttf").toExternalForm(), 100);


    // MISCELLANEOUS COLORS
    public static final Color TRANSPARENT = new Color(0, 0, 0, .4);
    public static final Color INVISIBLE = new Color(0, 0, 0, 0);
    public static final Color RANGE_INDICATOR = new Color(.25, .25, .25, .3);
    public static final Color VICTORY = new Color(0, .2, 0, .5);
    public static final Color LOSS = new Color(.2, .02, .05, .5);
    public static final Color MENU_COLOR = new Color(.75, .7, .6, 1);
    public static final Color MENU_BORDER = new Color(.5, .4, .3, 1);
    public static final Color HEALTH_COLOR = new Color(.6, 0, 0, .7);
    public static final Color RED_RANGE = new Color(.5, .1, .2, .3);


    // MONKEY COLORS
    public static final Color BROWN_1 = new Color(0.588, 0.428, 0.322, 1);
    public static final Color BROWN_2 = new Color(0.488, 0.328, 0.222, 1);
    public static final Color BROWN_3 = new Color(0.388, 0.228, 0.122, 1);
    public static final Color RED_1 = new Color(.8, 0.15, 0.25, 1);
    public static final Color RED_2 = new Color(.6, 0.08, 0.15, 1);


    // BLOON COLORS
    public static final Color RED_COLOR = new Color(0.938, .3, .35, 1);
    public static final Color BLUE_COLOR = new Color(0.3, .7, 1, 1);
    public static final Color GREEN_COLOR = new Color(0.25, .9, .35, 1);
    public static final Color YELLOW_COLOR = new Color(1, 1, .5, 1);
    public static final Color YELLOW_BORDER = new Color(.9, .8, 0, 1);
    public static final Color PINK_COLOR = new Color(1, .65, .8, 1);
    public static final Color PINK_BORDER = new Color(0.9, .4, .55, 1);
    public static final Color BLACK_COLOR = new Color(0.15, .15, .15, 1);
    public static final Color CAMO_LIGHT = new Color(.45, .8, .5, 1);
    public static final Color CAMO_DARK = new Color(.15, .39, .25, 1);
}