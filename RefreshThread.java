public class RefreshThread implements Runnable{
    GameFrame gameFrame;
    RefreshThread (GameFrame gameFrame){
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        while (true){
                gameFrame.repaint();
            try {
                Thread.sleep(Constants.RepaintThreadSleep);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
