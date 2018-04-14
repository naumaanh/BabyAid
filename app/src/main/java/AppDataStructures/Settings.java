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

    public int styleInt;

    public static Settings instance;
    private Settings() {
        is24HTime = false;
        isDarkTheme = false;
        isMetric = false;

        styleInt = android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
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
