import java.awt.*;
import java.awt.image.*;
public class sprite implements drawable
{
    private BufferedImage self;
    int imgwidth;
    public sprite(BufferedImage me)
    {
        self=me;
        //imgSize=self.getHeight()*self.getWidth();
    }
    
    public void draw(Graphics g, int x, int y){
        Graphics2D g2= (Graphics2D)g;
        g.drawImage(self, x, y, null);
    }
    
    public BufferedImage getImg(){
        return self;
    }
    
    public void drawRecolor(int x, int y, Graphics g, Color col, Color col2){
        Graphics2D g2 = (Graphics2D)g;
        /**
         * Draw a sprite but replace and instances of col in the sprite with col2
         * Useful if you have one-color sprites
         * Not applicable in many situations, can be scrapped
         */
        BufferedImage temp = deepCopy(self);
        for(int i = 0; i<temp.getWidth();i++){
            for(int i2 = 0; i2<temp.getHeight();i2++){
                if(temp.getRGB(i,i2)==col.getRGB()){
                    temp.setRGB(i,i2,col2.getRGB());
                }
            }
        }
        g2.drawImage(temp,x,y,null);
    }

    BufferedImage deepCopy(BufferedImage sprite) { //This method is necessary for the recolor to work, it can be ignored
        ColorModel cm = sprite.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = sprite.copyData(sprite.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
