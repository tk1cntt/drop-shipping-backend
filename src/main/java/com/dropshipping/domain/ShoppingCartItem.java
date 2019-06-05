package com.dropshipping.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ShoppingCartItem.
 */
@Entity
@Table(name = "shopping_cart_item")
public class ShoppingCartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "item_image")
    private String itemImage;

    @Column(name = "item_link")
    private String itemLink;

    @Column(name = "item_price")
    private Float itemPrice;

    @Column(name = "item_price_ndt")
    private Float itemPriceNDT;

    @Column(name = "item_note")
    private String itemNote;

    @Column(name = "properties_id")
    private String propertiesId;

    @Column(name = "properties_image")
    private String propertiesImage;

    @Column(name = "properties_md_5")
    private String propertiesMD5;

    @Column(name = "properties_name")
    private String propertiesName;

    @Column(name = "properties_type")
    private String propertiesType;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "require_min")
    private Integer requireMin;

    @Column(name = "total_amount")
    private Float totalAmount;

    @Column(name = "total_amount_ndt")
    private Float totalAmountNDT;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JsonIgnoreProperties("shoppingCartItems")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JsonIgnoreProperties("shoppingCartItems")
    private User createBy;

    @ManyToOne
    @JsonIgnoreProperties("shoppingCartItems")
    private User updateBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public ShoppingCartItem itemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemImage() {
        return itemImage;
    }

    public ShoppingCartItem itemImage(String itemImage) {
        this.itemImage = itemImage;
        return this;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemLink() {
        return itemLink;
    }

    public ShoppingCartItem itemLink(String itemLink) {
        this.itemLink = itemLink;
        return this;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public Float getItemPrice() {
        return itemPrice;
    }

    public ShoppingCartItem itemPrice(Float itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public void setItemPrice(Float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Float getItemPriceNDT() {
        return itemPriceNDT;
    }

    public ShoppingCartItem itemPriceNDT(Float itemPriceNDT) {
        this.itemPriceNDT = itemPriceNDT;
        return this;
    }

    public void setItemPriceNDT(Float itemPriceNDT) {
        this.itemPriceNDT = itemPriceNDT;
    }

    public String getItemNote() {
        return itemNote;
    }

    public ShoppingCartItem itemNote(String itemNote) {
        this.itemNote = itemNote;
        return this;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
    }

    public String getPropertiesId() {
        return propertiesId;
    }

    public ShoppingCartItem propertiesId(String propertiesId) {
        this.propertiesId = propertiesId;
        return this;
    }

    public void setPropertiesId(String propertiesId) {
        this.propertiesId = propertiesId;
    }

    public String getPropertiesImage() {
        return propertiesImage;
    }

    public ShoppingCartItem propertiesImage(String propertiesImage) {
        this.propertiesImage = propertiesImage;
        return this;
    }

    public void setPropertiesImage(String propertiesImage) {
        this.propertiesImage = propertiesImage;
    }

    public String getPropertiesMD5() {
        return propertiesMD5;
    }

    public ShoppingCartItem propertiesMD5(String propertiesMD5) {
        this.propertiesMD5 = propertiesMD5;
        return this;
    }

    public void setPropertiesMD5(String propertiesMD5) {
        this.propertiesMD5 = propertiesMD5;
    }

    public String getPropertiesName() {
        return propertiesName;
    }

    public ShoppingCartItem propertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
        return this;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public String getPropertiesType() {
        return propertiesType;
    }

    public ShoppingCartItem propertiesType(String propertiesType) {
        this.propertiesType = propertiesType;
        return this;
    }

    public void setPropertiesType(String propertiesType) {
        this.propertiesType = propertiesType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ShoppingCartItem quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRequireMin() {
        return requireMin;
    }

    public ShoppingCartItem requireMin(Integer requireMin) {
        this.requireMin = requireMin;
        return this;
    }

    public void setRequireMin(Integer requireMin) {
        this.requireMin = requireMin;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public ShoppingCartItem totalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getTotalAmountNDT() {
        return totalAmountNDT;
    }

    public ShoppingCartItem totalAmountNDT(Float totalAmountNDT) {
        this.totalAmountNDT = totalAmountNDT;
        return this;
    }

    public void setTotalAmountNDT(Float totalAmountNDT) {
        this.totalAmountNDT = totalAmountNDT;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public ShoppingCartItem createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public ShoppingCartItem updateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public ShoppingCartItem shoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        return this;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public User getCreateBy() {
        return createBy;
    }

    public ShoppingCartItem createBy(User user) {
        this.createBy = user;
        return this;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public ShoppingCartItem updateBy(User user) {
        this.updateBy = user;
        return this;
    }

    public void setUpdateBy(User user) {
        this.updateBy = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingCartItem)) {
            return false;
        }
        return id != null && id.equals(((ShoppingCartItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
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
            "}";
    }
}
