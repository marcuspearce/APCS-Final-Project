import greenfoot.Actor;
import greenfoot.GreenfootImage;


/**
 * Gate extends Wall is a special wall with different image that ghosts move
 * through when they spawn. After the ghosts spawn, they cannot re-enter the
 * gate. Pacman can never go through the Gate.
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/20/17
 */
public class Gate extends Wall
{
    // Extends class wall so animates can't go thru

    private GreenfootImage gate1 = new GreenfootImage( "gateImage 2.jpg" );


    /**
     * Constructor for Gate: sets and scales image
     */
    public Gate()
    {
        gate1.scale( 20, 20 );
        setImage( gate1 );
    }


    /**
     * Act - do whatever the Gate wants to do. This method is called whenever the
     * 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Gates don't act
    }

}
