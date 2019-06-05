package com.dropshipping.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.dropshipping.domain.enumeration.TransactionType;

/**
 * A DTO for the {@link com.dropshipping.domain.OrderTransaction} entity.
 */
public class OrderTransactionDTO implements Serializable {

    private Long id;

    private Float amount;

    private String note;

    private TransactionType status;

    private LocalDate orderDate;

    private LocalDate createAt;

    private LocalDate updateAt;


    private Long orderCartId;

    private Long approverId;

    private String approverLogin;

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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TransactionType getStatus() {
        return status;
    }

    public void setStatus(TransactionType status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long userId) {
        this.approverId = userId;
    }

    public String getApproverLogin() {
        return approverLogin;
    }

    public void setApproverLogin(String userLogin) {
        this.approverLogin = userLogin;
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

        OrderTransactionDTO orderTransactionDTO = (OrderTransactionDTO) o;
        if (orderTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderTransactionDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", note='" + getNote() + "'" +
            ", status='" + getStatus() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            ", orderCart=" + getOrderCartId() +
            ", approver=" + getApproverId() +
            ", approver='" + getApproverLogin() + "'" +
            ", createBy=" + getCreateById() +
            ", createBy='" + getCreateByLogin() + "'" +
            ", updateBy=" + getUpdateById() +
            ", updateBy='" + getUpdateByLogin() + "'" +
            "}";
    }
}
