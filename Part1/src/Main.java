import java.util.concurrent.TimeUnit;
import java.lang.Math;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/**
 * A class for horse entity which can be used with a race object
 *
 * @author Mohammed Uddin
 * @version 1
 */

public class Horse
{
    //Fields of class Horse
    private String horseName;
    private char horseSymbol;
    private int distanceTravelled;
    private boolean horseFallen;
    private double horseConfidence;


    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.horseName = horseName;
        this.horseSymbol = horseSymbol;
        this.distanceTravelled = 0;
        this.horseFallen = false;
        this.horseConfidence = horseConfidence;
    }


    //Other methods of class Horse

    /**
     * Sets the horseFallen attribute of a horse object to true
     */
    public void fall()
    {
        this.horseFallen = true;

        return;

    }

    /**
     * Returns the value held within the confidence variable
     * @return the horse's confidence
     */
    public double getConfidence()
    {
        return this.horseConfidence;

    }

    /**
     * Returns the value held within the distance travelled variable
     * @return the distance the horse has travelled
     */

    public int getDistanceTravelled()
    {
        return this.distanceTravelled;
    }
    /**
     * Returns the value held within the horse name variable
     * @return the horse's name
     */
    public String getName()
    {
        return this.horseName;

    }
    /**
     * Returns the value held within the horse symbol variable
     * @return the symbol representing the horse
     */
    public char getSymbol()
    {
        return this.horseSymbol;
    }

    /**
     * Puts the horse at the start of the race by setting the value of the distance attribute to 0
     */

    public void goBackToStart()
    {
        this.distanceTravelled = 0;

        return;
    }
    /**
     * Checks whether the horse has fallen using the horseFallen attribute
     * @return It will return true if the horseFallen attribute is set to true and false if its not true.
     */
    public boolean hasFallen()
    {
        if (this.horseFallen == true) {

            return true;
        }
        else {

            return false;
        }
    }

    /**
     * Moves the horse one place forward by incrementing the distance travelled variable
     */

    public void moveForward()
    {
        this.distanceTravelled ++;

        return;
    }

    /**
     * Changes the confidence of the horse by setting the horseConfidence variable with a new value passed in to the method
     * @param the newConfidence to be given to the horse
     */
    public void setConfidence(double newConfidence)
    {
        this.horseConfidence = newConfidence;

        return;
    }
    /**
     * Changes the symbol that represents the horse by changing the horse symbol variable with a given character
     * @param the newSymbol character that should be used to represent the horse
     */

    public void setSymbol(char newSymbol)
    {
        this.horseSymbol = newSymbol;

        return;
    }

}

/**
 * Represents a LaneFullException class which extends from the Exception class
 * This class is used to throw exceptions for when a horse is being added to a full lane in the race class
 */
public class LaneFullException extends Exception
{
    public LaneFullException(String message)
    {
        super(message);
    }
}

public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */

    public void addHorse(Horse theHorse, int laneNumber) throws IllegalArgumentException, LaneFullException
    {
        if (laneNumber == 1 && lane1Horse == null)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2 && lane2Horse == null)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3 && lane3Horse == null)
        {
            lane3Horse = theHorse;
        }
        else if (laneNumber > 3 || laneNumber < 1)
        {
            throw new IllegalArgumentException("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
        else {

            throw new LaneFullException("Cannot add horse to lane " + laneNumber + " because the lane is already full");
        }
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;

        //reset all the lanes (all horses not fallen and back to 0).
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();

        while (!finished)
        {
            //move each horse
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);

            //print the race positions
            printRace();

            //print the confidence ratings of the horses
            printConfidence(lane1Horse);
            printConfidence(lane2Horse);
            printConfidence(lane3Horse);

            //if any of the three horses has won the race is finished
            if ( raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse) )
            {
                finished = true;
            }


            //wait for 100 milliseconds
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
        System.out.println();
        printConfidence(lane1Horse);
        printConfidence(lane2Horse);
        printConfidence(lane3Horse);
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move and its confidence decreases
     *
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move,
        //so only run if it has not fallen

        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
                theHorse.moveForward();
            }

            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
            // if the horse falls, then the confidence will drop.
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
                double newConfidence = theHorse.getConfidence() - 0.1;
                theHorse.setConfidence(newConfidence);

            }
        }
    }

    /**
     * Determines if a horse has won the race, if the horse has won the race then it will print a message saying the horse has won the race and
     increase the confidence of the horse.
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        String name = theHorse.getName();
        double newConfidence = theHorse.getConfidence() + 0.1;
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            System.out.println();
            System.out.println("AND THE WINNER IS " + name);
            theHorse.setConfidence(newConfidence);

            return true;
        }
        else
        {
            return false;
        }
    }

    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window

        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        printLane(lane1Horse);
        System.out.println();

        printLane(lane2Horse);
        System.out.println();

        printLane(lane3Horse);
        System.out.println();

        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();
    }

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        //print a | for the beginning of the lane
        System.out.print('|');

        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);

        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u2322');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }

        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);

        //print the | for the end of the track
        System.out.print('|');
    }

    /**
     * Prints the confidence of the horses
     *
     * @param theHorse the horse that needs its confidence rating to be displayed
     */

    private void printConfidence (Horse theHorse)
    {
        System.out.println(theHorse.getName() + " (Current Confidence " + theHorse.getConfidence() + ")");

    }

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     *
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}


