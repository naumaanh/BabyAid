package AppDataStructures;

/**
 * Created by aaronbatch on 3/5/18.
 */

public class Settings {
    public boolean is24HTime;
    public boolean isDarkTheme;
    public boolean isMetric;

    public int childIndex = 0;
    public Integer numberOfChildren = 0;

    public static Settings instance;
    private Settings() {
        is24HTime = false;
        isDarkTheme = false;
        isMetric = false;
    }

    public static Settings getInstance()
    {
        if (instance == null)
        {
            instance = new Settings();
        }
        return instance;
    }
}
