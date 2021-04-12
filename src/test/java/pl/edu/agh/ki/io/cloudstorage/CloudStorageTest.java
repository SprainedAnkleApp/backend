package pl.edu.agh.ki.io.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GoogleCloudFileService.class })
public class CloudStorageTest {
    @Nested
    class BothWayTransferTest {
        private Path path = Paths.get("src/test/java/pl/edu/agh/ki/io/daef#@34daw");

        @Autowired
        GoogleCloudFileService fileService;

        @BeforeEach
        public void createTestDir() {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @AfterEach
        public void cleanResultsDir() {
            try {
                FileUtils.cleanDirectory(new File(path.toString()));
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Test
        public void uploadFileTest() throws Exception {

            MultipartFile multipartFile = new MockMultipartFile("dog.jpeg",
                    new FileInputStream(new File("src/test/java/pl/edu/agh/ki/io/fixtures/dog.jpeg")));

            fileService.upload(multipartFile, "dog.jpeg");

            fileService.download("dog.jpeg", path.toString() + "/dog.jpeg");

            MultipartFile resultFile = new MockMultipartFile("dog.jpeg",
                    new FileInputStream(new File(path.toString() + "/dog.jpeg")));

            assertArrayEquals(multipartFile.getBytes(), resultFile.getBytes());
        }
    }
}
