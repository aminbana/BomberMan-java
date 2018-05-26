import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Map implements Saveable{



    Cell[][] cells;
    BufferedImage Free_img , Block_img , Wall_img;


    Map (){

        cells = new Cell[Constants.total_x_blocks][Constants.total_y_blocks];
        for (int j = 0; j < Constants.total_y_blocks; j++){
            for (int i = 0; i < Constants.total_x_blocks; i++){
                CellType type = CellType.Free;



                Random r = new Random();
                if ((((r.nextInt() % 5) == 0)) & (!(i == 1 & j == 1))){
                    if ((i == 1 & j == 2) | (i == 1 & j == 3)) {
                        if ((!(cells[2][1].type == CellType.Wall)) & (!(cells[3][1].type == CellType.Wall))) {
                            type = CellType.Wall;
                        }
                    } else{
                        type = CellType.Wall;
                    }
                }



                if (i == 0) type = CellType.Block;
                else if (i == (Constants.total_x_blocks - 1)) type = CellType.Block;
                else if (j == 0) type = CellType.Block;
                else if (j == (Constants.total_y_blocks - 1)) type = CellType.Block;
                else if (((i % 2) == 0) & ((j % 2)== 0)) type = CellType.Block;;

                cells[i][j] = new Cell(i , j , type);

//                System.out.print(" (" + i +"," + j + "): " + cells[i][j].type);
            }
//            System.out.println("");
        }

        try {
            Free_img = ImageIO.read(new File("Images/Grass.jpg"));
            Block_img = ImageIO.read(new File("Images/Block.jpg"));
            Wall_img = ImageIO.read(new File("Images/Wall.jpg"));
        }
        catch (IOException ex) {
            System.out.println("Image error");
            ex.printStackTrace();
        }
    }

    public void printMap (){
        for (int i = 0; i < Constants.total_x_blocks; i++){
            for (int j = 0; j < Constants.total_y_blocks; j++){

                System.out.print(" (" + i +"," + j + "): " + cells[i][j].type);
            }
            System.out.println("");
        }
    }

    public void Paint (Graphics g){
        int paint_x;
        int paint_y;

        for (int i = 0; i < Constants.total_x_blocks; i++){
            for (int j = 0; j < Constants.total_y_blocks; j++){
                BufferedImage img;
                if (cells[i][j].type == CellType.Free)
                    img = Free_img;
                else if (cells[i][j].type == CellType.Block)
                    img = Block_img;
                else //if (cells[i][j].type == Type.Wall)
                    img = Wall_img;
                paint_x = Constants.block_size * i - Constants.Aperture_Shift_Amount_X;
                paint_y = Constants.block_size * j - Constants.Aperture_Shift_Amount_Y;
                g.drawImage(img , paint_x , paint_y, Constants.block_size , Constants.block_size , null);
            }
        }

    }


    @Override
    public void save(PrintStream printStream) {
        printStream.println();
        for (int i = 0; i < Constants.total_x_blocks; i++){
            for (int j = 0; j < Constants.total_y_blocks; j++){
                printStream.println(cells[i][j].type);
            }
        }
        printStream.println("");
    }


    @Override
    public void load(Scanner sc) {
        sc.nextLine();

        for (int i = 0; i < Constants.total_x_blocks; i++){
            for (int j = 0; j < Constants.total_y_blocks; j++){
                String str = sc.nextLine();
                if (str.equals("Block")){
                    cells[i][j].type = CellType.Block;
                } else if (str.equals("Wall")){
                    cells[i][j].type = CellType.Wall;
                } else {
                    cells[i][j].type = CellType.Free;
                }
            }
        }
        sc.nextLine();

    }
}
