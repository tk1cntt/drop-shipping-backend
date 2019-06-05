package com.dropshipping.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dropshipping.domain.OrderItem} entity.
 */
public class OrderItemDTO implements Serializable {

    private Long id;

    private String avatar;

    private String originLink;

    private String name;

    private String note;

    private Float price;

    private Float priceNDT;

    private String propertiesId;

    private String propertiesImage;

    private String propertiesMD5;

    private String propertiesName;

    private String propertiesType;

    private Integer quantityOrder;

    private Integer quantityPending;

    private Integer quantityReceived;

    private Float totalPrice;

    private Float totalPriceNDT;

    private String website;

    private LocalDate createAt;

    private LocalDate updateAt;


    private Long orderCartId;

    private Long createById;

    private String createByLogin;

    private Long updateById;

    private String updateByLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOriginLink() {
        return originLink;
    }

    public void setOriginLink(String originLink) {
        this.originLink = originLink;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPriceNDT() {
        return priceNDT;
    }

    public void setPriceNDT(Float priceNDT) {
        this.priceNDT = priceNDT;
    }

    public String getPropertiesId() {
        return propertiesId;
    }

    public void setPropertiesId(String propertiesId) {
        this.propertiesId = propertiesId;
    }

    public String getPropertiesImage() {
        return propertiesImage;
    }

    public void setPropertiesImage(String propertiesImage) {
        this.propertiesImage = propertiesImage;
    }

    public String getPropertiesMD5() {
        return propertiesMD5;
    }

    public void setPropertiesMD5(String propertiesMD5) {
        this.propertiesMD5 = propertiesMD5;
    }

    public String getPropertiesName() {
        return propertiesName;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public String getPropertiesType() {
        return propertiesType;
    }

    public void setPropertiesType(String propertiesType) {
        this.propertiesType = propertiesType;
    }

    public Integer getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public Integer getQuantityPending() {
        return quantityPending;
    }

    public void setQuantityPending(Integer quantityPending) {
        this.quantityPending = quantityPending;
    }

    public Integer getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(Integer quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getTotalPriceNDT() {
        return totalPriceNDT;
    }

    public void setTotalPriceNDT(Float totalPriceNDT) {
        this.totalPriceNDT = totalPriceNDT;
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

    public Long getOrderCartId() {
        return orderCartId;
    }

    public void setOrderCartId(Long orderCartId) {
        this.orderCartId = orderCartId;
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

        OrderItemDTO orderItemDTO = (OrderItemDTO) o;
        if (orderItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "id=" + getId() +
            ", avatar='" + getAvatar() + "'" +
            ", originLink='" + getOriginLink() + "'" +
            ", name='" + getName() + "'" +
            ", note='" + getNote() + "'" +
            ", price=" + getPrice() +
            ", priceNDT=" + getPriceNDT() +
            ", propertiesId='" + getPropertiesId() + "'" +
            ", propertiesImage='" + getPropertiesImage() + "'" +
            ", propertiesMD5='" + getPropertiesMD5() + "'" +
            ", propertiesName='" + getPropertiesName() + "'" +
            ", propertiesType='" + getPropertiesType() + "'" +
            ", quantityOrder=" + getQuantityOrder() +
            ", quantityPending=" + getQuantityPending() +
            ", quantityReceived=" + getQuantityReceived() +
            ", totalPrice=" + getTotalPrice() +
            ", totalPriceNDT=" + getTotalPriceNDT() +
            ", website='" + getWebsite() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            ", orderCart=" + getOrderCartId() +
            ", createBy=" + getCreateById() +
            ", createBy='" + getCreateByLogin() + "'" +
            ", updateBy=" + getUpdateById() +
            ", updateBy='" + getUpdateByLogin() + "'" +
            "}";
    }
}
