import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import greenfoot.*;
import greenfoot.util.Location; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *  Tester for methods not testable by GUI (data structures, etc.)
 *
 *  @author  Marcus Pearce, Justin Hu, Vincent Hwang
 *  @version May 31, 2017
 *  @author  Period: 5
 *  @author  Assignment: Peckman Final Project
 *
 *  @author  Sources: None
 */
public class Tester extends Actor
{
    private GreenfootImage image = new GreenfootImage( 200, 200 );

    /**
     * Constructor for objects of class Tester (used to run tests once)
     */
    public Tester()
    {
        image.drawString( "Tester", 20, 20);
    }        
    
    /**
     * Runs all tests and prints results to terminal
     */
    public void aRunAll()
    {
        System.out.println("TESTS FOR GHOST CLASS:");
        
        ghostAtJunction();       
        ghostGetNeighbours();
        ghostGetLocation();      
        ghostGetColour();
        ghostGetTester();
        ghostSetTester();
        
        System.out.println();
        System.out.println("TESTS FOR STARTSQUARE");
        
        startSquareTest();
                
        System.out.println();
        System.out.println("TESTS FOR PACWORLD CLASS:");
        
        pacworldRead();
        pacworldGetTimer();        
        pacworldSetHype();
        pacworldGetHype();
        pacworldSetHypeTimer();
        pacworldGetHypeTimer();
        pacworldGetScoreCounter();
        pacworldGetWorldActive();
        pacworldSetWorldActive();
        pacworldGetNumDotsEaten();
        pacworldIncNumDotsEaten();
        pacworldGetGameWon();
        pacworldGetTotalDots();
        
        pacworldGetPacLoc();
        pacworldRedLoc();
        pacworldBlueLoc();
        pacworldOrangeLoc();
        pacworldPinkLoc();
        
        pacworldGetPacman();
        pacworldGetRed();
        pacworldGetBlue();
        pacworldGetOrange();
        pacworldGetPink();
        
    }

    
    // GHOST TESTS
    
    
    public void ghostAtJunction()
    {
        Pacworld world = (Pacworld)getWorld();
        Ghost g = world.getRed();
                
        g.setLocation( 65, 125 ); 
        boolean test = (g.atJunction() == false);
        
        String text = "FAIL";
        if (test)
        {
            text = "PASS";
        }      
        System.out.println("ghostAtJunction:\t" + text);
    }
    
    public void ghostGetNeighbours()
    {
        Pacworld world = (Pacworld)getWorld();
        Ghost g = world.getRed();
        
        g.setLocation( 52, 110 );
        ArrayList<Location> list = g.getNeighbours();
        
        int size = list.size();
        Location first = list.remove( 0 );
        Location second = list.remove( 0 );
        
        boolean test = (size == 2 && first.getX() == 45 && first.getY() == 110
                         && second.getX() == 60 && second.getY() == 110 );
            
        String text = "FAIL";
        if (test)
        {
            text = "PASS";
        }       
        System.out.println("ghostGetNeighbours:\t" + text);
    }
    
    public void ghostGetLocation()
    {
        Pacworld world = (Pacworld)getWorld();
        Ghost g = world.getRed();
        
        g.setLocation( 65, 125 );
        Location loc = g.getLocation();
        
        boolean test = (loc.getX() == 65 && loc.getY() == 125);
        
        String text = "FAIL";
        if (test)
        {
            text = "PASS";
        }        
        System.out.println("ghostGetLocation:\t" + text);
    }
    
    public void ghostGetColour()
    {
        Pacworld world = (Pacworld)getWorld();
        Ghost g = world.getRed();
        g.setColour( "red" );
        
        boolean test = ( "red" == g.getColour());
        
        String text = "FAIL";
        if (test)
        {
            text = "PASS";
        }        
        System.out.println("ghostGetColour:\t" + text);
    }
    
    public void ghostGetTester()
    {
        Pacworld world = (Pacworld)getWorld();
        Ghost g = world.getRed();
        
        g.setTester(false);
        
        boolean test = (false == g.getTester());
        
        String text = "FAIL";
        if (test)
        {
            text = "PASS";
        }        
        System.out.println("ghostGetTester:\t" + text);
    }
    
    public void ghostSetTester()
    {
        Pacworld world = (Pacworld)getWorld();
        Ghost g = world.getRed();
        
        boolean b = g.getTester();
        
        g.setTester( true );
        
        boolean test = (b != g.getTester());
        
        String text = "FAIL";
        if (test)
        {
            text = "PASS";
        }        
        System.out.println("ghostSetTester:\t" + text);
    }
    
    
    // STARTSQUARE TESTS
    
    
    public void startSquareTest()
    {
        Pacworld w = (Pacworld)getWorld();
        StartSquare ss = w.getStartSquare();
        Location sl = ss.getLocation();
        Location l = new Location(65,65);
        if(sl.getX() == l.getX() && sl.getY() == l.getY())
        {
            System.out.println( "startSquareTest\t" + "PASS" );
        }
        else
        {
            System.out.println( "startSquareTest\t" + "FAIL" );
        }
    }
    
    
    // PACWORLD TESTS
    
    
    public void pacworldRead()
    {
        Pacworld world = (Pacworld)getWorld();
        String output = world.read( "PacMap1.txt" );
        
        boolean test = (output != null);
        
        String text = "FAIL";
        if (test)
        {
            text = "PASS";
        }        
        System.out.println("pacworldRead:\t" + text);
        
    }
    
