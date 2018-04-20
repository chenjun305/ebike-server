package com.ecgobike.service.impl;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.FileType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.service.FileService;
import com.google.common.base.Strings;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by ChenJun on 2018/4/18.
 */
@Service
public class CosFileServiceImpl implements FileService {

    private static final String fileNameUserAvatar = "user-avatar";
    private static final String fileNameUserIdcard = "user-idcard";
    private static COSClient cosClient = null;

    static {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(Constants.COS_SECRET_ID, Constants.COS_SECRET_KEY);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        cosClient = new COSClient(cred, clientConfig);
    }


    @Override
    public String[] saveFile(FileType type, String uid, MultipartFile[] files) throws GException {
        //String fileName = UUID.randomUUID().toString() + prepareSuffix(file);
        String bucketName;
        String fileNamePrefix = uid + "-";
        switch (type) {
            case USER_AVATAR:
                bucketName = Constants.BUCKET_USER_AVATAR;
                fileNamePrefix += fileNameUserAvatar;
                break;

            case USER_IDCARD:
                bucketName = Constants.BUCKET_USER_IDCARD;
                fileNamePrefix += fileNameUserIdcard;
                break;

            default:
                throw new GException(ErrorConstants.ILLEGAL_IMAGE_FORMAT);
        }
        String[] fileNames = new String[files.length];
        for (int i=0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = fileNamePrefix + "-" + i + prepareSuffix(file);
            fileNames[i] = fileName;
            String key = "/" + fileName;
            try {
//                if (cosClient.doesObjectExist(bucketName, key)) {
//                    System.out.println(key + " already exist, delete first");
//                    cosClient.deleteObject(bucketName, key);
//                }
                System.out.println("upload " + key);
                ObjectMetadata objectMetadata = new ObjectMetadata();
                // 设置输入流长度
                objectMetadata.setContentLength(file.getSize());

                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), objectMetadata);
                PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
                String etag = putObjectResult.getETag();
                System.out.println("etag=" + etag);
            } catch (IOException e) {
                throw new GException(ErrorConstants.ILLEGAL_IMAGE_FORMAT);
            } catch (CosClientException e) {
                throw new GException(ErrorConstants.ERR_UPLOAD_PIC);
            }
        }
        return fileNames;
    }

    protected String prepareSuffix(MultipartFile file) throws GException {
        String originalFilename = file.getOriginalFilename();
        if (Strings.isNullOrEmpty(originalFilename) || originalFilename.indexOf(".") == -1) {
            throw new GException(ErrorConstants.ILLEGAL_IMAGE_FORMAT);
        }
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
