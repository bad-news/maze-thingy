import java.util.Stack;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Generates the maze
 * This is in a separate class to prevent clutter in the driver
 */
public class mazeGen
{
    boolean isFilling;
    //Stack<Point> solution, solution;
    ArrayList<Point> startPoints;
    Stack<Point> solution;
    enum Move{
        UP,RIGHT,DOWN,LEFT
    }
    ArrayList<Move> possible = new ArrayList<Move>();
    int x;
    int y;
    private int height,width;
    private int[][] map;
    private mazeSolve solver;
    public mazeGen(int height, int width)
    {
        isFilling=false;
        this.height=height;
        this.width=width;
    }
    
    public int[][] gen(){
        init();
        generate();
        addDoor();
        solver=new mazeSolve(height,width,map);
        return map;
    }
    
    public Stack<Point> solution(){
        return solver.solution();
    }
    
    public void addDoor(){
        map[2][1]=4;
    }

    private void init(){
        startPoints = new ArrayList<Point>();
        solution = new Stack<Point>();
        map = new int[height][width];
        for(int i = 0;i < height; i++){
            for(int i2 = 0; i2 < width; i2++){
                if(i<2||i2<2||i>height-3||i2>width-3){
                    if((i==1||i==height-2)&&(i2>0&&i2<width-1)||(i2==1||i2==width-2)&&(i>0&&i<height-1))
                        map[i][i2]=1;
                    else
                        map[i][i2]=2;
                } else {
                    map[i][i2]=1;
                }
            }
        }
    }

    private void generate(){
        boolean deadEnd = false;
        x=y=2;
        int numVisited=1;
        map[y][x]=0;
        startPoints.add(new Point(y,x));
        while(numVisited<(height-3)*(width-3)/4){
            if(canMove(1)){ //Check whether it should solution
                Collections.shuffle(possible);
                move(possible.get(0));
                map[y][x]=0;
                startPoints.add(new Point(y,x));
                //numVisited++;
            } else { //If it has to solution
                //map[y][x]=2;
                //startPoints.add(new Point(y,x));
                if(Math.random()*100>100)
                map[y][x]=5;
                Collections.shuffle(startPoints);
                Point tmp = startPoints.get(0);
                y=tmp.x;
                x=tmp.y;
                startPoints.remove(0);
                numVisited++;
            }
            if(x==width-3&&y==height-3){
                map[y][x]=0;
                map[y][x+1]=3;
                Collections.shuffle(startPoints);
                Point tmp = startPoints.get(0);
                y=tmp.x;
                x=tmp.y;
                startPoints.remove(0);
                numVisited++;
                //System.out.println("End generated");
            }
            //System.out.println(numVisited);
            //System.out.println((height-3)*(width-3)/4);
        }
        map[2][2]=0;
    }
    
    public void solve(){
        boolean deadEnd = false;
        x=y=2;
        solution.push(new Point(y,x));
        while(true){
            if(canMove(0)){ //Check whether it should solution
                move(possible.get((int)(Math.random()*possible.size())));
                solution.push(new Point(y,x));
                deadEnd=false;
            } else { //If it has to solution
                //map[y][x]=2;
                solution.pop();
                x=solution.peek().y;
                y=solution.peek().x;
                deadEnd=true;
            }
            if(x==width-3&&y==height-3){
                solution = (Stack<Point>)solution.clone();
                //System.out.println("End generated");
                break;
            }
        }
    }

    private boolean canMove(int t){
        possible.clear();
        if(map[y-2][x]==t){
            possible.add(Move.UP);
        }
        if(map[y][x+2]==t){
            possible.add(Move.RIGHT);
        }
        if(map[y+2][x]==t){
            possible.add(Move.DOWN);
        }
        if(map[y][x-2]==t){
            possible.add(Move.LEFT);
        }
        return !possible.isEmpty();
    }

    private void move(Move move){
        switch (move){
            case UP:
            y--;
            map[y][x]=0;
            y--;
            break;
            case RIGHT:
            x++;
            map[y][x]=0;
            x++;
            break;
            case DOWN:
            y++;
            map[y][x]=0;
            y++;
            break;
            case LEFT:
            x--;
            map[y][x]=0;
            x--;
            break;
        }
    }
}
