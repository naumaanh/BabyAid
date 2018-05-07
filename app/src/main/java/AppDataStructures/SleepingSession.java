package AppDataStructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by aaronbatch on 3/5/18.
 */

// Class representing a sleeping session
@XStreamAlias("SSESS")
public class SleepingSession extends Session
{
    // Constructor for sleeping session
    public SleepingSession()
    {
        super(SessionType.SLEEPING_SESSION);
    }
}
