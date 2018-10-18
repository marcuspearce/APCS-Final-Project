import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.util.Location;


/**
 * Pacworld extends World, a class that is imported from Greenfoot. This class
 * creates and manages the world. Pacworld creates the world for the game by
 * reading the info in from a given text file. Pacworld is referenced by Actor
 * for the timer (overall and hypeCounter) and ScoreCounter. It creates the map
 * and GUI for the user to see and play the game.
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/13/2017
 */
public class Pacworld extends World
{

    // For World (arbitrary size)
    /**
     * Initializes the width of the world
     */
    private static int width = 27 * 5;

    /**
     * Initializes the height of the world
     */
    private static int height = 33 * 5;

    // For Hype Mode
    /**
     * Initializes the boolean variable for hype mode to false
     */
    private boolean isHype = false;

    /**
     * Initializes the hype timer to zero
     */
    private int hypeTimer = 0; 

    // For Score
    /**
     * Initializes the object score counter to zero
     */
    private ScoreCounter sc = new ScoreCounter( "Score: 0" );

    // For Ghost Spawning
    /**
     * Initializes the timer to zero
     */
    private int timer = 0;

    /**
     * Initializes the total dots to zero
     */
    private int totalDots = 0;

    /**
     * Initializes the number of dots eaten to zero
     */
    private int numDotsEaten = 0;

    /**
     * Initializes the status of active world to true
     */
    private boolean worldActive = true;

    /**
     * Initializes the location of each ghost
     */
    private Location pacLoc, redLoc, blueLoc, pinkLoc, orangeLoc;

    /**
     * Initializes the number of lives to three
     */
    private int pacLives = 3; // for when dies

    // Life images
    /**
     * Initializes the images of live sprites
     */
    private Edible life1 = new Edible();
    private Edible life2 = new Edible();
    private Edible life3 = new Edible();

    /**
     * Initializes the condition of whether the game is won
     */
    private boolean gameWon;

    

    /**
     * Constructs the size of the world, sets the speed of the world, and reads
     * the text file for the map
     */
    public Pacworld()
    {
        // size arbitrary: uses parameters width, height, cellSize

        super( width + 5, height, 4 ); // SCALE 4 FOR SMOOTHER MOVEMENT
        Greenfoot.setSpeed( 45 );

        createWorld( "PacMap1.txt" ); //given customizable text file for map
    }


    /**
     * Uses given map text file to create new world - different characters in
     * text file correspond to different objects in the world
     */
    public void createWorld( String textFile )
    {
        String map = read( textFile );
        String[] splited = map.split( "\\s+" );

        width = splited.length / height;
        int count = 0;

        // Adds everything in "background" basically not animate

        for ( int r = 0; r < height; r++ )
        {
            for ( int c = 0; c < width; c++ )
            {
                String val = splited[count];

                // Walls and notWalls

                if ( val.equals( "0" ) )
                {
                    addObject( new Wall(), c * 5, r * 5 ); // wall size is 20x20
                }

                else if ( val.equals( "G" ) )
                {
                    addObject( new Gate(), c * 5, r * 5 );
                }

                else // not a wall
                {
                    addObject( new NotWall(), c * 5, r * 5 );
                }

                // Other objects

                if ( val.equals( "-" ) || val.equals( "|" ) )
                {
                    addObject( new Dot(), c * 5, r * 5 );
                    totalDots++; // to find how many dots there are in map
                }

                else if ( val.equals( "+" ) )
                {
                    addObject( new BigDot(), c * 5, r * 5 );
                    totalDots++;
                }

                count++;
            }
        }

        // Animate objects on top of others: Pacman and ghosts and score and
        // startSquare

        count = 0;

        for ( int r = 0; r < height; r++ ) // wall size is 20x20
        {
            for ( int c = 0; c < width; c++ )
            {
                String val = splited[count];

                // StartSquare (Also used for textboxes)
                if ( val.equals( "E" ) ) // for empieza
                {
                    addObject( new StartSquare(), c * 5, r * 5 );
                }

                // PACMAN
                else if ( val.equals( "P" ) )
                {
                    addObject( new Pacman(), c * 5, r * 5 );
                    pacLoc = new Location( c * 5, r * 5 ); // save Location for
                                                           // respawn
                }

                // GHOSTS
                else if ( val.equals( "R" ) )
                {
                    addObject( new RedGhost(), c * 5, r * 5 );
                    redLoc = new Location( c * 5, r * 5 );
                }
                else if ( val.equals( "B" ) )
                {
                    addObject( new BlueGhost(), c * 5, r * 5 );
                    blueLoc = new Location( c * 5, r * 5 );
                }
                else if ( val.equals( "O" ) )
                {
                    addObject( new OrangeGhost(), c * 5, r * 5 );
                    orangeLoc = new Location( c * 5, r * 5 );
                }
                else if ( val.equals( "V" ) ) // can't repeat "P" lmao
                {
                    addObject( new PinkGhost(), c * 5, r * 5 );
                    pinkLoc = new Location( c * 5, r * 5 );
                }

                // Label "Score"
                else if ( val.equals( "S" ) )
                {
                    addObject( sc, c * 5, r * 5 );
                }

                count++;
            }
        }

        // add life images (little pacmen)

        this.addObject( life1, 5, 5 );
        this.addObject( life2, 10, 5 );
        this.addObject( life3, 15, 5 );
    }


