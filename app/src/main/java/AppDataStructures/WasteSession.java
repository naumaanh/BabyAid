package AppDataStructures;

/**
 * Created by aaronbatch on 3/5/18.
 */

// Class representing a waste session
public class WasteSession extends Session
{
    public String wasteType = ""; // String representing the type of waste (solid, liquid, or both)
    public String wasteColor = ""; // String representing the color of the waste
    public String consistency = ""; // String representing the consistency of the waste

    // Constructor for session
    public WasteSession()
    {
        super(SessionType.WASTE_SESSION);
    }

}
