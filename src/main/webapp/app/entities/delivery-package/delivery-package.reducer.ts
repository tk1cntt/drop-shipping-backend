import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDeliveryPackage, defaultValue } from 'app/shared/model/delivery-package.model';

export const ACTION_TYPES = {
  FETCH_DELIVERYPACKAGE_LIST: 'deliveryPackage/FETCH_DELIVERYPACKAGE_LIST',
  FETCH_DELIVERYPACKAGE: 'deliveryPackage/FETCH_DELIVERYPACKAGE',
  CREATE_DELIVERYPACKAGE: 'deliveryPackage/CREATE_DELIVERYPACKAGE',
  UPDATE_DELIVERYPACKAGE: 'deliveryPackage/UPDATE_DELIVERYPACKAGE',
  DELETE_DELIVERYPACKAGE: 'deliveryPackage/DELETE_DELIVERYPACKAGE',
  RESET: 'deliveryPackage/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDeliveryPackage>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DeliveryPackageState = Readonly<typeof initialState>;

// Reducer

export default (state: DeliveryPackageState = initialState, action): DeliveryPackageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DELIVERYPACKAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DELIVERYPACKAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DELIVERYPACKAGE):
    case REQUEST(ACTION_TYPES.UPDATE_DELIVERYPACKAGE):
    case REQUEST(ACTION_TYPES.DELETE_DELIVERYPACKAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DELIVERYPACKAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DELIVERYPACKAGE):
    case FAILURE(ACTION_TYPES.CREATE_DELIVERYPACKAGE):
    case FAILURE(ACTION_TYPES.UPDATE_DELIVERYPACKAGE):
    case FAILURE(ACTION_TYPES.DELETE_DELIVERYPACKAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DELIVERYPACKAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DELIVERYPACKAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DELIVERYPACKAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_DELIVERYPACKAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DELIVERYPACKAGE):
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

const apiUrl = 'api/delivery-packages';

// Actions

export const getEntities: ICrudGetAllAction<IDeliveryPackage> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DELIVERYPACKAGE_LIST,
  payload: axios.get<IDeliveryPackage>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDeliveryPackage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DELIVERYPACKAGE,
    payload: axios.get<IDeliveryPackage>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDeliveryPackage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DELIVERYPACKAGE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDeliveryPackage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DELIVERYPACKAGE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDeliveryPackage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DELIVERYPACKAGE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
