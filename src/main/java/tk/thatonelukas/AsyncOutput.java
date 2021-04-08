package tk.thatonelukas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class AsyncOutput implements Runnable {
    private InputStream procOut;
    private Process process;
    private Boolean useDateTime;
    private String collection = "";

    private static final Logger LOGGER = LogManager.getLogger(AsyncOutput.class);

    public AsyncOutput(InputStream procOut, Process process, Boolean useDateTime) {
        this.procOut = procOut;
        this.process = process;
        this.useDateTime = useDateTime;
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
                LOGGER.info(getDateTime() + "stdout: " + collection);
                collection = "";
            } else {
                collection = collection + output;
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private String getDateTime() {
        if (!this.useDateTime) return "";
        return java.time.LocalDate.now().toString() + " " + java.time.LocalTime.now().toString() + " - ";
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
