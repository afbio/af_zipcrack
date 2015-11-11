import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class CommandRunner {

    public static BufferedReader execute(String compressedFilePath, String pass) {

        BufferedReader in = null;

        try {
            Runtime rt = Runtime.getRuntime();
            Process p = null;
            p = rt.exec("7z x -p" + pass + " -oout -y " + compressedFilePath);
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return in;
    }

}
