package com.louislivi.fastdep.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File storage util
 *
 * @author : louislivi
 */
public class FileStorageUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageUtil.class);

    private final Path fileStorageBaseLocation;

    private final Path fileStorageLocation;

    private static final Pattern DATE_FORMAT_PATTERN = Pattern.compile("(?<=\\{)(.+?)(?=})");

    private FastDepFileProperties fastDepFileProperties;

    /**
     * Upload file
     *
     * @param file file entity
     * @return FileResponse
     */
    public FileResponse uploadFile(MultipartFile file) {
        String fileName = this.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fastDepFileProperties.getDownloadUrl())
                .path(parseDateDir(fastDepFileProperties.getUploadDir()))
                .path(File.separator)
                .path(fileName)
                .scheme(fastDepFileProperties.getScheme())
                .toUriString();
        return new FileResponse(fileName, fileDownloadUri, file.getSize(), file.getOriginalFilename());
    }

    /**
     * Download file
     *
     * @param fileName file name
     * @param request  http request
     * @param response http response
     * @throws IOException io
     */
    public void downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Load file as Resource
        fileName = URLDecoder.decode(fileName, "UTF-8");
        Resource resource = this.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        response.setContentType(contentType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename());
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = resource.getInputStream();
        readInputStreamToOutStream(inputStream, outputStream);
    }

    /**
     * readInputStream to OutStream
     * @param inStream in stream
     * @param outStream out Stream
     * @throws IOException io
     */
    private void readInputStreamToOutStream(InputStream inStream, OutputStream outStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        outStream.close();
    }

    /**
     * Construction
     *
     * @param fastDepFileProperties fastDepFileProperties
     */
    public FileStorageUtil(FastDepFileProperties fastDepFileProperties) {
        this.fastDepFileProperties = fastDepFileProperties;
        this.fileStorageBaseLocation = Paths.get(fastDepFileProperties.getBaseDir())
                .toAbsolutePath().normalize();
        this.fileStorageLocation = Paths.get(fastDepFileProperties.getBaseDir(), parseDateDir(fastDepFileProperties.getUploadDir()))
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * Parse date dir path
     *
     * @param uploadDir upload dir path
     * @return uploadDir
     */
    public static String parseDateDir(String uploadDir) {
        if (StringUtils.isEmpty(uploadDir)) {
            return uploadDir;
        }
        Matcher matcher = DATE_FORMAT_PATTERN.matcher(uploadDir);
        while (matcher.find()) {
            String pattern = matcher.group();
            String replacePath = "";
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                replacePath = sdf.format(new Date());
            } catch (Exception ignore) {
            }
            uploadDir = uploadDir.replace(String.format("{%s}", pattern), replacePath);
        }
        return uploadDir;
    }

    /**
     * uuid
     *
     * @return uuid
     */
    public static String uuid() {
        String s = UUID.randomUUID().toString();
        return s.replace("-", "");
    }

    /**
     * save file
     * @param file file
     * @return file name
     */
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (!this.fastDepFileProperties.getOriginName()) {
            String[] fileNameArr = fileName.split("\\.");
            fileName = uuid() + "." + fileNameArr[fileNameArr.length - 1];
        }
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /**
     * Load file resource
     *
     * @param fileName file name
     * @return resource
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageBaseLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }
}