    public void pacworldGetTimer()
    {
        Pacworld world = (Pacworld)getWorld();
        //Ghost g = world.getRed();
        
        boolean test = (0 == world.getTimer());
        
        String text = "FAIL";
        if (test)
        {
            text = "PASS";
        }        
        System.out.println("pacworldGetTimer:\t" + text);
    }  
    
    public void pacworldSetTimer()
    {
        Pacworld world = (Pacworld)getWorld();
        world.setTimer( 5 );

        boolean test = (world.getTimer() == 5);

        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldSetTimer:\t" + text );
    }


    public void pacworldSetHype()
    {
        Pacworld world = (Pacworld)getWorld();
        world.setHype( true );
        
        boolean test = (world.getHype() == true);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldSetHype:\t" + text );
    }
    
    public void pacworldGetHype()
    {
        Pacworld world = (Pacworld)getWorld();
        world.setHype( true );
        world.getHype();
        
        boolean test = (world.getHype() == true);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetHype:\t" + text );
    }
    
    public void pacworldSetHypeTimer()
    {
        Pacworld world = (Pacworld)getWorld();
        world.setHypeTimer( 5 );
        
        boolean test = (world.getHypeTimer() == 5);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldSetHypeTimer:\t" + text );
    }
    
    public void pacworldGetHypeTimer()
    {
        Pacworld world = (Pacworld)getWorld();
        world.setHypeTimer( 10 );
        
        boolean test = (10 == world.getHypeTimer());
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetHypeTimer:\t" + text );
    }

    public void pacworldGetScoreCounter()
    {
        Pacworld world = (Pacworld)getWorld();
        
        boolean test = (world.getScoreCounter() != null);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetScoreCounter:\t" + text );
    }
    
    public void pacworldGetWorldActive()
    {
        Pacworld world = (Pacworld)getWorld();
        world.getWorldActive();
        
        boolean test = (world.getWorldActive());
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetWorldActive\t" + text );
    }
    
    public void pacworldSetWorldActive()
    {
        Pacworld world = (Pacworld)getWorld();
        world.setWorldActive( true );
        
        boolean test = (world.getWorldActive() == true);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldSetWorldActive\t" + text );
    }
    
    public void pacworldGetNumDotsEaten()
    {
        Pacworld world = (Pacworld)getWorld();
        
        boolean test = (world.getNumDotsEaten() == 0);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetNumDotsEaten\t" + text );
    }
    
    public void pacworldIncNumDotsEaten()
    {
        Pacworld world = (Pacworld)getWorld();
        int prev = world.getNumDotsEaten();
        world.incNumDotsEaten();
        
        boolean test = (prev + 1 == world.getNumDotsEaten());
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldIncNumDotsEaten\t" + text );
    }
    
    public void pacworldGetGameWon()
    {
        Pacworld world = (Pacworld)getWorld();
        //world.winGame(); messes with other tests
        
        //should not have won yet
        boolean test = (world.getGameWon() == false);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetGameWon\t" + text );
    }
    
    public void pacworldGetTotalDots()
    {
        Pacworld world = (Pacworld)getWorld();
        
        //counted
        boolean test = (world.getTotalDots() == 281);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetTotalDots\t" + text );
    }
    
    public void pacworldGetPacLoc()
    {
        Pacworld world = (Pacworld)getWorld();
        Pacman pac = world.getPacman();
        
        // should return original location (65, 125)
        Location loc = world.getPacLoc();

        boolean test = ( loc.getX() == 65 && loc.getY() == 125 );

        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetPacLoc\t" + text );
    }
    
    public void pacworldRedLoc()
    {
        Pacworld world = (Pacworld)getWorld();
        Location loc = world.redLoc();

        boolean test = ( loc.getX() == 65 && loc.getY() == 80 );
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldRedLoc\t" + text );
    }
    
    public void pacworldBlueLoc()
    {
        Pacworld world = (Pacworld)getWorld();
        Location loc = world.blueLoc();

        boolean test = ( loc.getX() == 60 && loc.getY() == 80 );
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldBlueLoc\t" + text );
    }
    
    public void pacworldOrangeLoc()
    {
        Pacworld world = (Pacworld)getWorld();
        Location loc = world.orangeLoc();

        boolean test = ( loc.getX() == 75 && loc.getY() == 80 );
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldOrangeLoc\t" + text );
    }
    
    public void pacworldPinkLoc()
    {
        Pacworld world = (Pacworld)getWorld();
        Location loc = world.pinkLoc();

        boolean test = ( loc.getX() == 70 && loc.getY() == 80 );
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldPinkLoc\t" + text );
    }

    public void pacworldGetPacman()
    {
        Pacworld world = (Pacworld)getWorld();
        
        boolean test = (world.getPacman() != null);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetPacman\t" + text );
    }
    
    public void pacworldGetRed()
    {
        Pacworld world = (Pacworld)getWorld();
        
        boolean test = (world.getRed() != null);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetRed\t" + text );
    }
    
    public void pacworldGetBlue()
    {
        Pacworld world = (Pacworld)getWorld();
        
        boolean test = (world.getBlue() != null);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetBlue\t" + text );
    }
    
    public void pacworldGetOrange()
    {
        Pacworld world = (Pacworld)getWorld();
        
        boolean test = (world.getOrange() != null);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetOrange\t" + text );
    }
    
    public void pacworldGetPink()
    {
        Pacworld world = (Pacworld)getWorld();
        
        boolean test = (world.getPink() != null);
        
        String text = "FAIL";
        if ( test )
        {
            text = "PASS";
        }
        System.out.println( "pacworldGetPink\t" + text );
    }

    

}
