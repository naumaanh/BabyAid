package AppDataStructures;

/**
 * Created by aaronbatch on 3/5/18.
 */

public class Settings {
    public boolean is24HTime;
    public boolean isDarkTheme;
    public boolean isMetric;

    public Settings()
    {
        is24HTime = false;
        isDarkTheme = false;
        isMetric = false;
    }
    public Settings(boolean time, boolean theme, boolean metric)
    {
        is24HTime = time;
        isDarkTheme = theme;
        isMetric = metric;
    }
}
