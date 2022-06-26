package com.demo.Backend.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenRequest {

    private String url;
    private String username;
    private int ttl;
}
