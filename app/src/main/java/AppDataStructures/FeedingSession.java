package AppDataStructures;

/**
 * Created by aaronbatch on 3/5/18.
 */

// Class which represents a feeding session
public class FeedingSession extends Session
{
    public String method = ""; // String representing the method in which the child was fed
    public String foodType = ""; // String representing the type of food that was fed to the child
    public int amount = 0; // Integer representing the amount of food given to the child (if known)

    // Constructor for feeding session
    public FeedingSession()
    {
        super(SessionType.FEEDING_SESSION);
    }

}
