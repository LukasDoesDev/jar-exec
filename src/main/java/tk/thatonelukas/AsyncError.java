package tk.thatonelukas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class AsyncError implements Runnable {
    private InputStream procErr;
    private Process process;
    private Boolean useDateTime;
    private String collection = "";

    private static final Logger LOGGER = LogManager.getLogger(AsyncError.class);

    public AsyncError(InputStream procErr, Process process, Boolean useDateTime) {
        this.procErr = procErr;
        this.process = process;
        this.useDateTime = useDateTime;
    }

    private void getLogs() {
        try {
            int readErr = procErr.read();
            if (readErr == -1) return;
            byte currentByte = (byte) readErr;
            char output = (char) currentByte;
            // System.out.println("" + readErr + " | " + output);
            // System.out.print(Character.toLowerCase(output));
            // System.out.print(output);
            if (output == '\n') {
                LOGGER.info(getDateTime() + "stderr: " + collection);
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
