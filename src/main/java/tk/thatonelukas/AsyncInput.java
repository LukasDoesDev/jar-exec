package tk.thatonelukas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.InputStream;

public class AsyncInput implements Runnable {
    private InputStream inputStream;
    private BufferedWriter procIn;
    private Process process;
    private String collection = "";

    // private static final Logger LOGGER = LogManager.getLogger(AsyncInput.class);

    public AsyncInput(BufferedWriter procIn, Process process) {
        this.procIn = procIn;
        this.inputStream = System.in;
        this.process = process;
    }

    public void run() {
        try {
            Thread.sleep(200);
            while (process.isAlive())
            {
                int readOut = inputStream.read();
                byte currentByte = (byte) readOut;
                char output = (char) currentByte;
                // System.out.println("" + readOut + " | " + output);
                // System.out.print(Character.toLowerCase(output));
                // System.out.print(output);
                if (output == '\n') {
                    procIn.write(collection + "\n");
                    procIn.flush();
                    collection = "";
                } else {
                    collection = collection + output;
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
