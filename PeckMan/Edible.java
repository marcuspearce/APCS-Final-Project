import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Edible extends Actor and is an abstract class for items to be eaten by Pacman
 * (Dot and BigDot).
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/15/17
 */
public class Edible extends Actor
{
    private GreenfootImage life1 = new GreenfootImage( "PacmanEating-1.png" );


    /**
     * Sets image for pacman lives - used for life sprites in top left corner of screen
     */
    public Edible()
    {
        setImage( life1 ); // used for life sprites
    }


    /**
     * Act - do whatever the Edbile wants to do. This method is called whenever the
     * 'Act' or 'Run' button gets pressed in the environment.
     */public void act()
    {
        // This object doesn't act
    }
}
