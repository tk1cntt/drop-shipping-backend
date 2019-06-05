package com.dropshipping.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dropshipping.domain.ShoppingCartItem} entity.
 */
public class ShoppingCartItemDTO implements Serializable {

    private Long id;

    private String itemId;

    private String itemImage;

    private String itemLink;

    private Float itemPrice;

    private Float itemPriceNDT;

    private String itemNote;

    private String propertiesId;

    private String propertiesImage;

    private String propertiesMD5;

    private String propertiesName;

    private String propertiesType;

    private Integer quantity;

    private Integer requireMin;

    private Float totalAmount;

    private Float totalAmountNDT;

    private LocalDate createAt;

    private LocalDate updateAt;


    private Long shoppingCartId;

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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemLink() {
        return itemLink;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public Float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Float getItemPriceNDT() {
        return itemPriceNDT;
    }

    public void setItemPriceNDT(Float itemPriceNDT) {
        this.itemPriceNDT = itemPriceNDT;
    }

    public String getItemNote() {
        return itemNote;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRequireMin() {
        return requireMin;
    }

    public void setRequireMin(Integer requireMin) {
        this.requireMin = requireMin;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getTotalAmountNDT() {
        return totalAmountNDT;
    }

    public void setTotalAmountNDT(Float totalAmountNDT) {
        this.totalAmountNDT = totalAmountNDT;
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

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
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

        ShoppingCartItemDTO shoppingCartItemDTO = (ShoppingCartItemDTO) o;
        if (shoppingCartItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingCartItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingCartItemDTO{" +
            "id=" + getId() +
            ", itemId='" + getItemId() + "'" +
            ", itemImage='" + getItemImage() + "'" +
            ", itemLink='" + getItemLink() + "'" +
            ", itemPrice=" + getItemPrice() +
            ", itemPriceNDT=" + getItemPriceNDT() +
            ", itemNote='" + getItemNote() + "'" +
            ", propertiesId='" + getPropertiesId() + "'" +
            ", propertiesImage='" + getPropertiesImage() + "'" +
            ", propertiesMD5='" + getPropertiesMD5() + "'" +
            ", propertiesName='" + getPropertiesName() + "'" +
            ", propertiesType='" + getPropertiesType() + "'" +
            ", quantity=" + getQuantity() +
            ", requireMin=" + getRequireMin() +
            ", totalAmount=" + getTotalAmount() +
            ", totalAmountNDT=" + getTotalAmountNDT() +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            ", shoppingCart=" + getShoppingCartId() +
            ", createBy=" + getCreateById() +
            ", createBy='" + getCreateByLogin() + "'" +
            ", updateBy=" + getUpdateById() +
            ", updateBy='" + getUpdateByLogin() + "'" +
            "}";
    }
}
