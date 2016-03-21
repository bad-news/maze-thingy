import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.util.
public class Camera implements KeyListener{
    //Initialize player stuff
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public int dir;
    public int turnSize=9;
    public int[][] map;
    public boolean left, right, forward, back, turningLeft, turningRight, movingForward, movingBack;
    public final double MOVE_SPEED = .0625;
    public final double ROTATION_SPEED = Math.toRadians(turnSize);
    public Camera(double x, double y, int d, double xp, double yp, int[][] m) {
        xPos = x;
        yPos = y;
        dir=d;
        updateDirs();
        xPlane = xp;
        yPlane = yp;
        map=m;//.clone();
    }

    public void updateDirs(){
        xDir=Math.cos(Math.toRadians(dir));
        yDir=Math.sin(Math.toRadians(dir));
    }
    
    public void roundDirs(){
        xDir=Math.round(xDir);
        yDir=Math.round(yDir);
    }

    public Camera(Camera c){
        xPos=c.xPos;
        yPos=c.yPos;
        xDir=c.xDir;
        yDir=c.yDir;
        xPlane=c.xPlane;
        yPlane=c.yPlane;
        map=c.map;
    }

    public void keyPressed(KeyEvent key) {
        if((key.getKeyCode() == KeyEvent.VK_ESCAPE))
            System.exit(0);
        if((key.getKeyCode() == KeyEvent.VK_LEFT)){
            turnLeft();
            left = true;
        }
        if((key.getKeyCode() == KeyEvent.VK_RIGHT)){
            turnRight();
            right = true;
        }
        if((key.getKeyCode() == KeyEvent.VK_UP)){
            moveForward();
            forward = true;
        }
        if((key.getKeyCode() == KeyEvent.VK_DOWN)){
            moveBackward();
            back = true;
        }
    }

    public void keyReleased(KeyEvent key) {
        if((key.getKeyCode() == KeyEvent.VK_LEFT))
            left = false;
        if((key.getKeyCode() == KeyEvent.VK_RIGHT))
            right = false;
        if((key.getKeyCode() == KeyEvent.VK_UP))
            forward = false;
        if((key.getKeyCode() == KeyEvent.VK_DOWN))
            back = false;
    }

    public void update() {
        if(movingForward) {
            xPos+=xDir*MOVE_SPEED;
            yPos+=yDir*MOVE_SPEED;
            //System.out.println("xPos: "+xPos+" yPos: "+yPos);
            //System.out.println("xChange: "+xChange+" yChange: "+yChange);
            if(Math.abs((xPos-(int)xPos)-0.5)<0.001&&Math.abs(yPos-(int)yPos-0.5)<0.001){
                movingForward=false;
            }
        }
        if(movingBack) {
            xPos-=xDir*MOVE_SPEED;
            yPos-=yDir*MOVE_SPEED;
            if(Math.abs((xPos-(int)xPos)-0.5)<0.001&&Math.abs(yPos-(int)yPos-0.5)<0.001){
                movingBack=false;
            }
        }
        if(turningLeft){
            dir=(dir-turnSize+360)%360;
            updateDirs();
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
            if(dir%90==0){
                turningLeft=false;
            }
        }
        if(turningRight){
            dir=(dir+turnSize)%360;
            updateDirs();
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
            if(dir%90==0){
                turningRight=false;
            }
        }
    }

    public void turnLeft(){
        if(!left&&!turningRight&&!movingForward&&!movingBack){
            turningLeft=true;
        }
    }

    public void turnRight(){
        if(!right&&!turningLeft&&!movingForward&&!movingBack){
            turningRight=true;
        }
    }

    public void moveForward(){
        if(!forward&&!turningLeft&&!turningRight&&!movingBack){
            if(map[(int)(yPos)+(int)yDir][(int)xPos+(int)xDir]==0||
            map[(int)(yPos)+(int)yDir][(int)xPos+(int)xDir]==3){
                movingForward=true;
                roundDirs();
            }
        }
    }

    public void moveBackward(){
        if(!back&&!turningLeft&&!turningRight&&!movingForward){
            if(map[(int)(yPos)-(int)yDir][(int)xPos-(int)xDir]==0||
            map[(int)(yPos)-(int)yDir][(int)xPos-(int)xDir]==3){
                movingBack=true;
                roundDirs();
            }
        }
    }

    public void keyTyped(KeyEvent arg0) {

    }
}