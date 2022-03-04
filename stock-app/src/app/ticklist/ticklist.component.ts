import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { candleService } from '../candle-service.service';
import { TokenStorageService } from '../token-storage.service';
import { delStock, portfolioItem, ticklist,user } from '../Model';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { searchForm } from '../Model';

@Component({
  selector: 'app-ticklist',
  templateUrl: './ticklist.component.html',
  styleUrls: ['./ticklist.component.css']
})
export class TicklistComponent implements OnInit {
  form!:FormGroup
  userSearch!:searchForm
  userSelection!:string
  delStock!:delStock

  search!:string
  result!:ticklist[]
  singleTicker!:ticklist
  err!:string

  userPortfolio:portfolioItem[]=[]
  user!:user
  constructor(private route:ActivatedRoute, private candleSvc:candleService,
    private fb:FormBuilder,private router:Router, private tokenSvc:TokenStorageService) { }

  ngOnInit(): void {
    // this.route.queryParams.subscribe(   //use routerlink on page to navigate and find stuff
    //   params => {this.search=params['q']}
    // )
    this.user={} as user
    this.candleSvc.getPortfolio(this.user,this.tokenSvc.getUser(),this.tokenSvc.getToken())
    .then(portfolio=>{
      this.userPortfolio=portfolio;console.log(this.userPortfolio[0].date_added)
    }).catch(error =>{console.info(error) ;this.err=error});

    this.form = this.createForm();
  }
    createForm():FormGroup{
      return this.fb.group({
        ticker: this.fb.control('',[Validators.required])
      })
    };
    createForm2():FormGroup{
      return this.fb.group({
        userSelection: this.fb.control('')
      })
    };

    processForm(){
      this.userSearch = this.form.value
      console.info(this.userSearch.ticker)
      this.candleSvc.getSearchResults(this.userSearch.ticker.toLowerCase(),this.tokenSvc.getToken()) //this.tokenSvc.getToken()
      // returns ticklist[] of tickers and info
      .then(result => {this.result=result,
        console.info(this.result)}
        ).catch(error => this.err=error)
    }

    selectTicker(i:number){
      this.userSearch.ticker=this.result[i].symbol;
      console.info(this.userSearch.ticker)
      for(let i = 0; i<this.result.length;i++){
        if(this.userSearch.ticker==this.result[i].symbol){
          this.candleSvc.tickInfoToTickerComponent(this.result[i])
          this.router.navigate(['/api/stock',this.userSearch.ticker])
        }
      }
    }

    deleteStock(i:number){
      this.candleSvc.delStock(this.userPortfolio[i],this.tokenSvc.getToken())
      this.candleSvc.getPortfolio(this.user,this.tokenSvc.getUser(),this.tokenSvc.getToken())
    .then(portfolio=>{
      this.userPortfolio=portfolio;console.log(this.userPortfolio[0].date_added)
    })
    }
    refresh(){
      this.candleSvc.getPortfolio(this.user,this.tokenSvc.getUser(),this.tokenSvc.getToken())
    .then(portfolio=>{
      this.userPortfolio=portfolio;console.log(this.userPortfolio[0].date_added)
    })
    }

}
