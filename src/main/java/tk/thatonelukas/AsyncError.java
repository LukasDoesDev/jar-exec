package tk.thatonelukas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class AsyncError implements Runnable {
    private InputStream procOut;
    private Process process;
    private String collection = "";

    private static final Logger LOGGER = LogManager.getLogger(AsyncError.class);

    public AsyncError(InputStream procOut, Process process) {
        this.procOut = procOut;
        this.process = process;
    }

    private void getLogs() {
        try {
            int readOut = procOut.read();
            if (readOut == -1) return;
            byte currentByte = (byte) readOut;
            char output = (char) currentByte;
            // System.out.println("" + readOut + " | " + output);
            // System.out.print(Character.toLowerCase(output));
            // System.out.print(output);
            if (output == '\n') {
                LOGGER.info("stderr: " + collection);
                collection = "";
            } else {
                collection = collection + output;
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void run() {
        try {
            Thread.sleep(200);
            getLogs();
            while (process.isAlive())
            {
                getLogs();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
