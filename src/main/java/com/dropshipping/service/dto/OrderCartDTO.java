package com.dropshipping.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.dropshipping.domain.enumeration.OrderStatus;

/**
 * A DTO for the {@link com.dropshipping.domain.OrderCart} entity.
 */
public class OrderCartDTO implements Serializable {

    private Long id;

    private String code;

    private String avatar;

    private Float amountDiscount;

    private Float amountPaid;

    private Float depositAmount;

    private Float depositRatio;

    private LocalDate depositTime;

    private Float domesticShippingChinaFeeNDT;

    private Float domesticShippingChinaFee;

    private Float domesticShippingVietnamFee;

    private Integer quantityOrder;

    private Integer quantityPending;

    private Integer quantityReceived;

    private Integer rate;

    private String receiverName;

    private String receiverAddress;

    private String receiverMobile;

    private String receiverNote;

    private Float refundAmountByAlipay;

    private Float refundAmountByComplaint;

    private Float refundAmountByOrder;

    private Float refundAmountPending;

    private Float shippingChinaVietnamFee;

    private Float shippingChinaVietnamFeeDiscount;

    private String shopAliwang;

    private String shopId;

    private String shopLink;

    private String shopName;

    private String website;

    private String websiteCode;

    private String websiteLadingCode;

    private OrderStatus status;

    private String statusName;

    private String statusStyle;

    private Integer tallyFee;

    private Float totalAmount;

    private Float totalAmountNDT;

    private Float totalPaidByCustomer;

    private Float serviceFee;

    private Float serviceFeeDiscount;

    private Float totalServiceFee;

    private Float finalAmount;

    private LocalDate createAt;

    private LocalDate updateAt;


    private Long buyerId;

    private String buyerLogin;

    private Long chinaStockerId;

    private String chinaStockerLogin;

    private Long vietnamStockerId;

    private String vietnamStockerLogin;

    private Long exporterId;

