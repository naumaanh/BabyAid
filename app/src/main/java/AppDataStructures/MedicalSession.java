package AppDataStructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by aaronbatch on 3/5/18.
 */
@XStreamAlias("MSESS")
public class MedicalSession extends Session {
    @XStreamAlias("MTYPE")
    public String type = "";
    @XStreamAlias("MDOSAGE")
    public int dosage;
    @XStreamAlias("MWAY")
    public medGiven way;

    public MedicalSession() {
        super(SessionType.MEDICATION_SESSION);
        way = null;
    }
}
