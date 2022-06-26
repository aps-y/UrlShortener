package com.demo.Backend.data.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ShortenedStore {
    private String actualUrl;
    private String shortUrl;
    private long timeCreated;
    private long ttl;
    private String username;
}
