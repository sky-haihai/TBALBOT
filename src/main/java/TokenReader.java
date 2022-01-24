import com.fasterxml.jackson.core.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TokenReader {

    public static String read() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(".\\token.json");
        var obj = jsonParser.parse(reader);

        JSONObject token = (JSONObject) obj;

        String result = (String) token.get("token");

        return result;
    }
}
