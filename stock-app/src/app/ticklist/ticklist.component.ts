import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { candleService } from '../candle-service.service';
import { ticklist } from '../Model';
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

  search!:string
  result!:ticklist[]
  err!:string

  constructor(private route:ActivatedRoute, private candleSvc:candleService, private fb:FormBuilder) { }

  ngOnInit(): void {
    // this.route.queryParams.subscribe(   //use routerlink on page to navigate and find stuff
    //   params => {this.search=params['q']}
    // )
    this.form = this.createForm();





  }
    createForm():FormGroup{
      return this.fb.group({
        ticker: this.fb.control('',[Validators.required])
      })
    };

    processForm(){
      this.userSearch = this.form.value
      this.candleSvc.getSearchResults(this.userSearch.ticker.toLowerCase())
      .then(result => {this.result=result,
        console.info(this.result)}
        ).catch(error => this.err=error)
    }

}
