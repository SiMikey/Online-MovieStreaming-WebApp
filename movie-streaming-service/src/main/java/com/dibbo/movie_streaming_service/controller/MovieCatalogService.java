package com.dibbo.movie_streaming_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieCatalogService {

    public static final String Catalog_service ="http://Movie-catalog-service";

    @Autowired
    private RestTemplate restTemplate;

    public String getMoviePath(Long movieInfoId){
        var response = restTemplate.getForEntity(Catalog_service+"/movie-Info/find-path-by-id/{movieInfoId}",String.class,movieInfoId);
        return response.getBody();
    }

}
