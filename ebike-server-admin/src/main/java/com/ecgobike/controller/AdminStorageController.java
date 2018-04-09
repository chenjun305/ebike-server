package com.ecgobike.controller;

import com.ecgobike.pojo.request.StorageInParams;
import com.ecgobike.pojo.request.StorageOutParams;
import com.ecgobike.pojo.response.MessageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChenJun on 2018/4/8.
 */
@RestController
@RequestMapping("/storage")
public class AdminStorageController {

    @PostMapping("/out")
    public MessageDto out(StorageOutParams params) {
        return MessageDto.responseSuccess();
    }

    @RequestMapping("/out/list")
    public MessageDto outList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                                          Pageable pageable
    ) {
        return MessageDto.responseSuccess();
    }

    @PostMapping("/in")
    public MessageDto in(StorageInParams params) {
        return MessageDto.responseSuccess();
    }

    @RequestMapping("/in/list")
    public MessageDto inList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        return MessageDto.responseSuccess();
    }
}
