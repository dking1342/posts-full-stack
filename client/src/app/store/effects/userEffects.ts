import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, of, startWith, map, mergeMap } from "rxjs";
import { Datastate } from "src/app/enums/datastate";
import { FetchResponse, ResponseAppState, User } from "src/app/interfaces/fetch-responses";
import { FetchService } from "src/app/services/fetch.service";
import { ADD_USER, ADD_USER_ERROR, ADD_USER_LOADING, ADD_USER_SUCCESS, GET_USER, GET_USER_ERROR, GET_USER_LOADING, GET_USER_SUCCESS } from "../actions/userActions";

@Injectable()
export class UserEffects {
    constructor(
        private actions$:Actions,
        private fetch:FetchService
    ){}

    getUser$ = createEffect(()=>this.actions$.pipe(
        ofType(GET_USER),
        mergeMap(({username})=>
            this.fetch.getUser$(username).pipe(
                map((res:FetchResponse<User>)=>{
                    let response:ResponseAppState<FetchResponse<User>> = {
                        dataState:Datastate.LOADED,
                        appData:{
                            ...res
                        },
                        error:""
                    }
                    return GET_USER_SUCCESS({res:response});
                }),
                startWith(GET_USER_LOADING()),
                catchError((error:string)=>of(GET_USER_ERROR({error})))
            )
        )
    ));

    addUser$ = createEffect(()=>this.actions$.pipe(
        ofType(ADD_USER),
        mergeMap(({user})=>
            this.fetch.addUser$(user).pipe(
                map((res:FetchResponse<User>)=>{
                    let response:ResponseAppState<FetchResponse<User>> = {
                        dataState:Datastate.LOADED,
                        appData:{
                            ...res
                        },
                        error:""
                    }
                    return ADD_USER_SUCCESS({res:response});
                }),
                startWith(ADD_USER_LOADING()),
                catchError((error:string)=>of(ADD_USER_ERROR({error})))
            )
        )
    ));


}