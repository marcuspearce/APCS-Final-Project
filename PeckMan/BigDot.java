import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * BigDot extends Edible and its purpose is to temporarily change the status of
 * the world to hypeMode when eaten. In the class the image is set, and objects
 * of the BigDot class are referenced for score.
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/20/17
 */
public class BigDot extends Edible
{
    private GreenfootImage bigDot = new GreenfootImage( "dot_L.png" );

    /**
     * Constructor for BigDot, sets and scales image
     */
    public BigDot()
    {
        bigDot.scale( 32, 32 );
        setImage( bigDot );
    }


    /** 
     * Act - do whatever the BigDot wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Nothing in BigDot
    }
}
