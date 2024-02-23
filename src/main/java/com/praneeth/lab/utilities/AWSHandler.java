package com.praneeth.lab.utilities;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.cloudfront.model.CreateInvalidationRequest;
import com.amazonaws.services.cloudfront.model.CreateInvalidationResult;
import com.amazonaws.services.cloudfront.model.InvalidationBatch;
import com.amazonaws.services.cloudfront.model.Paths;
import com.amazonaws.services.mediaconvert.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3EncryptionV2;
import com.amazonaws.services.s3.model.*;
import com.praneeth.lab.exception.dto.CustomServiceException;
import eu.medsea.mimeutil.MimeUtil2;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.praneeth.lab.constants.AppConstants.ErrorConstants.INVALID_FILE_TYPE;
import static com.praneeth.lab.constants.AppConstants.ErrorConstants.LARGE_FILE_SIZE;


@Component
@RequiredArgsConstructor
@Log4j2
public class AWSHandler {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    @Value("${aws.s3.bucket-url}")
    private String bucketUrl;
    @Value("${aws.s3.project-folder}")
    private String resourceBucketFolder;





    public void deleteFileFromS3Bucket(String fileUrl) {
        log.info("Deleting file from s3 bucket: {}", fileUrl);
        String extension = FilenameUtils.getExtension(fileUrl);
        if (fileUrl == null || extension.equals("")) {
            log.error("Invalid fileUrl: {}", fileUrl);
        } else {
            String fileName = fileUrl.replace(bucketUrl, "");
            log.info("Deleting fileName: {}", fileName);
            s3Client.deleteObject(bucketName, fileName);
            log.info("File deleted: {}", fileName);
        }
    }

   /* public void deleteHtmlFileFromS3Bucket(String fileUrl) {

        String[] parts = fileUrl.split("/");

        parts[3] = "public/" + parts[3];

        String newUrl = String.join("/", parts);

        log.info("Deleting file from s3 bucket: {}", newUrl);

        String extension = FilenameUtils.getExtension(newUrl);
        if (newUrl == null || extension.equals("")) {
            log.error("Invalid fileUrl: {}", newUrl);
        } else {
            String fileName = newUrl.replace(frontendBucketUrl, "");
            log.info("Deleting fileName: {}", fileName);
            s3Client.deleteObject(frontendBucketName, fileName);
            log.info("File deleted: {}", fileName);
        }
    }*/

    private AacSettings getAacSettings() {
        return new AacSettings()
                .withAudioDescriptionBroadcasterMix(AacAudioDescriptionBroadcasterMix.NORMAL)
                .withBitrate(64000)
                .withRateControlMode(AacRateControlMode.CBR)
                .withCodecProfile(AacCodecProfile.HEV1)
                .withCodingMode(AacCodingMode.CODING_MODE_2_0)
                .withRawFormat(AacRawFormat.NONE)
                .withSampleRate(48000)
                .withSpecification(AacSpecification.MPEG4);
    }

    private VideoDescription getVideoDescription(VideoPreprocessor videoPreProcessors, VideoCodecSettings videoCodecSettings) {
        return new VideoDescription()
                .withWidth(852)
                .withScalingBehavior(ScalingBehavior.DEFAULT)
                .withHeight(480)
                .withVideoPreprocessors(videoPreProcessors)
                .withTimecodeInsertion(VideoTimecodeInsertion.DISABLED)
                .withAntiAlias(AntiAlias.ENABLED)
                .withSharpness(50)
                .withCodecSettings(videoCodecSettings)
                .withAfdSignaling(AfdSignaling.NONE)
                .withDropFrameTimecode(DropFrameTimecode.ENABLED)
                .withRespondToAfd(RespondToAfd.NONE)
                .withColorMetadata(ColorMetadata.INSERT);
    }

    private H264Settings getH264Settings(H264QvbrSettings qvbrSettings) {
        return new H264Settings()
                .withInterlaceMode(H264InterlaceMode.PROGRESSIVE)
                .withNumberReferenceFrames(3)
                .withSyntax(H264Syntax.DEFAULT)
                .withSoftness(0)
                .withGopClosedCadence(1)
                .withGopSize(90.0)
                .withSlices(1)
                .withGopBReference(H264GopBReference.DISABLED)
                .withMaxBitrate(600000) // 1000000
                .withSlowPal(H264SlowPal.DISABLED)
                .withSpatialAdaptiveQuantization(H264SpatialAdaptiveQuantization.ENABLED)
                .withTemporalAdaptiveQuantization(H264TemporalAdaptiveQuantization.ENABLED)
                .withFlickerAdaptiveQuantization(H264FlickerAdaptiveQuantization.DISABLED)
                .withEntropyEncoding(H264EntropyEncoding.CABAC)
                .withFramerateControl(H264FramerateControl.INITIALIZE_FROM_SOURCE)
                .withRateControlMode(H264RateControlMode.QVBR)
                .withQvbrSettings(qvbrSettings)
                .withCodecProfile(H264CodecProfile.MAIN)
                .withTelecine(H264Telecine.NONE)
                .withMinIInterval(0)
                .withAdaptiveQuantization(H264AdaptiveQuantization.HIGH)
                .withCodecLevel(H264CodecLevel.AUTO)
                .withFieldEncoding(H264FieldEncoding.PAFF)
                .withSceneChangeDetect(H264SceneChangeDetect.ENABLED)
                .withQualityTuningLevel(H264QualityTuningLevel.SINGLE_PASS)
                .withFramerateConversionAlgorithm(H264FramerateConversionAlgorithm.DUPLICATE_DROP)
                .withUnregisteredSeiTimecode(H264UnregisteredSeiTimecode.DISABLED)
                .withGopSizeUnits(H264GopSizeUnits.FRAMES)
                .withParControl(H264ParControl.INITIALIZE_FROM_SOURCE)
                .withNumberBFramesBetweenReferenceFrames(2)
                .withRepeatPps(H264RepeatPps.DISABLED)
                .withDynamicSubGop(H264DynamicSubGop.STATIC);
    }

