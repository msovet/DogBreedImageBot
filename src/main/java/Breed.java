import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Breed {
    public static List<String> getBreeds() throws IOException {
        URL url = new URL("https://dog.ceo/api/breeds/list/all");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);

        String apiResult = object.getString("status");
        JSONObject breeds = object.getJSONObject("message");
        List<String> allBreeds = new ArrayList<>();
        for (Object breed : breeds.names()) {
            allBreeds.add(breed.toString());
        }


        return allBreeds;
    }

    public static String getRandomBreedDog(String breedDog) throws IOException {
        URL url = new URL("https://dog.ceo/api/breed/" + breedDog.toLowerCase(Locale.ROOT) + "/images/random");

        Scanner in = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (in.hasNext()) {
            result.append(in.nextLine());
        }
        JSONObject object = new JSONObject(result.toString());

        return object.getString("message");
    }

}

