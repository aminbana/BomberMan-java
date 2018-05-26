public class BombThread implements Runnable{
    Bomb bomb;
    Map map;
    Player player;
    int miliSeconds = 0;
    BombThread(){
        this.bomb = Constants.bomb;
        this.player = Constants.player;
        map = Constants.map;
    }
    @Override
    public void run() {
        map = Constants.map;

        while (bomb.isEnable) {
            try {
                Thread.sleep(100);
                miliSeconds++;
            } catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("ms:" + miliSeconds);
            System.out.println(Constants.BombAnimationChangeExplodeImg);
            int test = (int) ((bomb.ImgIndex + 1) * Constants.BombAnimationChangeHoldImg);
            System.out.println("Threshold: " + test);
            if (miliSeconds >= (int) ((bomb.ImgIndex + 1) * Constants.BombAnimationChangeHoldImg)){
                System.out.println("idx = " + bomb.ImgIndex + " ms = " + miliSeconds);
                bomb.ImgIndex++;
                bomb.ChangePic();
                Constants.gameFrame.repaint();
            }
            if (miliSeconds >= Constants.BombHoldTime){
                miliSeconds = 0;
                bomb.isEnable = false;
                bomb.isExploding = true;
            }

        }

        bomb.ImgIndex = 0;
        bomb.ChangePic();

        while (bomb.isExploding){
            try {
                Thread.sleep(100);
                miliSeconds++;
            } catch (Exception e){
                e.printStackTrace();
            }

            if (miliSeconds >= (int) ((bomb.ImgIndex + 1) * Constants.BombAnimationChangeExplodeImg)){
                System.out.println("idx = " + bomb.ImgIndex + " ms = " + miliSeconds);
                bomb.ImgIndex++;
                bomb.ChangePic();
                Constants.gameFrame.repaint();
            }

            if (miliSeconds > Constants.BreakWallsTime){

                this.DestroyWalls();
            }

            if (miliSeconds >= Constants.BombExplodingTime){
                miliSeconds = 0;
                bomb.isEnable = false;
                bomb.isExploding = false;
                break;
            }
        }

        bomb.ImgIndex = 0;


    }


    public void DestroyWalls (){
//        System.out.println(bomb.Location_x);
//        System.out.println(bomb.Location_y);
//        System.out.println(map.cells[bomb.Location_x - 1][bomb.Location_y].type);
//        System.out.println(Constants.map.cells[bomb.Location_x - 1][bomb.Location_y].type);

        if (map.cells[bomb.Location_x - 1][bomb.Location_y].type == CellType.Wall)
            map.cells[bomb.Location_x - 1][bomb.Location_y].type = CellType.Free;
        if (map.cells[bomb.Location_x + 1][bomb.Location_y].type == CellType.Wall)
            map.cells[bomb.Location_x + 1][bomb.Location_y].type = CellType.Free;
        if (map.cells[bomb.Location_x][bomb.Location_y + 1].type == CellType.Wall)
            map.cells[bomb.Location_x][bomb.Location_y + 1].type = CellType.Free;
        if (map.cells[bomb.Location_x][bomb.Location_y - 1].type == CellType.Wall)
            map.cells[bomb.Location_x][bomb.Location_y - 1].type = CellType.Free;
    }

}
