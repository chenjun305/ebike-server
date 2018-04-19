package com.ecgobike.service.impl;

import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.FileType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.service.FileService;
import com.google.common.base.Strings;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
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

    private static final long appId = 1251198400;
    private static final String secretId = "AKID8EE3jE50KUhjkatmwdVJ1YJJEzeAYlq7";
    private static final String secretKey = "Nl4QbbnwLdkkZDmBOQaZPSO9ILDuPo37";
    private static final String bucketNameUserAvatar = "ebike-user-avatar";
    private static final String bucketNameStaffAvatar = "ebike-staff-avatar";
    private static final String bucketNameUserIdcard = "ebike-user-idcard";
    private static final String bucketNameStaffIdcard = "ebike-staff-idcard";
    private static final String fileNameUserAvatar = "user-avatar";
    private static final String fileNameStaffAvatar = "staff-avatar";
    private static final String fileNameUserIdcard = "user-idcard";
    private static final String fileNameStaffIdcard = "staff-idcard";

    private static COSClient cosClient = null;

    static {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        cosClient = new COSClient(cred, clientConfig);
    }


    @Override
    public String saveFile(FileType type, String uid, MultipartFile file) throws GException {
        //String fileName = UUID.randomUUID().toString() + prepareSuffix(file);
        String bucketName = "";
        String fileName = uid + "-";
        switch (type) {
            case USER_AVATAR:
                bucketName = bucketNameUserAvatar;
                fileName += fileNameUserAvatar;
                break;
            case STAFF_AVATAR:
                bucketName = bucketNameStaffAvatar;
                fileName += fileNameStaffAvatar;
                break;
            case USER_IDCARD:
                bucketName = bucketNameUserIdcard;
                fileName += fileNameUserIdcard;
                break;
            case STAFF_IDCARD:
                bucketName = bucketNameStaffIdcard;
                fileName += fileNameStaffIdcard;
                break;
            default:
                throw new GException(ErrorConstants.ILLEGAL_IMAGE_FORMAT);
        }
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        bucketName = bucketName + "-" + appId;
        fileName  += prepareSuffix(file);
        String key = "/" + fileName;
        System.out.println("key=" + key);

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置输入流长度
            objectMetadata.setContentLength(file.getSize());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), objectMetadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            String etag = putObjectResult.getETag();
            System.out.println("etag=" + etag);
        } catch (IOException e) {
            throw new GException(ErrorConstants.ILLEGAL_IMAGE_FORMAT);
        }
        return fileName;
    }

    protected String prepareSuffix(MultipartFile file) throws GException {
        String originalFilename = file.getOriginalFilename();
        if (Strings.isNullOrEmpty(originalFilename) || originalFilename.indexOf(".") == -1) {
            throw new GException(ErrorConstants.ILLEGAL_IMAGE_FORMAT);
        }
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
