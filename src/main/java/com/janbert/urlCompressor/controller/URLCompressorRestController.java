package com.janbert.urlCompressor.controller;

import com.janbert.urlCompressor.service.URLEntityNotFoundException;
import com.janbert.urlCompressor.service.URLService;
import com.janbert.urlCompressor.model.URLEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class URLCompressorRestController {

    private Logger LOG = LoggerFactory.getLogger(URLCompressorRestController.class);
    private URLService urlService;

    public URLCompressorRestController(URLService urlService){
        this.urlService = urlService;
    }

    @GetMapping("/test")
    public String hello(){
        return "Hello this is the URL Shortener";
    }

    @GetMapping("/url/{id}")
    public URLEntity getUrl(@PathVariable long id){
        LOG.info("We are in getUrl with pathvariable id [{}]", id);
        URLEntity urlEntity = null;
        try {
            urlEntity = urlService.findById(Long.valueOf(id));
        } catch(URLEntityNotFoundException e){
            LOG.error("URL with id [{}] not found", id, e);
        }
        return urlEntity;
    }
}
