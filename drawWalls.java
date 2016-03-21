import java.util.ArrayList;
import java.awt.Color;
import java.awt.*;
import java.awt.image.RescaleOp;
public class drawWalls {
    public int[][] map;
    public int mapWidth, mapHeight, width, height;
    public spriteSheet textures;
    public Dimension monitor;
    public drawWalls(int[][] m, int mapW, int mapH, spriteSheet tex, int w, int h) {
        map = m;
        mapWidth = mapW;
        mapHeight = mapH;
        textures = tex;
        width = w;
        height = h;
        monitor = Toolkit.getDefaultToolkit().getScreenSize();
    }

    public void update(Camera camera, Graphics2D g2) {
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0,0,monitor.width,monitor.height/2);
        g2.setColor(new Color(47,47,47));
        g2.fillRect(0,monitor.height/2,monitor.width,monitor.height);
        GradientPaint grad = new GradientPaint(0f,0f,Color.DARK_GRAY,0f,(float)monitor.height+200,Color.BLACK);
        g2.setPaint(grad);
        g2.fillRect(0,0,monitor.width,monitor.height);
        double ratio=(double)monitor.height/(double)width;
        for(int x=0; x<(int)(width); x++) {
            double cx1 = (-(((1/ratio)-1))*width);
            int tx =(int)(cx1+((((((1/ratio)-1)*2)+1)*(width-x))));
            double cameraX = 2*tx/(double)(width) -1;
            double rayDirX = camera.xDir + camera.xPlane * cameraX;
            double rayDirY = camera.yDir + camera.yPlane * cameraX;
            //Map position
            int mapX = (int)camera.xPos;
            int mapY = (int)camera.yPos;
            //length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;
            //Length of ray from one side to next in map
            double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
            double perpWallDist;
            //Direction to go in x and y
            int stepX, stepY;
            boolean hit = false;//was a wall hit
            int side=0;//was the wall vertical or horizontal
            //Figure out the step direction and initial distance to a side
            if (rayDirX < 0)
            {
                stepX = -1;
                sideDistX = (camera.xPos - mapX) * deltaDistX;
            }
            else
            {
                stepX = 1;
                sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
            }
            if (rayDirY < 0)
            {
                stepY = -1;
                sideDistY = (camera.yPos - mapY) * deltaDistY;
            }
            else
            {
                stepY = 1;
                sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
            }
            //Loop to find where the ray hits a wall
            while(!hit) {
                //Jump to next square
                if (sideDistX < sideDistY)
                {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                }
                else
                {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                //Check if ray has hit a wall
                if(map[mapY][mapX] > 0) hit = true;
            }
            //Calculate distance to the point of impact
            if(side==0)
                perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
            else
                perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);  
            //System.out.println(perpWallDist);
            int lineHeight;
            //if(perpWallDist > 0) 
            lineHeight = Math.abs((int)((height / (perpWallDist*2))));
            //else lineHeight = height;
            //calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight/2+ height/2;
            int drawEnd = lineHeight/2 + height/2;
            int texNum = map[mapY][mapX] - 1;
            double wallX;//Exact position of where wall was hit
            if(side==1) {//If its a y-axis wall
                wallX = (camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
            } else {//X-axis wall
                wallX = (camera.yPos + ((mapX - camera.xPos + (1- stepX) / 2) / rayDirX) * rayDirY);
            }
            double wallDist=wallX;
            //System.out.println(wallDist);
            wallX-=Math.floor(wallX);

            //x coordinate on the texture
            int texX = (int)(wallX*(textures.getSprite(texNum).getImg().getWidth()));
            //draw stripe
            g2.drawImage(textures.getSprite(texNum).getImg(),x,drawStart,x+1,drawEnd,texX,0,texX+1,textures.getSprite(texNum).getImg().getHeight(),null);
            //if(perpWallDist<1){
            perpWallDist+=0.2;
            //}
            g2.setColor(new Color(0,0,0,(float)Math.abs((1-((0.0+1/perpWallDist)/2)))));
            g2.drawLine(x,drawStart,x,drawEnd-2);
        }
        //return pixels;
    }
}
