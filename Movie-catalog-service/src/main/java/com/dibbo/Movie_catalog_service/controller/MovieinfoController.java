package com.dibbo.Movie_catalog_service.controller;

import com.dibbo.Movie_catalog_service.model.MovieInfo;
import com.dibbo.Movie_catalog_service.model.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieinfoController {

    @Autowired
    private MovieRepository repository;

    @PostMapping("/movie-Info/save")
    public List<MovieInfo> saveAll(@RequestBody List<MovieInfo> movieInfoList){
        return repository.saveAll(movieInfoList);
    }

    @GetMapping("/movie-Info/list")
    public List<MovieInfo>getAll(){return repository.findAll();
    }

    @GetMapping("/movie-Info/find-path-by-id/{movieInfoId}")
    public  String findPathById(@PathVariable Long movieInfoId){
       var videoInfoOptional = repository.findById(movieInfoId);
       return videoInfoOptional.map(MovieInfo::getPath).orElse(null);
    }


}
