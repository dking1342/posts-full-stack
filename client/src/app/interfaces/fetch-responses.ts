import { Datastate } from "../enums/datastate";

export interface ResponseAppState<T> {
    dataState:Datastate;
    appData?:T;
    error?:string;
}

export interface FetchResponse<T> {
    timestamp?: Date,
    HttpStatus?: string,
    status?: string,
    statusCode?: number,
    message?: string,
    data?: T[],
};

export interface Post {
    post_id:string,
    post_title:string,
    post_body:string,
    post_author:User,
    post_likes:number,
    post_dislikes:number,
    comments:Comment[],
    post_createdAt:Date,
    post_votes:Vote[],
    post_momentAt:string
};

export interface Comment {
    comment_id:string,
    comment_author:User,
    comment_body:string,
    comment_likes:number,
    comment_dislikes:number,
    comment_createdAt:Date,
    comment_votes:CommentVote,
    comment_momentAt:string
}

export interface User {
    user_id:string,
    username:string,
    user_posts:Post[],
    user_votes:Vote[]
}

export interface Vote{
    vote_id:string,
    value:number,
    user_id:string,
    user:User,
    post_id:string,
    post:Post
}

export interface CommentVote{
    vote_id:string,
    value:number,
    user_id:string,
    user:User,
    post_id:string,
    comment_id:string,
    comment:Comment
}

export let userObj:User = {
    user_id:"",
    username:"",
    user_posts:[],
    user_votes:[]
};

export let postObj:Post = {
    post_id:"",
    post_title:"",
    post_body:"",
    post_author:userObj,
    post_likes:0,
    post_dislikes:0,
    comments:[],
    post_createdAt:new Date(),
    post_votes:[],
    post_momentAt:""
}

export let voteObj:Vote = {
    vote_id:"",
    value:0,
    user_id:"",
    user:userObj,
    post_id:"",
    post:postObj
}

export let responseFetchVote:ResponseAppState<FetchResponse<Vote>> = {
    dataState:Datastate.LOADED,
    appData:{
        timestamp: new Date(),
        HttpStatus: "",
        status: "",
        statusCode: 200,
        message: "",
        data:[],
    },
    error:""
}
export let responseFetchPost:ResponseAppState<FetchResponse<Post>> = {
    dataState:Datastate.LOADED,
    appData:{
        timestamp: new Date(),
        HttpStatus: "",
        status: "",
        statusCode: 200,
        message: "",
        data:[],
    },
    error:""
}
