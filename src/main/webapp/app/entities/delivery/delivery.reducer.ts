import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDelivery, defaultValue } from 'app/shared/model/delivery.model';

export const ACTION_TYPES = {
  FETCH_DELIVERY_LIST: 'delivery/FETCH_DELIVERY_LIST',
  FETCH_DELIVERY: 'delivery/FETCH_DELIVERY',
  CREATE_DELIVERY: 'delivery/CREATE_DELIVERY',
  UPDATE_DELIVERY: 'delivery/UPDATE_DELIVERY',
  DELETE_DELIVERY: 'delivery/DELETE_DELIVERY',
  RESET: 'delivery/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDelivery>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DeliveryState = Readonly<typeof initialState>;

// Reducer

export default (state: DeliveryState = initialState, action): DeliveryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DELIVERY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DELIVERY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DELIVERY):
    case REQUEST(ACTION_TYPES.UPDATE_DELIVERY):
    case REQUEST(ACTION_TYPES.DELETE_DELIVERY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DELIVERY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DELIVERY):
    case FAILURE(ACTION_TYPES.CREATE_DELIVERY):
    case FAILURE(ACTION_TYPES.UPDATE_DELIVERY):
    case FAILURE(ACTION_TYPES.DELETE_DELIVERY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DELIVERY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DELIVERY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DELIVERY):
    case SUCCESS(ACTION_TYPES.UPDATE_DELIVERY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DELIVERY):
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

const apiUrl = 'api/deliveries';

// Actions

export const getEntities: ICrudGetAllAction<IDelivery> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DELIVERY_LIST,
  payload: axios.get<IDelivery>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDelivery> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DELIVERY,
    payload: axios.get<IDelivery>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDelivery> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DELIVERY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDelivery> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DELIVERY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDelivery> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DELIVERY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
