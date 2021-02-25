package tk.thatonelukas;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;

import java.util.Arrays;

public class Main {
    public static BufferedWriter childIn;

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        if (args == null || args.length < 1 || (args.length == 1 && args[0] == "nogui")) {
            LOGGER.warn("No input given, exiting");
            System.exit(1);
        }
        LOGGER.info("first arg: \"" + args[0] + "\"");
        if (args[0] == "nogui") {
            LOGGER.info("first arg detected as nogui");
            args = Arrays.copyOfRange(args, 1, args.length);
        }
        String argStr = String.join(" ", args);
        String cmd = argStr;

        try {

            LOGGER.info("Launching with: " + cmd);
            LOGGER.info("Launch dir: " + System.getProperty("user.dir"));
            Process process = Runtime.getRuntime().exec(cmd);

            childIn = new BufferedWriter( new OutputStreamWriter(process.getOutputStream()) );
            InputStream procOut = process.getInputStream();

            AsyncInput asyncInput = new AsyncInput(childIn, process);
            Thread inThread = new Thread(asyncInput);
            inThread.start();

            AsyncOutput asyncOutput = new AsyncOutput(procOut, process);
            Thread outThread = new Thread(asyncOutput);
            outThread.start();

            while (process.isAlive()) {}

            inThread.interrupt();
            outThread.interrupt();
            System.exit(0);
        }

        catch (Exception err) {
            err.printStackTrace();
        }
    }

}
