
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Actor;
import greenfoot.GreenfootImage;


/**
 * NotWall extends Actor and is an empty space which animate objects can move
 * through. The class itself sets the image.
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/17/17
 */
public class NotWall extends Actor
{
    private GreenfootImage notWall = new GreenfootImage( "BlackRect.png" );


    /**
     * Constructor: sets image (already scaled)
     */
    public NotWall()
    {
        setImage( notWall );
    }


    /**
     * Act - do whatever the NotWall wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // NotWalls don't move
    }

}
