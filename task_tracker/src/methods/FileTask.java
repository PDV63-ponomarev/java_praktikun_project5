package methods;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileTask {

    public static void writeGetText() throws IOException {



        Writer fileWriter = new FileWriter("tasks.CSV", true);

//        if (fileWriter.)

        fileWriter.close();
    }

}
