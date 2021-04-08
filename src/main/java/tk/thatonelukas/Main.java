package tk.thatonelukas;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;

import java.util.Arrays;

public class Main {
    public static BufferedWriter childIn;

    public static Boolean useDateTime = true;

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        if (args == null || args.length < 1 || (args.length == 1 && args[0].equals("nogui"))) {
            LOGGER.warn("WARN ! No input given, exiting");
            System.exit(1);
        }
        String firstArg = args[0];
        if (firstArg.equals("nogui")) {
            args = Arrays.copyOfRange(args, 1, args.length);
        }
        if (firstArg.equals("--disable-dt-log")) {
            args = Arrays.copyOfRange(args, 1, args.length);
            useDateTime = false;
            LOGGER.info("INFO ! Disabled date and time log info");
        }
        String argStr = String.join(" ", args);
        String cmd = argStr;

        try {

            LOGGER.info(getDateTime() + "INFO ! Launching with: " + cmd);
            LOGGER.info(getDateTime() + "INFO ! Launch dir: " + System.getProperty("user.dir"));
            Process process = Runtime.getRuntime().exec(cmd);

            childIn = new BufferedWriter( new OutputStreamWriter(process.getOutputStream()) );
            InputStream procOut = process.getInputStream();
            InputStream procErr = process.getErrorStream();

            AsyncInput asyncInput = new AsyncInput(childIn, process);
            Thread inThread = new Thread(asyncInput);
            inThread.start();

            AsyncOutput asyncOutput = new AsyncOutput(procOut, process, useDateTime);
            Thread outThread = new Thread(asyncOutput);
            outThread.start();

            AsyncError asyncError = new AsyncError(procErr, process, useDateTime);
            Thread errThread = new Thread(asyncError);
            errThread.start();

            while (process.isAlive()) {}

            LOGGER.info(getDateTime() + "INFO ! program quit");

            inThread.interrupt();
            outThread.interrupt();
            errThread.interrupt();
            System.exit(0);
        }

        catch (Exception err) {
            err.printStackTrace();
        }
    }

    private static String getDateTime() {
        if (!useDateTime) return "";
        return java.time.LocalDate.now().toString() + " " + java.time.LocalTime.now().toString() + " - ";
    }

}
