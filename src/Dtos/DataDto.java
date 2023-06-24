import javax.xml.crypto.Data;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataDto implements Serializable {
    private static final long serialVersionUID =1L;
    public  boolean IsPaused;
    public  Point cannonPos;
    public java.util.List<Point> fire_listXY = new ArrayList<Point>();
    public int score;
    public boolean IsGameOver;
    public boolean NewGameRequest;

    public DataDto(){
        this.IsPaused = false;
        this.cannonPos = new Point();
        this.fire_listXY = new ArrayList<>();
        this.IsGameOver = false;
        this.score = 0;
        this.NewGameRequest = false;
    }
    public DataDto(boolean is, Point c, List<Point>Fires, boolean IsGame) {
        this.IsPaused = is;
        this.cannonPos = c;
        this.fire_listXY = Fires;
        this.IsGameOver = IsGame;
    }
}
