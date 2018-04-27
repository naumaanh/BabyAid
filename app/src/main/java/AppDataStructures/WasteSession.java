package AppDataStructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by aaronbatch on 3/5/18.
 */

// Class representing a waste session
@XStreamAlias("WSESS")
public class WasteSession extends Session
{
    @XStreamAlias("WTYPE")
    public String wasteType = ""; // String representing the type of waste (solid, liquid, or both)
    @XStreamAlias("WCOLOR")
    public String wasteColor = ""; // String representing the color of the waste
    @XStreamAlias("CONSISTENCY")
    public String consistency = ""; // String representing the consistency of the waste

    // Constructor for session
    public WasteSession()
    {
        super(SessionType.WASTE_SESSION);
    }

}
