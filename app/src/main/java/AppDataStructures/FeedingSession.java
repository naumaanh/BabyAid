package AppDataStructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by aaronbatch on 3/5/18.
 */

// Class which represents a feeding session
@XStreamAlias("FSESS")
public class FeedingSession extends Session
{
    @XStreamAlias("FMETHOD")
    public String method = ""; // String representing the method in which the child was fed
    @XStreamAlias("FTYPE")
    public String foodType = ""; // String representing the type of food that was fed to the child
    @XStreamAlias("FAMOUNT")
    public int amount = 0; // Integer representing the amount of food given to the child (if known)

    // Constructor for feeding session
    public FeedingSession()
    {
        super(SessionType.FEEDING_SESSION);
    }

}
