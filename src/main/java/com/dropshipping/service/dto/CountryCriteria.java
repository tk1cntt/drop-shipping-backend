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
 * Criteria class for the {@link com.dropshipping.domain.Country} entity. This class is used
 * in {@link com.dropshipping.web.rest.CountryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter enabled;

    private LocalDateFilter createAt;

    private LocalDateFilter updateAt;

    private LongFilter citiesId;

    private LongFilter regionId;

    public CountryCriteria(){
    }

    public CountryCriteria(CountryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.createAt = other.createAt == null ? null : other.createAt.copy();
        this.updateAt = other.updateAt == null ? null : other.updateAt.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
    }

    @Override
    public CountryCriteria copy() {
        return new CountryCriteria(this);
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

    public LongFilter getCitiesId() {
        return citiesId;
    }

    public void setCitiesId(LongFilter citiesId) {
        this.citiesId = citiesId;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CountryCriteria that = (CountryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(createAt, that.createAt) &&
            Objects.equals(updateAt, that.updateAt) &&
            Objects.equals(citiesId, that.citiesId) &&
            Objects.equals(regionId, that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        enabled,
        createAt,
        updateAt,
        citiesId,
        regionId
        );
    }

    @Override
    public String toString() {
        return "CountryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (createAt != null ? "createAt=" + createAt + ", " : "") +
                (updateAt != null ? "updateAt=" + updateAt + ", " : "") +
                (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
                (regionId != null ? "regionId=" + regionId + ", " : "") +
            "}";
    }

}
