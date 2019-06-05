package com.dropshipping.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dropshipping.domain.OrderPackage} entity.
 */
public class OrderPackageDTO implements Serializable {

    private Long id;

    private String ladingCode;

    private Float heightPackage;

    private Float widthPackage;

    private Float lengthPackage;

    private Float netWeight;

    private String status;

    private String statusName;

    private String statusStyle;

    private LocalDate createAt;

    private LocalDate updateAt;


    private Long orderCartId;

    private Long warehouseId;

    private Long createById;

    private String createByLogin;

    private Long updateById;

    private String updateByLogin;

    private Long deliveryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public Float getHeightPackage() {
        return heightPackage;
    }

    public void setHeightPackage(Float heightPackage) {
        this.heightPackage = heightPackage;
    }

    public Float getWidthPackage() {
        return widthPackage;
    }

    public void setWidthPackage(Float widthPackage) {
        this.widthPackage = widthPackage;
    }

    public Float getLengthPackage() {
        return lengthPackage;
    }

    public void setLengthPackage(Float lengthPackage) {
        this.lengthPackage = lengthPackage;
    }

    public Float getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Float netWeight) {
        this.netWeight = netWeight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusStyle() {
        return statusStyle;
    }

    public void setStatusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderPackageDTO orderPackageDTO = (OrderPackageDTO) o;
        if (orderPackageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderPackageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderPackageDTO{" +
            "id=" + getId() +
            ", ladingCode='" + getLadingCode() + "'" +
            ", heightPackage=" + getHeightPackage() +
            ", widthPackage=" + getWidthPackage() +
            ", lengthPackage=" + getLengthPackage() +
            ", netWeight=" + getNetWeight() +
            ", status='" + getStatus() + "'" +
            ", statusName='" + getStatusName() + "'" +
            ", statusStyle='" + getStatusStyle() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            ", orderCart=" + getOrderCartId() +
            ", warehouse=" + getWarehouseId() +
            ", createBy=" + getCreateById() +
            ", createBy='" + getCreateByLogin() + "'" +
            ", updateBy=" + getUpdateById() +
            ", updateBy='" + getUpdateByLogin() + "'" +
            ", delivery=" + getDeliveryId() +
            "}";
    }
}
