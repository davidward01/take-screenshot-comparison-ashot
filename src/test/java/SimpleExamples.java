import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.Test;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SimpleExamples {
    @Test
    void testImageAgainstS3Image() throws IOException {
        // Set up the S3 client
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();
        String bucketName = "your-bucket-name";
        String objectKey = "your-object-key";

        // Load the actual image from a file
        File actualImageFile = new File("path/to/actual/screenshot.png");
        BufferedImage actualImage = ImageIO.read(actualImageFile);

        // Load the expected image from S3
        S3Object s3Object = s3Client.getObject(bucketName, objectKey);
        BufferedImage expectedImage = ImageIO.read(s3Object.getObjectContent());

        // Compare the images using AShot
        ImageDiff diff = new ImageDiffer().makeDiff(expectedImage, actualImage);
        assertFalse(diff.hasDiff(), "Images are not the same");
    }

}