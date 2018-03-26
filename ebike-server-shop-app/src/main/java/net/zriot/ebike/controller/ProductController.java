package net.zriot.ebike.controller;

import net.zriot.ebike.pojo.response.BatteryVO;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.pojo.response.EBikeVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/23.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @RequestMapping("/list")
    public MessageDto list(){
        List<EBikeVO> ebikeList = new ArrayList<>();
        EBikeVO eBikeVO = new EBikeVO();
        eBikeVO.setModel("1234567");
        eBikeVO.setColor("white");
        eBikeVO.setSellNum(23);
        eBikeVO.setStockNum(12);
        ebikeList.add(eBikeVO);
        List<BatteryVO> batteryList = new ArrayList<>();
        BatteryVO batteryVO = new BatteryVO();
        batteryVO.setType("XXX Type");
        batteryVO.setStockNum(12);
        batteryList.add(batteryVO);
        Map<String, Object> data = new HashMap<>();
        data.put("ebikeList", ebikeList);
        data.put("batteryList", batteryList);
        return MessageDto.responseSuccess(data);
    }
}
