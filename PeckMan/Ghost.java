import java.awt.List;
import java.util.ArrayList;
import java.util.Queue;

import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.util.Location;


/**
 * Ghost extends Animate and is the parent class for all ghosts. It implements
 * overarching methods for movement and for tracking the desired location. All
 * ghosts move towards a given target location, with that target varying
 * according to specific ghosts. All ghosts can only change direction at
 * junctions and cannot double back (turn 180 degrees). Ghosts can eat Pacman
 * unless hypeMode is on, in which case they can be eaten. In hypeMode all
 * ghosts temporarily change their movement to run away from Pacman or
 * “scramble,” and their images change to the dark blue DeadGhost. They spawn
 * from the centre of the map at given time intervals, and return there if they
 * are eaten. Once eating, they respawn according to the queue.
 * 
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/17/2017
 */
public abstract class Ghost extends Animate
{
    /**
     * Initializes the default direction to right
     */
    private String direction = "right";

    /**
     * Initializes the image of the dead ghost
     */
    private GreenfootImage ded1 = new GreenfootImage( "DeadGhost.png" );

    /**
     * Initializes the tester to false
     */
    private boolean tester = false;

    /**
     * Initializes the color
     */
    private String colour;

    /**
     * Initializes the timer to zero
     */
    //private int timer = 0; // fixed timer


    /**
     * Act - do whatever the Ghost wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        Pacworld world = (Pacworld)getWorld();

        // Hype mode code

        if ( world.getHype() )
        {
            ded1.scale( 20, 20 );
            setImage( ded1 );

            // increment hype timer
            world.setHypeTimer( world.getHypeTimer() + 1 );
            if ( world.getHypeTimer() > 450 ) // arbitrary number for time
            {
                world.setHypeTimer( 0 );
                world.setHype( false );
            }
        }

        // Eating

        // if can eat Pacman (intersecting objects) and not hypeMode, eat him
        if ( getOneIntersectingObject( Pacman.class ) != null && !world.getHype() )
        {
            eat();
        }

        // Movement

        if ( !atJunction() )
        {
            move( direction );
        }
        else if ( world.getHype() ) // if hypeMode ghosts run away
        {
            scramble();
        }
        else
        {
            move( getTargetLocation() );
        }

        if ( atWorldEdge() )
        {
            if ( direction == "right" )
            {
                setLocation( 0, getY() );
            }
            else if ( direction == "left" )
            {
                setLocation( getWorld().getWidth(), getY() );
            }
        }
    }


    // Movement

    /**
     * Movement for when isHype: ghosts run away (farthest location)
     */
    public void scramble()
    {
        // Find junction furthest from Pacman

        ArrayList<Location> neigh = getNeighbours();
        Pacworld world = (Pacworld)getWorld();

        int pacX = world.getPacman().getX();
        int pacY = world.getPacman().getY();
        int tempX = 0;
        int tempY = 0;
        Location farthestLoc = new Location( 0, 0 ); // should get overwritten
        double dist = 0; // really big number

        // find closest location
        while ( !neigh.isEmpty() )
        {
            Location temp = neigh.remove( 0 );
            tempX = temp.getX();
            tempY = temp.getY();

            double tempDist = Math
                .sqrt( ( ( pacX - tempX ) * ( pacX - tempX ) ) + ( ( pacY - tempY ) * ( pacY - tempY ) ) );

            if ( tempDist > dist )
            {
                farthestLoc = temp;
                dist = tempDist;
            }
        }

        move( farthestLoc );
    }


    /**
     * Finds best valid path to target location (without doubling back on itself
     * or going through walls)
     * 
     * @param target
     *            desired location calculated differently according to ghosts
     */
    public void move( Location target )
    {
        // move towards target

        if ( target.getX() > this.getX() && !( direction == "left" ) ) // go
                                                                       // LEFT
        {
            direction = "right";
            move( direction );
        }
        else if ( target.getX() < this.getX() && !( direction == "right" ) )
        {
            direction = "left";
            move( direction );
        }
        else if ( target.getY() > this.getY() && !( direction == "up" ) ) // go
                                                                          // down
        {
            direction = "down";
            move( direction );
        }
        else if ( target.getY() < this.getY() && !( direction == "down" ) )
        {
            direction = "up";
            move( direction );
        }
        else // to keep from doubling back on itself
        {
            // order of arbitrary movement LEFT, RIGHT, UP, DOWN.

            tester = true;

            if ( canMove( "left" ) && !( direction == "right" ) ) // go LEFT
            {
                direction = "left";
                move( direction );
            }
            else if ( canMove( "right" ) && !( direction == "left" ) )
            {

                direction = "right";
                move( direction );
            }
            else if ( canMove( "up" ) && !( direction == "down" ) )
            {
                direction = "up";
                move( direction );
            }
            else if ( canMove( "down" ) && !( direction == "up" ) )
            {
                direction = "down";
                move( direction );
            }
        }
    }


