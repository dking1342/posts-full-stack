import { createAction, props } from "@ngrx/store";
import { FetchResponse, ResponseAppState, User } from "src/app/interfaces/fetch-responses";

export const GET_USER = createAction(
    "[User] Get user",
    props<{username:string}>()
);
export const GET_USER_LOADING = createAction(
    "[User] Get user loading"
);
export const GET_USER_ERROR = createAction(
    "[User] Get user error",
    props<{error:string}>()
);
export const GET_USER_SUCCESS = createAction(
    "[User/API] Get user success",
    props<{res:ResponseAppState<FetchResponse<User>>}>()
);

export const CLEAR_USER = createAction(
    "[User] Clear user"
);

export const ADD_USER = createAction(
    "[User] Add user",
    props<{user:User}>()
);
export const ADD_USER_LOADING = createAction(
    "[User] Add user loading"
);
export const ADD_USER_ERROR = createAction(
    "[User] Add user error",
    props<{error:string}>()
);
export const ADD_USER_SUCCESS = createAction(
    "[User/API] Add user success",
    props<{res:ResponseAppState<FetchResponse<User>>}>()
);