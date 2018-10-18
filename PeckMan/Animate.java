import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Animate extends Actor and includes overarching methods for the movement of
 * all moving actors (Pacman and Ghosts).
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/15/17
 */
public class Animate extends Actor
{
    /**
     * Act - do whatever the Animate wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // to be specified in subclasses
    }


    /**
     * Moves given Animate one unit in designated direction. If Pacman, rotates
     * image for animation
     * 
     * @param direction
     *            desired direction of movement
     */
    public void move( String direction )
    {
        if ( direction == "right" )
        {
            if ( this.getClass() == Pacman.class )
            {
                turnTowards( getX() + 1, getY() );
            }
            setLocation( getX() + 1, getY() );
        }
        if ( direction == "left" )
        {
            if ( this.getClass() == Pacman.class )
            {
                turnTowards( getX() - 1, getY() );
            }
            setLocation( getX() - 1, getY() );
        }
        if ( direction == "up" )
        {
            if ( this.getClass() == Pacman.class )
            {
                turnTowards( getX(), getY() - 1 );
            }
            setLocation( getX(), getY() - 1 );
        }
        if ( direction == "down" )
        {
            if ( this.getClass() == Pacman.class )
            {
                turnTowards( getX(), getY() + 1 );
            }
            setLocation( getX(), getY() + 1 );
        }
    }


    /**
     * Test if animate object can move in given direction
     * 
     * @param direction
     *            given direction of desired movement
     * @return true/false if can move in that direction
     */
    public boolean canMove( String direction )
    {
        int x;
        int y;
        if ( direction == "right" )
        {
            x = getX() + 1;
            y = getY();
        }
        else if ( direction == "left" )
        {
            x = getX() - 1;
            y = getY();
        }
        else if ( direction == "up" )
        {
            x = getX();
            y = getY() - 1;
        }
        else
        {
            x = getX();
            y = getY() + 1;
        }
        int origX = getX();
        int origY = getY();
        setLocation( x, y );
        Actor theWall = getOneIntersectingObject( Wall.class );

        setLocation( origX, origY );

        return theWall == null;
    }


     /**
     * Check if actor at edge of world (for going far left to far right)
     * 
     * @return true/false if at edge
     */
    public boolean atWorldEdge()
    {
        if ( getX() > getWorld().getWidth() - 2 || getX() == 0 )
            return true;
        if ( getY() > getWorld().getHeight() - 2 || getY() == 0 )
            return true;
        else
            return false;
    }

}