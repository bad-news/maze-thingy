import java.awt.*;
import java.awt.image.*;
public class fakeGif implements drawable
{
    private sprite[] frames;
    private int frame;
    public fakeGif(sprite[] make)
    {
        frame=0;
        frames=make;
    }
    
    public void draw(Graphics g, int x, int y){
        frames[frame].draw(g,x,y);
        frame=(frame+1)%frames.length;
    }
}
