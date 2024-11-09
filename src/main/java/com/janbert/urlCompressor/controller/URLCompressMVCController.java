package com.janbert.urlCompressor.controller;

import com.janbert.urlCompressor.service.URLUtility;
import com.janbert.urlCompressor.model.URLEntity;
import com.janbert.urlCompressor.repository.URLRepository;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;


@Controller
public class URLCompressMVCController {

    private Logger LOG = LoggerFactory.getLogger(URLCompressorRestController.class);
    private URLRepository urlRepository;

    private ModelMapper modelMapper;
    @Value("${urlCompressor.hostpath:http://localhost:8080/}")
    private String hostPath;

    public URLCompressMVCController(URLRepository urlRepository, ModelMapper modelMapper){
        this.urlRepository = urlRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String showForm(Model theModel){
        URLEntity url = new URLEntity();
        url.setHashmap("default");
        theModel.addAttribute("theUrl", url);
        return "urlForm";
    }

    @PostMapping("/compressUrl")
    public ModelAndView compressURL(@ModelAttribute("theUrl") URLEntity url, Model theModel,
                              final RedirectAttributes redirectAttributes){
        LOG.info("WE GOT DTO [{}]", url);

        url.setHashmap(URLUtility.encrypt(url.getUrlString()));

        url = urlRepository.save(url);

        String base64 = "";
        if(Objects.isNull(url)) {
            return createErrorMessage("Uh oh we can't compress your URL");
        } else {
            base64 = URLUtility.convertBase10ToBase64(url.getId());
        }

        return new ModelAndView("redirect:/compressUrl/" + url.getHashmap() + base64);
    }

    @GetMapping("/compressUrl/{url}")
    public String showCompressedUrl(@PathVariable("url") String urlId, Model theModel){

        theModel.addAttribute("compressedUrl", hostPath + urlId);

        return "compressedURL";
    }

    @GetMapping("/{url}")
    public ModelAndView getUrl(@PathVariable("url") String urlCode, Model theModel){

        if(urlCode.length() < URLUtility.HASHEND) {
            return createErrorMessage("Uh oh we can't seem to find your URL");
        }

        var keyMap = URLUtility.split(urlCode);

        String base64 = keyMap.get(URLUtility.BASE_64_STRING);
        String hash = keyMap.get(URLUtility.HASH_STRING);

        if(Strings.isEmpty(base64)) {
            return createErrorMessage("Uh oh we can't seem to find your URL");
        }

        Long urlId = URLUtility.convertBase64ToBase10(base64);

        URLEntity url = urlRepository.findById(urlId).orElse(null);

        if(Objects.isNull(url)) {
            return createErrorMessage("Uh oh we can't seem to find your URL");
        }

        return new ModelAndView("redirect:" + url.getUrlString());
    }

    private ModelAndView createErrorMessage(String errorMessage){
        var keyMap = new HashMap<String, String>();
        keyMap.put("errorMessage", errorMessage);
        return new ModelAndView("compressedUrl", keyMap);
    }

}
