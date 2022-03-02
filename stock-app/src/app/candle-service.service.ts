import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, lastValueFrom } from "rxjs";
import { ticklist,stock,user} from './Model';

const URL_GET_CANDLSTICKS = "http://localhost:8080"
@Injectable()
export class candleService{
  tickinfo!:ticklist
  onAdd$ = new BehaviorSubject<ticklist>(this.tickinfo)
  getTickerInfo$ = this.onAdd$.asObservable();
  constructor(private http:HttpClient){}

  getTickerCandles(ticker:string):Promise<any>{
    return lastValueFrom(this.http.get<any>( `${URL_GET_CANDLSTICKS}/api/stock/${ticker}`))
  }

  getSearchResults(search:string):Promise<any>{
    const q = new HttpParams().set("q",search);
    return lastValueFrom(this.http.get<any>(`${URL_GET_CANDLSTICKS}/api/search/ticklist`,{params: q}))
  }

  tickInfoToTickerComponent(ticklist:ticklist){
    this.tickinfo=ticklist
    this.onAdd$.next(this.tickinfo)

  }
  //send ticker data to spring.
  addToPortfolio(addStock:stock):Promise<void>{
    return lastValueFrom(this.http.post<void>(`${URL_GET_CANDLSTICKS}/api/portfolio`, addStock))
  }
  //send Login info to spring for validation.
  loginInfoToSpring(loginInf:user):Promise<any>{
    return lastValueFrom(this.http.post<any>(`${URL_GET_CANDLSTICKS}/api/login`, loginInf))
  }
}
