import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static ArrayList<String> getParams(StringBuilder log) {
        ArrayList<String> params = new ArrayList<>();

        String lineSeparator = System.getProperty("line.separator");
        Pattern pattern = Pattern.compile("\\.\\s\\*\\*\\*.+\\*\\*\\*"
                + System.getProperty("line.separator") +
                "\\..+"
                + lineSeparator
                + lineSeparator
                + ".*" +
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
                ".*" +
                lineSeparator +
                "-+");
        Matcher matcher = pattern.matcher(log);
        while (matcher.find()) {
            params.add(matcher.group());
        }

        return params;
    }

    public static StringBuilder logFileString(String path) throws FileNotFoundException {
        File file = new File(path);
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine());
                fileContents.append(lineSeparator);
            }
            return fileContents;
        } finally {
            scanner.close();
        }
    }

    public static void main(String[] args) throws IOException {

        /*
        * Modificar aquí *
        */

        String logPath = "C:\\Users\\yago\\Downloads\\Pruebalog (1).log";

        String outPath = "C:\\Users\\yago\\Documents";

        String fileName = "Nombre to polluo";

        Double GT = .85;
        /*
        * FIN DE MODIFICACIONES *
         */

        StringBuilder log = logFileString(logPath);

        ArrayList<String> parameters = getParams(log);

        Map<String, Company> companyMap = CompanyBuilder.getInstance().buildCompanies(parameters);

        companyMap.values().forEach(company ->
                company.getObservations().sort(
                        (o1, o2) -> {
                            if (Double.parseDouble(o1.get("Adj R-squared")) < Double.parseDouble(o2.get("Adj R-squared")))
                                return 1;
                            else if (Double.parseDouble(o1.get("Adj R-squared")) > Double.parseDouble(o2.get("Adj R-squared")))
                                return -1;
                            else return 0;
                        })
        );
        System.out.println(companyMap.values());

        buildCSVFile(outPath, fileName, companyMap.values(), GT);

    }

    private static void buildCSVFile(String outPath, String fileName, Collection<Company> companies, double GT) throws IOException {

        StringBuilder csv = new StringBuilder("Company;Adj R-squared;Formula");
        companies.forEach(company -> {
            if(Double.parseDouble(company.getObservations().get(0).get("Adj R-squared")) > GT) {
                csv.append(System.getProperty("line.separator"));

                csv.append(company.getSymbol()).append(";");

                csv.append(company.getObservations().get(0).get("Adj R-squared")).append(";");

                csv.append(company.getObservations().get(0).get("Formula"));
            }
        });

        BufferedWriter bw = new BufferedWriter(new FileWriter(outPath + File.separator + fileName + ".csv"));
        bw.write(csv.toString());
        bw.flush();
        bw.close();

    }
}
