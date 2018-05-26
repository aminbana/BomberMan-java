import java.awt.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Constants implements Saveable{

    static GameFrame gameFrame;

    static int total_x_blocks = 21;
    static int total_y_blocks = 11;

    static int block_size = 120;

    static int total_x_size = block_size * total_x_blocks;
    static int total_y_size = block_size * total_y_blocks;


    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    static int Prefered_Res_X = (int) screenSize.getWidth();//1920;
    static int Prefered_Res_Y = (int) screenSize.getHeight();//1080;

//    static int Prefered_Res_X = 1600;
//    static int Prefered_Res_Y = 768;


    final static int RightDir = -2;
    final static int LeftDir = -RightDir;
    final static int UpDir = 1;
    final static int DownDir = -UpDir ;



    static ArrayList<Monster> monsters = new ArrayList<>();

    static int Res_X = (Prefered_Res_X < total_x_size) ? Prefered_Res_X : total_x_size;
    static int Res_Y = (Prefered_Res_Y < total_y_size) ? Prefered_Res_Y : total_y_size;

    static int Aperture_Shift_Amount_X = 0;
    static int Aperture_Shift_Amount_Y = 0;


    static int MoveLeftValue = 1;
    static int MoveRightValue = 1;
    static int MoveUpValue = 1;
    static int MoveDownValue = 1;


    static int RepaintThreadSleep = 20;
    static int MovingPlayerSpeed = 5; //delay in ms

    static Player player;
    static Map map;

    static int Character_Width = 90;
    static int Character_Height = 140;
    static int MonsterWidth = 100;
    static int MonsterHeight = 100;

    static int ImageChangeAnimationRate = 8;


    static int Bomb_Height = 60;
    static int Bomb_Width = 60;

    static int BombHoldTime = 20;
    static int BombExplodingTime = 10;
    static int BreakWallsTime = BombExplodingTime / 2;

    static double BombAnimationChangeHoldImg = Constants.BombHoldTime / 19.0;;
    static double BombAnimationChangeExplodeImg = Constants.BombExplodingTime / 14.0;

    static Bomb bomb = null;

    static int density = 5; //5 menas each 5*5 cells has a monster

    Constants(){
        total_x_size = block_size * total_x_blocks;
        total_y_size = block_size * total_y_blocks;


        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Prefered_Res_X = (int) screenSize.getWidth();//1920;
        Prefered_Res_Y = (int) screenSize.getHeight();//1080;

//    static int Prefered_Res_X = 1600;
//    static int Prefered_Res_Y = 768;





        int Res_X = (Prefered_Res_X < total_x_size) ? Prefered_Res_X : total_x_size;
        int Res_Y = (Prefered_Res_Y < total_y_size) ? Prefered_Res_Y : total_y_size;



    }

    @Override
    public void save(PrintStream printStream) {

        printStream.println("");
        printStream.println(total_x_blocks);
        printStream.println(total_y_blocks);
        printStream.println(Aperture_Shift_Amount_X);
        printStream.println(Aperture_Shift_Amount_Y);
        printStream.println("");

    }


    @Override
    public void load(Scanner sc) {
        sc.nextLine();

        total_x_blocks = sc.nextInt();
        sc.nextLine();

        total_y_blocks = sc.nextInt();
        sc.nextLine();

        Aperture_Shift_Amount_X = sc.nextInt();
        sc.nextLine();

        Aperture_Shift_Amount_Y = sc.nextInt();
        sc.nextLine();

        sc.nextLine();
    }






}
