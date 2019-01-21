package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GetWebsite {

    /**
     * A static utility method to get a website as a string list
     */
    public static List<String> getWebsiteAsStringList(String pageUrl) {

        System.err.println(pageUrl);

        List<String> result = new ArrayList<>();

        try {
            URL url = new URL(pageUrl);

            //Emplois Québec, why do you use ISO_8859_1 ????

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.ISO_8859_1));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.add(inputLine);
                System.out.println(inputLine);
            }

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

}