    /**
     * Converts the info from the given text file to a String (to be used in
     * createWorld)
     * 
     * @param name
     *            of given text file
     * @return String version of text file
     */
    public String read( String name )
    {
        String fileName = "PacMap1.txt";
        String text = "";
        String line = null;
        int numLines = 0;

        try
        {
            FileReader fileReader = new FileReader( fileName );
            BufferedReader bufferedReader = new BufferedReader( fileReader );

            while ( ( line = bufferedReader.readLine() ) != null )
            {
                text += line + " ";
                numLines++;
            }

            bufferedReader.close();

            height = numLines;

            return text;
        }
        catch ( FileNotFoundException e )
        {
            // Auto-generated catch block
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            // Auto-generated catch block
            e.printStackTrace();
        }

        return "Something went wrong";
    }


    // For Ghost Spawning

    /**
     * Returns timer (timer for world)
     * 
     * @return int time passed
     */
    public int getTimer()
    {
        return timer;
    }


    /**
     * Sets the timer to given time
     * 
     * @param amt
     *            given time
     */
    public void setTimer( int amt )
    {
        timer = amt;
    }


    // For Hype Mode

    /**
     * Sets boolean for hype mode to given
     * 
     * @param hype
     *            true/false if in hypeMode or not
     */
    public void setHype( boolean hype )
    {
        isHype = hype;
    }


    /**
     * Returns true/false on if hypeMode is active
     * 
     * @return true/false if hypeMode active
     */
    public boolean getHype()
    {
        return isHype;
    }


    /**
     * Return value of hypeTimer
     * 
     * @return int value of hypeTimer
     */
    public int getHypeTimer()
    {
        return hypeTimer;
    }


    /**
     * Set value of hypeTimer to desired value
     * 
     * @param amt
     *            given value for hypeTimer
     */
    public void setHypeTimer( int amt )
    {
        hypeTimer = amt;
    }


    // For scoring

    /**
     * Accesses scoreCounter object in given Pacworld (used for scoring)
     * 
     * @return scoreCounter in the world
     */
    public ScoreCounter getScoreCounter()
    {
        return sc;
    }


    // For respawning Pacman

    /**
     * If Pacman dies, stop game and reset Pacman and ghosts to original
     * locations. If ran out of lives, end game.
     */
    public void pacReset()
    {
        pacLives--;
        if ( pacLives <= 0 )
        {
            this.removeObject( life1 );
            gameOver();
        }
        else
        {
            // remove Ghosts from map to put back at spawner
            this.removeObject( getRed() );
            this.removeObject( getBlue() );
            this.removeObject( getOrange() );
            this.removeObject( getPink() );

            // put Animates back in starting positions
            this.addObject( new Pacman(), pacLoc.getX(), pacLoc.getY() );
            this.addObject( new RedGhost(), redLoc.getX(), redLoc.getY() );
            this.addObject( new BlueGhost(), blueLoc.getX(), blueLoc.getY() );
            this.addObject( new OrangeGhost(), orangeLoc.getX(), orangeLoc.getY() );
            this.addObject( new PinkGhost(), pinkLoc.getX(), pinkLoc.getY() );

            // remove life image
            if ( pacLives == 2 )
            {
                this.removeObject( life3 );
            }
            else if ( pacLives == 1 )
            {
                this.removeObject( life2 );
            }

            // reset stats then restart
            timer = 0;
            worldActive = true;
        }
    }


    /**
     * Returns the original location of pacman
     * 
     * @return the location of pacman
     */
    public Location getPacLoc()
    {
        return pacLoc;
    }


    /**
     * Returns the condition of the whether the world is still active
     * 
     * @return if the world is active
     */
    public boolean getWorldActive()
    {
        return worldActive;
    }


    /**
     * Sets the condition of the world
     * 
     * @param b
     */
    public void setWorldActive( boolean b )
    {
        worldActive = b;
    }


    // for respawning ghosts when eaten by pacman (in pacman class)
    // returns STARTING LOCATION for ghosts
    
    /**
     * Returns location of the red ghost
     * 
     * @return location of red ghost
     */
    public Location redLoc()
    {
        return redLoc;
    }


    /**
     * Returns location of the blue ghost
     * 
     * @return location of blue ghost
     */
    public Location blueLoc()
    {
        return blueLoc;
    }


    /**
     * Returns the location of orange ghost
     * 
     * @return location of orange ghost
     */
    public Location orangeLoc()
    {
        return orangeLoc;
    }


