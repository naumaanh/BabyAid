package AppDataStructures;

import android.content.Context;
import android.content.res.AssetManager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

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
        XStream xstream = null;
        String xml = "";
        java.util.Scanner s;
        try {
            AssetManager assetManager = context.getAssets();
            s = new Scanner(new File(context.getFilesDir() + File.separator + "settings.xml")).useDelimiter("\\A");
            xml = s.hasNext() ? s.next() : "";
            xstream = new XStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xstream.processAnnotations(Settings.class);
        if (xml.isEmpty())
        {
            instance = new Settings();
        }
        else
        {
            instance = (Settings) xstream.fromXML(xml);
        }
        return instance;
    }

    public static void save(Context context)
    {
        File file = new File(context.getFilesDir() + File.separator + "settings.xml");
        XStream xstream = null;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(file));
            xstream = new XStream();
            xstream.processAnnotations(Settings.class);
            pw.print(xstream.toXML(instance));
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
