import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;


public class Cell {


    final int location_x , location_y;
    LinkedList<Monster> cellMonsters;
    CellType type;
    Cell(int location_x , int location_y , CellType type){
        this.location_x = location_x;
        this.location_y = location_y;
        this.type = type;
        cellMonsters = new LinkedList<>();
    }


}
