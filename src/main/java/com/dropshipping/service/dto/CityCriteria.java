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
 * Criteria class for the {@link com.dropshipping.domain.City} entity. This class is used
 * in {@link com.dropshipping.web.rest.CityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter index;

    private BooleanFilter enabled;

    private LocalDateFilter createAt;

    private LocalDateFilter updateAt;

    private LongFilter countryId;

    private LongFilter districtsId;

    public CityCriteria(){
    }

    public CityCriteria(CityCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.index = other.index == null ? null : other.index.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.createAt = other.createAt == null ? null : other.createAt.copy();
        this.updateAt = other.updateAt == null ? null : other.updateAt.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.districtsId = other.districtsId == null ? null : other.districtsId.copy();
    }

    @Override
    public CityCriteria copy() {
        return new CityCriteria(this);
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

    public IntegerFilter getIndex() {
        return index;
    }

    public void setIndex(IntegerFilter index) {
        this.index = index;
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

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public LongFilter getDistrictsId() {
        return districtsId;
    }

    public void setDistrictsId(LongFilter districtsId) {
        this.districtsId = districtsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CityCriteria that = (CityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(index, that.index) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(createAt, that.createAt) &&
            Objects.equals(updateAt, that.updateAt) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(districtsId, that.districtsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        index,
        enabled,
        createAt,
        updateAt,
        countryId,
        districtsId
        );
    }

    @Override
    public String toString() {
        return "CityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (index != null ? "index=" + index + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (createAt != null ? "createAt=" + createAt + ", " : "") +
                (updateAt != null ? "updateAt=" + updateAt + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
                (districtsId != null ? "districtsId=" + districtsId + ", " : "") +
            "}";
    }

}
