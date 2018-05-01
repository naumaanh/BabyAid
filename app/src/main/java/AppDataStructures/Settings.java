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
    @XStreamAlias("ISMETRIC")
    public boolean isMetric;
    @XStreamAlias("CINDEX")
    public int childIndex = 0;
    @XStreamAlias("NUMCHILDREN")
    public Integer numberOfChildren = 0;
    @XStreamAlias("STYLEINT")
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
