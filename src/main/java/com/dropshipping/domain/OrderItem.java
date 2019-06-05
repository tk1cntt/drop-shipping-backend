package com.dropshipping.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A OrderItem.
 */
@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "origin_link")
    private String originLink;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "price")
    private Float price;

    @Column(name = "price_ndt")
    private Float priceNDT;

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

    @Column(name = "quantity_order")
    private Integer quantityOrder;

    @Column(name = "quantity_pending")
    private Integer quantityPending;

    @Column(name = "quantity_received")
    private Integer quantityReceived;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "total_price_ndt")
    private Float totalPriceNDT;

    @Column(name = "website")
    private String website;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JsonIgnoreProperties("orderItems")
    private OrderCart orderCart;

    @ManyToOne
    @JsonIgnoreProperties("orderItems")
    private User createBy;

    @ManyToOne
    @JsonIgnoreProperties("orderItems")
    private User updateBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public OrderItem avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOriginLink() {
        return originLink;
    }

    public OrderItem originLink(String originLink) {
        this.originLink = originLink;
        return this;
    }

    public void setOriginLink(String originLink) {
        this.originLink = originLink;
    }

    public String getName() {
        return name;
    }

    public OrderItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public OrderItem note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getPrice() {
        return price;
    }

    public OrderItem price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPriceNDT() {
        return priceNDT;
    }

    public OrderItem priceNDT(Float priceNDT) {
        this.priceNDT = priceNDT;
        return this;
    }

    public void setPriceNDT(Float priceNDT) {
        this.priceNDT = priceNDT;
    }

    public String getPropertiesId() {
        return propertiesId;
    }

    public OrderItem propertiesId(String propertiesId) {
        this.propertiesId = propertiesId;
        return this;
    }

    public void setPropertiesId(String propertiesId) {
        this.propertiesId = propertiesId;
    }

    public String getPropertiesImage() {
        return propertiesImage;
    }

    public OrderItem propertiesImage(String propertiesImage) {
        this.propertiesImage = propertiesImage;
        return this;
    }

    public void setPropertiesImage(String propertiesImage) {
        this.propertiesImage = propertiesImage;
    }

    public String getPropertiesMD5() {
        return propertiesMD5;
    }

    public OrderItem propertiesMD5(String propertiesMD5) {
        this.propertiesMD5 = propertiesMD5;
        return this;
    }

    public void setPropertiesMD5(String propertiesMD5) {
        this.propertiesMD5 = propertiesMD5;
    }

    public String getPropertiesName() {
        return propertiesName;
    }

    public OrderItem propertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
        return this;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public String getPropertiesType() {
        return propertiesType;
    }

    public OrderItem propertiesType(String propertiesType) {
        this.propertiesType = propertiesType;
        return this;
    }

    public void setPropertiesType(String propertiesType) {
        this.propertiesType = propertiesType;
    }

    public Integer getQuantityOrder() {
        return quantityOrder;
    }

    public OrderItem quantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
        return this;
    }

    public void setQuantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public Integer getQuantityPending() {
        return quantityPending;
    }

    public OrderItem quantityPending(Integer quantityPending) {
        this.quantityPending = quantityPending;
        return this;
    }

    public void setQuantityPending(Integer quantityPending) {
        this.quantityPending = quantityPending;
    }

    public Integer getQuantityReceived() {
        return quantityReceived;
    }

    public OrderItem quantityReceived(Integer quantityReceived) {
        this.quantityReceived = quantityReceived;
        return this;
    }

    public void setQuantityReceived(Integer quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public OrderItem totalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getTotalPriceNDT() {
        return totalPriceNDT;
    }

    public OrderItem totalPriceNDT(Float totalPriceNDT) {
        this.totalPriceNDT = totalPriceNDT;
        return this;
    }

    public void setTotalPriceNDT(Float totalPriceNDT) {
        this.totalPriceNDT = totalPriceNDT;
    }

    public String getWebsite() {
        return website;
    }

    public OrderItem website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public OrderItem createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public OrderItem updateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public OrderCart getOrderCart() {
        return orderCart;
    }

    public OrderItem orderCart(OrderCart orderCart) {
        this.orderCart = orderCart;
        return this;
    }

    public void setOrderCart(OrderCart orderCart) {
        this.orderCart = orderCart;
    }

    public User getCreateBy() {
        return createBy;
    }

    public OrderItem createBy(User user) {
        this.createBy = user;
        return this;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public OrderItem updateBy(User user) {
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
        if (!(o instanceof OrderItem)) {
            return false;
        }
        return id != null && id.equals(((OrderItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
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
            "}";
    }
}
