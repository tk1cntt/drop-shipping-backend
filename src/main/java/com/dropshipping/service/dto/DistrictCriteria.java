package com.dropshipping.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.dropshipping.domain.District} entity. This class is used
 * in {@link com.dropshipping.web.rest.DistrictResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /districts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DistrictCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter enabled;

    private LocalDateFilter createAt;

    private LocalDateFilter updateAt;

    private LongFilter cityId;

    private LongFilter wardsId;

    public DistrictCriteria(){
    }

    public DistrictCriteria(DistrictCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.createAt = other.createAt == null ? null : other.createAt.copy();
        this.updateAt = other.updateAt == null ? null : other.updateAt.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.wardsId = other.wardsId == null ? null : other.wardsId.copy();
    }

    @Override
    public DistrictCriteria copy() {
        return new DistrictCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
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

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public LongFilter getWardsId() {
        return wardsId;
    }

    public void setWardsId(LongFilter wardsId) {
        this.wardsId = wardsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DistrictCriteria that = (DistrictCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(createAt, that.createAt) &&
            Objects.equals(updateAt, that.updateAt) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(wardsId, that.wardsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        enabled,
        createAt,
        updateAt,
        cityId,
        wardsId
        );
    }

    @Override
    public String toString() {
        return "DistrictCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (createAt != null ? "createAt=" + createAt + ", " : "") +
                (updateAt != null ? "updateAt=" + updateAt + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
                (wardsId != null ? "wardsId=" + wardsId + ", " : "") +
            "}";
    }

}
