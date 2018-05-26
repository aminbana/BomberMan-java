import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class GameFrame extends JPanel {


    GameFrame(){
        super();
    }

    @Override
    protected void paintComponent(Graphics G) {

        super.paintComponent(G);
        Constants.map.Paint(G);
        Constants.player.bomb.Paint(G);
        Constants.player.Paint(G);

        for (Monster monster:Constants.monsters){
            monster.Paint(G);
        }


    }


}