    /**
     * Returns the location of the pink ghost
     * 
     * @return location of pink ghost
     */
    public Location pinkLoc()
    {
        return pinkLoc;
    }


    // for respawning objects in pacReset
    /**
     * Adds Pacman in array list
     * 
     * @return Pacman
     */
    public Pacman getPacman()
    {
        ArrayList<Pacman> pList = new ArrayList<Pacman>();
        pList = (ArrayList<Pacman>)this.getObjects( Pacman.class );
        Pacman p = pList.get( 0 );
        return p;
    }


    /**
     * Adds red ghost into array list
     * 
     * @return red ghost
     */
    public RedGhost getRed()
    {
        ArrayList<RedGhost> gList = new ArrayList<RedGhost>();
        gList = (ArrayList<RedGhost>)this.getObjects( RedGhost.class );
        RedGhost g = gList.get( 0 );
        return g;
    }


    /**
     * Adds blue ghost into array list
     * 
     * @return blue ghost
     */
    public BlueGhost getBlue()
    {
        ArrayList<BlueGhost> gList = new ArrayList<BlueGhost>();
        gList = (ArrayList<BlueGhost>)this.getObjects( BlueGhost.class );
        BlueGhost g = gList.get( 0 );
        return g;
    }


    /**
     * Adds orange ghost into array list
     * 
     * @return orange ghost
     */
    public OrangeGhost getOrange()
    {
        ArrayList<OrangeGhost> gList = new ArrayList<OrangeGhost>();
        gList = (ArrayList<OrangeGhost>)this.getObjects( OrangeGhost.class );
        OrangeGhost g = gList.get( 0 );
        return g;
    }


    /**
     * Adds pink ghost into array list
     * 
     * @return pink ghost
     */
    public PinkGhost getPink()
    {
        ArrayList<PinkGhost> gList = new ArrayList<PinkGhost>();
        gList = (ArrayList<PinkGhost>)this.getObjects( PinkGhost.class );
        PinkGhost g = gList.get( 0 );
        return g;
    }


    /**
     * Adds start square into array list
     * 
     * @return start square
     */
    public StartSquare getStartSquare()
    {
        ArrayList<StartSquare> sList = new ArrayList<StartSquare>();
        sList = (ArrayList<StartSquare>)this.getObjects( StartSquare.class );
        StartSquare s = sList.get( 0 );
        return s;
    }


    /**
     * What to do when Pacman runs out of all three lives. Removes all actors
     * from world and displays message "Game Over"
     */
    public void gameOver()
    {
        worldActive = false;

        // remove Ghosts from map once game is over
        this.removeObject( getRed() );
        this.removeObject( getBlue() );
        this.removeObject( getOrange() );
        this.removeObject( getPink() );

        GreenfootImage gameOver = new GreenfootImage( "Game Over", 20, Color.WHITE, Color.BLACK );
        this.getStartSquare().setImage( gameOver );
    }


    /**
     * Returns the number of dots eaten
     * @return number of dots eaten
     */
    public int getNumDotsEaten()
    {
        return numDotsEaten;
    }


    /**
     * Increases the number of dots eaten
     */
    public void incNumDotsEaten()
    {
        numDotsEaten++;
    }


    /**
     * Routine when gameWon (all pacDots eaten while Pacman still has lives)
     */
    public void winGame()
    {
        worldActive = false;
        gameWon = true;

        // remove Ghosts from map once game is over
        this.removeObject( getRed() );
        this.removeObject( getBlue() );
        this.removeObject( getOrange() );
        this.removeObject( getPink() );

        GreenfootImage winMessage = new GreenfootImage( "Congrats", 20, Color.WHITE, Color.BLACK );
        this.getStartSquare().setImage( winMessage );
    }


    /**
     * Accessor method of whether the game is won
     * 
     * @return boolean condition of game
     */
    public boolean getGameWon()
    {
        return gameWon;
    }


    /**
     * Accessor method of total amount of dots
     * 
     * @return the total amount of dots
     */
    public int getTotalDots()
    {
        return totalDots;
    }


    /**
     * Used to respawn ghosts when die
     * 
     * @param g
     *            given ghost
     */
    public void respawn( Queue<Ghost> g )
    {
        if ( g.isEmpty() )
        {
            return;
        }
        
        if ( g.peek().getColour().equals( "red" ) )
        {
            addObject( new RedGhost(), redLoc().getX(), redLoc().getY() );
        }
        else if ( g.peek().getColour().equals( "blue" ) )
        {
            addObject( new BlueGhost(), blueLoc().getX(), blueLoc().getY() );
        }
        else if ( g.peek().getColour().equals( "orange" ) )
        {
            addObject( new OrangeGhost(), orangeLoc().getX(), orangeLoc().getY() );
        }
        else if ( g.peek().getColour().equals( "pink" ) )
        {
            addObject( new PinkGhost(), pinkLoc().getX(), pinkLoc().getY() );
        }
        g.remove();
    }
}