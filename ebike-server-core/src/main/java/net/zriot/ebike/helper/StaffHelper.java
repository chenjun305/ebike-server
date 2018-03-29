package net.zriot.ebike.helper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.zriot.ebike.entity.Staff;
import net.zriot.ebike.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by ChenJun on 2018/3/29.
 */
@Component
public class StaffHelper {

    private static StaffService staffService;

    @Autowired
    public void setStaffService(StaffService staffService) {
        StaffHelper.staffService = staffService;
    }

    /**
     * 警告:此cache中的staff只能用作获取不变数据，不能用作获取动态数据
     */
    private static Cache<String, Staff> staffCache = CacheBuilder.newBuilder().expireAfterAccess(10 * 60, TimeUnit.SECONDS).maximumSize(500).build();

    public static Staff findStaffByUid(String uid) {
        Staff staff = staffCache.getIfPresent(uid);
        if (staff == null) {
            staff = staffService.findOneByUid(uid);
            if (staff != null) {
                staffCache.put(uid, staff);
            }
        }
        return staff;
    }

}
