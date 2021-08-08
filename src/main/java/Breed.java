import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Breed {
    public static String getBreeds(String message, Model model) throws IOException {
        URL url = new URL("https://dog.ceo/api/breeds/list/all");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);

        String apiResult = object.getString("status");
        JSONObject breeds = object.getJSONObject("message");


        return apiResult;
    }
}

