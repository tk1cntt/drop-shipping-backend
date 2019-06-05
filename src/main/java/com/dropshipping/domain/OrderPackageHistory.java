package com.dropshipping.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A OrderPackageHistory.
 */
@Entity
@Table(name = "order_package_history")
public class OrderPackageHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JsonIgnoreProperties("orderPackageHistories")
    private OrderPackage orderPackage;

    @ManyToOne
    @JsonIgnoreProperties("orderPackageHistories")
    private Warehouse warehouse;

    @ManyToOne
    @JsonIgnoreProperties("orderPackageHistories")
    private User createBy;

    @ManyToOne
    @JsonIgnoreProperties("orderPackageHistories")
    private User updateBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public OrderPackageHistory status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public OrderPackageHistory statusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusStyle() {
        return statusStyle;
    }

    public OrderPackageHistory statusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
        return this;
    }

    public void setStatusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public OrderPackageHistory createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public OrderPackageHistory updateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public OrderPackage getOrderPackage() {
        return orderPackage;
    }

    public OrderPackageHistory orderPackage(OrderPackage orderPackage) {
        this.orderPackage = orderPackage;
        return this;
    }

    public void setOrderPackage(OrderPackage orderPackage) {
        this.orderPackage = orderPackage;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public OrderPackageHistory warehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public User getCreateBy() {
        return createBy;
    }

    public OrderPackageHistory createBy(User user) {
        this.createBy = user;
        return this;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public OrderPackageHistory updateBy(User user) {
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
        if (!(o instanceof OrderPackageHistory)) {
            return false;
        }
        return id != null && id.equals(((OrderPackageHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderPackageHistory{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", statusName='" + getStatusName() + "'" +
            ", statusStyle='" + getStatusStyle() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            "}";
    }
}
