package com.janbert.urlCompressor.service;

import com.janbert.urlCompressor.model.URLEntity;
import com.janbert.urlCompressor.repository.URLRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class URLService {

    public static final int HASHLIMIT = 5;
    private final URLRepository urlRepository;

    public URLService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public URLEntity findById(Long id) throws URLEntityNotFoundException {

        /*//code prefix 5 characters are hash so we have to remove
        if(code.length() < HASHLIMIT) {
            throw new URLEntityNotFoundException(String.format("URL code %s is not valid", code))
        }

        var keyMap = URLUtility.split(code);
        Long urlIndexBase64 = Long.getLong(keyMap.get("base64String"));
        Long urlIndex = URLUtility.convertBase10(urlIndexBase64);
*/

        Optional<URLEntity> optionalUrlEntity = urlRepository.findById(id);

        if(optionalUrlEntity.isEmpty()){
            throw new URLEntityNotFoundException(String.format("URL with code [%s] not found", Long.toString(id)));
        }

        return optionalUrlEntity.get();
    }
}
