package tk.thatonelukas;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;

public class Main {
    public static BufferedWriter childIn;

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        boolean debugging = true;
        if (args == null || args.length < 1 && !debugging) {
            LOGGER.warn("No input given, exiting");
            System.exit(1);
        }
        if (args[0] == "nogui") {
            String[] args = Arrays.copyOfRange(oldArr, 1, oldArr.length);
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
