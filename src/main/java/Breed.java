import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Breed {
    public static List<String> getBreeds(String message, Model model) throws IOException {
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
        for (Object breed:breeds.names()) {
            allBreeds.add(breed.toString());
//            JSONArray jsonArray = (JSONArray) breeds.get(breed.toString());
//            if (jsonArray.length() != 0) {
//                System.out.println(jsonArray);
//            }
        }
        //        for(int i = 0;i<breeds.length();i++) {
//            JSONObject object1 = array.getJSONObject(i);
//            System.out.println(array.getJSONObject(i));
//            tmp += array.getJSONObject(i);
//        }

        return allBreeds;
    }

    public static String getRandomBreedDog(String breedDog) throws IOException {
        URL url = new URL("https://dog.ceo/api/breed/" +  breedDog + "/images/random");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        return object.getString("message");
    }

}

