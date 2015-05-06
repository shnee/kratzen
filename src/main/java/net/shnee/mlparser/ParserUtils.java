package net.shnee.mlparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author shnee
 */
public class ParserUtils {
    
    /**
     * Download the source of a URL and return it as a String.
     * @param url URL to get the source from.
     * @return Returns the source of the given URL as a String.
     * @throws IOException Throws an IOException if there was an error
     *                     downloading the source.
    */
    static public String downloadURLSource(String url) throws IOException {
        URL target = new URL(url);
        URLConnection conn = target.openConnection();
        StringBuilder strBuilder;
        try (BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                                         "UTF-8"))) {
            String inputLine;
            strBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                strBuilder.append(inputLine);
        }

        return strBuilder.toString();
    }
    
}
