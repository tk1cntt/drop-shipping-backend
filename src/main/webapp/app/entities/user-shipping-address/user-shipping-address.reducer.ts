import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserShippingAddress, defaultValue } from 'app/shared/model/user-shipping-address.model';

export const ACTION_TYPES = {
  FETCH_USERSHIPPINGADDRESS_LIST: 'userShippingAddress/FETCH_USERSHIPPINGADDRESS_LIST',
  FETCH_USERSHIPPINGADDRESS: 'userShippingAddress/FETCH_USERSHIPPINGADDRESS',
  CREATE_USERSHIPPINGADDRESS: 'userShippingAddress/CREATE_USERSHIPPINGADDRESS',
  UPDATE_USERSHIPPINGADDRESS: 'userShippingAddress/UPDATE_USERSHIPPINGADDRESS',
  DELETE_USERSHIPPINGADDRESS: 'userShippingAddress/DELETE_USERSHIPPINGADDRESS',
  RESET: 'userShippingAddress/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserShippingAddress>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type UserShippingAddressState = Readonly<typeof initialState>;

// Reducer

export default (state: UserShippingAddressState = initialState, action): UserShippingAddressState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERSHIPPINGADDRESS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERSHIPPINGADDRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USERSHIPPINGADDRESS):
    case REQUEST(ACTION_TYPES.UPDATE_USERSHIPPINGADDRESS):
    case REQUEST(ACTION_TYPES.DELETE_USERSHIPPINGADDRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USERSHIPPINGADDRESS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERSHIPPINGADDRESS):
    case FAILURE(ACTION_TYPES.CREATE_USERSHIPPINGADDRESS):
    case FAILURE(ACTION_TYPES.UPDATE_USERSHIPPINGADDRESS):
    case FAILURE(ACTION_TYPES.DELETE_USERSHIPPINGADDRESS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERSHIPPINGADDRESS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERSHIPPINGADDRESS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERSHIPPINGADDRESS):
    case SUCCESS(ACTION_TYPES.UPDATE_USERSHIPPINGADDRESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERSHIPPINGADDRESS):
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

const apiUrl = 'api/user-shipping-addresses';

// Actions

export const getEntities: ICrudGetAllAction<IUserShippingAddress> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USERSHIPPINGADDRESS_LIST,
  payload: axios.get<IUserShippingAddress>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IUserShippingAddress> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERSHIPPINGADDRESS,
    payload: axios.get<IUserShippingAddress>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUserShippingAddress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERSHIPPINGADDRESS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserShippingAddress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERSHIPPINGADDRESS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserShippingAddress> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERSHIPPINGADDRESS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
