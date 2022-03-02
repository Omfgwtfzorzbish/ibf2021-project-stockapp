export interface candlestick{
  c:number[]
  h:number[]
  l:number[]
  o:number[]
  t:string[]

}
export interface ticklist{
 description:string
 displaySymbol:string
 symbol:string
 type:string

}

export interface searchForm{
  ticker:string   //user seach query
}

export interface stock{
  ticker:string
}

export interface user{
  username:string
  //email:string
  password:string
}

export interface loginStatus{
  loginStatus:number
}
export interface token{
  token:string
}
