package com.ecgobike.service;

import com.ecgobike.common.enums.FileType;
import com.ecgobike.common.exception.GException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created by ChenJun on 2018/4/18.
 */
public interface FileService {
    String[] saveFile(FileType type, String uid, MultipartFile files[]) throws GException;
}
