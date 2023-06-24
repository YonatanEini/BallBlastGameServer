public class CheckPauseThread extends Thread {
    public void run() {
        while (true){

            if (program.firstClientData.IsPaused || program.secondClientData.IsPaused){
                program.IsOnPause = true;
            }
            else{
                program.IsOnPause = false;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
