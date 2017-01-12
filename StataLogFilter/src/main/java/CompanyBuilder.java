import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yago on 12/01/2017.
 */
public class CompanyBuilder {
  private static CompanyBuilder _instance = new CompanyBuilder();

  public static CompanyBuilder getInstance() {
    return _instance;
  }

  private ArrayList<String> parameters;

  private CompanyBuilder() {
  }

  public Map<String, ArrayList<Observation>> buildCompanies(ArrayList<String> parameters) {
    Map<String, ArrayList<Observation>> companies = new HashMap<>();

    parameters.forEach(parameter -> {

      String symbol = buildCompanySymbol(parameter);

      Observation observation = new Observation(symbol);

      Map<String, Double> observations = buildObservation(parameter);

    });

    return companies;
  }

  private Map<String, Double> buildObservation(String parameter) {
    Map<String, Double> observation = new HashMap<>();

    Double numberOfObservation = getNumberOfObs(parameter);

    observation.put("Number of obs", numberOfObservation);


    return observation;
  }

  private Double getNumberOfObs(String parameter) {
    Matcher matcher = Pattern.compile("(Number\\sof\\sobs)(\\s+=\\s+)(([0-9]+\\.?[0-9]+)|(\\.?[0-9]+))").matcher(parameter);
    String stringOfObs = matcher.find() ? matcher.group() : "";

    matcher = Pattern.compile("[0-9]+").matcher(stringOfObs);

    return matcher.find() ? Double.parseDouble(matcher.group()) : 0;
  }

  private String buildCompanySymbol(String parameter) {
    Matcher matcher = Pattern.compile("\\*\\*\\*\\s[A-z]+[0-9]+\\.csv\\s\\*\\*\\*").matcher(parameter);
    String badSymbol = matcher.find() ? matcher.group() : "";

    matcher = Pattern.compile("[A-z]+").matcher(badSymbol);
    return matcher.find() ? matcher.group() : "";
  }
}
