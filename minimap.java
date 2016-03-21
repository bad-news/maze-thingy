import java.util.Stack;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.BasicStroke;
//Imports for Compass
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * Write a description of class minimap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class minimap
{
    private Color[] mmCols;
    private Stack<Point> solution;
    private int[][] map;
    private int width;
    private Image compass, torch;
    public minimap(int[][] m, int width, Stack<Point> s)
    {
        mmCols = new Color[]{Color.black,Color.gray,Color.DARK_GRAY,Color.red,Color.green,Color.white};
        map=m;
        solution=s;
        this.width=width;
        BufferedImage tCompass, tTorch;
        try {                
            tCompass = ImageIO.read(new File("compass.png"));
            compass=tCompass.getScaledInstance(width,width,2);
            tTorch = ImageIO.read(new File("torch.png"));
            torch=tTorch.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width-width,Toolkit.getDefaultToolkit().getScreenSize().height,2);
        } catch (IOException ex) {
            // handle exception...
        }

    }

    public void drawMap(Graphics2D g2, Player player){

        for(int i = 0; i<map.length;i++){
            for(int i2 = 0; i2<map[i].length;i2++){
                g2.setColor(mmCols[map[i][i2]]);
                g2.fillRect(Toolkit.getDefaultToolkit().getScreenSize().width-10*(map[i].length-i2),i*10,10,10);
            }
        }
        drawSolution(g2);
        g2.setColor(Color.cyan);
        g2.fillRect(Toolkit.getDefaultToolkit().getScreenSize().width-10*(map[(int)player.yPos].length-(int)player.xPos),(int)player.yPos*10,10,10);
        drawDir(g2, player);
    }

    public void drawSolution(Graphics2D g2){
        g2.setColor(Color.orange);
        Stack<Point> s = new Stack<Point>();
        while(solution.size()>0){
            Point tmp=solution.pop();
            s.push(tmp);
            g2.fillRect(Toolkit.getDefaultToolkit().getScreenSize().width-10*(map[tmp.x].length-tmp.y),tmp.x*10,10,10);
        }
        while(s.size()>0){
            solution.push(s.pop());
        }
    }

    public void drawDir(Graphics2D g2, Player player){
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(4));
        g2.drawImage(torch,0,0,null);
        AffineTransform rot= new AffineTransform();
        rot.translate(Toolkit.getDefaultToolkit().getScreenSize().width-(width/2), Toolkit.getDefaultToolkit().getScreenSize().height/2);
        rot.rotate(Math.toRadians(-player.dir-90));
        g2.setTransform(rot);
        g2.drawImage((Image)compass,(int)(-width/2),(int)(-width/2),null);
        rot.rotate(Math.toRadians(-(-player.dir-90)+180));
        g2.setTransform(rot);
        g2.drawLine(0,0,0,(int)(width/3));
        g2.drawLine(0,(int)(width/3),25,(int)(width/6));
        g2.drawLine(0,(int)(width/3),-25,(int)(width/6));
        g2.dispose();

    }
}
