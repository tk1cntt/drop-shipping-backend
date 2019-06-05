package com.dropshipping.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.dropshipping.domain.enumeration.TransactionType;

/**
 * A OrderTransaction.
 */
@Entity
@Table(name = "order_transaction")
public class OrderTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "note")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionType status;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JsonIgnoreProperties("orderTransactions")
    private OrderCart orderCart;

    @ManyToOne
    @JsonIgnoreProperties("orderTransactions")
    private User approver;

    @ManyToOne
    @JsonIgnoreProperties("orderTransactions")
    private User createBy;

    @ManyToOne
    @JsonIgnoreProperties("orderTransactions")
    private User updateBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public OrderTransaction amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public OrderTransaction note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TransactionType getStatus() {
        return status;
    }

    public OrderTransaction status(TransactionType status) {
        this.status = status;
        return this;
    }

    public void setStatus(TransactionType status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderTransaction orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public OrderTransaction createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public OrderTransaction updateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public OrderCart getOrderCart() {
        return orderCart;
    }

    public OrderTransaction orderCart(OrderCart orderCart) {
        this.orderCart = orderCart;
        return this;
    }

    public void setOrderCart(OrderCart orderCart) {
        this.orderCart = orderCart;
    }

    public User getApprover() {
        return approver;
    }

    public OrderTransaction approver(User user) {
        this.approver = user;
        return this;
    }

    public void setApprover(User user) {
        this.approver = user;
    }

    public User getCreateBy() {
        return createBy;
    }

    public OrderTransaction createBy(User user) {
        this.createBy = user;
        return this;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public OrderTransaction updateBy(User user) {
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
        if (!(o instanceof OrderTransaction)) {
            return false;
        }
        return id != null && id.equals(((OrderTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderTransaction{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", note='" + getNote() + "'" +
            ", status='" + getStatus() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            "}";
    }
}
