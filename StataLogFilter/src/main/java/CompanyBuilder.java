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
      try {
        String symbol = buildCompanySymbol(parameter);

        Map<String, String> observation = buildObservations(parameter);

        if (companies.containsKey(symbol)) {
          companies.get(symbol).addObservation(observation);
        } else {
          Company company = new Company(symbol);
          company.addObservation(observation);

          companies.put(symbol, company);
        }
      } catch ( IllegalArgumentException ex ) {
        // do nothing on ex
      }
    });

    return companies;
  }

  private Map<String, String> buildObservations(String parameter) {
    Map<String, String> observation = new HashMap<>();


      String rSqared = getAdjRsquared( parameter );
      observation.put("Adj R-squared", rSqared);

      String formula = buildFormula( parameter );
      observation.put( "Formula", formula );

      String file = buildFile( parameter );
      observation.put( "File", file );

      /*String numberOfObservation = getNumberOfObs(parameter);
      observation.put("Number of obs", numberOfObservation);*/

      return observation;
  }

  private String buildFile( String parameter ) {
    Matcher matcher = Pattern.compile("\\*\\*\\*\\s[A-z]+[0-9]+\\.csv\\s\\*\\*\\*").matcher(parameter);
    String badSymbol = matcher.find() ? matcher.group() : "";

    return badSymbol.replace("*", "").replace(" ", "");
  }

  private String getAdjRsquared(String parameter ){
    Matcher matcher = Pattern.compile( "(\\s{2})+(Adj R-squared)(\\s+=\\s+)(([0-9]+\\.?[0-9]+)|(\\.?[0-9]+))" ).matcher(parameter);
    String [] values;
    if( matcher.find() ) {
      values = matcher.group().replaceAll("\\s", "").split("=");

      return values[1];
    } else {
      throw new IllegalArgumentException("No ADJ R-squared found");
    }
  }

  private String getNumberOfObs(String parameter) {
    Matcher matcher = Pattern.compile("(Number\\sof\\sobs)(\\s+=\\s+)(([0-9]+\\.?[0-9]+)|(\\.?[0-9]+))").matcher(parameter);
    String stringOfObs = matcher.find() ? matcher.group() : "";

    matcher = Pattern.compile("[0-9]+").matcher(stringOfObs);

    return matcher.find() ? matcher.group() : "";
  }

  private String buildCompanySymbol(String parameter) {
    Matcher matcher = Pattern.compile("\\*\\*\\*\\s[A-z]+[0-9]+\\.csv\\s\\*\\*\\*").matcher(parameter);
    String badSymbol = matcher.find() ? matcher.group() : "";

    matcher = Pattern.compile("[A-z]+").matcher(badSymbol);
    return matcher.find() ? matcher.group() : "";
  }

  private String buildFormula(String parameter) {
    Matcher matcher = Pattern.compile("\\. reg.*").matcher(parameter);

    if ( matcher.find() ){
      return matcher.group().substring( 1 );
    } else {
      throw new IllegalArgumentException( "Formula not found..." );
    }
  }

}
