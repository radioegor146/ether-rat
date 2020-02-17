package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageModule {
    private static String MessageText;
    private static String MessageType;

    public static void run(MessageReceivedEvent var0) {
        Message var1 = var0.getMessage();
        if (var1.getChannel().getName().contains("module-message")) {
            if (!var1.getContentDisplay().equalsIgnoreCase("!Message " + Brain.Username) && !var1.getContentDisplay().equalsIgnoreCase("!Message @ALL")) {
                if (var1.getContentDisplay().contains("!Text")) {
                    MessageText = var1.getContentDisplay().replace("!Text ", "");
                    Helper.sendMessage(Color.GREEN, "Module Message", "Text has been successfully set to: `" + MessageText + "`.", var0);
                } else if (var1.getContentDisplay().contains("!Type")) {
                    if (var1.getContentDisplay().equalsIgnoreCase("!Type Information")) {
                        MessageType = "Information";
                    } else if (var1.getContentDisplay().equalsIgnoreCase("!Type Warning")) {
                        MessageType = "Warning";
                    } else if (var1.getContentDisplay().equalsIgnoreCase("!Type Question")) {
                        MessageType = "Question";
                    } else if (var1.getContentDisplay().equalsIgnoreCase("!Type Error")) {
                        MessageType = "Error";
                    } else {
                        MessageType = "Message";
                    }

                    Helper.sendMessage(Color.GREEN, "Module Message", "Type has been successfully set to: `" + MessageType + "`.", var0);
                }
            } else {
                if (MessageText == null) {
                    Helper.sendMessage(Color.RED, "Module Message", "Text is empty.", var0);
                    return;
                }

                if (MessageType == null) {
                    Helper.sendMessage(Color.RED, "Module Message", "Type is empty.", var0);
                    return;
                }

                Helper.sendMessage(Color.YELLOW, "Module Message (" + Brain.Username + ")", "Sending a message `" + MessageText + "` with type `" + MessageType + "`...", var0);
                Runnable var2 = () -> {

                    switch (MessageType) {
                        case "Information":
                            JOptionPane.showMessageDialog((Component) null, MessageText, "Information", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case "Warning":
                            JOptionPane.showMessageDialog((Component) null, MessageText, "Warning", JOptionPane.WARNING_MESSAGE);
                            break;
                        case "Question":
                            JOptionPane.showMessageDialog((Component) null, MessageText, "Question", JOptionPane.QUESTION_MESSAGE);
                            break;
                        case "Error":
                            JOptionPane.showMessageDialog((Component) null, MessageText, "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog((Component) null, MessageText, "Message", JOptionPane.PLAIN_MESSAGE);
                            break;
                    }

                    Helper.sendMessage(Color.GREEN, "Module Message (" + Brain.Username + ")", "Message `" + MessageText + "` with type `" + MessageType + "` was sent successfully.", var0);
                };
                Thread var3 = new Thread(var2);
                var3.start();
            }

        }
    }
}
