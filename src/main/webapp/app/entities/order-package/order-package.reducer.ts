import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderPackage, defaultValue } from 'app/shared/model/order-package.model';

export const ACTION_TYPES = {
  FETCH_ORDERPACKAGE_LIST: 'orderPackage/FETCH_ORDERPACKAGE_LIST',
  FETCH_ORDERPACKAGE: 'orderPackage/FETCH_ORDERPACKAGE',
  CREATE_ORDERPACKAGE: 'orderPackage/CREATE_ORDERPACKAGE',
  UPDATE_ORDERPACKAGE: 'orderPackage/UPDATE_ORDERPACKAGE',
  DELETE_ORDERPACKAGE: 'orderPackage/DELETE_ORDERPACKAGE',
  RESET: 'orderPackage/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderPackage>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OrderPackageState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderPackageState = initialState, action): OrderPackageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERPACKAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERPACKAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERPACKAGE):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERPACKAGE):
    case REQUEST(ACTION_TYPES.DELETE_ORDERPACKAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERPACKAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERPACKAGE):
    case FAILURE(ACTION_TYPES.CREATE_ORDERPACKAGE):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERPACKAGE):
    case FAILURE(ACTION_TYPES.DELETE_ORDERPACKAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERPACKAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERPACKAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERPACKAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERPACKAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERPACKAGE):
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

const apiUrl = 'api/order-packages';

// Actions

export const getEntities: ICrudGetAllAction<IOrderPackage> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORDERPACKAGE_LIST,
  payload: axios.get<IOrderPackage>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOrderPackage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERPACKAGE,
    payload: axios.get<IOrderPackage>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrderPackage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERPACKAGE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrderPackage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERPACKAGE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderPackage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERPACKAGE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
