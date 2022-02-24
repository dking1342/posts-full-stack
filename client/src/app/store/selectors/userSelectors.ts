import { createFeatureSelector, createSelector } from '@ngrx/store';
import { FetchResponse, ResponseAppState, User } from 'src/app/interfaces/fetch-responses';

const selectFeatureUser = createFeatureSelector<ResponseAppState<FetchResponse<User>>>("users");

export const selectUsersData = createSelector(selectFeatureUser,(state:ResponseAppState<FetchResponse<User>>)=> state.appData?.data);
export const selectUserDataState = createSelector(selectFeatureUser,(state:ResponseAppState<FetchResponse<User>>)=>state.dataState);
export const selectUserError = createSelector(selectFeatureUser,(state:ResponseAppState<FetchResponse<User>>)=>state.error);
export const selectUserUsername = createSelector(selectFeatureUser,(state:ResponseAppState<FetchResponse<User>>)=>state.appData?.data![0].username);