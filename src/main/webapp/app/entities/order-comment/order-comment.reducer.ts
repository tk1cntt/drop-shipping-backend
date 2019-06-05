import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderComment, defaultValue } from 'app/shared/model/order-comment.model';

export const ACTION_TYPES = {
  FETCH_ORDERCOMMENT_LIST: 'orderComment/FETCH_ORDERCOMMENT_LIST',
  FETCH_ORDERCOMMENT: 'orderComment/FETCH_ORDERCOMMENT',
  CREATE_ORDERCOMMENT: 'orderComment/CREATE_ORDERCOMMENT',
  UPDATE_ORDERCOMMENT: 'orderComment/UPDATE_ORDERCOMMENT',
  DELETE_ORDERCOMMENT: 'orderComment/DELETE_ORDERCOMMENT',
  RESET: 'orderComment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderComment>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OrderCommentState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderCommentState = initialState, action): OrderCommentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERCOMMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERCOMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERCOMMENT):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERCOMMENT):
    case REQUEST(ACTION_TYPES.DELETE_ORDERCOMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERCOMMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERCOMMENT):
    case FAILURE(ACTION_TYPES.CREATE_ORDERCOMMENT):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERCOMMENT):
    case FAILURE(ACTION_TYPES.DELETE_ORDERCOMMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERCOMMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERCOMMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERCOMMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERCOMMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERCOMMENT):
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

const apiUrl = 'api/order-comments';

// Actions

export const getEntities: ICrudGetAllAction<IOrderComment> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORDERCOMMENT_LIST,
  payload: axios.get<IOrderComment>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOrderComment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERCOMMENT,
    payload: axios.get<IOrderComment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrderComment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERCOMMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrderComment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERCOMMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderComment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERCOMMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
