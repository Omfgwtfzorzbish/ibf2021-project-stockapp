import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { lastValueFrom } from "rxjs";

const URL_GET_CANDLSTICKS = "http://localhost:8080"
@Injectable()
export class candleService{
  constructor(private http:HttpClient){}

  getTickerCandles(ticker:string):Promise<any>{
    return lastValueFrom(this.http.get<any>( `${URL_GET_CANDLSTICKS}/api/stock/${ticker}`))
  }
}
