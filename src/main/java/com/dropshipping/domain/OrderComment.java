package com.dropshipping.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.dropshipping.domain.enumeration.CommentType;

/**
 * A OrderComment.
 */
@Entity
@Table(name = "order_comment")
public class OrderComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "sender")
    private String sender;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private CommentType type;

    @Column(name = "create_at")
    private LocalDate createAt;

    @ManyToOne
    @JsonIgnoreProperties("orderComments")
    private OrderCart orderCart;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public OrderComment message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public OrderComment sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public CommentType getType() {
        return type;
    }

    public OrderComment type(CommentType type) {
        this.type = type;
        return this;
    }

    public void setType(CommentType type) {
        this.type = type;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public OrderComment createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public OrderCart getOrderCart() {
        return orderCart;
    }

    public OrderComment orderCart(OrderCart orderCart) {
        this.orderCart = orderCart;
        return this;
    }

    public void setOrderCart(OrderCart orderCart) {
        this.orderCart = orderCart;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderComment)) {
            return false;
        }
        return id != null && id.equals(((OrderComment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderComment{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", sender='" + getSender() + "'" +
            ", type='" + getType() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            "}";
    }
}
