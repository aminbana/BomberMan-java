import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Bomb implements Saveable{
    int Location_x;
    int Location_y;
    boolean isEnable = false;
    boolean isExploding = false;

    int block_size = Constants.block_size;
    int Bomb_Height = Constants.Bomb_Height;
    int Bomb_Width= Constants.Bomb_Width;

    int ImgIndex = 0;
    BufferedImage Bomb_Img;

    Thread controlThread;
    Player player;
    Bomb(){

        this.player = Constants.player;
        try {
            Bomb_Img = ImageIO.read(new File("Images/Bomb_0.png"));
        } catch (Exception e){
            e.printStackTrace();
        }

        block_size = Constants.block_size;
        Bomb_Height = Constants.Bomb_Height;
        Bomb_Width= Constants.Bomb_Width;


        block_size = Constants.block_size;
        Bomb_Height = Constants.Bomb_Height;
        Bomb_Width= Constants.Bomb_Width;
        ImgIndex = 0;

        ImgIndex = 0;

    }


    public void Paint (Graphics g){

        if (isEnable & !isExploding){
            g.drawImage(Bomb_Img ,Location_x*block_size + 10 - Constants.Aperture_Shift_Amount_X ,Location_y*block_size + 10 - Constants.Aperture_Shift_Amount_Y ,block_size -20 , block_size -20 , null);//(int) (ImageScaleFactor * Bomb_Width) , (int) (ImageScaleFactor * Bomb_Height) , null);
        } else if (isExploding){
            g.drawImage(Bomb_Img ,(Location_x - 2) *block_size  - Constants.Aperture_Shift_Amount_X ,(Location_y - 2)*block_size - Constants.Aperture_Shift_Amount_Y ,5 * block_size  , 5 * block_size  , null);//(int) (ImageScaleFactor * Bomb_Width) , (int) (ImageScaleFactor * Bomb_Height) , null);
        }

    }


    public void ChangePic (){
        if (isEnable & (!isExploding)){
            try {
                Bomb_Img = ImageIO.read(new File("Images/Bomb_" + ImgIndex + ".png"));

            } catch (Exception e){

                System.out.println("Images/Bomb_" + ImgIndex + ".png");
                //e.printStackTrace();
            }
        } else if (isExploding){
            try {
                Bomb_Img = ImageIO.read(new File("Images/Expl_" + ImgIndex + ".png"));

            } catch (Exception e){

                System.out.println("Images/Expl_" + ImgIndex + ".png");
                //e.printStackTrace();
            }
        }
    }

    public void PlaceBomb (int x , int y) {
        isEnable = true;
        isExploding = false;
        Location_x = x;
        Location_y = y;
        ImgIndex = 0;

        try {
            Bomb_Img = ImageIO.read(new File("Images/Bomb_" + ImgIndex + ".png"));
        } catch (Exception e){
            e.printStackTrace();
        }
        controlThread = new Thread(new BombThread());
        controlThread.start();
    }

    @Override
    public void save(PrintStream printStream) {
        printStream.println("");
        printStream.println(Location_x);
        printStream.println(Location_y);
        printStream.println(isEnable);
        printStream.println(isExploding);
        printStream.println("");
    }

    @Override
    public void load(Scanner sc) {

        sc.nextLine();

        Location_x = sc.nextInt();
        sc.nextLine();;
        Location_y = sc.nextInt();
        sc.nextLine();

        isEnable = sc.nextBoolean();
        sc.nextLine();

        isExploding = sc.nextBoolean();
        sc.nextLine();

        if (isEnable | isExploding){
            controlThread = new Thread(new BombThread());
            controlThread.start();
        }

        sc.nextLine();
    }
}