    private M3u8Settings getM3u8Settings() {
        return new M3u8Settings()
                .withAudioFramesPerPes(4)
                .withPcrControl(M3u8PcrControl.PCR_EVERY_PES_PACKET)
                .withPmtPid(480)
                .withPrivateMetadataPid(503)
                .withProgramNumber(1)
                .withPatInterval(0)
                .withPmtInterval(0)
                .withVideoPid(481)
                .withAudioPids(Arrays.asList(482, 483, 484, 485, 486, 487, 488, 489, 490, 491, 492, 493, 494, 495, 496, 497, 498));
    }

    public Optional<String> uploadToS3Bucket(MultipartFile file, String name, String folder) {
        try {
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (file.getSize() > 15000000) throw new CustomServiceException(LARGE_FILE_SIZE);

            assert fileExtension != null;

            MimeUtil2 mimeUtil = new MimeUtil2();
            mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            String mimeType = MimeUtil2.getMostSpecificMimeType(mimeUtil.getMimeTypes(inputStream)).toString();

            log.info("mimeType : " + mimeType);
            log.info("file.getContentType() : " + file.getContentType());
            log.info("FilenameUtils.getExtension(file.getOriginalFilename()) : " + FilenameUtils.getExtension(file.getOriginalFilename()));

            if (mimeType.equalsIgnoreCase("application/zip") && file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document") && FilenameUtils.getExtension(file.getOriginalFilename()).equals("docx")) {
                fileExtension = "docx";
            } else if (mimeType.equalsIgnoreCase("application/msword") && file.getContentType().equalsIgnoreCase("application/msword") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("doc")) {
                fileExtension = "doc";
            } else if (mimeType.equalsIgnoreCase("image/png") && file.getContentType().equalsIgnoreCase("image/png") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("png")) {
                fileExtension = "png";
            } else if (mimeType.equalsIgnoreCase("image/jpeg") && file.getContentType().equalsIgnoreCase("image/jpeg") && (FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("jpeg") || FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("jpg"))) {
                fileExtension = "jpeg";
            } else if (mimeType.equalsIgnoreCase("application/pdf") && file.getContentType().equalsIgnoreCase("application/pdf") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("pdf")) {
                fileExtension = "pdf";
            } else if (mimeType.equalsIgnoreCase("image/jpg") && file.getContentType().equalsIgnoreCase("image/jpg") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("jpg")) {
                fileExtension = "jpg";
            }else if (mimeType.equalsIgnoreCase("application/zip") && file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("xlsx")) {
                fileExtension = "xlsx";
            }else if (mimeType.equalsIgnoreCase("application/octet-stream") && file.getContentType().equalsIgnoreCase("text/csv") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("csv")) {
                fileExtension = "csv";
            }else if (mimeType.equalsIgnoreCase("application/msword") && file.getContentType().equalsIgnoreCase("application/vnd.ms-excel") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("xls")) {
                fileExtension = "xls";
            } else {
                throw new CustomServiceException(INVALID_FILE_TYPE);
            }

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            String fileName = resourceBucketFolder + folder + name +"."+ fileExtension;
            s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
            String fileUrl = bucketName + fileName;
            log.info("fileUrl: {}", fileUrl);
            return Optional.of(bucketUrl + fileName);
        } catch (IOException e) {
            log.trace("Error occurred while uploading image to s3: {}", e.getMessage());
            return Optional.empty();
        }
    }


