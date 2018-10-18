import java.awt.List;
import java.util.ArrayList;
import java.util.Queue;

import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.util.Location;


/**
 * BlueGhost extends Ghost. The blue ghost takes the position of both Pacman and
 * red ghost to create a vector, which is then doubled in order to determine the
 * blue ghost’s point of interest.
 * 
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/25/17
 */
public class BlueGhost extends Ghost
{

    private GreenfootImage blue1 = new GreenfootImage( "BlueGhost.png" );

    // For spawning
    private boolean isActive = false;


    /**
     * Constructor for BlueGhost: sets and scales image and saves colour
     */
    public BlueGhost()
    {
        blue1.scale( 20, 20 );
        setImage( blue1 );
        setColour( "blue" );
    }


    /**
     * Act - do whatever the BlueGhost wants to do. This method is called whenever
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

        // blue third to spawn
        if ( world.getTimer() > 180 && !isActive )
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
                blue1.scale( 20, 20 );
                setImage( blue1 );
            }

            super.act();
        }
    }


    /**
     * Returns target location Finds neighbour/junction closest to location two
     * times the vector between Pacman and redGhost CONDITIONS TO DEAL WITH:
     * RedGhost dead, desired location outside of map
     * 
     * @return target location, specified according to ghost
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
        

        // Specific to blueGhost
        int redX = world.getRed().getLocation().getX();
        int redY = world.getRed().getLocation().getY();
        int desX, desY; // desired location

        // find vector and double
        if ( redX < pacX ) // ghost to left
        {
            desX = pacX + ( 2 * ( pacX - redX ) );
        }
        else // ghost to right
        {
            desX = pacX - ( 2 * ( pacX - redX ) );
        }

        if ( redY < pacY ) // ghost above
        {
            desY = pacY + ( 2 * ( pacY - redY ) );
        }
        else // ghost below
        {
            desY = pacY - ( 2 * ( pacY - redY ) );
        }
        

        // find closest location to desired
        while ( !neigh.isEmpty() )
        {
            Location temp = neigh.remove( 0 );
            tempX = temp.getX();
            tempY = temp.getY();

            double tempDist = Math
                .sqrt( ( ( desX - tempX ) * ( desX - tempX ) ) + ( ( desY - tempY ) * ( desY - tempY ) ) );

            if ( tempDist < dist )
            {
                closestLoc = temp;
                dist = tempDist;
            }
        }

        return closestLoc;
    }
}