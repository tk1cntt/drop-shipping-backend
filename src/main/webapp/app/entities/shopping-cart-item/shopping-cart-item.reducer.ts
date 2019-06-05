import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShoppingCartItem, defaultValue } from 'app/shared/model/shopping-cart-item.model';

export const ACTION_TYPES = {
  FETCH_SHOPPINGCARTITEM_LIST: 'shoppingCartItem/FETCH_SHOPPINGCARTITEM_LIST',
  FETCH_SHOPPINGCARTITEM: 'shoppingCartItem/FETCH_SHOPPINGCARTITEM',
  CREATE_SHOPPINGCARTITEM: 'shoppingCartItem/CREATE_SHOPPINGCARTITEM',
  UPDATE_SHOPPINGCARTITEM: 'shoppingCartItem/UPDATE_SHOPPINGCARTITEM',
  DELETE_SHOPPINGCARTITEM: 'shoppingCartItem/DELETE_SHOPPINGCARTITEM',
  RESET: 'shoppingCartItem/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShoppingCartItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ShoppingCartItemState = Readonly<typeof initialState>;

// Reducer

export default (state: ShoppingCartItemState = initialState, action): ShoppingCartItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SHOPPINGCARTITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHOPPINGCARTITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SHOPPINGCARTITEM):
    case REQUEST(ACTION_TYPES.UPDATE_SHOPPINGCARTITEM):
    case REQUEST(ACTION_TYPES.DELETE_SHOPPINGCARTITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SHOPPINGCARTITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHOPPINGCARTITEM):
    case FAILURE(ACTION_TYPES.CREATE_SHOPPINGCARTITEM):
    case FAILURE(ACTION_TYPES.UPDATE_SHOPPINGCARTITEM):
    case FAILURE(ACTION_TYPES.DELETE_SHOPPINGCARTITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHOPPINGCARTITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHOPPINGCARTITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHOPPINGCARTITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_SHOPPINGCARTITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHOPPINGCARTITEM):
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

const apiUrl = 'api/shopping-cart-items';

// Actions

export const getEntities: ICrudGetAllAction<IShoppingCartItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SHOPPINGCARTITEM_LIST,
  payload: axios.get<IShoppingCartItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IShoppingCartItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHOPPINGCARTITEM,
    payload: axios.get<IShoppingCartItem>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IShoppingCartItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHOPPINGCARTITEM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShoppingCartItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHOPPINGCARTITEM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShoppingCartItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHOPPINGCARTITEM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
