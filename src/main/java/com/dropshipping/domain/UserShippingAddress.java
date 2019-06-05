package com.dropshipping.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A UserShippingAddress.
 */
@Entity
@Table(name = "user_shipping_address")
public class UserShippingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "note")
    private String note;

    @Column(name = "is_shipping_address")
    private Boolean isShippingAddress;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JsonIgnoreProperties("userShippingAddresses")
    private UserProfile userProfile;

    @ManyToOne
    @JsonIgnoreProperties("userShippingAddresses")
    private User createBy;

    @ManyToOne
    @JsonIgnoreProperties("userShippingAddresses")
    private User updateBy;

    @ManyToOne
    @JsonIgnoreProperties("userShippingAddresses")
    private City city;

    @ManyToOne
    @JsonIgnoreProperties("userShippingAddresses")
    private District district;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserShippingAddress name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public UserShippingAddress address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public UserShippingAddress mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNote() {
        return note;
    }

    public UserShippingAddress note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean isIsShippingAddress() {
        return isShippingAddress;
    }

    public UserShippingAddress isShippingAddress(Boolean isShippingAddress) {
        this.isShippingAddress = isShippingAddress;
        return this;
    }

    public void setIsShippingAddress(Boolean isShippingAddress) {
        this.isShippingAddress = isShippingAddress;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public UserShippingAddress createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public UserShippingAddress updateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public UserShippingAddress userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public User getCreateBy() {
        return createBy;
    }

    public UserShippingAddress createBy(User user) {
        this.createBy = user;
        return this;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public UserShippingAddress updateBy(User user) {
        this.updateBy = user;
        return this;
    }

    public void setUpdateBy(User user) {
        this.updateBy = user;
    }

    public City getCity() {
        return city;
    }

    public UserShippingAddress city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public District getDistrict() {
        return district;
    }

    public UserShippingAddress district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserShippingAddress)) {
            return false;
        }
        return id != null && id.equals(((UserShippingAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserShippingAddress{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", note='" + getNote() + "'" +
            ", isShippingAddress='" + isIsShippingAddress() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", updateAt='" + getUpdateAt() + "'" +
            "}";
    }
}
