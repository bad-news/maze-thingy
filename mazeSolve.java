import java.util.Stack;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Generates the maze
 * This is in a separate class to prevent clutter in the driver
 */
public class mazeSolve
{
    boolean isFilling;
    Stack<Point> backtrack, solution;
    enum Move{
        UP,RIGHT,DOWN,LEFT
    }
    ArrayList<Move> possible = new ArrayList<Move>();
    int x;
    int y;
    private int height,width;
    private int[][] map;
    public mazeSolve(int height, int width, int[][] m)
    {
        map = new int[height][width];
        for(int i = 0;i < height; i++){
            for(int i2 = 0; i2 < width; i2++){
                map[i][i2]=m[i][i2];
            }
        }
        //map=m;
        this.height=height;
        this.width=width;
        backtrack = new Stack<Point>();
        solution = new Stack<Point>();
    }
    
    private void init(){
        
    }
    
    public Stack<Point> solution(){
        solve();
        return solution;
    }
    
    public void addDoor(){
        map[2][1]=4;
    }

    private void solve(){
        boolean deadEnd = false;
        x=y=2;
        int numVisited=1;
        map[y][x]=1;
        backtrack.push(new Point(y,x));
        while(numVisited<(height-3)*(width-3)/4){
            if(canMove()){ //Check whether it should backtrack
                Collections.shuffle(possible);
                move(possible.get(0));
                map[y][x]=1;
                backtrack.push(new Point(y,x));
                numVisited++;
                deadEnd=false;
            } else { //If it has to backtrack
                //map[y][x]=2;
                backtrack.pop();
                backtrack.pop();
                x=backtrack.peek().y;
                y=backtrack.peek().x;
                deadEnd=true;
            }
            if(x==width-3&&y==height-3){
                map[y][x]=0;
                map[y][x+1]=3;
                solution = (Stack<Point>)backtrack.clone();
                //System.out.println("End generated");
                backtrack.pop();
                backtrack.pop();
                x=backtrack.peek().y;
                y=backtrack.peek().x;

            }
        }
        map[2][2]=0;
    }

    private boolean canMove(){
        possible.clear();
        if(map[y-1][x]==0){
            possible.add(Move.UP);
        }
        if(map[y][x+1]==0){
            possible.add(Move.RIGHT);
        }
        if(map[y+1][x]==0){
            possible.add(Move.DOWN);
        }
        if(map[y][x-1]==0){
            possible.add(Move.LEFT);
        }
        return !possible.isEmpty();
    }

    private void move(Move move){
        switch (move){
            case UP:
            y--;
            map[y][x]=1;
            backtrack.push(new Point(y,x));
            y--;
            break;
            case RIGHT:
            x++;
            map[y][x]=1;
            backtrack.push(new Point(y,x));
            x++;
            break;
            case DOWN:
            y++;
            map[y][x]=1;
            backtrack.push(new Point(y,x));
            y++;
            break;
            case LEFT:
            x--;
            map[y][x]=1;
            backtrack.push(new Point(y,x));
            x--;
            break;
        }
    }
}
