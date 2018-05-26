import jdk.nashorn.internal.ir.Block;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Player implements Saveable{
    int Lives;
    String name;
    int Minor_Loc_x;
    int Minor_Loc_y;

    int Character_Width = Constants.Character_Width;
    int Character_Height = Constants.Character_Height;

    int Loc_x;
    int Loc_y;

    int block_size = Constants.block_size;

    //boolean killPlayer = false;

    boolean movingRight;
    boolean movingUp;
    boolean movingDown;
    boolean movingLeft;
    static int ImgAnimationCounter;

    boolean WantstoPlaceBomb;

    int Last_Direction = 0;

    Bomb bomb;
    BufferedImage Player_img;

    Player(){
        this.Lives = 3;
        this.name = "Amin";
        this.Loc_x = 1;
        this.Loc_y = 1;

        Constants.player = this;

        this.Minor_Loc_x = Character_Width / 2;
        this.Minor_Loc_y = Character_Height;

        Character_Width = Constants.Character_Width;
        Character_Height = Constants.Character_Height;


        ImgAnimationCounter = 0;

        movingRight = false;
        movingUp = false;
        movingDown = false;
        movingLeft = false;

        WantstoPlaceBomb = false;


        block_size = Constants.block_size;

        try {
            Player_img = ImageIO.read(new File("Images/Player_Right_0.png"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        Constants.bomb = new Bomb();
        this.bomb = Constants.bomb;

    }

    public void placeBomb (){
      //  System.out.println(this.bomb.isEnable | this.bomb.isExploding);
        if (!(this.bomb.isEnable | this.bomb.isExploding))
        {
            this.bomb.PlaceBomb(this.Loc_x , this.Loc_y);
        }
    }


    public void Paint (Graphics g){
        int painting_x;
        int painting_y;
        painting_x = Loc_x*block_size + Minor_Loc_x - Character_Width / 2 - Constants.Aperture_Shift_Amount_X;
        painting_y = Loc_y*block_size + Minor_Loc_y - Character_Height  - Constants.Aperture_Shift_Amount_Y;
        g.drawImage(Player_img ,painting_x , painting_y ,Character_Width , Character_Height , null);
        g.drawImage(Player_img , Loc_x * block_size + Minor_Loc_x - Constants.Aperture_Shift_Amount_X, Loc_y * block_size + Minor_Loc_y - Constants.Aperture_Shift_Amount_Y, 10 , 10 , null);
    }

    public void MoveLeft (){
        try {
            if (ImgAnimationCounter >= 10 * Constants.ImageChangeAnimationRate ){
                ImgAnimationCounter = 0;
            }
            Player_img = ImageIO.read(new File("Images/Player_Left_" + ImgAnimationCounter/Constants.ImageChangeAnimationRate + ".png"));
            ImgAnimationCounter++;

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        if ((Loc_x * block_size + Minor_Loc_x ) - Constants.MoveLeftValue - Character_Width/2 < ((Loc_x) * block_size)) {
//            System.out.println(Constants.map.cells[Loc_x][Loc_y-1].type);
//            System.out.println(Loc_y);
            if (Constants.map.cells[Loc_x-1][Loc_y].type != CellType.Free)
                return;
        }

        if ((Loc_x*block_size + Minor_Loc_x < Constants.Aperture_Shift_Amount_X + (Constants.Res_X/3))){
            if (Constants.Aperture_Shift_Amount_X - Constants.MoveLeftValue > 0) {
                Constants.Aperture_Shift_Amount_X -= Constants.MoveLeftValue;
            } else Constants.Aperture_Shift_Amount_X = 0;
        }


        if (this.Minor_Loc_x - Constants.MoveLeftValue < 0){
            Minor_Loc_x = block_size - Constants.MoveLeftValue + Minor_Loc_x;
            Loc_x -= 1;
        } else {
            Minor_Loc_x =  Minor_Loc_x - Constants.MoveLeftValue;
        }

        Last_Direction = Constants.LeftDir;
    }

    public void MoveRight (){


        try {
            if (ImgAnimationCounter >= 10 * Constants.ImageChangeAnimationRate ){
                ImgAnimationCounter = 0;
            }
            Player_img = ImageIO.read(new File("Images/Player_Right_" + ImgAnimationCounter/Constants.ImageChangeAnimationRate + ".png"));
            ImgAnimationCounter++;

        } catch (IOException ex) {
            ex.printStackTrace();
        }


//        System.out.println("Y = " + Loc_y + " Minor Y = " + Minor_Loc_y);
//        System.out.println("X = " + Loc_x + " Minor X = " + Minor_Loc_x);
//        System.out.println((Loc_x * block_size + Minor_Loc_y ) + Character_Width/2 + Constants.MoveRightValue);

        if ((Loc_x * block_size + Minor_Loc_x ) + Character_Width/2 + Constants.MoveRightValue  > ((Loc_x+1) * block_size)) {

            if (Constants.map.cells[Loc_x+1][Loc_y].type != CellType.Free)
                return;
        }

        if ((Loc_x*block_size + Minor_Loc_x > Constants.Aperture_Shift_Amount_X + (Constants.Res_X/3*2))){
            if (Constants.Aperture_Shift_Amount_X + Constants.MoveRightValue < Constants.total_x_size - Constants.Res_X) {
                Constants.Aperture_Shift_Amount_X += Constants.MoveRightValue;
            } else Constants.Aperture_Shift_Amount_X = Constants.total_x_size - Constants.Res_X;
        }

        if (this.Minor_Loc_x + Constants.MoveRightValue > block_size){
            Minor_Loc_x = this.Minor_Loc_x + Constants.MoveRightValue - block_size;
            Loc_x += 1;
        } else {
            Minor_Loc_x = this.Minor_Loc_x + Constants.MoveRightValue;
        }

        Last_Direction = Constants.RightDir;

    }

    public void MoveUp (){
        try {
            if (ImgAnimationCounter >= 8 * Constants.ImageChangeAnimationRate ){
                ImgAnimationCounter = 0;
            }
            Player_img = ImageIO.read(new File("Images/Player_Up_" + ImgAnimationCounter/Constants.ImageChangeAnimationRate + ".png"));
            ImgAnimationCounter++;

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        if ((Loc_y * block_size + Minor_Loc_y ) - Constants.MoveUpValue - 10 < ((Loc_y) * block_size)) {
//            System.out.println(Constants.map.cells[Loc_x][Loc_y-1].type);
//            System.out.println(Loc_y);
            if (Constants.map.cells[Loc_x][Loc_y-1].type != CellType.Free)
                return;
        }




        if ((Loc_y*block_size + Minor_Loc_y < Constants.Aperture_Shift_Amount_Y + (Constants.Res_Y/3))){
            if (Constants.Aperture_Shift_Amount_Y - Constants.MoveUpValue > 0) {
                Constants.Aperture_Shift_Amount_Y -= Constants.MoveUpValue;
            } else Constants.Aperture_Shift_Amount_Y = 0;
        }


        if (Minor_Loc_y - Constants.MoveUpValue < 0){
            Minor_Loc_y = block_size - Constants.MoveUpValue + Minor_Loc_y;
            Loc_y -= 1;
        } else {
            Minor_Loc_y =  Minor_Loc_y - Constants.MoveUpValue;
        }


        Last_Direction = Constants.UpDir;

    }

    public void MoveDown (){
//        System.out.println(Constants.map.cells[Loc_x][Loc_y+1].type);
//        System.out.println("Y = " + Loc_y + " Minor Y = " + Minor_Loc_y);
//        System.out.println("X = " + Loc_x + " Minor X = " + Minor_Loc_x);


        try {
            if (ImgAnimationCounter >= 8 * Constants.ImageChangeAnimationRate) {
                ImgAnimationCounter = 0;
            }
            Player_img = ImageIO.read(new File("Images/Player_Down_" + ImgAnimationCounter / Constants.ImageChangeAnimationRate + ".png"));
            ImgAnimationCounter++;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if ((Loc_y * block_size + Minor_Loc_y ) + Constants.MoveDownValue  > ((Loc_y+1) * block_size)) {

            if (Constants.map.cells[Loc_x][Loc_y+1].type != CellType.Free)
                return;
        }


//        System.out.println(" " + Constants.Res_Y + " " + (Constants.Res_Y*1/2));
//        System.out.println(Constants.total_y_size - Constants.Res_Y);

        if (((Loc_y * block_size + Minor_Loc_y) > Constants.Aperture_Shift_Amount_Y + (Constants.Res_Y / 2))) {
            if (Constants.Aperture_Shift_Amount_Y + Constants.MoveDownValue < Constants.total_y_size - Constants.Res_Y) {
                Constants.Aperture_Shift_Amount_Y += Constants.MoveDownValue;
            } else Constants.Aperture_Shift_Amount_Y = Constants.total_y_size - Constants.Res_Y;
        }

        if (this.Minor_Loc_y + Constants.MoveDownValue > block_size) {
            Minor_Loc_y = this.Minor_Loc_y + Constants.MoveDownValue - block_size;
            Loc_y += 1;
        } else {
            Minor_Loc_y = this.Minor_Loc_y + Constants.MoveDownValue;
        }

        Last_Direction = Constants.DownDir;
    }


    @Override
    public void save(PrintStream printStream) {
        printStream.println("");
        printStream.println(Lives);
        printStream.println(name);
        printStream.println(Minor_Loc_x);
        printStream.println(Minor_Loc_y);
        printStream.println(Loc_x);
        printStream.println(Loc_y);
        printStream.println("");
        bomb.save(printStream);
    }

    @Override
    public void load(Scanner sc) {

        sc.nextLine();

        Lives = sc.nextInt();
        sc.nextLine();

        name = sc.nextLine();

        Minor_Loc_x = sc.nextInt();
        sc.nextLine();
        Minor_Loc_y = sc.nextInt();
        sc.nextLine();

        Loc_x = sc.nextInt();
        sc.nextLine();

        Loc_y = sc.nextInt();
        sc.nextLine();

        sc.nextLine();

        bomb.load(sc);

        System.out.println("Load Done");
    }


    public void CheckAccident(){
        if (Last_Direction == Constants.RightDir){

        }
    }

    public void killPlayer (){
        Lives--;
        Loc_x = 1;
        Loc_y = 1;

        Minor_Loc_x = Character_Height;
        Minor_Loc_y = Character_Width / 2;

    }
}
