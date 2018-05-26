public class MovePlayerThread implements Runnable {

    GameFrame gameFrame;
    MovePlayerThread(){
        super();


    }

    @Override
    public void run() {
        gameFrame = Constants.gameFrame;
        while (true) {
            //if ()

            if (Constants.player.movingRight)
            {
                Constants.player.MoveRight();
                gameFrame.repaint();
            }
            else if (Constants.player.movingLeft) {
                Constants.player.MoveLeft();
                gameFrame.repaint();
            }
            else if (Constants.player.movingDown){
                Constants.player.MoveDown();
                gameFrame.repaint();
            }
            else if (Constants.player.movingUp) {
                Constants.player.MoveUp();
                gameFrame.repaint();
            }
            if (Constants.player.WantstoPlaceBomb){
                Constants.player.placeBomb  ();
                gameFrame.repaint();
            }
            synchronized (Constants.player){
                Constants.player.CheckAccident();
            }

            try {
                Thread.sleep(Constants.MovingPlayerSpeed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
