import { createReducer, on } from '@ngrx/store';
import { Datastate } from 'src/app/enums/datastate';
import { FetchResponse, ResponseAppState, User } from 'src/app/interfaces/fetch-responses';
import { ADD_USER, ADD_USER_ERROR, ADD_USER_LOADING, ADD_USER_SUCCESS, CLEAR_USER, GET_USER, GET_USER_ERROR, GET_USER_LOADING, GET_USER_SUCCESS } from '../actions/userActions';

export const initialStateUser:ResponseAppState<FetchResponse<User>> = {
    dataState:Datastate.LOADED,
    appData:{
        timestamp:new Date(),
        HttpStatus:"",
        status:"",
        statusCode:0,
        message:"",
        data:[]
    },
    error:"",
};

type fnType = (state:ResponseAppState<FetchResponse<User>>) => ResponseAppState<FetchResponse<User>>;

const saveToLocalStorage:fnType = (state) => {
    localStorage.setItem('users',JSON.stringify(state));
    let localState = JSON.parse(localStorage.getItem('users')!);
    return localState;
};

export const userReducer = createReducer(
    initialStateUser,
    on(GET_USER,state=>state),
    on(GET_USER_LOADING,state=>{
        state = {
            ...state,
            dataState:Datastate.LOADING
        }
        return state;
    }),
    on(GET_USER_ERROR,(state,{error})=>{
        state = {
            ...state,
            dataState:Datastate.ERROR,
            error
        };
        return state;
    }),
    on(GET_USER_SUCCESS,(state,{res})=>{
        state = {
            ...res
        };
        return saveToLocalStorage(state);
    }),
    on(CLEAR_USER,state=>{
        state = {
            ...state,
            appData:{
                ...state.appData,
                data:[]
            }
        }
        return state;
    }),
    on(ADD_USER,state=>state),
    on(ADD_USER_LOADING,state=>{
        state = {
            ...state,
            dataState:Datastate.LOADING
        };
        return state;
    }),
    on(ADD_USER_ERROR,(state,{error})=>{
        state = {
            ...state,
            dataState:Datastate.ERROR,
            error,
        };
        return state;
    }),
    on(ADD_USER_SUCCESS,(state,{res})=>{
        state = {
            ...res
        };
        return saveToLocalStorage(state);
    })
)