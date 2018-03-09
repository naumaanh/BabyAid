package AppDataStructures;

/**
 * Created by aaronbatch on 3/5/18.
 */

enum medGiven {
    ORAL_LIQUID, ORAL_PILL, SUPPOSITORY, INJECTION
}

public class MedicalSession extends Session {
    public String type;
    public int dosage;
    public medGiven way;

    public MedicalSession()
    {

    }
    public MedicalSession(String t, int d, medGiven w)
    {
        type = t;
        dosage = d;
        way = w;
    }
}
