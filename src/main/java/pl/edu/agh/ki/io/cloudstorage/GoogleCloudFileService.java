package pl.edu.agh.ki.io.cloudstorage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class GoogleCloudFileService {

    public void upload(MultipartFile file, String path) throws IOException {
        try {
            StorageOptions storageOptions = StorageOptions.newBuilder().setProjectId("sprainedankle")
                    .setCredentials(GoogleCredentials
                            .fromStream(new FileInputStream("gcloud-credentials/sprainedankle-4c6c48239f8b.json")))
                    .build();
            Storage storage = storageOptions.getService();
            storage.create(BlobInfo.newBuilder("sprained-ankle-photos", path).build(), file.getBytes(),
                    BlobTargetOption.predefinedAcl(PredefinedAcl.PRIVATE));
        } catch (IllegalStateException e) {
            throw new RuntimeException();
        }
    }

    public void download(String photoName, String destFilePath) {
        try {
            StorageOptions storageOptions = StorageOptions.newBuilder().setProjectId("sprainedankle")
                    .setCredentials(GoogleCredentials
                            .fromStream(new FileInputStream("gcloud-credentials/sprainedankle-4c6c48239f8b.json")))
                    .build();
            Storage storage = storageOptions.getService();
            Blob blob = storage.get(BlobId.of("sprained-ankle-photos", photoName));

            blob.downloadTo(Paths.get(destFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
