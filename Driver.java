import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.Toolkit;
public class Driver extends Canvas implements Runnable
{
    //Declare game objects/vars
    public spriteSheet textures;
    public Player player;
    public drawWalls screen;
    public static int[][] map;
    public gui UI;
    //Declare program objects/vars
    private Thread t;
    private Graphics bufferGraphics = null; //The graphics for the backbuffer
    private BufferStrategy bufferStrategy = null; //The strategy the app uses
    private boolean running;
    private Dimension size;
    int height, width, guiW;

    public Driver(Dimension size)
    {
        this.size=size;
        //Create map
        int dim=11;//Integer.parseInt(JOptionPane.showInputDialog(null,"How large would you like your maze to be?"));
        height = 3+2*dim;
        width = 3+2*dim;

        //Set up the GUI
        guiW = 256;//Sets the width of the GUI
        UI = new gui(guiW);
        //Set the program up to run
        t = new Thread(this);
        running = true;
        setPreferredSize(size);
        //Initialize all variables and objects below here
        textures=new spriteSheet(1024,1024,1,5,"textures.png");
        genMaze();
    }

    protected void genMaze(){
        mazeGen maze= new mazeGen(height, width);
        map = maze.gen();
        UI.addMap(new minimap(map,guiW,maze.solution()));
        init();
        screen = new drawWalls(map, width, height, textures, size.width-guiW, size.height);
    }

    private void init(){
        Camera camera = new Camera(2.5, 2.5, 0, 0, -.66, map);
        player = new Player(camera);
        addKeyListener(player);
    }

    public void run()//This puts the thread to work by giving it actions to carry out
    {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 30.0;//60 times per second
        double delta = 0;
        requestFocus();
        while(running)
        {
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;
            while (delta >= 1)//Make sure update is only happening 60 times a second
            {
                //handles all of the logic restricted time
                player.update();
                if(player.map[(int)player.yPos][(int)player.xPos]==3){
                    genMaze();
                    //System.out.println("Should generate a new maze");
                    UI.floor++;
                }
                delta--;
            }

            screen.update(player, (Graphics2D)bufferStrategy.getDrawGraphics());
            UI.drawGUI((Graphics2D)bufferStrategy.getDrawGraphics(),player);
            DrawBackbufferToScreen();
            try{
                //t.sleep(10);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void DoLogic()
    {
        //Calculations of the program involving motion, collision detection, etc.
    }

    public void Draw(Color c, int d)//this can be used to draw static things (things that will not change)
    {
        bufferGraphics = bufferStrategy.getDrawGraphics();
        try{
            //This is where you will draw to the backbuffer
        }
        catch(Exception e){
            //In case something goes wrong
            e.printStackTrace();
        }
        finally{
            bufferGraphics.dispose();
        }
    }

    public void DrawBackbufferToScreen()//This draws everything you've added to the buffer then clears the buffered graphics
    {
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
        bufferStrategy.getDrawGraphics().clearRect(0, 0, getSize().width, getSize().height);
    }

    public void paint(Graphics g)//Don't mess with this method,
    {
        if(bufferStrategy == null)
        {
            createBufferStrategy(3);
            bufferStrategy = getBufferStrategy();
            bufferGraphics = bufferStrategy.getDrawGraphics();
            t.start();
        }
        Graphics2D g2 = (Graphics2D)g;
    }
}
