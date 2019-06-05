package com.dropshipping.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DeliveryPackage.
 */
@Entity
@Table(name = "delivery_package")
public class DeliveryPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JsonIgnoreProperties("deliveryPackages")
    private Delivery delivery;

    @ManyToOne
    @JsonIgnoreProperties("deliveryPackages")
    private User createBy;

    @ManyToOne
    @JsonIgnoreProperties("deliveryPackages")
    private User updateBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public DeliveryPackage createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public DeliveryPackage updateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public DeliveryPackage delivery(Delivery delivery) {
        this.delivery = delivery;
        return this;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public User getCreateBy() {
        return createBy;
    }

    public DeliveryPackage createBy(User user) {
        this.createBy = user;
        return this;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public DeliveryPackage updateBy(User user) {
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
        if (!(o instanceof DeliveryPackage)) {
            return false;
        }
        return id != null && id.equals(((DeliveryPackage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeliveryPackage{" +
            "id=" + getId() +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            "}";
    }
}
