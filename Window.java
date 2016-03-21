import javax.swing.*;
import java.awt.*;
/**
 * The actual JFrame
 * For most programs, no code needs to be added in this class
 * When running a program, this is the class from which you start
 */
public class Window extends JFrame
{
    public Window(String name)
    {
        super(name);//actually sets the title to a JFrame
        Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension windowSize = monitor;//Set the dimensions of your window here!
        setSize(windowSize);
        setUndecorated(true);
        setResizable(false);
    }
    public static void main(String[] args)
    {
        Window window = new Window("Example of Program Name");//Set the window header/title of your program here      
        Container contentPane = window.getContentPane();
        Driver d = new Driver(window.getSize());
        contentPane.add(d);
        window.setVisible(true);
    }
}
