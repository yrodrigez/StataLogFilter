import java.util.*;
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



  public Map<String, Company> buildCompanies(ArrayList<String> parameters) {
    Map<String, Company> companies = new HashMap<>();

    parameters.forEach(parameter -> {
      String symbol = buildCompanySymbol(parameter);

      Map<String, Double> observation = buildObservations(parameter);

      if( companies.containsKey( symbol ) ){
        companies.get( symbol ).addObservation( observation );
      } else {
        Company company = new Company( symbol );
        company.addObservation( observation );

        companies.put( symbol, company );
      }
    });

    return companies;
  }

  private Map<String, Double> buildObservations(String parameter) {
    Map<String, Double> observation = new HashMap<>();

    Double numberOfObservation = getNumberOfObs(parameter);
    observation.put("Number of obs", numberOfObservation);

    Double rSqared = getRSqared(parameter);
    observation.put("R-Sqared", rSqared);

    return observation;
  }

  private Double getRSqared ( String parameter ){
    Matcher matcher = Pattern.compile( "(\\s{2})+(R-squared)(\\s+=\\s+)(([0-9]+\\.?[0-9]+)|(\\.?[0-9]+))" ).matcher(parameter);
    String [] values;
    if( matcher.find() ) {
      values = matcher.group().split("=");
      return Double.parseDouble(values[1]);
    } else {
      throw new IllegalArgumentException("No R-squared found");
    }
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
