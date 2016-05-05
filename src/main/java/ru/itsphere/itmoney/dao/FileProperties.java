package ru.itsphere.itmoney.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileProperties {

    @Value("${data.access.file}")
    public String fileName;

    @Value("${charset.file}")
    public String charsetName;

    public String getFileName() {
        return fileName;
    }

    public String getCharsetName() {
        return charsetName;
    }

}