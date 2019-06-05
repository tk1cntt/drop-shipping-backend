package com.dropshipping.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.dropshipping.domain.ShoppingCart} entity.
 */
public class ShoppingCartDTO implements Serializable {

    private Long id;

    private String aliwangwang;

    private Float depositAmount;

    private Float depositRatio;

    private Float serviceFee;

    private Float serviceFeeDiscount;

    private Boolean itemChecking;

    private Boolean itemWoodCrating;

    private String shopId;

    private String shopLink;

    private String shopName;

    private String shopNote;

    private String sourceData;

    private Float totalAmount;

    private Integer totalLink;

    private Integer totalQuantity;

    private Float finalAmount;

    private String website;

    private LocalDate createAt;

    private LocalDate updateAt;

    private Long createById;

    private String createByLogin;

    private Long updateById;

    private String updateByLogin;

    private List<ShoppingCartItemDTO> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliwangwang() {
        return aliwangwang;
    }

    public void setAliwangwang(String aliwangwang) {
        this.aliwangwang = aliwangwang;
    }

    public Float getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Float depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Float getDepositRatio() {
        return depositRatio;
    }

    public void setDepositRatio(Float depositRatio) {
        this.depositRatio = depositRatio;
    }

    public Float getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Float serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Float getServiceFeeDiscount() {
        return serviceFeeDiscount;
    }

    public void setServiceFeeDiscount(Float serviceFeeDiscount) {
        this.serviceFeeDiscount = serviceFeeDiscount;
    }

    public Boolean isItemChecking() {
        return itemChecking;
    }

    public void setItemChecking(Boolean itemChecking) {
        this.itemChecking = itemChecking;
    }

    public Boolean isItemWoodCrating() {
        return itemWoodCrating;
    }

    public void setItemWoodCrating(Boolean itemWoodCrating) {
        this.itemWoodCrating = itemWoodCrating;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopLink() {
        return shopLink;
    }

    public void setShopLink(String shopLink) {
        this.shopLink = shopLink;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNote() {
        return shopNote;
    }

    public void setShopNote(String shopNote) {
        this.shopNote = shopNote;
    }

    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalLink() {
        return totalLink;
    }

    public void setTotalLink(Integer totalLink) {
        this.totalLink = totalLink;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Float getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Float finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public Long getCreateById() {
        return createById;
    }

    public void setCreateById(Long userId) {
        this.createById = userId;
    }

    public String getCreateByLogin() {
        return createByLogin;
    }

    public void setCreateByLogin(String userLogin) {
        this.createByLogin = userLogin;
    }

    public Long getUpdateById() {
        return updateById;
    }

    public void setUpdateById(Long userId) {
        this.updateById = userId;
    }

    public String getUpdateByLogin() {
        return updateByLogin;
    }

    public void setUpdateByLogin(String userLogin) {
        this.updateByLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingCartDTO shoppingCartDTO = (ShoppingCartDTO) o;
        if (shoppingCartDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingCartDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
            "id=" + getId() +
            ", aliwangwang='" + getAliwangwang() + "'" +
            ", depositAmount=" + getDepositAmount() +
            ", depositRatio=" + getDepositRatio() +
            ", serviceFee=" + getServiceFee() +
            ", serviceFeeDiscount=" + getServiceFeeDiscount() +
            ", itemChecking='" + isItemChecking() + "'" +
            ", itemWoodCrating='" + isItemWoodCrating() + "'" +
            ", shopId='" + getShopId() + "'" +
            ", shopLink='" + getShopLink() + "'" +
            ", shopName='" + getShopName() + "'" +
            ", shopNote='" + getShopNote() + "'" +
            ", sourceData='" + getSourceData() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", totalLink=" + getTotalLink() +
            ", totalQuantity=" + getTotalQuantity() +
            ", finalAmount=" + getFinalAmount() +
            ", website='" + getWebsite() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            ", createBy=" + getCreateById() +
            ", createBy='" + getCreateByLogin() + "'" +
            ", updateBy=" + getUpdateById() +
            ", updateBy='" + getUpdateByLogin() + "'" +
            "}";
    }

    /**
     * @return the items
     */
    public List<ShoppingCartItemDTO> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<ShoppingCartItemDTO> items) {
        this.items = items;
    }
}
