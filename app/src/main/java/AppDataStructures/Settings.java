package AppDataStructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by aaronbatch on 3/5/18.
 */
@XStreamAlias("SETTINGS")
public class Settings {
    @XStreamAlias("24HTIME")
    public boolean is24HTime;
    @XStreamAlias("DARKTHEME")
    public boolean isDarkTheme;
    @XStreamAlias("METRIC")
    public boolean isMetric;
    @XStreamAlias("CINDEX")
    public int childIndex = 0;
    @XStreamAlias("CHILDNUM")
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
