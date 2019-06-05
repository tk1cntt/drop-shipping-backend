import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderTransaction, defaultValue } from 'app/shared/model/order-transaction.model';

export const ACTION_TYPES = {
  FETCH_ORDERTRANSACTION_LIST: 'orderTransaction/FETCH_ORDERTRANSACTION_LIST',
  FETCH_ORDERTRANSACTION: 'orderTransaction/FETCH_ORDERTRANSACTION',
  CREATE_ORDERTRANSACTION: 'orderTransaction/CREATE_ORDERTRANSACTION',
  UPDATE_ORDERTRANSACTION: 'orderTransaction/UPDATE_ORDERTRANSACTION',
  DELETE_ORDERTRANSACTION: 'orderTransaction/DELETE_ORDERTRANSACTION',
  RESET: 'orderTransaction/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderTransaction>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OrderTransactionState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderTransactionState = initialState, action): OrderTransactionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERTRANSACTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERTRANSACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERTRANSACTION):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERTRANSACTION):
    case REQUEST(ACTION_TYPES.DELETE_ORDERTRANSACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERTRANSACTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERTRANSACTION):
    case FAILURE(ACTION_TYPES.CREATE_ORDERTRANSACTION):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERTRANSACTION):
    case FAILURE(ACTION_TYPES.DELETE_ORDERTRANSACTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERTRANSACTION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERTRANSACTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERTRANSACTION):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERTRANSACTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERTRANSACTION):
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

const apiUrl = 'api/order-transactions';

// Actions

export const getEntities: ICrudGetAllAction<IOrderTransaction> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORDERTRANSACTION_LIST,
  payload: axios.get<IOrderTransaction>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOrderTransaction> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERTRANSACTION,
    payload: axios.get<IOrderTransaction>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrderTransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERTRANSACTION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrderTransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERTRANSACTION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderTransaction> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERTRANSACTION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
