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