    public Optional<String> uploadToS3BucketToFile(FileSystemResource file, String name, String folder) {
        try {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.contentLength());
            String fileName = resourceBucketFolder + folder + name + ".csv";
            s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
            String fileUrl = bucketName + fileName;
            log.info("fileUrl: {}", fileUrl);
            return Optional.of(bucketUrl + fileName);
        } catch (IOException e) {
            log.trace("Error occurred while uploading image to s3: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /*public Optional<String> uploadHtmlFileToS3Bucket(FileSystemResource file, String name, String folder) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.contentLength());
            metadata.setContentType("text/html");
            String fileName =  folder + name + ".html";
            s3Client.putObject(frontendBucketName, fileName, file.getInputStream(), metadata);
            String fileUrl = frontendBucketName +"/program/"+ name + ".html";
            log.info("fileUrl: {}", fileUrl);
            String url = "program/"+ name + ".html";
            return Optional.of(frontendBucketUrl + url);
        } catch (IOException e) {
            log.trace("Error occurred while uploading file to s3: {}", e.getMessage());
            return Optional.empty();
        }
    }*/

/*
    public Optional<String> clientSideEncryption(MultipartFile file, String name, String folder) {
        log.info("start method client Side encryption");
        try {

            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (file.getSize() > 15000000) throw new CustomServiceException(LARGE_FILE_SIZE);

            MimeUtil2 mimeUtil = new MimeUtil2();
            mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            String mimeType = MimeUtil2.getMostSpecificMimeType(mimeUtil.getMimeTypes(inputStream)).toString();

            log.info("mimeType : {}", mimeType);
            log.info("file.getContentType() : {}" , file.getContentType());
            log.info("FilenameUtils.getExtension(file.getOriginalFilename()) : {} " , FilenameUtils.getExtension(file.getOriginalFilename()));


            if (mimeType.equalsIgnoreCase("application/zip") && file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document") && FilenameUtils.getExtension(file.getOriginalFilename()).equals("docx")) {
                fileExtension = "docx";
            } else if (mimeType.equalsIgnoreCase("application/msword") && file.getContentType().equalsIgnoreCase("application/msword") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("doc")) {
                fileExtension = "doc";
            } else if (mimeType.equalsIgnoreCase("image/png") && file.getContentType().equalsIgnoreCase("image/png") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("png")) {
                fileExtension = "png";
            } else if (mimeType.equalsIgnoreCase("image/jpeg") && file.getContentType().equalsIgnoreCase("image/jpeg") && (FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("jpeg") || FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("jpg"))) {
                fileExtension = "jpeg";
            } else if (mimeType.equalsIgnoreCase("application/pdf") && file.getContentType().equalsIgnoreCase("application/pdf") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("pdf")) {
                fileExtension = "pdf";
            } else if (mimeType.equalsIgnoreCase("image/jpg") && file.getContentType().equalsIgnoreCase("image/jpg") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("jpg")) {
                fileExtension = "jpg";
            }else if (mimeType.equalsIgnoreCase("application/zip") && file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("xlsx")) {
                fileExtension = "xlsx";
            }else if (mimeType.equalsIgnoreCase("application/octet-stream") && file.getContentType().equalsIgnoreCase("text/csv") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("csv")) {
                fileExtension = "csv";
            }else if (mimeType.equalsIgnoreCase("application/msword") && file.getContentType().equalsIgnoreCase("application/vnd.ms-excel") && FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("xls")) {
                fileExtension = "xls";
            } else {
                throw new CustomServiceException(INVALID_FILE_TYPE);
            }

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            String keyName = donationBucketFolder + folder + name + "."+fileExtension;
            s3Encryption.putObject(bucketName, keyName, file.getInputStream(), metadata);
            S3Object s3Object = s3Encryption.getObject(bucketName, keyName);
            log.info("key name => {}",s3Object.getKey());
            return Optional.of(s3Object.getKey());

        } catch (Exception e) {
            log.trace(e);
            throw new CustomServiceException(e.getMessage());
        }
    }
*/


    /*public ByteArrayOutputStream downloadEncryptedFile(String key_name) {
        try {
            S3Object s3Object = s3Encryption.getObject(new GetObjectRequest(bucketName, key_name));
            InputStream is = s3Object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream;
        }catch (IOException ioException) {
            log.error("IOException: {}" , ioException.getMessage());
            throw new CustomServiceException("sorry you can't download this file at this time");
        } catch (AmazonServiceException serviceException) {
            log.info("AmazonServiceException Message:  {}  ", serviceException.getMessage());
            throw new CustomServiceException("sorry you can't download this file at this time");
        } catch (AmazonClientException clientException) {
            log.info("AmazonClientException Message: {}", clientException.getMessage());
            throw new CustomServiceException("sorry you can't download this file at this time");
        }

    }*/

   /* public void invalidateCache(String fileUrl){

        String[] parts = fileUrl.split("/");
        String path = "/public/" + parts[parts.length-2] + "/" + parts[parts.length-1];

        log.info("Invalidating file from s3 bucket: {}", path);

        String unique_id= UUID.randomUUID().toString();
        Paths invalidation_paths = new Paths().withItems(path).withQuantity(1);
        InvalidationBatch invalidation_batch = new InvalidationBatch(invalidation_paths, unique_id);
        CreateInvalidationRequest invalidation = new CreateInvalidationRequest(frontendDistributionId, invalidation_batch);
        CreateInvalidationResult result = amazonCloudFrontClient.createInvalidation(invalidation);

        log.info("invalidate result => {}", result);
    }*/
}
