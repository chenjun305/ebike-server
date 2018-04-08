package com.ecgobike.controller;

import com.ecgobike.pojo.request.StoragePutParams;
import com.ecgobike.pojo.response.MessageDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChenJun on 2018/4/8.
 */
@RestController
@RequestMapping("/storage")
public class AdminStorageController {

    public MessageDto put(StoragePutParams params) {
        return MessageDto.responseSuccess();
    }
}
