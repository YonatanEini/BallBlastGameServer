import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.css.RGBColor;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class program {
    public static List<Point> firstClientFireList = new LinkedList<>();
    public static Gson gson = new Gson();
    public static List<Ball> ballList = new LinkedList<>();
    public static boolean IsOnPause = false;
    public static DataDto firstClientData = new DataDto();
    public static DataDto secondClientData = new DataDto();
    public static  boolean IsGameOver = false;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<BallDto> ballsList = ConvertToDtoList(ballList);
        String jsonStr = gson.toJson(ballsList);
        RecceiveDataDto startingDtoObject = new RecceiveDataDto(firstClientData, ballsList);
        String dataStrJson = gson.toJson(startingDtoObject);
        ServerSocket server = new ServerSocket(8080);
        System.out.println("attempting connection");
        Socket firstClient = server.accept();
        System.out.println("first client in!");
        Socket secondClient = server.accept();
        System.out.println("second client in");
        InitializeConnection(firstClient, secondClient);
        new AnnoyingBeep();
        CheckPauseThread pauseChek = new CheckPauseThread();
        pauseChek.start();
        CheckGameOverThread overCheck = new CheckGameOverThread();
        overCheck.start();
    }

    public static void ReadClientOneData(Socket client) throws IOException, ClassNotFoundException {
        InputStream stsream = client.getInputStream();
        Type clientOutputData = new TypeToken<DataDto>() {}.getType();
        while(true)
        {
            //receive
            ObjectInputStream objectTransfer = new ObjectInputStream(stsream);
            DataDto outputClientData = new Gson().fromJson((String) objectTransfer.readObject(), clientOutputData);
            HandleCollestions(outputClientData.fire_listXY, 1);
            firstClientData = outputClientData;
            //sending back
            if (!outputClientData.IsGameOver) {
                List<BallDto> ballsList = ConvertToDtoList(ballList);
                RecceiveDataDto frameData = new RecceiveDataDto(secondClientData, ballsList);
                String jsonData = gson.toJson(frameData);
                SendClientOneData(client, jsonData);
            }
        }
    }
    public static void ReadSecondData(Socket client) throws IOException, ClassNotFoundException {
        InputStream stsream = client.getInputStream();
        Type clientOutputData = new TypeToken<DataDto>() {}.getType();
        while(true)
        {
            //receive
            ObjectInputStream objectTransfer = new ObjectInputStream(stsream);
            DataDto outputClientData = new Gson().fromJson((String) objectTransfer.readObject(), clientOutputData);
            HandleCollestions(outputClientData.fire_listXY, 2);
            secondClientData = outputClientData;
            //sending back
            if (!outputClientData.IsGameOver) {
                List<BallDto> ballsList = ConvertToDtoList(ballList);
                RecceiveDataDto frameDto = new RecceiveDataDto(firstClientData, ballsList);
                String jsonFrameData = gson.toJson(frameDto);
                SendSecondClientData(client, jsonFrameData);
            }
        }
    }
    public static void SendClientOneData(Socket client, Object obj) throws IOException, ClassNotFoundException
    {
        OutputStream stream = client.getOutputStream();
        ObjectOutputStream objectTransfer = new ObjectOutputStream(stream);
        objectTransfer.writeObject(obj);
    }
    public static void SendSecondClientData(Socket client, Object obj) throws IOException, ClassNotFoundException
    {
        OutputStream stream = client.getOutputStream();
        ObjectOutputStream objectTransfer = new ObjectOutputStream(stream);
        objectTransfer.writeObject(obj);
    }
    public  static void ActivateBallListOnConnection()
    {
        for (int i=0; i<ballList.size();i++)
        {
            ballList.get(i).start();
        }
    }
    public static void InitializeConnection(Socket firstClient, Socket secondClient){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ReadClientOneData(firstClient);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ReadSecondData(secondClient);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        ActivateBallListOnConnection();
        t1.start();
        t2.start();
    }
    public static  List<BallDto> ConvertToDtoList(List<Ball> balls)
    {
        List<BallDto> ballDtoList =  new LinkedList<>();
        for (int i=0; i<balls.size();i++){
            BallDto balldto = new BallDto(balls.get(i).x, balls.get(i).y, balls.get(i).HP, balls.get(i).width, balls.get(i).Rgb);
            ballDtoList.add(balldto);
        }
        return  ballDtoList;
    }
    public static void HandleCollestions(List<Point> fireList, int clientNumber){
        for(int i=0; i<ballList.size();i++){
            ballList.get(i).CheckCollesion(fireList);
            if (ballList.get(i).HP <= 0) {
                ballList.remove(ballList.get(i));

            }
        }
    }
    public static class AnnoyingBeep {
        Toolkit toolkit;
        Timer timer;

        public AnnoyingBeep() {
            toolkit = Toolkit.getDefaultToolkit();
            timer = new Timer();
            timer.schedule(new RemindTask(),
                    1000,        //initial delay
                    4 * 1000);  //subsequent rate
        }

        class RemindTask extends TimerTask {
            int counter=1;
            public void run() {
                if(IsOnPause || IsGameOver)
                    return;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException t) {
                    t.printStackTrace();
                }
                Ball b1 = new Ball(make_random_number()[0], make_random_number()[1], 50,1,Random_easy_HP() ,new int[] {255,255,50});
                ballList.add(b1);
                b1.start();
                counter++;
            }
        }
    }
    public static int[] make_random_number()
    {
        int[] values = new int[2];
        int x;
        Random random = new Random();
        x = random.nextInt(2);
        if (x == 1) {
            x = random.nextInt(500 / 4);
            values[1] = x;
        } else {
            x = random.nextInt(500 / 4);
            values[1] = x;
            values[0] = 1280;
        }
        return values;
    }
    public static int Random_easy_HP()
    {
        Random random = new Random();
        int x = random.nextInt(31);
        return x;
    }

}
