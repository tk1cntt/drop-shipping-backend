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
 * A Delivery.
 */
@Entity
@Table(name = "delivery")
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_method")
    private String deliveryMethod;

    @Column(name = "delivery_method_name")
    private String deliveryMethodName;

    @Column(name = "export_time")
    private LocalDate exportTime;

    @Column(name = "packed_time")
    private LocalDate packedTime;

    @Column(name = "status")
    private String status;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "status_style")
    private String statusStyle;

    @Column(name = "total_weight")
    private Float totalWeight;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "delivery")
    private Set<DeliveryPackage> packages = new HashSet<>();

    @OneToMany(mappedBy = "delivery")
    private Set<OrderPackage> orders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("deliveries")
    private Warehouse warehouse;

    @ManyToOne
    @JsonIgnoreProperties("deliveries")
    private User createBy;

    @ManyToOne
    @JsonIgnoreProperties("deliveries")
    private User updateBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public Delivery deliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
        return this;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public Delivery deliveryMethodName(String deliveryMethodName) {
        this.deliveryMethodName = deliveryMethodName;
        return this;
    }

    public void setDeliveryMethodName(String deliveryMethodName) {
        this.deliveryMethodName = deliveryMethodName;
    }

    public LocalDate getExportTime() {
        return exportTime;
    }

    public Delivery exportTime(LocalDate exportTime) {
        this.exportTime = exportTime;
        return this;
    }

    public void setExportTime(LocalDate exportTime) {
        this.exportTime = exportTime;
    }

    public LocalDate getPackedTime() {
        return packedTime;
    }

    public Delivery packedTime(LocalDate packedTime) {
        this.packedTime = packedTime;
        return this;
    }

    public void setPackedTime(LocalDate packedTime) {
        this.packedTime = packedTime;
    }

    public String getStatus() {
        return status;
    }

    public Delivery status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public Delivery statusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusStyle() {
        return statusStyle;
    }

    public Delivery statusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
        return this;
    }

    public void setStatusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
    }

    public Float getTotalWeight() {
        return totalWeight;
    }

    public Delivery totalWeight(Float totalWeight) {
        this.totalWeight = totalWeight;
        return this;
    }

    public void setTotalWeight(Float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public Delivery createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public Delivery updateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public Set<DeliveryPackage> getPackages() {
        return packages;
    }

    public Delivery packages(Set<DeliveryPackage> deliveryPackages) {
        this.packages = deliveryPackages;
        return this;
    }

    public Delivery addPackages(DeliveryPackage deliveryPackage) {
        this.packages.add(deliveryPackage);
        deliveryPackage.setDelivery(this);
        return this;
    }

    public Delivery removePackages(DeliveryPackage deliveryPackage) {
        this.packages.remove(deliveryPackage);
        deliveryPackage.setDelivery(null);
        return this;
    }

    public void setPackages(Set<DeliveryPackage> deliveryPackages) {
        this.packages = deliveryPackages;
    }

    public Set<OrderPackage> getOrders() {
        return orders;
    }

    public Delivery orders(Set<OrderPackage> orderPackages) {
        this.orders = orderPackages;
        return this;
    }

    public Delivery addOrders(OrderPackage orderPackage) {
        this.orders.add(orderPackage);
        orderPackage.setDelivery(this);
        return this;
    }

    public Delivery removeOrders(OrderPackage orderPackage) {
        this.orders.remove(orderPackage);
        orderPackage.setDelivery(null);
        return this;
    }

    public void setOrders(Set<OrderPackage> orderPackages) {
        this.orders = orderPackages;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Delivery warehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public User getCreateBy() {
        return createBy;
    }

    public Delivery createBy(User user) {
        this.createBy = user;
        return this;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public Delivery updateBy(User user) {
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
        if (!(o instanceof Delivery)) {
            return false;
        }
        return id != null && id.equals(((Delivery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Delivery{" +
            "id=" + getId() +
            ", deliveryMethod='" + getDeliveryMethod() + "'" +
            ", deliveryMethodName='" + getDeliveryMethodName() + "'" +
            ", exportTime='" + getExportTime() + "'" +
            ", packedTime='" + getPackedTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusName='" + getStatusName() + "'" +
            ", statusStyle='" + getStatusStyle() + "'" +
            ", totalWeight=" + getTotalWeight() +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            "}";
    }
}
