import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class main {

    public static ArrayList<String> getParams(StringBuilder log){
        ArrayList<String> params = new ArrayList<>();

        String lineSeparator = System.getProperty("line.separator");
        Pattern pattern = Pattern.compile("\\.\\s\\*\\*\\*.+\\*\\*\\*"
                + System.getProperty("line.separator") +
                "\\..+"
                + lineSeparator
                + lineSeparator
                +".*" +
                lineSeparator+
                ".*" +
                lineSeparator +
                ".*" +
                lineSeparator +
                ".*" +
                lineSeparator +
                ".*" +
                lineSeparator +
                ".*" +
                lineSeparator +
                ".*" +
                lineSeparator +
                "-+");
        Matcher matcher = pattern.matcher(log);
        while (matcher.find()){
            params.add(matcher.group());
        }

        return params;
    }

    public static StringBuilder logFileString(String path) throws FileNotFoundException {
        File file = new File(path);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine());
                fileContents.append(lineSeparator);
            }
            return fileContents;
        } finally {
            scanner.close();
        }
    }

    public static void main(String [] args) throws FileNotFoundException {
        StringBuilder log = logFileString("E:\\Downloads\\Pruebalog.log");

        for(String s: getParams(log)){
            System.out.println(s);

            //break;
        }

    }
}
