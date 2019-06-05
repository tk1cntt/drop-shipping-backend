import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDistrict, defaultValue } from 'app/shared/model/district.model';

export const ACTION_TYPES = {
  FETCH_DISTRICT_LIST: 'district/FETCH_DISTRICT_LIST',
  FETCH_DISTRICT: 'district/FETCH_DISTRICT',
  CREATE_DISTRICT: 'district/CREATE_DISTRICT',
  UPDATE_DISTRICT: 'district/UPDATE_DISTRICT',
  DELETE_DISTRICT: 'district/DELETE_DISTRICT',
  RESET: 'district/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDistrict>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DistrictState = Readonly<typeof initialState>;

// Reducer

export default (state: DistrictState = initialState, action): DistrictState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DISTRICT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DISTRICT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DISTRICT):
    case REQUEST(ACTION_TYPES.UPDATE_DISTRICT):
    case REQUEST(ACTION_TYPES.DELETE_DISTRICT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DISTRICT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DISTRICT):
    case FAILURE(ACTION_TYPES.CREATE_DISTRICT):
    case FAILURE(ACTION_TYPES.UPDATE_DISTRICT):
    case FAILURE(ACTION_TYPES.DELETE_DISTRICT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISTRICT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISTRICT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DISTRICT):
    case SUCCESS(ACTION_TYPES.UPDATE_DISTRICT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DISTRICT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/districts';

// Actions

export const getEntities: ICrudGetAllAction<IDistrict> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DISTRICT_LIST,
    payload: axios.get<IDistrict>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDistrict> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DISTRICT,
    payload: axios.get<IDistrict>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDistrict> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DISTRICT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDistrict> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DISTRICT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDistrict> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DISTRICT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
