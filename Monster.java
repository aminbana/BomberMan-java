import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Monster {

    int ID;

    int Minor_Loc_x;
    int Minor_Loc_y;

    int MonsterWidth = Constants.MonsterWidth;
    int MonsterHeight = Constants.MonsterHeight;

    int Loc_x;
    int Loc_y;

    int block_size = Constants.block_size;


    int movingDir = 0;


    int ImgAnimationCounter;

    boolean isDead = false;

    BufferedImage MonsterImg;



    Monster(){

        this.Loc_x = 1;
        this.Loc_y = 1;

        movingDir = 0;

        MonsterWidth = Constants.MonsterWidth;
        MonsterHeight = Constants.MonsterHeight;

        this.Minor_Loc_x = MonsterWidth / 2;
        this.Minor_Loc_y = MonsterHeight;


        ImgAnimationCounter = 0;

        block_size = Constants.block_size;

        try {
            MonsterImg = ImageIO.read (new File("Images/Monster.png"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    public void Paint (Graphics g){
        int painting_x;
        int painting_y;
        painting_x = Loc_x*block_size + Minor_Loc_x - MonsterWidth / 2 - Constants.Aperture_Shift_Amount_X;
        painting_y = Loc_y*block_size + Minor_Loc_y - MonsterHeight  - Constants.Aperture_Shift_Amount_Y;
        g.drawImage(MonsterImg ,painting_x , painting_y ,MonsterWidth , MonsterHeight , null);
        g.drawImage(MonsterImg , Loc_x * block_size + Minor_Loc_x - Constants.Aperture_Shift_Amount_X , Loc_y * block_size + Minor_Loc_y - Constants.Aperture_Shift_Amount_Y, 10 , 10 , null);
    }


    public void move (){
//        System.out.println("moving Dir( " + ID + ") :" + movingDir  );
//        System.out.println("Locat ( " + Loc_x + ", " + Loc_y + ")");
//        System.out.println("Minor ( " + Minor_Loc_x + ", " + Minor_Loc_y + ")");

        if (movingDir == Constants.RightDir){
            MoveRight();
//            System.out.println("Moving Right");
        } else if (movingDir == Constants.LeftDir){
            MoveLeft ();
//            System.out.println("Moving Left");
        } else if (movingDir == Constants.UpDir){
            MoveUp ();
//            System.out.println("Moving Up");
        } else if (movingDir == Constants.DownDir){
            MoveDown ();
//            System.out.println("Moving Down");
        } else {
            return;
        }

//        System.out.println("   *********    ");
    }


    public void MoveRight (){

        if (Minor_Loc_x + MonsterWidth/2 + Constants.MoveRightValue  > block_size) {
            if (Constants.map.cells[Loc_x+1][Loc_y].type != CellType.Free) {
                movingDir = -movingDir;
                return;
            }
        }

        if (this.Minor_Loc_x + Constants.MoveRightValue > block_size){
            Minor_Loc_x = Minor_Loc_x + Constants.MoveRightValue - block_size;
            Loc_x += 1;
        } else {
            Minor_Loc_x = Minor_Loc_x + Constants.MoveRightValue;
        }
    }


    public void MoveLeft (){


        if ( Minor_Loc_x  - Constants.MoveLeftValue - MonsterWidth /2 < 0) {
            if (Constants.map.cells[Loc_x-1][Loc_y].type != CellType.Free) {
                movingDir *= -1;
                return;
            }
        }

        if (this.Minor_Loc_x - Constants.MoveLeftValue < 0){
            Minor_Loc_x = block_size + Minor_Loc_x - Constants.MoveLeftValue ;
            Loc_x -= 1;
        } else {
            Minor_Loc_x =  Minor_Loc_x - Constants.MoveLeftValue;
        }

    }


    public void MoveUp (){

        if ( Minor_Loc_y  - Constants.MoveUpValue < 0) {
            if (Constants.map.cells[Loc_x][Loc_y-1].type != CellType.Free) {
                movingDir *= -1;
                return;
            }
        }

        if (Minor_Loc_y - Constants.MoveUpValue < 0){
            Minor_Loc_y = block_size - Constants.MoveUpValue + Minor_Loc_y;
            Loc_y -= 1;
        } else {
            Minor_Loc_y =  Minor_Loc_y - Constants.MoveUpValue;
        }

    }

    public void MoveDown (){

        if ( Minor_Loc_y + Constants.MoveDownValue  >  block_size) {

            if (Constants.map.cells[Loc_x][Loc_y+1].type != CellType.Free) {
                movingDir *= -1;
                return;
            }
        }

        if (Minor_Loc_y + Constants.MoveDownValue > block_size) {
            Minor_Loc_y = Minor_Loc_y + Constants.MoveDownValue - block_size;
            Loc_y += 1;
        } else {
            Minor_Loc_y = Minor_Loc_y + Constants.MoveDownValue;
        }

    }

    public void updateMap (int previous_x , int previous_y){
        Constants.map.cells[Loc_x][Loc_y].cellMonsters.add(this);
        Constants.map.cells[previous_x][previous_y].cellMonsters.remove(this);
    }
}
