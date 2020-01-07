package com.louislivi.fastdep.file;

/**
 * File response
 *
 * @author : louislivi
 */
public class FileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String originName;
    private long size;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public FileResponse(String fileName, String fileDownloadUri, long size, String originName) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.size = size;
        this.originName = originName;
    }
}
