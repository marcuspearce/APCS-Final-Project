import java.awt.List;
import java.util.ArrayList;
import java.util.Queue;

import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.util.Location;


/**
 * RedGhost extends Ghost. It chases Pacman by targeting its current location
 * and taking the most direct route to it.
 * 
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/20/17
 */
public class RedGhost extends Ghost
{
    private GreenfootImage red1 = new GreenfootImage( "RedGhost.png" );

    // For spawning
    private boolean isActive = false;


    /**
     * Constructs the image size, sets the image, and labels the color for the
     * red ghost
     */
    public RedGhost()
    {
        red1.scale( 20, 20 );
        setImage( red1 );
        setColour( "red" );
    }


    /**
     * Act - do whatever the RedGhost wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        Pacworld world = (Pacworld)getWorld();

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

        Location ss = world.getStartSquare().getLocation();

        // red first to spawn
        if ( world.getTimer() > 0 && !isActive )
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
                red1.scale( 20, 20 );
                setImage( red1 );
            }

            super.act();
        }
    }


    /**
     * Returns target location In this case returns location closest to Pacman
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
        Location closestLoc = new Location( 0, 0 ); // should get overwritten
        double dist = 10000000; // really big number

        // find closest location
        while ( !neigh.isEmpty() )
        {
            Location temp = neigh.remove( 0 );
            tempX = temp.getX();
            tempY = temp.getY();

            double tempDist = Math
                .sqrt( ( ( pacX - tempX ) * ( pacX - tempX ) ) + ( ( pacY - tempY ) * ( pacY - tempY ) ) );

            if ( tempDist < dist )
            {
                closestLoc = temp;
                dist = tempDist;
            }
        }

        return closestLoc;
    }
}