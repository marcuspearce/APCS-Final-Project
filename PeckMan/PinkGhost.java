import java.awt.List;
import java.util.ArrayList;
import java.util.Queue;

import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.util.Location;


/**
 * PinkGhost extends Ghost. The pink ghost aims in front of Pacman’s current
 * direction as oppose to its current location. Its desired location is four
 * units in front of Pacman’s current path.
 * 
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/20/17
 */
public class PinkGhost extends Ghost
{
    /**
     * Initializes object to the image of a pink ghost
     */
    private GreenfootImage pink1 = new GreenfootImage( "PinkGhost.png" );

    // For spawning
    /**
     * Initializes boolean variable to false
     */
    private boolean isActive = false;


    /**
     * Constructs the image size, sets the image, and labels the color for the
     * pink ghost
     */
    public PinkGhost()
    {
        pink1.scale( 20, 20 );
        setImage( pink1 );
        setColour( "pink" );
    }


    /**
     * Act - do whatever the PinkGhost wants to do. This method is called whenever
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

        // pink second to spawn
        if ( world.getTimer() > 90 && !isActive )
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
                pink1.scale( 20, 20 );
                setImage( pink1 );
            }

            super.act();
        }
    }


    /**
     * Returns target location For Pink ghost, finds neighbour closest to
     * location four units in front of Pacmans current path
     * 
     * @return target location, specified according to ghost
     */
    public Location getTargetLocation()
    {
        ArrayList<Location> neigh = getNeighbours();
        Pacworld world = (Pacworld)getWorld();
        Pacman pac = world.getPacman();
        int pacX = pac.getX();
        int pacY = pac.getY();

        // Specific to pink ghost

        // desired coordinates (changed below)
        int desX = pac.getX();
        int desY = pac.getY();

        if ( pac.getDirection().equals( "up" ) )
        {
            desY -= 20;
        }
        if ( pac.getDirection().equals( "down" ) )
        {
            desY += 20;
        }
        if ( pac.getDirection().equals( "left" ) )
        {
            desX -= 20;
        }
        if ( pac.getDirection().equals( "right" ) )
        {
            desX += 20;
        }

        
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