    /**
     * Find closest accessible junctions or "neighbours" around given ghost
     * 
     * @return ArrayList with potential target locations
     */
    public ArrayList getNeighbours()
    {
        ArrayList<Location> res = new ArrayList<Location>();

        int origX = this.getX();
        int origY = this.getY();
        Location origLoc = new Location( origX, origY );

        // for checking left
        if ( canMove( "left" ) )
        {
            move( "left" );
        }
        // Check for world edge left
        while ( this.canMove( "left" ) && !atJunction() && !atWorldEdge() )
        {
            move( "left" );
        }
        Location leftLoc = this.getLocation();
        this.setLocation( origX, origY );

        // for checking right
        if ( canMove( "right" ) )
        {
            move( "right" );
        }
        // Check for world edge right
        while ( this.canMove( "right" ) && !atJunction() && !atWorldEdge() ) 
        {
            move( "right" );
        }
        Location rightLoc = this.getLocation();
        this.setLocation( origX, origY );

        // for checking up
        if ( canMove( "up" ) )
        {
            move( "up" );
        }
        while ( this.canMove( "up" ) && !atJunction() )
        {
            move( "up" );
        }
        Location upLoc = this.getLocation();
        this.setLocation( origX, origY );

        // for checking down
        if ( canMove( "down" ) )
        {
            move( "down" );
        }
        while ( this.canMove( "down" ) && !atJunction() )
        {
            move( "down" );
        }
        Location downLoc = this.getLocation();
        this.setLocation( origX, origY );

        //checks for world edge left
        if ( !( origX == leftLoc.getX() ) && !atWorldEdge() ) 
        {
            res.add( leftLoc );
        }
        //checks for world edge right
        if ( !( origX == rightLoc.getX() ) && !atWorldEdge() ) 
        {
            res.add( rightLoc );
        }
        if ( !( origY == upLoc.getY() ) )
        {
            res.add( upLoc );
        }
        if ( !( origY == downLoc.getY() ) )
        {
            res.add( downLoc );
        }
        
        return res;

    }


    /**
     * Returns target location; different for each ghost
     * 
     * @return target location, specified according to ghost
     */
    public abstract Location getTargetLocation();


    /**
     * Test if at a junction Junction: location where ghost can move in more
     * than one direction
     * 
     * @return true/false if ghost is at junction
     */
    public boolean atJunction()
    {
        // can move in more than one direction
        return ( ( canMove( "down" ) || canMove( "up" ) ) && ( canMove( "left" ) || canMove( "right" ) ) );
    }


    // Eating

    /**
     * Eat pacman: Remove from world and end game cycle
     */
    public void eat()
    {
        Actor a = getOneIntersectingObject( Pacman.class );
        Pacworld world = (Pacworld)getWorld();
        Pacman p = world.getPacman();

        // if can eat Pacman (intersecting objects), eat him
        if ( getOneIntersectingObject( Pacman.class ) != null )
        {
            world.removeObject( a );
            //p.setIsDead( true );
            world.setWorldActive( false );
        }
    }


    /**
     * Gets the Location (x & y coordinates) of given actor
     * 
     * @return location of given actor
     */
    public Location getLocation()
    {
        return ( new Location( getX(), getY() ) );
    }


    /**
     * Gets the color of the ghost
     * 
     * @return color of the ghost
     */
    public String getColour()
    {
        return colour;
    }


    /**
     * 
     * Access method to get color
     * 
     * @param col given colour
     */
    public void setColour( String col )
    {
        colour = col;
    }


    /**
     * Access method to get tester.
     * 
     * @return tester used for ghost spawning: when routine to get out of gate executes
     */
    public boolean getTester()
    {
        return tester;
    }


    /**
     * Flips the tester's boolean condition from false to true or true to false.
     * @param b desired boolean to switch to
     */
    public void setTester( boolean b )
    {
        tester = b;
    }
}