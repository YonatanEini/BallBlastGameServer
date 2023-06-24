import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecceiveDataDto implements Serializable {
    private static final long serialVersionUID =1L;
    public  boolean IsPaused;
    public Point cannonPos;
    public List<Point> fire_listXY;
    public List<BallDto> current_ballList;
    public int score;
    public boolean IsGameOver;
    public boolean NewGameRequest;

    public RecceiveDataDto(){
        this.IsPaused = false;
        this.cannonPos = new Point();
        this.fire_listXY = new ArrayList<>();
        this.current_ballList = new ArrayList<>();
        this.IsGameOver = false;
    }
    public RecceiveDataDto(boolean pause, Point cannon, List<Point>currentFire, List<BallDto> current_ballList){
        this.IsPaused = pause;
        this.cannonPos = cannon;
        this.fire_listXY = currentFire;
        this.current_ballList = current_ballList;
    }
    public RecceiveDataDto(DataDto clientData, List<BallDto> ballList){
        this.IsPaused = clientData.IsPaused;
        this.cannonPos = clientData.cannonPos;
        this.fire_listXY = clientData.fire_listXY;
        this.current_ballList = ballList;
        this.IsGameOver = clientData.IsGameOver;
        this.score = clientData.score;
        this.NewGameRequest = clientData.NewGameRequest;
    }
}
