import java.awt.*;

public class BallDto {
    public Point CurrentPosition;
    public int Hp;
    public int Width;
    public int [] RgbColors;
    public BallDto()
    {
        this.CurrentPosition = new Point();
        this.Hp = 0;
        this.Width = 0;
        this.RgbColors = new int[3];
    }
    public BallDto(int x, int y,int hp, int width, int[] rgb){
        this.CurrentPosition  = new Point(x, y);
        this.Hp = hp;
        this.Width = width;
        this.RgbColors = rgb;
    }

}
