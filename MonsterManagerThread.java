import java.util.ArrayList;

public class MonsterManagerThread implements Runnable {

    Monster monster;

    public MonsterManagerThread(Monster monster) {
        this.monster = monster;
    }

    @Override
    public void run() {
//        System.out.println("Monster Thread");
        while (true){
            int previous_x = monster.Loc_x;
            int previous_y = monster.Loc_y;
            monster.move ();
            if (monster.Loc_x != previous_x | monster.Loc_y != previous_y){
                monster.updateMap(previous_x , previous_y);
            }


            try {
                Thread.sleep(Constants.MovingPlayerSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Constants.gameFrame.repaint();
        }
    }
}
