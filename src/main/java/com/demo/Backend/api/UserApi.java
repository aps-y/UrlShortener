package com.demo.Backend.api;


import com.demo.Backend.Model.*;
import com.demo.Backend.data.entity.ShortenedStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserApi {

    private static final String BASE_PATH = "http://localhost:8080/";

    Map<String, String> users = new HashMap<>();

    Map<String, ShortenedStore> shortenedUrls = new HashMap();

    private long num = 100000;
    private String getShortenedUrl() {
        return ""+(num++);
    }

    @CrossOrigin
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public boolean userLogin(@RequestBody UserCredentials userCredentials) {

        if(users.containsKey(userCredentials.getUsername()) && users.get(userCredentials.getUsername()).equals(userCredentials.getPassword())){
            return true;
        }

        return false;
    }

    @CrossOrigin
    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public boolean userSignUp(@RequestBody UserCredentials userCredentials) {

        if(users.containsKey(userCredentials.getUsername()))
            return false;
        users.put(userCredentials.getUsername(), userCredentials.getPassword());
        return true;
    }

    @CrossOrigin
    @RequestMapping(path = "/shortenUrl", method = RequestMethod.POST)
    public ShortenedResponse shortenUrl(@RequestBody ShortenRequest request) {

        String shortenedUrl = getShortenedUrl();
        ShortenedStore entry = new ShortenedStore();
        entry.setShortUrl(shortenedUrl);
        entry.setActualUrl(request.getUrl());
        entry.setUsername(request.getUsername());
        entry.setTimeCreated(Instant.now().toEpochMilli());
        entry.setTtl(request.getTtl());
        shortenedUrl = shortenedUrl;
        shortenedUrls.put(shortenedUrl, entry);

        return new ShortenedResponse(BASE_PATH + shortenedUrl);
    }

    @CrossOrigin
    @RequestMapping(path = "/{shortUrl}", method = RequestMethod.GET)
    public ActualResponse shortenUrl(@PathVariable(name = "shortUrl") String shortUrl) {
        if(!shortenedUrls.containsKey(shortUrl)) {
            return new ActualResponse(null, "url not found");
        }
        ShortenedStore value = shortenedUrls.get(shortUrl);
        long currentTime = Instant.now().toEpochMilli();
        if(currentTime > value.getTimeCreated() + value.getTtl()*1000) {
            shortenedUrls.remove(shortUrl);
            return new ActualResponse(null, "url expired");
        }

        return new ActualResponse(value.getActualUrl(), null);
    }

}
