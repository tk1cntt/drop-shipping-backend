import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICountry, defaultValue } from 'app/shared/model/country.model';

export const ACTION_TYPES = {
  FETCH_COUNTRY_LIST: 'country/FETCH_COUNTRY_LIST',
  FETCH_COUNTRY: 'country/FETCH_COUNTRY',
  CREATE_COUNTRY: 'country/CREATE_COUNTRY',
  UPDATE_COUNTRY: 'country/UPDATE_COUNTRY',
  DELETE_COUNTRY: 'country/DELETE_COUNTRY',
  RESET: 'country/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICountry>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CountryState = Readonly<typeof initialState>;

// Reducer

export default (state: CountryState = initialState, action): CountryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COUNTRY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COUNTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COUNTRY):
    case REQUEST(ACTION_TYPES.UPDATE_COUNTRY):
    case REQUEST(ACTION_TYPES.DELETE_COUNTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COUNTRY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COUNTRY):
    case FAILURE(ACTION_TYPES.CREATE_COUNTRY):
    case FAILURE(ACTION_TYPES.UPDATE_COUNTRY):
    case FAILURE(ACTION_TYPES.DELETE_COUNTRY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COUNTRY_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_COUNTRY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COUNTRY):
    case SUCCESS(ACTION_TYPES.UPDATE_COUNTRY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COUNTRY):
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

const apiUrl = 'api/countries';

// Actions

export const getEntities: ICrudGetAllAction<ICountry> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COUNTRY_LIST,
    payload: axios.get<ICountry>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICountry> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COUNTRY,
    payload: axios.get<ICountry>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICountry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COUNTRY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICountry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COUNTRY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICountry> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COUNTRY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
