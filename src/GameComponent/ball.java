
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.Serializable;
import java.text.AttributedString;
import java.util.List;
import java.util.Random;
import java.util.Timer;

 class Ball extends Thread {
    public int level;
    public  int x,y,width;
    public int color;
    public int HP;
    public int dir_x;
    public int dir_y;
    public int Rgb [];
    public Ball(int x,int y,int width,int level, int hp, int [] rgb){
        this.x=x;
        this.y=y;
        this.width=width;
        this.HP=hp;
        this.level = level;
        this.dir_x = 1;
        this.dir_y = 1;
        this.color = 5;
        this.Rgb = rgb;
    }
    public Ball(int x,int y,int width,int level, int hp,int dir_x, int dir_y, int [] rgb){
        this.x=x;
        this.y=y;
        this.width=width;
        this.HP=hp;
        this.level = level;
        this.dir_x = dir_x;
        this.dir_y = dir_y;
        this.color = 5;
        this.Rgb = rgb;
    }
    public void run(){
        Make_start_movement();
        int heightScreen = 658;
        int widthScreen = 1281;
        this.setPriority(1);
        int dirx=this.dir_x,diry=this.dir_y;
        Make_start_movement();
        while (true) {
                if (this.HP <= 0) {
                    if (level == 1) {

                    } else {
                        SplitBall();
                    }
                    break;
                }
                if (x + width / 2 > widthScreen)
                    dirx = -1;

                if (x - width / 2 < 0)
                    dirx = 1;

                if (y + width / 2 > heightScreen) {
                    int x = HandleGroundHit(dirx);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    diry = 1;
                    dirx = x;
                }
                if (y - width / 2 < 0)
                    diry = 1;
                if (!program.IsOnPause) {
                    x += dirx;
                    y += diry;
                }

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
    public void Make_start_movement()
    {
        int widthScreen = 1281;
        int dir_x;
        if (this.x==widthScreen)
            dir_x=-1;
        else
            dir_x =1;
        if (dir_x==1) {
            while (this.x - this.width - 10 < 0) {
                if (!program.IsOnPause) {
                    this.x += dir_x;
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            while (this.x + this.width + 10 < widthScreen) {
                if (!program.IsOnPause) {
                    this.x += dir_x;
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int HandleGroundHit(int dir)
    {
        int heightScreen = 658;
        int widthScreen = 1281;
        int dir_x=0;
        if(level > 0)
        {
            int IncX=2;
            dir_x=dir;
            while(this.y >= (heightScreen/3)) {
                    if (x + width / 2 > widthScreen)
                        dir_x = -1;
                    if (x - width / 2 < 0)
                        dir_x = 1;
                      if(!program.IsOnPause) {
                          this.y -= 1;
                          if (IncX == 0) {
                              this.x += dir_x;
                              IncX = 2;
                          } else
                              IncX--;
                      }
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
        return dir_x;
    }
    public void SplitBall()
    {
        Ball ball_right = new Ball(this.x - this.width, this.y+10, 35,this.level -1 ,5 ,-1, 1, this.Rgb);
        Ball ball_left = new Ball(this.x + this.width, this.y+10, 35,this.level -1,5 ,1, 1, this.Rgb);
        ball_left.start();
        ball_right.start();
        program.ballList.add(ball_right);
        program.ballList.add(ball_left);
    }
     public  double distance(int a,int b){
         return Math.sqrt(Math.pow(a,2.0)+Math.pow(b,2.0));
     }
     public void CheckCollesion(List<Point> fireList ){
         for (int i = 0; i < fireList.size(); i++)
         {
             if((distance(fireList.get(i).x- this.x,fireList.get(i).y-this.y)<20/2+ this.width/2)) {
                 this.HP -= 1;
             }
         }
     }



 }