    private String exporterLogin;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Float getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(Float amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public Float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Float getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Float depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Float getDepositRatio() {
        return depositRatio;
    }

    public void setDepositRatio(Float depositRatio) {
        this.depositRatio = depositRatio;
    }

    public LocalDate getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(LocalDate depositTime) {
        this.depositTime = depositTime;
    }

    public Float getDomesticShippingChinaFeeNDT() {
        return domesticShippingChinaFeeNDT;
    }

    public void setDomesticShippingChinaFeeNDT(Float domesticShippingChinaFeeNDT) {
        this.domesticShippingChinaFeeNDT = domesticShippingChinaFeeNDT;
    }

    public Float getDomesticShippingChinaFee() {
        return domesticShippingChinaFee;
    }

    public void setDomesticShippingChinaFee(Float domesticShippingChinaFee) {
        this.domesticShippingChinaFee = domesticShippingChinaFee;
    }

    public Float getDomesticShippingVietnamFee() {
        return domesticShippingVietnamFee;
    }

    public void setDomesticShippingVietnamFee(Float domesticShippingVietnamFee) {
        this.domesticShippingVietnamFee = domesticShippingVietnamFee;
    }

    public Integer getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public Integer getQuantityPending() {
        return quantityPending;
    }

    public void setQuantityPending(Integer quantityPending) {
        this.quantityPending = quantityPending;
    }

    public Integer getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(Integer quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverNote() {
        return receiverNote;
    }

    public void setReceiverNote(String receiverNote) {
        this.receiverNote = receiverNote;
    }

    public Float getRefundAmountByAlipay() {
        return refundAmountByAlipay;
    }

    public void setRefundAmountByAlipay(Float refundAmountByAlipay) {
        this.refundAmountByAlipay = refundAmountByAlipay;
    }

    public Float getRefundAmountByComplaint() {
        return refundAmountByComplaint;
    }

    public void setRefundAmountByComplaint(Float refundAmountByComplaint) {
        this.refundAmountByComplaint = refundAmountByComplaint;
    }

    public Float getRefundAmountByOrder() {
        return refundAmountByOrder;
    }

    public void setRefundAmountByOrder(Float refundAmountByOrder) {
        this.refundAmountByOrder = refundAmountByOrder;
    }

    public Float getRefundAmountPending() {
        return refundAmountPending;
    }

    public void setRefundAmountPending(Float refundAmountPending) {
        this.refundAmountPending = refundAmountPending;
    }

    public Float getShippingChinaVietnamFee() {
        return shippingChinaVietnamFee;
    }

    public void setShippingChinaVietnamFee(Float shippingChinaVietnamFee) {
        this.shippingChinaVietnamFee = shippingChinaVietnamFee;
    }

    public Float getShippingChinaVietnamFeeDiscount() {
        return shippingChinaVietnamFeeDiscount;
    }

    public void setShippingChinaVietnamFeeDiscount(Float shippingChinaVietnamFeeDiscount) {
        this.shippingChinaVietnamFeeDiscount = shippingChinaVietnamFeeDiscount;
    }

    public String getShopAliwang() {
        return shopAliwang;
    }

    public void setShopAliwang(String shopAliwang) {
        this.shopAliwang = shopAliwang;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopLink() {
        return shopLink;
    }

    public void setShopLink(String shopLink) {
        this.shopLink = shopLink;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsiteCode() {
        return websiteCode;
    }

    public void setWebsiteCode(String websiteCode) {
        this.websiteCode = websiteCode;
    }

    public String getWebsiteLadingCode() {
        return websiteLadingCode;
    }

    public void setWebsiteLadingCode(String websiteLadingCode) {
        this.websiteLadingCode = websiteLadingCode;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusStyle() {
        return statusStyle;
    }

    public void setStatusStyle(String statusStyle) {
        this.statusStyle = statusStyle;
    }

    public Integer getTallyFee() {
        return tallyFee;
    }

    public void setTallyFee(Integer tallyFee) {
        this.tallyFee = tallyFee;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getTotalAmountNDT() {
        return totalAmountNDT;
    }

    public void setTotalAmountNDT(Float totalAmountNDT) {
        this.totalAmountNDT = totalAmountNDT;
    }

    public Float getTotalPaidByCustomer() {
        return totalPaidByCustomer;
    }

    public void setTotalPaidByCustomer(Float totalPaidByCustomer) {
        this.totalPaidByCustomer = totalPaidByCustomer;
    }

    public Float getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Float serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Float getServiceFeeDiscount() {
        return serviceFeeDiscount;
    }

    public void setServiceFeeDiscount(Float serviceFeeDiscount) {
        this.serviceFeeDiscount = serviceFeeDiscount;
    }

    public Float getTotalServiceFee() {
        return totalServiceFee;
    }

    public void setTotalServiceFee(Float totalServiceFee) {
        this.totalServiceFee = totalServiceFee;
    }

    public Float getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Float finalAmount) {
        this.finalAmount = finalAmount;
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

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long userId) {
        this.buyerId = userId;
    }

    public String getBuyerLogin() {
        return buyerLogin;
    }

    public void setBuyerLogin(String userLogin) {
        this.buyerLogin = userLogin;
    }

    public Long getChinaStockerId() {
        return chinaStockerId;
    }

    public void setChinaStockerId(Long userId) {
        this.chinaStockerId = userId;
    }

    public String getChinaStockerLogin() {
        return chinaStockerLogin;
    }

    public void setChinaStockerLogin(String userLogin) {
        this.chinaStockerLogin = userLogin;
    }

    public Long getVietnamStockerId() {
        return vietnamStockerId;
    }

    public void setVietnamStockerId(Long userId) {
        this.vietnamStockerId = userId;
    }

    public String getVietnamStockerLogin() {
        return vietnamStockerLogin;
    }

    public void setVietnamStockerLogin(String userLogin) {
        this.vietnamStockerLogin = userLogin;
    }

    public Long getExporterId() {
        return exporterId;
    }

    public void setExporterId(Long userId) {
        this.exporterId = userId;
    }

    public String getExporterLogin() {
        return exporterLogin;
    }

    public void setExporterLogin(String userLogin) {
        this.exporterLogin = userLogin;
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

        OrderCartDTO orderCartDTO = (OrderCartDTO) o;
        if (orderCartDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderCartDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderCartDTO{" +
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
            ", buyer=" + getBuyerId() +
            ", buyer='" + getBuyerLogin() + "'" +
            ", chinaStocker=" + getChinaStockerId() +
            ", chinaStocker='" + getChinaStockerLogin() + "'" +
            ", vietnamStocker=" + getVietnamStockerId() +
            ", vietnamStocker='" + getVietnamStockerLogin() + "'" +
            ", exporter=" + getExporterId() +
            ", exporter='" + getExporterLogin() + "'" +
            ", createBy=" + getCreateById() +
            ", createBy='" + getCreateByLogin() + "'" +
            ", updateBy=" + getUpdateById() +
            ", updateBy='" + getUpdateByLogin() + "'" +
            "}";
    }
}
