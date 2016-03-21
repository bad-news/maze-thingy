import java.awt.Graphics;
/**
 * Classes that will draw anything should implement this
 */
public interface drawable
{
    /**
     * Draws something
     * 
     * @param  g    Your buffered graphics
     * @param  x    X position to draw at
     * @param  y    Y position to draw at
     * @return      Draws your code on the graphics buffer
     */
    //void draw(Graphics g);
    void draw(Graphics g, int x, int y);
    /**
     * Important note:
     * The first line in this method should always create a Graphics2D object
     * Draw only on the Graphics2D object you made
     */
}
