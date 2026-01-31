package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/*Esta clase modela los atributos y metodos comunes a todos los distintos tipos de parser existentes en la aplicacion*/
public abstract class GeneralParser {

    protected String parseredSiteName;

    public abstract List<HashMap<String, Object>> parse(String settings);

    public abstract String getParseredSiteName();

}

