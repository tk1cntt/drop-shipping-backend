import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderHistory, defaultValue } from 'app/shared/model/order-history.model';

export const ACTION_TYPES = {
  FETCH_ORDERHISTORY_LIST: 'orderHistory/FETCH_ORDERHISTORY_LIST',
  FETCH_ORDERHISTORY: 'orderHistory/FETCH_ORDERHISTORY',
  CREATE_ORDERHISTORY: 'orderHistory/CREATE_ORDERHISTORY',
  UPDATE_ORDERHISTORY: 'orderHistory/UPDATE_ORDERHISTORY',
  DELETE_ORDERHISTORY: 'orderHistory/DELETE_ORDERHISTORY',
  RESET: 'orderHistory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderHistory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OrderHistoryState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderHistoryState = initialState, action): OrderHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_ORDERHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_ORDERHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_ORDERHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERHISTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERHISTORY):
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

const apiUrl = 'api/order-histories';

// Actions

export const getEntities: ICrudGetAllAction<IOrderHistory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORDERHISTORY_LIST,
  payload: axios.get<IOrderHistory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOrderHistory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERHISTORY,
    payload: axios.get<IOrderHistory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrderHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrderHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderHistory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERHISTORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
