package AppDataStructures;

import android.content.Context;
import android.content.res.AssetManager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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

    public static Settings getInstance(Context context)
    {
        InputStreamReader is = null;
        XStream xstream = null;
        try {
            AssetManager assetManager = context.getAssets();
            is = new InputStreamReader(assetManager.open("settings.xml"));
            xstream = new XStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xstream.processAnnotations(Settings.class);
        instance = (Settings)xstream.fromXML(is);

        if (instance == null)
        {
            instance = new Settings();
        }
        return instance;
    }

    public static void save()
    {
        PrintWriter pw = null;
        XStream xstream = null;
        try {
            pw = new PrintWriter("settings.xml");
            xstream = new XStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xstream.processAnnotations(Settings.class);
        pw.print(xstream.toXML(instance));
    }
}
