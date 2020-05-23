package chat_client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogController {

    public static void WriteLog(String fileName, String text) throws IOException {
        File folder = new File("c:\\temp\\");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        BufferedWriter bw = null;
        try {
            File file = new File("c:\\temp\\" + fileName + ".txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(text);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }
        }
    }
}
