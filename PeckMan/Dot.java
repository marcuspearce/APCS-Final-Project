import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Dot extends Edible and is meant to be eaten by Pacman. In the class the image
 * is set, and objects of the Dot class are referenced for score.
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/15/17
 */
public class Dot extends Edible
{
    private GreenfootImage dot = new GreenfootImage( "dot_S.png" );

    /**
     * Constructor for Dot: sets and scales image
     */
    public Dot()
    {
        dot.scale( 16, 16 );
        setImage( dot );
    }


    /**
     * Act - do whatever the Dot wants to do. This method is called whenever the
     * 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // dot doesn't do anything
    }
}
