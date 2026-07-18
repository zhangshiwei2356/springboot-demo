package com.demo.business.support;

/**
 * 档案打包下载结果。
 */
public class ArchiveDownloadPackage {

    private final byte[] content;
    private final String filename;

    public ArchiveDownloadPackage(byte[] content, String filename) {
        this.content = content;
        this.filename = filename;
    }

    public byte[] getContent() {
        return content;
    }

    public String getFilename() {
        return filename;
    }
}
