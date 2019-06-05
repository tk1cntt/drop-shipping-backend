package com.dropshipping.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.dropshipping.domain.enumeration.Gender;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.dropshipping.domain.UserProfile} entity. This class is used
 * in {@link com.dropshipping.web.rest.UserProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserProfileCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private GenderFilter gender;

    private StringFilter email;

    private StringFilter mobile;

    private LocalDateFilter createAt;

    private LocalDateFilter updateAt;

    private LongFilter createById;

    private LongFilter updateById;

    private LongFilter cityId;

    private LongFilter districtId;

    private LongFilter addressId;

    public UserProfileCriteria(){
    }

    public UserProfileCriteria(UserProfileCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.createAt = other.createAt == null ? null : other.createAt.copy();
        this.updateAt = other.updateAt == null ? null : other.updateAt.copy();
        this.createById = other.createById == null ? null : other.createById.copy();
        this.updateById = other.updateById == null ? null : other.updateById.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
    }

    @Override
    public UserProfileCriteria copy() {
        return new UserProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public LocalDateFilter getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateFilter createAt) {
        this.createAt = createAt;
    }

    public LocalDateFilter getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateFilter updateAt) {
        this.updateAt = updateAt;
    }

    public LongFilter getCreateById() {
        return createById;
    }

    public void setCreateById(LongFilter createById) {
        this.createById = createById;
    }

    public LongFilter getUpdateById() {
        return updateById;
    }

    public void setUpdateById(LongFilter updateById) {
        this.updateById = updateById;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserProfileCriteria that = (UserProfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(email, that.email) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(createAt, that.createAt) &&
            Objects.equals(updateAt, that.updateAt) &&
            Objects.equals(createById, that.createById) &&
            Objects.equals(updateById, that.updateById) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullName,
        gender,
        email,
        mobile,
        createAt,
        updateAt,
        createById,
        updateById,
        cityId,
        districtId,
        addressId
        );
    }

    @Override
    public String toString() {
        return "UserProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (createAt != null ? "createAt=" + createAt + ", " : "") +
                (updateAt != null ? "updateAt=" + updateAt + ", " : "") +
                (createById != null ? "createById=" + createById + ", " : "") +
                (updateById != null ? "updateById=" + updateById + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
                (districtId != null ? "districtId=" + districtId + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
            "}";
    }

}
