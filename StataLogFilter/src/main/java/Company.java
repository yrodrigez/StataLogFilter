import java.util.ArrayList;
import java.util.Map;

/**
 * @author Yago on 12/01/2017.
 */
public class Company {

  private String _symbol;
  private ArrayList<Map<String, String>> _parameters;

  public Company(
    final String symbol,
    final ArrayList<Map<String, String>> observations
  ) {
    super();

    _symbol = symbol;
    _parameters = observations;
  }

  public Company(final String symbol){
    super();

    _symbol = symbol;
    _parameters = new ArrayList<>();
  }

  public String getSymbol() {
    return _symbol;
  }

  public void setSymbol(final String symbol) {
    this._symbol = symbol;
  }

  public ArrayList<Map<String, String>> getObservations() {
    return _parameters;
  }

  public void setObservations(final ArrayList<Map<String, String>> observations) {
    this._parameters = observations;
  }

  public void addObservation(Map<String, String> observation) {
    _parameters.add( observation );
  }

  @Override
  public String toString() {
    return "Company{" +
      "_symbol='" + _symbol + '\'' +
      ", _parameters=" + _parameters.toString() +
      '}' + '\n';
  }
}
