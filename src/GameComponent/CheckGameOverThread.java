public class CheckGameOverThread extends Thread {
    public void run() {
        while (true){

            if (program.firstClientData.IsGameOver && program.secondClientData.IsGameOver){
                program.IsGameOver = true;
                program.ballList.clear();
            }
            else{
                program.IsGameOver = false;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
