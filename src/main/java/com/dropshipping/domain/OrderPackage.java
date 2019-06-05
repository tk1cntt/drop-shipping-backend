package com.dropshipping.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OrderPackage.
 */
@Entity
@Table(name = "order_package")
public class OrderPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lading_code")
    private String ladingCode;

    @Column(name = "height_package")
    private Float heightPackage;

    @Column(name = "width_package")
    private Float widthPackage;

    @Column(name = "length_package")
    private Float lengthPackage;

    @Column(name = "net_weight")
    private Float netWeight;

    @Column(name = "status")
    private String status;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "status_style")
    private String statusStyle;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JsonIgnoreProperties("orderPackages")
    private OrderCart orderCart;

    @OneToMany(mappedBy = "orderPackage")
    private Set<OrderPackageHistory> packages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("orderPackages")
    private Warehouse warehouse;

    @ManyToOne
    @JsonIgnoreProperties("orderPackages")
    private User createBy;

    @ManyToOne
    @JsonIgnoreProperties("orderPackages")
    private User updateBy;

    @ManyToOne
    @JsonIgnoreProperties("orderPackages")
    private Delivery delivery;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public OrderPackage ladingCode(String ladingCode) {
        this.ladingCode = ladingCode;
        return this;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public Float getHeightPackage() {
        return heightPackage;
    }

    public OrderPackage heightPackage(Float heightPackage) {
        this.heightPackage = heightPackage;
        return this;
    }

    public void setHeightPackage(Float heightPackage) {
        this.heightPackage = heightPackage;
    }

    public Float getWidthPackage() {
        return widthPackage;
    }

    public OrderPackage widthPackage(Float widthPackage) {
        this.widthPackage = widthPackage;
        return this;
    }

    public void setWidthPackage(Float widthPackage) {
        this.widthPackage = widthPackage;
    }

    public Float getLengthPackage() {
        return lengthPackage;
    }

    public OrderPackage lengthPackage(Float lengthPackage) {
        this.lengthPackage = lengthPackage;
        return this;
    }

    public void setLengthPackage(Float lengthPackage) {
        this.lengthPackage = lengthPackage;
    }

    public Float getNetWeight() {
        return netWeight;
    }

    public OrderPackage netWeight(Float netWeight) {
        this.netWeight = netWeight;
        return this;
    }

    public void setNetWeight(Float netWeight) {
        this.netWeight = netWeight;
    }

    public String getStatus() {
        return status;
    }

    public OrderPackage status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public OrderPackage statusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusStyle() {
        return statusStyle;
    }

    public OrderPackage statusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
        return this;
    }

    public void setStatusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public OrderPackage createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public OrderPackage updateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public OrderCart getOrderCart() {
        return orderCart;
    }

    public OrderPackage orderCart(OrderCart orderCart) {
        this.orderCart = orderCart;
        return this;
    }

    public void setOrderCart(OrderCart orderCart) {
        this.orderCart = orderCart;
    }

    public Set<OrderPackageHistory> getPackages() {
        return packages;
    }

    public OrderPackage packages(Set<OrderPackageHistory> orderPackageHistories) {
        this.packages = orderPackageHistories;
        return this;
    }

    public OrderPackage addPackages(OrderPackageHistory orderPackageHistory) {
        this.packages.add(orderPackageHistory);
        orderPackageHistory.setOrderPackage(this);
        return this;
    }

    public OrderPackage removePackages(OrderPackageHistory orderPackageHistory) {
        this.packages.remove(orderPackageHistory);
        orderPackageHistory.setOrderPackage(null);
        return this;
    }

    public void setPackages(Set<OrderPackageHistory> orderPackageHistories) {
        this.packages = orderPackageHistories;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public OrderPackage warehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public User getCreateBy() {
        return createBy;
    }

    public OrderPackage createBy(User user) {
        this.createBy = user;
        return this;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public OrderPackage updateBy(User user) {
        this.updateBy = user;
        return this;
    }

    public void setUpdateBy(User user) {
        this.updateBy = user;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public OrderPackage delivery(Delivery delivery) {
        this.delivery = delivery;
        return this;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPackage)) {
            return false;
        }
        return id != null && id.equals(((OrderPackage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderPackage{" +
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
            "}";
    }
}
