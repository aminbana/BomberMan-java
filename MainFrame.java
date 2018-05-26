import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainFrame extends JFrame{

    private JPanel contentPane;
    private GameFrame gameFrame;


    int total_x_blocks = Constants.total_x_blocks;
    int total_y_blocks = Constants.total_y_blocks;

    int block_size = Constants.block_size;

    int total_x_size = Constants.total_x_size;
    int total_y_size = Constants.total_y_size;

    Map map;
    Player player;
    Constants constants;


    Thread MovePlayerThread;
    Thread RefreshThread;

    ArrayList<Thread> MonsterManagers = new ArrayList<>();


    ArrayList <Saveable> saveables = new ArrayList<>();



    MainFrame(){
        super("Game");


        //Constants.map.printMap();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane , BoxLayout.X_AXIS));
        this.setSize(new Dimension(Constants.Res_X , Constants.Res_Y));
        gameFrame = new GameFrame ();



        contentPane.add(gameFrame);

        addKeyListeners();

        this.setResizable(false);
        this.setUndecorated(true);




        constants = new Constants();
        map = new Map();
        player = new Player();


        saveables.add(constants);
        saveables.add(map);
        saveables.add(player);

        Constants.gameFrame = gameFrame;
        Constants.player = this.player;
        Constants.map = this.map;

        initalMonsters ();

        MovePlayerThread = new Thread(new MovePlayerThread ());
        MovePlayerThread.start();


        for (Monster monster:Constants.monsters){
            Thread MonsterManager = new Thread(new MonsterManagerThread(monster));
            MonsterManager.start();
        }


        this.setVisible(true);




    }


    private void addKeyListeners (){
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                //if (!(player.movingLeft | player.movingRight | player.movingDown | player.movingUp)){
                System.out.println(e.getKeyCode());

                if (e.getKeyCode() == KeyEvent.VK_L)
                {
                    load();
                }

                if (e.getKeyCode() == KeyEvent.VK_S)
                {
                    save();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        player.movingDown = true;

                }


                if (e.getKeyCode() == KeyEvent.VK_UP) {
                        player.movingUp = true;
                }


                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        player.movingRight = true;
                }


                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        player.movingLeft = true;
                }


                if (e.getKeyCode() == KeyEvent.VK_B) {
                    player.WantstoPlaceBomb = true;


                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    player.movingDown = false;
                }


                if (e.getKeyCode() == KeyEvent.VK_UP){
                    player.movingUp = false;
                }



                if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    player.movingRight= false;
                }


                if (e.getKeyCode() == KeyEvent.VK_LEFT){
                    player.movingLeft = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_B){
                    player.WantstoPlaceBomb = false;
                }
            }
        });
    }


    public void save (){
//        Path currentRelativePath
//        currentRelativePath.toAbsolutePath().toString()
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/SavePath/"));
        int retrival = fileChooser.showSaveDialog(this);
        PrintStream printer = null;
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                printer=new PrintStream(fileChooser.getSelectedFile());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        for (Saveable saveable:saveables){
            saveable.save(printer);
        }

    }


    public void load (){
        synchronized (Constants.player) {
            JFileChooser fileChooser = new JFileChooser();
            int retrival = fileChooser.showOpenDialog(this);
            PrintStream printer = null;
            Scanner sc = null;
            try {
                sc = new Scanner(fileChooser.getSelectedFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            map = new Map();
            player = new Player();
            constants = new Constants();

            Constants.map = map;
            Constants.player = player;

            saveables = new ArrayList<>();

            saveables.add(constants);
            saveables.add(map);
            saveables.add(player);

            for (Saveable saveable : saveables) {
                saveable.load(sc);
            }
        }
    }


    public void initalMonsters (){
        int x_dir_count = Constants.total_x_blocks / Constants.density;
        int y_dir_count = Constants.total_y_blocks / Constants.density;

        int count = 0;

        for (int i = 0; i < x_dir_count; i++) {
            for (int j = 0; j < y_dir_count; j++) {
                Random random = new Random();
                int Monster_x = random.nextInt(Constants.density);
                int Monster_y = random.nextInt(Constants.density);

                Monster_x += Constants.density * i;
                Monster_y += Constants.density * j;

                while ((map.cells[Monster_x][Monster_y].type != CellType.Free) | (Monster_x < 3 &  Monster_y < 3)) {
                    Monster_y = random.nextInt(Constants.density);
                    Monster_x = random.nextInt(Constants.density);
                    Monster_x += Constants.density * i;
                    Monster_y += Constants.density * j;
                }

                int dir;
                if (Constants.map.cells[Monster_x - 1][Monster_y].type == CellType.Block & Constants.map.cells[Monster_x + 1][Monster_y].type == CellType.Block) {
                    dir = Constants.DownDir;
                } else {
                    dir = Constants.RightDir;
                }
                boolean direction = random.nextBoolean();

                if (direction) {
                    dir *= -1;
                }

                Monster monster = new Monster();
                monster.movingDir = dir;
                monster.Loc_x = Monster_x;
                monster.Loc_y = Monster_y;
                monster.Minor_Loc_x = Constants.MonsterWidth / 2;
                monster.Minor_Loc_y = Constants.MonsterHeight;
                monster.ID = count;
                count++;
                Constants.monsters.add(monster);
            }
        }
    }

}
