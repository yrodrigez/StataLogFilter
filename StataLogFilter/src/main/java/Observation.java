import java.util.ArrayList;
import java.util.Map;

/**
 * @author Yago on 12/01/2017.
 */
public class Observation {

  private String _symbol;
  private ArrayList<Map<String, Double>> _observations;

  public Observation(
    final String symbol,
    final ArrayList<Map<String, Double>> observations
  ) {
    super();

    _symbol = symbol;
    _observations = observations;
  }

  public Observation(final String symbol){
    super();

    _symbol = symbol;
  }

  public String getSymbol() {
    return _symbol;
  }

  public void setSymbol(final String symbol) {
    this._symbol = symbol;
  }

  public ArrayList<Map<String, Double>> getObservations() {
    return _observations;
  }

  public void setObservations(final ArrayList<Map<String, Double>> observations) {
    this._observations = observations;
  }
}
