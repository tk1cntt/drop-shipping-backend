import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderPackageHistory, defaultValue } from 'app/shared/model/order-package-history.model';

export const ACTION_TYPES = {
  FETCH_ORDERPACKAGEHISTORY_LIST: 'orderPackageHistory/FETCH_ORDERPACKAGEHISTORY_LIST',
  FETCH_ORDERPACKAGEHISTORY: 'orderPackageHistory/FETCH_ORDERPACKAGEHISTORY',
  CREATE_ORDERPACKAGEHISTORY: 'orderPackageHistory/CREATE_ORDERPACKAGEHISTORY',
  UPDATE_ORDERPACKAGEHISTORY: 'orderPackageHistory/UPDATE_ORDERPACKAGEHISTORY',
  DELETE_ORDERPACKAGEHISTORY: 'orderPackageHistory/DELETE_ORDERPACKAGEHISTORY',
  RESET: 'orderPackageHistory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderPackageHistory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OrderPackageHistoryState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderPackageHistoryState = initialState, action): OrderPackageHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERPACKAGEHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERPACKAGEHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERPACKAGEHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERPACKAGEHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_ORDERPACKAGEHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERPACKAGEHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERPACKAGEHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_ORDERPACKAGEHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERPACKAGEHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_ORDERPACKAGEHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERPACKAGEHISTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERPACKAGEHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERPACKAGEHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERPACKAGEHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERPACKAGEHISTORY):
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

const apiUrl = 'api/order-package-histories';

// Actions

export const getEntities: ICrudGetAllAction<IOrderPackageHistory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORDERPACKAGEHISTORY_LIST,
  payload: axios.get<IOrderPackageHistory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOrderPackageHistory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERPACKAGEHISTORY,
    payload: axios.get<IOrderPackageHistory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrderPackageHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERPACKAGEHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrderPackageHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERPACKAGEHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderPackageHistory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERPACKAGEHISTORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
