package AppDataStructures;

/**
 * Created by aaronbatch on 3/5/18.
 */

public class MedicalSession extends Session {
    public String type;
    public int dosage;
    public medGiven way;

    public MedicalSession() {
        super();
        sType = sessionType.MEDICAL;
    }
}
