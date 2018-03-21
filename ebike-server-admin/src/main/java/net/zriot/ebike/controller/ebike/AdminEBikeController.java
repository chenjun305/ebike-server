package net.zriot.ebike.controller.ebike;

import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.ebike.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/20.
 */
@RestController
@RequestMapping("/ebike")
public class AdminEBikeController {

    @Autowired
    EBikeService eBikeService;

    @PostMapping("/list")
    public MessageDto list() {
        List<EBike> ebikes = eBikeService.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("ebikes", ebikes);
        return MessageDto.responseSuccess(data);
    }
}
