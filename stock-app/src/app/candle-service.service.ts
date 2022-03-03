import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, lastValueFrom } from "rxjs";
import { ticklist,stock,user, registerUser, portfolioItem} from './Model';
import { TokenStorageService } from "./token-storage.service";

const URL_GET_CANDLSTICKS = "http://localhost:8080"

const httpOptions = {
  headers: new HttpHeaders({'Content-Type':'application/json'})
}
@Injectable()
export class candleService{
  tickinfo!:ticklist
  token!:string|null
  onAdd$ = new BehaviorSubject<ticklist>(this.tickinfo)
  getTickerInfo$ = this.onAdd$.asObservable()
  // token$ = new BehaviorSubject<string>(this.tokensub)
  // getTokenInfo$ =this.token$.asObservable()

  constructor(private http:HttpClient, private tokenSvc:TokenStorageService){}

  getTickerCandles(ticker:string,token:string|null):Promise<any>{
    if(typeof token === "string"){this.token=token;
      console.info(this.token)} else{console.info("token is null")}
    const p={
      headers:new HttpHeaders(
        {'Authorization': ('Bearer '+ this.token)})}
    return lastValueFrom(this.http.get<any>( `api/stock/${ticker}`,p)) //${URL_GET_CANDLSTICKS}
  }

  getSearchResults(search:string,token:string|null):Promise<any>{
    if(typeof token === "string"){this.token=token;
      console.info(this.token)} else{console.info("token is null")}
    const p={
      headers:new HttpHeaders(
        {'Authorization': ('Bearer '+ this.token)})}
   // const q = new HttpParams().set("q",search);
    return lastValueFrom(this.http.get<any>(`api/search/ticklist?q=`+search,p))
  }

  tickInfoToTickerComponent(ticklist:ticklist){
    this.tickinfo=ticklist
    this.onAdd$.next(this.tickinfo)

  }
  //send ticker data to spring.
  addToPortfolio(addStock:stock,token:string|null, username:string|null):Promise<void>{
    if(typeof token === "string"){this.token=token;
      console.info(this.token)} else{console.info("token is null")}
      if(typeof username === "string"){addStock.username=username;
        console.info(addStock.username)} else{console.info("username is null")}
    const p={
      headers:new HttpHeaders(
        {'Authorization': ('Bearer '+ this.token)})}
    return lastValueFrom(this.http.post<void>(`api/portfolio`, addStock,p))
  }
  //send Login info to spring for validation.
  loginInfoToSpring(loginInf:user):Promise<any>{
    return lastValueFrom(this.http.post<any>(`api/login`, loginInf))
    //return lastValueFrom(this.http.post<any>(`/api/login`, loginInf))
  }
  registerUser(regInf:registerUser):Promise<void>{
    return lastValueFrom(this.http.post<void>(`api/register`, regInf))
  }
  getPortfolio(user:user, username:string|null,token:string|null):Promise<portfolioItem[]>{
    if(typeof token === "string"){this.token=token;
      console.info(this.token)} else{console.info("token is null")}
      if(typeof username === "string"){user.username=username;
       } else{console.info("username is null")}
      const p={
        headers:new HttpHeaders(
          {'Authorization': ('Bearer '+ this.token)})}
    return lastValueFrom(this.http.post<portfolioItem[]>(`api/stock/ticklist/getport`, user,p))
  }
}
