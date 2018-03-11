package net.zriot.ebike.model.ebike;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ebike")
public class EBike {
    @Id
    @GeneratedValue
    private Long id;
    private String sn;
    private String name;
    private String note;
    private Long shopId;
    private String uid;
    private LocalDateTime buyTime;
    private Byte isMembership;
    private BigDecimal membership;
    private BigDecimal monthFee;
    private LocalDate monthStartDate;
    private LocalDate monthEndDate;
    private String batterySn;
    private Byte status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public EBike() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDateTime getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(LocalDateTime buyTime) {
        this.buyTime = buyTime;
    }

    public byte getIsMembership() {
        return isMembership;
    }

    public void setIsMembership(byte isMembership) {
        this.isMembership = isMembership;
    }

    public LocalDate getMonthStartDate() {
        return monthStartDate;
    }

    public void setMonthStartDate(LocalDate monthStartDate) {
        this.monthStartDate = monthStartDate;
    }

    public LocalDate getMonthEndDate() {
        return monthEndDate;
    }

    public void setMonthEndDate(LocalDate monthEndDate) {
        this.monthEndDate = monthEndDate;
    }

    public void setIsMembership(Byte isMembership) {
        this.isMembership = isMembership;
    }

    public BigDecimal getMembership() {
        return membership;
    }

    public void setMembership(BigDecimal membership) {
        this.membership = membership;
    }

    public BigDecimal getMonthFee() {
        return monthFee;
    }

    public void setMonthFee(BigDecimal monthFee) {
        this.monthFee = monthFee;
    }

    public String getBatterySn() {
        return batterySn;
    }

    public void setBatterySn(String batterySn) {
        this.batterySn = batterySn;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
