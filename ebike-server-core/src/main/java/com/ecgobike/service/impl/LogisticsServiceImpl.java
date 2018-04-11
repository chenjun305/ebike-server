package com.ecgobike.service.impl;

import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.LogisticsStatus;
import com.ecgobike.common.enums.ProductType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Logistics;
import com.ecgobike.entity.Product;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.entity.Staff;
import com.ecgobike.repository.LogisticsRepository;
import com.ecgobike.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenJun on 2018/4/10.
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {
    @Autowired
    LogisticsRepository logisticsRepository;

    @Override
    public Logistics findOneBySn(String sn) {
        return logisticsRepository.findOneBySn(sn);
    }

    @Override
    public Page<Logistics> findAll(Pageable pageable) {
        return logisticsRepository.findAll(pageable);
    }

    @Override
    public Page<Logistics> findAllByType(ProductType type, Pageable pageable) {
        Product product = new Product();
        product.setType(type);
        Logistics logistics = new Logistics();
        logistics.setProduct(product);
        Example<Logistics> example = Example.of(logistics);
        return logisticsRepository.findAll(example, pageable);
    }

    @Override
    public Page<Logistics> findAllByStatus(LogisticsStatus status, Pageable pageable) {
        Logistics logistics = new Logistics();
        logistics.setStatus(status);
        Example<Logistics> example = Example.of(logistics);

        return logisticsRepository.findAll(example, pageable);
    }

    @Override
    public List<Logistics> in(Product product, List<String> snList) throws GException {
        List<Logistics> checkList = logisticsRepository.findAllBySnIn(snList);
        if (checkList != null && checkList.size() > 0) {
            throw new GException(ErrorConstants.ERR_DUPPLICATE_SN);
        }
        List<Logistics> list = new ArrayList<>();
        for (String sn : snList) {
            Logistics logistics = new Logistics();
            logistics.setSn(sn);
            logistics.setProduct(product);
            logistics.setStorageInTime(LocalDateTime.now());
            logistics.setStatus(LogisticsStatus.STORAGE);
            logistics.setCreateTime(LocalDateTime.now());
            logistics.setUpdateTime(LocalDateTime.now());
            list.add(logistics);
        }
        return logisticsRepository.saveAll(list);
    }

    @Override
    public List<Logistics> out(PurchaseOrder purchaseOrder, List<String> snList) throws GException {
        List<Logistics> list = new ArrayList<>();
        for (String sn : snList) {
            Logistics logistics = logisticsRepository.findOneBySn(sn);
            if (logistics == null) {
                throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
            }
            if (logistics.getStatus() != LogisticsStatus.STORAGE) {
                throw new GException(ErrorConstants.NOT_IN_STORAGE);
            }
            logistics.setStorageOutTime(LocalDateTime.now());
            logistics.setPurchaseSn(purchaseOrder.getSn());
            logistics.setStatus(LogisticsStatus.TRANSIT);
            logistics.setUpdateTime(LocalDateTime.now());
            list.add(logistics);
        }
        return logisticsRepository.saveAll(list);
    }

    @Override
    public List<Logistics> shopIn(PurchaseOrder purchaseOrder) {
        List<Logistics> list = logisticsRepository.findAllByPurchaseSn(purchaseOrder.getSn());
        for (Logistics logistics : list) {
            logistics.setShopId(purchaseOrder.getShopId());
            logistics.setShopInTime(LocalDateTime.now());
            logistics.setStatus(LogisticsStatus.SHOP);
            logistics.setUpdateTime(LocalDateTime.now());
        }
        return logisticsRepository.saveAll(list);
    }

    @Override
    public Logistics sell(String sn, Staff staff) throws GException {
        Logistics logistics = logisticsRepository.findOneBySn(sn);
        if (logistics == null || logistics.getProduct().getType() != ProductType.EBIKE) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (logistics.getStatus() != LogisticsStatus.SHOP
                || logistics.getShopId() != staff.getShopId()) {
            throw new GException(ErrorConstants.NOT_YOUR_SHOP_EBIKE);
        }
        logistics.setShopOutTime(LocalDateTime.now());
        logistics.setStatus(LogisticsStatus.SELLED);
        logistics.setUpdateTime(LocalDateTime.now());
        return logisticsRepository.save(logistics);
    }

}
