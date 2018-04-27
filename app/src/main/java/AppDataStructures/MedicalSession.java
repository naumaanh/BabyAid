package AppDataStructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by aaronbatch on 3/5/18.
 */
@XStreamAlias("MSESS")
public class MedicalSession extends Session {
    @XStreamAlias("MEDTYPE")
    public String type = "";
    @XStreamAlias("DOSAGE")
    public int dosage;
    @XStreamAlias("WAY")
    public medGiven way;

    public MedicalSession() {
        super(SessionType.MEDICATION_SESSION);
        way = null;
    }
}
