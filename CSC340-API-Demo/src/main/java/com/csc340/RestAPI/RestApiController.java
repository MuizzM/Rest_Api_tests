package com.csc340.RestAPI;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author sentini
 */
@RestController
public class RestApiController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello, %s!", name);
    }

    /**
     * Get a random quote from quotable and make it available at this endpoint.
     *
     * @return
     */
    @GetMapping("/quote")
    public Object getQuote() {
        String url = "https://api.quotable.io/random";
        RestTemplate restTemplate = new RestTemplate();
        Object jSonQuote = restTemplate.getForObject(url, Object.class);

        //Print the whole response to console.
        String quote = restTemplate.getForObject(url, String.class);
        //Parse out the most important info from the response.
        JSONObject jo = new JSONObject(quote);
        System.out.println(jo.toString());
        String quoteAuthor = jo.getString("author");
        String quoteContent = jo.getString("content");
        System.out.println(quoteAuthor);
        System.out.println(quoteContent);

        return jSonQuote;
    }

    /**
     * Get a list of universities from hipolabs and parse the most important
     * info to console.
     *
     * @return
     */
    @GetMapping("/universities")
    public List<Object> getUniversities() {
        String url = "http://universities.hipolabs.com/search?name=sports";
        RestTemplate restTemplate = new RestTemplate();
        Object[] universities = restTemplate.getForObject(url, Object[].class);

        //Print the whole response to console
        JSONArray ja = new JSONArray(universities);
        System.out.println(ja.toString());

        //Parse out relevant info from each entry in the response
        Iterator<Object> iter = ja.iterator();
        while (iter.hasNext()) {
            JSONObject jo = (JSONObject) iter.next();
            String name = jo.getString("name");
            String country = jo.getString("country");
            System.out.println(name + " : " + country);

        }
        System.out.println(Arrays.asList(universities));
        return Arrays.asList(universities);
    }

    @GetMapping("/age")
    public boolean getAge() {
        String url = "https://api.agify.io?name=Muizz";
        RestTemplate restTemplate = new RestTemplate();
        Object[] guessAge = restTemplate.getForObject(url, Object[].class);

        JSONArray ja = new JSONArray(guessAge);

        Iterator<Object> iter = ja.iterator();
        while (iter.hasNext()) {
            JSONObject Age = (JSONObject) iter.next();
            JSONObject names = Age.getJSONObject("name");//save as
            String name = Age.getString("name");

            int age = Age.getInt("age");
            System.out.printf("The Name is %s\n", name);
            System.out.printf("The guess age based upon name  is %d\n", age);
            System.out.println("\n");
        }

        return true;
    }
}
