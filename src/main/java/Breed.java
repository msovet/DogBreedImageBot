import org.json.JSONArray;
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
        String allBreeds = "";
        JSONObject breeds = object.getJSONObject("message");
        for (Object breed:breeds.names()) {
            allBreeds += "<a href='#'><b>" + breed.toString() + "</b></a>" + "\n";
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
}

