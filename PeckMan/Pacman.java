import java.util.LinkedList;
import java.util.Queue;

import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Pacman, which extends Animate, is directly controlled by the user. Pacman can
 * eat Dot, BigDot, and ghosts (when hypeMode is in effect). Movement is
 * controlled by user input using the up, down, left and right arrows. Pacman
 * can be eaten by ghosts if hypeMode is not on. Pacman references Pacworld to
 * increment score according to what he eats.
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/15/2017
 */
public class Pacman extends Animate
{

    /**
     * Initializes the direction to a blank string
     */
    private String direction = null;

    /**
     * Initializes the last direction to null
     */
    private String lastDirection = null;

    /**
     * Initializes the object to an image Pacman's mouth fully open
     */
    private GreenfootImage pac1 = new GreenfootImage( "PacmanEating-1.png" );

    /**
     * Initializes the object to an image Pacman's mouth halfway open
     */
    private GreenfootImage pac2 = new GreenfootImage( "PacmanEating-2.png" );

    /**
     * Initializes the object to an image Pacman's mouth closed
     */
    private GreenfootImage pac3 = new GreenfootImage( "PacmanEating-3.png" );

    /**
     * Initializes the variable for multiple kills
     */
    private int multikill; // for scoring when eating ghosts

    /**
     * Initializes a queue for the ghosts
     */
    Queue<Ghost> deadGhosts = new LinkedList<Ghost>(); 


    /**
     * Constructor for Pacman: Sets image (already scaled)
     */
    public Pacman()
    {
        setImage( pac1 );
        // already scaled
    }


    /**
     * Act - do whatever the Pacman wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        Pacworld world = (Pacworld)getWorld();

        if ( world.getTotalDots() == world.getNumDotsEaten() )
        {
            if (!world.getGameWon())
            {
                world.winGame();
            }

            return;
        }

        world.setTimer( world.getTimer() + 1 ); // Increment world timer through
                                                // Pacman

        int timer = world.getTimer();

        // ANIMATION

        if ( timer % 15 == 0 )
        {
            setImage( pac3 );
        }
        else if ( timer % 15 == 5 )
        {
            setImage( pac2 );
        }
        else if ( timer % 15 == 10 )
        {
            setImage( pac1 );
        }

        // EATING + Score

        if ( this.getOneIntersectingObject( Dot.class ) != null ) // eats Dot
        {
            eat( Dot.class );
        }

        else if ( this.getOneIntersectingObject( BigDot.class ) != null ) // eats
                                                                          // bigDot
        {
            eat( BigDot.class );
        }

        // Eating ghosts

        if ( world.getHype() )
        {
            Ghost g = (Ghost)this.getOneIntersectingObject( Ghost.class );
            if ( g != null )
            {
                eat( Ghost.class );
                if ( g.getColour().equals( "red" ) )
                {
                    //world.addObject( new RedGhost(), world.redLoc().getX(), world.redLoc().getY() );
                    deadGhosts.add( new RedGhost() );
                }
                if ( g.getColour().equals( "blue" ) )
                {
                    //world.addObject( new BlueGhost(), world.blueLoc().getX(), world.blueLoc().getY() );
                    deadGhosts.add( new BlueGhost() );
                }
                if ( g.getColour().equals( "orange" ) )
                {
                    //world.addObject( new OrangeGhost(), world.orangeLoc().getX(), world.orangeLoc().getY() );
                    deadGhosts.add( new OrangeGhost() );
                }
                if ( g.getColour().equals( "pink" ) )
                {
                    //world.addObject( new PinkGhost(), world.pinkLoc().getX(), world.pinkLoc().getY() );
                    deadGhosts.add( new PinkGhost() );
                }
                world.respawn(deadGhosts);
            }
        }

        // MOVEMENT

        direction = getDirection();

        if ( canMove( direction ) )
        {
            lastDirection = direction;
            move( direction );
        }
        else if ( canMove( lastDirection ) )
        {
            move( lastDirection );
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


    /**
     * Convert user input to desired direction
     * 
     * @return String of desired direction of movement
     */
    public String getDirection()
    {
        //String direction = null;
        if ( Greenfoot.isKeyDown( "up" ) )
            direction = "up";
        if ( Greenfoot.isKeyDown( "down" ) )
            direction = "down";
        if ( Greenfoot.isKeyDown( "left" ) )
            direction = "left";
        if ( Greenfoot.isKeyDown( "right" ) )
            direction = "right";
        if ( direction != null )
        {
            return direction;
        }
        else
        {
            return lastDirection;
        }
    }


    /**
     * Eat an object of a given class. Increment points (determined by object
     * eaten). Eaten objects are removed from world
     * 
     * @param c
     *            given class of object to be eaten
     */
    public void eat( Class c )
    {
        Actor a = getOneIntersectingObject( c );

        Pacworld world = (Pacworld)getWorld();
        ScoreCounter sc = world.getScoreCounter();

        if ( a == null ) // not on given class
        {
            return;
        }
        else
        {
            if ( c == Dot.class )
            {
                sc.increment( 10 ); // Dot worth 10 points
                world.incNumDotsEaten();
            }

            else if ( c == BigDot.class )
            {
                world.incNumDotsEaten();
                sc.increment( 100 ); // BigDot worth 100 pts
                world.setHype( true );
                world.setHypeTimer( 0 );
                multikill = 0;
            }

            // for killing ghost multiplier
            else if ( c == Ghost.class && world.getHype() )
            {
                if ( multikill == 0 )
                {
                    world.getScoreCounter().increment( 200 );
                }
                else if ( multikill == 1 )
                {
                    world.getScoreCounter().increment( 400 );
                }
                else if ( multikill == 2 )
                {
                    world.getScoreCounter().increment( 800 );
                }
                else if ( multikill == 3 )
                {
                    world.getScoreCounter().increment( 1600 );
                }
                multikill++;
            }

            world.removeObject( a );
        }
    }
}