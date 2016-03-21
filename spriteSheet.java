import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
public class spriteSheet
{
    private sprite[] sprites;
    public spriteSheet(int spriteHeight, int spriteWidth, int rows, int cols, String fileName)
    {
        BufferedImage img = null;
        try { //This loads your image
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sprites = new sprite[rows * cols];
        for (int i = 0; i < rows; i++) //This goes through the spritesheet and creates individual images
        {
            for (int j = 0; j < cols; j++)
            {
                sprites[(i * cols) + j] = new sprite(img.getSubimage(
                    j * spriteWidth,
                    i * spriteHeight,
                    spriteWidth,
                    spriteHeight
                ));
            }
        }
        //System.out.println(img.getType());
    }
    
    public fakeGif makeGif(int firstFrame, int frameCount){
        sprite[] temp = new sprite[frameCount];
        int t=0;
        for(int i=firstFrame; i<frameCount+firstFrame;i++){
            temp[t]=sprites[i];
            t++;
        }
        return new fakeGif(temp);
    }
    
    public sprite getSprite(int index){
        return sprites[index];
    }
}
