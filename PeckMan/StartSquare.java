
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.util.Location;


/**
 * StartSquare extends NotWall and is the location Ghosts go to when spawning.
 * It is directly next to the gate and next to the spawner for the Ghosts.
 * Ghosts use a different act() method to get to StartSquare then use their
 * respective movement algorithms. Ghosts can move through the Gate until it
 * reaches StartSquare.
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/20/17
 */
public class StartSquare extends NotWall
{
    // private GreenfootImage notWall = new GreenfootImage("BlackRect.png");

    /**
     * Constructor for StartSquare: same as NotWall
     */
    public StartSquare()
    {
        super(); //uses same image as NotWall
    }
    
    /**
     * Act - do whatever the StartSquare wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }


    /**
     * Gets the Location (x & y coordinates) of given actor
     * 
     * @return location of given actor
     */
    public Location getLocation()
    {
        return ( new Location( getX(), getY() ) );
    }
}
