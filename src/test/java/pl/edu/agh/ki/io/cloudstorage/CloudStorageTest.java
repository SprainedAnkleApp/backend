package pl.edu.agh.ki.io.cloudstorage;

import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class CloudStorageTest {

    @Test
    public void uploadFileTest() throws Exception{
        GoogleCloudFileService fileService = new GoogleCloudFileService();

        MultipartFile multipartFile = new MockMultipartFile("dog.jpeg", new FileInputStream(new File("src/test/java/pl/edu/agh/ki/io/fixtures/dog.jpeg")));

        fileService.upload(multipartFile);
    }
}
