import java.awt.List;
import java.util.ArrayList;
import java.util.Queue;

import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.util.Location;


/**
 * OrangeGhost extends Ghost. If Pacman is within a certain distance of
 * OrangeGhost, OrangeGhost will use random movement. Otherwise, it will target
 * Pacman’s current location (just like RedGhost).
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/23/17
 */
public class OrangeGhost extends Ghost
{
    /**
     * Initializes object to an image of an orange ghost
     */
    private GreenfootImage orange1 = new GreenfootImage( "OrangeGhost.png" );

    // For spawning
    /**
     * Initializes boolean variable to false
     */
    private boolean isActive = false;


    /**
     * Constructs the image size, sets the image, and labels the color for the
     * orange ghost
     */
    public OrangeGhost()
    {
        orange1.scale( 20, 20 );
        setImage( orange1 );
        setColour( "orange" );
    }


    /**
     * Act - do whatever the OrangeGhost wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        Pacworld world = (Pacworld)getWorld();
        Location ss = world.getStartSquare().getLocation();

        // check to see if world is active
        if ( !world.getWorldActive() ) // if world isn't active means pacman
                                       // died or gameWon
        {
            if ( !world.getGameWon() ) // means pacman died
            {
                world.pacReset();
            }

            return; // don't move if world isn't active
        }
        // orange last to spawn
        if ( world.getTimer() > 270 && !isActive )
        {
            move( ss );
        }

        if ( getTester() )
        {
            isActive = true;
            setTester(false);
        }

        if ( isActive )
        {

            if ( !world.getHype() )
            {
                orange1.scale( 20, 20 );
                setImage( orange1 );
            }

            super.act();
        }
    }


    /**
     * Returns target location For Orange ghost, tracks Pacman's exact location
     * if outside of range. If within range, uses random movement
     * 
     * @return target location
     */
    public Location getTargetLocation()
    {
        ArrayList<Location> neigh = getNeighbours();
        Pacworld world = (Pacworld)getWorld();

        int pacX = world.getPacman().getX();
        int pacY = world.getPacman().getY();
        int tempX, tempY;
        Location loc = new Location( 0, 0 ); // should get overwritten
        double dist = 10000000; // really big number

        // specific to OrangeGhost
        boolean rand = false; // use random movement true/false
        double totDist = Math
            .sqrt( ( ( pacX - getX() ) * ( pacX - getX() ) ) + ( ( pacY - getY() ) * ( pacY - getY() ) ) );
        int randude = (int)( Math.random() * neigh.size() );
        int randCount = 0;

        if ( totDist < 60 ) // if within range use random movement
        {
            rand = true;
        }

        // find location
        while ( !neigh.isEmpty() )
        {
            Location temp = neigh.remove( 0 );
            tempX = temp.getX();
            tempY = temp.getY();

            double tempDist = Math
                .sqrt( ( ( pacX - tempX ) * ( pacX - tempX ) ) + ( ( pacY - tempY ) * ( pacY - tempY ) ) );

            if ( tempDist < dist )
            {
                loc = temp;
                dist = tempDist;
            }

            if ( rand && randCount == randude ) // random movement found right
                                                // direction
            {
                return loc;
            }
            else
            {
                randCount++;
            }
        }

        // if not random movement (out of range) return closest location for
        // direct tracking
        return loc;
    }

}