import java.awt.Toolkit;
import java.awt.*;

/**
 * Write a description of class gui here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class gui
{
    minimap map;
    int width, floor;
    public gui(int w)
    {
        width=w;
        floor=1;
    }
    public void drawGUI(Graphics2D g2, Player p){
        g2.setColor(Color.black);
        g2.fillRect(Toolkit.getDefaultToolkit().getScreenSize().width-width,0,width,Toolkit.getDefaultToolkit().getScreenSize().height);
        g2.setColor(Color.white);
        g2.drawString("Floor: "+floor,Toolkit.getDefaultToolkit().getScreenSize().width-width,Toolkit.getDefaultToolkit().getScreenSize().height-60);
        map.drawMap(g2, p);
    }
    public void addMap(minimap m){
        map=m;
    }
    
    
}
