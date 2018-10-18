import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * ScoreCounter extends Actor and increments and updates the score. The score is
 * displayed in the upper right hand corner of the map and constantly updates
 * (created in PacWorld).
 * 
 * 
 * @author Marcus Pearce, Justin Hu, Vincent Hwang
 * @version 5/21/17
 */
public class ScoreCounter extends Actor
{
    private int score = 0;


    /**
     * Constructor for ScoreCounter: creates textbox with given String
     * @param text given string to be put in textBox
     */
    public ScoreCounter( String text )
    {
        setImage( new GreenfootImage( text, 20, Color.WHITE, Color.BLACK ) );
    }


    /**
     * Increments score and updates textbox/scoreboard
     * @param amt amount to increase score by
     */
    public void increment( int amt )
    {
        score += amt;
        setImage( new GreenfootImage( "Score: " + score, 20, Color.WHITE, Color.BLACK ) );
    }
    
    /**
     * Act - do whatever the ScoreCounter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // ScoreCounter doesn't act on its own
    }
}
