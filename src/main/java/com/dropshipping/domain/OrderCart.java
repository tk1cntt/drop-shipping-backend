package com.dropshipping.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.dropshipping.domain.enumeration.OrderStatus;

/**
 * A OrderCart.
 */
@Entity
@Table(name = "order_cart")
public class OrderCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "amount_discount")
    private Float amountDiscount;

    @Column(name = "amount_paid")
    private Float amountPaid;

    @Column(name = "deposit_amount")
    private Float depositAmount;

    @Column(name = "deposit_ratio")
    private Float depositRatio;

    @Column(name = "deposit_time")
    private LocalDate depositTime;

    @Column(name = "domestic_shipping_china_fee_ndt")
    private Float domesticShippingChinaFeeNDT;

    @Column(name = "domestic_shipping_china_fee")
    private Float domesticShippingChinaFee;

    @Column(name = "domestic_shipping_vietnam_fee")
    private Float domesticShippingVietnamFee;

    @Column(name = "quantity_order")
    private Integer quantityOrder;

    @Column(name = "quantity_pending")
    private Integer quantityPending;

    @Column(name = "quantity_received")
    private Integer quantityReceived;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_mobile")
    private String receiverMobile;

    @Column(name = "receiver_note")
    private String receiverNote;

    @Column(name = "refund_amount_by_alipay")
    private Float refundAmountByAlipay;

    @Column(name = "refund_amount_by_complaint")
    private Float refundAmountByComplaint;

    @Column(name = "refund_amount_by_order")
    private Float refundAmountByOrder;

    @Column(name = "refund_amount_pending")
    private Float refundAmountPending;

    @Column(name = "shipping_china_vietnam_fee")
    private Float shippingChinaVietnamFee;

    @Column(name = "shipping_china_vietnam_fee_discount")
    private Float shippingChinaVietnamFeeDiscount;

    @Column(name = "shop_aliwang")
    private String shopAliwang;

    @Column(name = "shop_id")
    private String shopId;

    @Column(name = "shop_link")
    private String shopLink;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "website")
    private String website;

    @Column(name = "website_code")
    private String websiteCode;

    @Column(name = "website_lading_code")
    private String websiteLadingCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "status_style")
    private String statusStyle;

    @Column(name = "tally_fee")
    private Integer tallyFee;

    @Column(name = "total_amount")
    private Float totalAmount;

    @Column(name = "total_amount_ndt")
    private Float totalAmountNDT;

    @Column(name = "total_paid_by_customer")
    private Float totalPaidByCustomer;

    @Column(name = "service_fee")
    private Float serviceFee;

    @Column(name = "service_fee_discount")
    private Float serviceFeeDiscount;

    @Column(name = "total_service_fee")
    private Float totalServiceFee;

    @Column(name = "final_amount")
    private Float finalAmount;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "orderCart")
    private Set<OrderItem> items = new HashSet<>();

    @OneToMany(mappedBy = "orderCart")
    private Set<OrderComment> comments = new HashSet<>();

    @OneToMany(mappedBy = "orderCart")
    private Set<OrderHistory> histories = new HashSet<>();

    @OneToMany(mappedBy = "orderCart")
    private Set<OrderPackage> packages = new HashSet<>();

    @OneToMany(mappedBy = "orderCart")
    private Set<OrderTransaction> transactions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("orderCarts")
    private User buyer;

    @ManyToOne
    @JsonIgnoreProperties("orderCarts")
    private User chinaStocker;

    @ManyToOne
    @JsonIgnoreProperties("orderCarts")
    private User vietnamStocker;

    @ManyToOne
    @JsonIgnoreProperties("orderCarts")
    private User exporter;

    @ManyToOne
    @JsonIgnoreProperties("orderCarts")
    private User createBy;

    @ManyToOne
    @JsonIgnoreProperties("orderCarts")
    private User updateBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public OrderCart code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAvatar() {
        return avatar;
    }

    public OrderCart avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Float getAmountDiscount() {
        return amountDiscount;
    }

    public OrderCart amountDiscount(Float amountDiscount) {
        this.amountDiscount = amountDiscount;
        return this;
    }

    public void setAmountDiscount(Float amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public Float getAmountPaid() {
        return amountPaid;
    }

    public OrderCart amountPaid(Float amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public void setAmountPaid(Float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Float getDepositAmount() {
        return depositAmount;
    }

    public OrderCart depositAmount(Float depositAmount) {
        this.depositAmount = depositAmount;
        return this;
    }

    public void setDepositAmount(Float depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Float getDepositRatio() {
        return depositRatio;
    }

    public OrderCart depositRatio(Float depositRatio) {
        this.depositRatio = depositRatio;
        return this;
    }

    public void setDepositRatio(Float depositRatio) {
        this.depositRatio = depositRatio;
    }

    public LocalDate getDepositTime() {
        return depositTime;
    }

    public OrderCart depositTime(LocalDate depositTime) {
        this.depositTime = depositTime;
        return this;
    }

    public void setDepositTime(LocalDate depositTime) {
        this.depositTime = depositTime;
    }

    public Float getDomesticShippingChinaFeeNDT() {
        return domesticShippingChinaFeeNDT;
    }

    public OrderCart domesticShippingChinaFeeNDT(Float domesticShippingChinaFeeNDT) {
        this.domesticShippingChinaFeeNDT = domesticShippingChinaFeeNDT;
        return this;
    }

    public void setDomesticShippingChinaFeeNDT(Float domesticShippingChinaFeeNDT) {
        this.domesticShippingChinaFeeNDT = domesticShippingChinaFeeNDT;
    }

    public Float getDomesticShippingChinaFee() {
        return domesticShippingChinaFee;
    }

    public OrderCart domesticShippingChinaFee(Float domesticShippingChinaFee) {
        this.domesticShippingChinaFee = domesticShippingChinaFee;
        return this;
    }

    public void setDomesticShippingChinaFee(Float domesticShippingChinaFee) {
        this.domesticShippingChinaFee = domesticShippingChinaFee;
    }

    public Float getDomesticShippingVietnamFee() {
        return domesticShippingVietnamFee;
    }

    public OrderCart domesticShippingVietnamFee(Float domesticShippingVietnamFee) {
        this.domesticShippingVietnamFee = domesticShippingVietnamFee;
        return this;
    }

    public void setDomesticShippingVietnamFee(Float domesticShippingVietnamFee) {
        this.domesticShippingVietnamFee = domesticShippingVietnamFee;
    }

    public Integer getQuantityOrder() {
        return quantityOrder;
    }

    public OrderCart quantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
        return this;
    }

    public void setQuantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public Integer getQuantityPending() {
        return quantityPending;
    }

    public OrderCart quantityPending(Integer quantityPending) {
        this.quantityPending = quantityPending;
        return this;
    }

    public void setQuantityPending(Integer quantityPending) {
        this.quantityPending = quantityPending;
    }

    public Integer getQuantityReceived() {
        return quantityReceived;
    }

    public OrderCart quantityReceived(Integer quantityReceived) {
        this.quantityReceived = quantityReceived;
        return this;
    }

    public void setQuantityReceived(Integer quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public Integer getRate() {
        return rate;
    }

    public OrderCart rate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public OrderCart receiverName(String receiverName) {
        this.receiverName = receiverName;
        return this;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public OrderCart receiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
        return this;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public OrderCart receiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
        return this;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverNote() {
        return receiverNote;
    }

    public OrderCart receiverNote(String receiverNote) {
        this.receiverNote = receiverNote;
        return this;
    }

    public void setReceiverNote(String receiverNote) {
        this.receiverNote = receiverNote;
    }

    public Float getRefundAmountByAlipay() {
        return refundAmountByAlipay;
    }

    public OrderCart refundAmountByAlipay(Float refundAmountByAlipay) {
        this.refundAmountByAlipay = refundAmountByAlipay;
        return this;
    }

    public void setRefundAmountByAlipay(Float refundAmountByAlipay) {
        this.refundAmountByAlipay = refundAmountByAlipay;
    }

    public Float getRefundAmountByComplaint() {
        return refundAmountByComplaint;
    }

    public OrderCart refundAmountByComplaint(Float refundAmountByComplaint) {
        this.refundAmountByComplaint = refundAmountByComplaint;
        return this;
    }

    public void setRefundAmountByComplaint(Float refundAmountByComplaint) {
        this.refundAmountByComplaint = refundAmountByComplaint;
    }

    public Float getRefundAmountByOrder() {
        return refundAmountByOrder;
    }

    public OrderCart refundAmountByOrder(Float refundAmountByOrder) {
        this.refundAmountByOrder = refundAmountByOrder;
        return this;
    }

    public void setRefundAmountByOrder(Float refundAmountByOrder) {
        this.refundAmountByOrder = refundAmountByOrder;
    }

    public Float getRefundAmountPending() {
        return refundAmountPending;
    }

    public OrderCart refundAmountPending(Float refundAmountPending) {
        this.refundAmountPending = refundAmountPending;
        return this;
    }

    public void setRefundAmountPending(Float refundAmountPending) {
        this.refundAmountPending = refundAmountPending;
    }

    public Float getShippingChinaVietnamFee() {
        return shippingChinaVietnamFee;
    }

    public OrderCart shippingChinaVietnamFee(Float shippingChinaVietnamFee) {
        this.shippingChinaVietnamFee = shippingChinaVietnamFee;
        return this;
    }

    public void setShippingChinaVietnamFee(Float shippingChinaVietnamFee) {
        this.shippingChinaVietnamFee = shippingChinaVietnamFee;
    }

    public Float getShippingChinaVietnamFeeDiscount() {
        return shippingChinaVietnamFeeDiscount;
    }

    public OrderCart shippingChinaVietnamFeeDiscount(Float shippingChinaVietnamFeeDiscount) {
        this.shippingChinaVietnamFeeDiscount = shippingChinaVietnamFeeDiscount;
        return this;
    }

    public void setShippingChinaVietnamFeeDiscount(Float shippingChinaVietnamFeeDiscount) {
        this.shippingChinaVietnamFeeDiscount = shippingChinaVietnamFeeDiscount;
    }

    public String getShopAliwang() {
        return shopAliwang;
    }

    public OrderCart shopAliwang(String shopAliwang) {
        this.shopAliwang = shopAliwang;
        return this;
    }

    public void setShopAliwang(String shopAliwang) {
        this.shopAliwang = shopAliwang;
    }

    public String getShopId() {
        return shopId;
    }

    public OrderCart shopId(String shopId) {
        this.shopId = shopId;
        return this;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopLink() {
        return shopLink;
    }

    public OrderCart shopLink(String shopLink) {
        this.shopLink = shopLink;
        return this;
    }

    public void setShopLink(String shopLink) {
        this.shopLink = shopLink;
    }

    public String getShopName() {
        return shopName;
    }

    public OrderCart shopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getWebsite() {
        return website;
    }

    public OrderCart website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsiteCode() {
        return websiteCode;
    }

    public OrderCart websiteCode(String websiteCode) {
        this.websiteCode = websiteCode;
        return this;
    }

    public void setWebsiteCode(String websiteCode) {
        this.websiteCode = websiteCode;
    }

    public String getWebsiteLadingCode() {
        return websiteLadingCode;
    }

    public OrderCart websiteLadingCode(String websiteLadingCode) {
        this.websiteLadingCode = websiteLadingCode;
        return this;
    }

    public void setWebsiteLadingCode(String websiteLadingCode) {
        this.websiteLadingCode = websiteLadingCode;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderCart status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public OrderCart statusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusStyle() {
        return statusStyle;
    }

    public OrderCart statusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
        return this;
    }

    public void setStatusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
    }

    public Integer getTallyFee() {
        return tallyFee;
    }

    public OrderCart tallyFee(Integer tallyFee) {
        this.tallyFee = tallyFee;
        return this;
    }

    public void setTallyFee(Integer tallyFee) {
        this.tallyFee = tallyFee;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public OrderCart totalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getTotalAmountNDT() {
        return totalAmountNDT;
    }

    public OrderCart totalAmountNDT(Float totalAmountNDT) {
        this.totalAmountNDT = totalAmountNDT;
        return this;
    }

    public void setTotalAmountNDT(Float totalAmountNDT) {
        this.totalAmountNDT = totalAmountNDT;
    }

    public Float getTotalPaidByCustomer() {
        return totalPaidByCustomer;
    }

    public OrderCart totalPaidByCustomer(Float totalPaidByCustomer) {
        this.totalPaidByCustomer = totalPaidByCustomer;
        return this;
    }

    public void setTotalPaidByCustomer(Float totalPaidByCustomer) {
        this.totalPaidByCustomer = totalPaidByCustomer;
    }

    public Float getServiceFee() {
        return serviceFee;
    }

    public OrderCart serviceFee(Float serviceFee) {
        this.serviceFee = serviceFee;
        return this;
    }

    public void setServiceFee(Float serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Float getServiceFeeDiscount() {
        return serviceFeeDiscount;
    }

    public OrderCart serviceFeeDiscount(Float serviceFeeDiscount) {
        this.serviceFeeDiscount = serviceFeeDiscount;
        return this;
    }

    public void setServiceFeeDiscount(Float serviceFeeDiscount) {
        this.serviceFeeDiscount = serviceFeeDiscount;
    }

    public Float getTotalServiceFee() {
        return totalServiceFee;
    }

    public OrderCart totalServiceFee(Float totalServiceFee) {
        this.totalServiceFee = totalServiceFee;
        return this;
    }

    public void setTotalServiceFee(Float totalServiceFee) {
        this.totalServiceFee = totalServiceFee;
    }

    public Float getFinalAmount() {
        return finalAmount;
    }

    public OrderCart finalAmount(Float finalAmount) {
        this.finalAmount = finalAmount;
        return this;
    }

    public void setFinalAmount(Float finalAmount) {
        this.finalAmount = finalAmount;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public OrderCart createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public OrderCart updateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public OrderCart items(Set<OrderItem> orderItems) {
        this.items = orderItems;
        return this;
    }

    public OrderCart addItems(OrderItem orderItem) {
        this.items.add(orderItem);
        orderItem.setOrderCart(this);
        return this;
    }

    public OrderCart removeItems(OrderItem orderItem) {
        this.items.remove(orderItem);
        orderItem.setOrderCart(null);
        return this;
    }

    public void setItems(Set<OrderItem> orderItems) {
        this.items = orderItems;
    }

    public Set<OrderComment> getComments() {
        return comments;
    }

    public OrderCart comments(Set<OrderComment> orderComments) {
        this.comments = orderComments;
        return this;
    }

    public OrderCart addComments(OrderComment orderComment) {
        this.comments.add(orderComment);
        orderComment.setOrderCart(this);
        return this;
    }

    public OrderCart removeComments(OrderComment orderComment) {
        this.comments.remove(orderComment);
        orderComment.setOrderCart(null);
        return this;
    }

    public void setComments(Set<OrderComment> orderComments) {
        this.comments = orderComments;
    }

    public Set<OrderHistory> getHistories() {
        return histories;
    }

    public OrderCart histories(Set<OrderHistory> orderHistories) {
        this.histories = orderHistories;
        return this;
    }

    public OrderCart addHistories(OrderHistory orderHistory) {
        this.histories.add(orderHistory);
        orderHistory.setOrderCart(this);
        return this;
    }

    public OrderCart removeHistories(OrderHistory orderHistory) {
        this.histories.remove(orderHistory);
        orderHistory.setOrderCart(null);
        return this;
    }

    public void setHistories(Set<OrderHistory> orderHistories) {
        this.histories = orderHistories;
    }

    public Set<OrderPackage> getPackages() {
        return packages;
    }

    public OrderCart packages(Set<OrderPackage> orderPackages) {
        this.packages = orderPackages;
        return this;
    }

    public OrderCart addPackages(OrderPackage orderPackage) {
        this.packages.add(orderPackage);
        orderPackage.setOrderCart(this);
        return this;
    }

    public OrderCart removePackages(OrderPackage orderPackage) {
        this.packages.remove(orderPackage);
        orderPackage.setOrderCart(null);
        return this;
    }

    public void setPackages(Set<OrderPackage> orderPackages) {
        this.packages = orderPackages;
    }

    public Set<OrderTransaction> getTransactions() {
        return transactions;
    }

    public OrderCart transactions(Set<OrderTransaction> orderTransactions) {
        this.transactions = orderTransactions;
        return this;
    }

    public OrderCart addTransactions(OrderTransaction orderTransaction) {
        this.transactions.add(orderTransaction);
        orderTransaction.setOrderCart(this);
        return this;
    }

    public OrderCart removeTransactions(OrderTransaction orderTransaction) {
        this.transactions.remove(orderTransaction);
        orderTransaction.setOrderCart(null);
        return this;
    }

    public void setTransactions(Set<OrderTransaction> orderTransactions) {
        this.transactions = orderTransactions;
    }

    public User getBuyer() {
        return buyer;
    }

    public OrderCart buyer(User user) {
        this.buyer = user;
        return this;
    }

    public void setBuyer(User user) {
        this.buyer = user;
    }

    public User getChinaStocker() {
        return chinaStocker;
    }

    public OrderCart chinaStocker(User user) {
        this.chinaStocker = user;
        return this;
    }

    public void setChinaStocker(User user) {
        this.chinaStocker = user;
    }

    public User getVietnamStocker() {
        return vietnamStocker;
    }

    public OrderCart vietnamStocker(User user) {
        this.vietnamStocker = user;
        return this;
    }

    public void setVietnamStocker(User user) {
        this.vietnamStocker = user;
    }

    public User getExporter() {
        return exporter;
    }

    public OrderCart exporter(User user) {
        this.exporter = user;
        return this;
    }

    public void setExporter(User user) {
        this.exporter = user;
    }

    public User getCreateBy() {
        return createBy;
    }

    public OrderCart createBy(User user) {
        this.createBy = user;
        return this;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public OrderCart updateBy(User user) {
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
        if (!(o instanceof OrderCart)) {
            return false;
        }
        return id != null && id.equals(((OrderCart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderCart{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", amountDiscount=" + getAmountDiscount() +
            ", amountPaid=" + getAmountPaid() +
            ", depositAmount=" + getDepositAmount() +
            ", depositRatio=" + getDepositRatio() +
            ", depositTime='" + getDepositTime() + "'" +
            ", domesticShippingChinaFeeNDT=" + getDomesticShippingChinaFeeNDT() +
            ", domesticShippingChinaFee=" + getDomesticShippingChinaFee() +
            ", domesticShippingVietnamFee=" + getDomesticShippingVietnamFee() +
            ", quantityOrder=" + getQuantityOrder() +
            ", quantityPending=" + getQuantityPending() +
            ", quantityReceived=" + getQuantityReceived() +
            ", rate=" + getRate() +
            ", receiverName='" + getReceiverName() + "'" +
            ", receiverAddress='" + getReceiverAddress() + "'" +
            ", receiverMobile='" + getReceiverMobile() + "'" +
            ", receiverNote='" + getReceiverNote() + "'" +
            ", refundAmountByAlipay=" + getRefundAmountByAlipay() +
            ", refundAmountByComplaint=" + getRefundAmountByComplaint() +
            ", refundAmountByOrder=" + getRefundAmountByOrder() +
            ", refundAmountPending=" + getRefundAmountPending() +
            ", shippingChinaVietnamFee=" + getShippingChinaVietnamFee() +
            ", shippingChinaVietnamFeeDiscount=" + getShippingChinaVietnamFeeDiscount() +
            ", shopAliwang='" + getShopAliwang() + "'" +
            ", shopId='" + getShopId() + "'" +
            ", shopLink='" + getShopLink() + "'" +
            ", shopName='" + getShopName() + "'" +
            ", website='" + getWebsite() + "'" +
            ", websiteCode='" + getWebsiteCode() + "'" +
            ", websiteLadingCode='" + getWebsiteLadingCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusName='" + getStatusName() + "'" +
            ", statusStyle='" + getStatusStyle() + "'" +
            ", tallyFee=" + getTallyFee() +
            ", totalAmount=" + getTotalAmount() +
            ", totalAmountNDT=" + getTotalAmountNDT() +
            ", totalPaidByCustomer=" + getTotalPaidByCustomer() +
            ", serviceFee=" + getServiceFee() +
            ", serviceFeeDiscount=" + getServiceFeeDiscount() +
            ", totalServiceFee=" + getTotalServiceFee() +
            ", finalAmount=" + getFinalAmount() +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            "}";
    }
}
