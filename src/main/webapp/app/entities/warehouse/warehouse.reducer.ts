import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWarehouse, defaultValue } from 'app/shared/model/warehouse.model';

export const ACTION_TYPES = {
  FETCH_WAREHOUSE_LIST: 'warehouse/FETCH_WAREHOUSE_LIST',
  FETCH_WAREHOUSE: 'warehouse/FETCH_WAREHOUSE',
  CREATE_WAREHOUSE: 'warehouse/CREATE_WAREHOUSE',
  UPDATE_WAREHOUSE: 'warehouse/UPDATE_WAREHOUSE',
  DELETE_WAREHOUSE: 'warehouse/DELETE_WAREHOUSE',
  RESET: 'warehouse/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWarehouse>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WarehouseState = Readonly<typeof initialState>;

// Reducer

export default (state: WarehouseState = initialState, action): WarehouseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WAREHOUSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WAREHOUSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WAREHOUSE):
    case REQUEST(ACTION_TYPES.UPDATE_WAREHOUSE):
    case REQUEST(ACTION_TYPES.DELETE_WAREHOUSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_WAREHOUSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WAREHOUSE):
    case FAILURE(ACTION_TYPES.CREATE_WAREHOUSE):
    case FAILURE(ACTION_TYPES.UPDATE_WAREHOUSE):
    case FAILURE(ACTION_TYPES.DELETE_WAREHOUSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_WAREHOUSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WAREHOUSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WAREHOUSE):
    case SUCCESS(ACTION_TYPES.UPDATE_WAREHOUSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WAREHOUSE):
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

const apiUrl = 'api/warehouses';

// Actions

export const getEntities: ICrudGetAllAction<IWarehouse> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WAREHOUSE_LIST,
  payload: axios.get<IWarehouse>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWarehouse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WAREHOUSE,
    payload: axios.get<IWarehouse>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWarehouse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WAREHOUSE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWarehouse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WAREHOUSE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWarehouse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WAREHOUSE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
