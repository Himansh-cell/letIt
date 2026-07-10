package com.letit.letit.Service;

import com.letit.letit.Entity.MultiMedia;
import com.letit.letit.StoreMedia.SaveMedia;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@ConditionalOnProperty(name = "media.storage", havingValue = "cloud")
public class SaveByCloud implements SaveMedia {

    @Override
    public MultiMedia save(MultipartFile file, String postId) throws IOException {
        throw new UnsupportedOperationException("Cloud storage not yet implemented");
    }

    @Override
    public void delete(String mediaId) throws IOException {
        throw new UnsupportedOperationException("Cloud storage not yet implemented");
    }
}
