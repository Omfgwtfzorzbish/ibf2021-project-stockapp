import { AfterContentInit, AfterViewChecked, AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute,ParamMap, Router } from '@angular/router';
import { candleService } from '../candle-service.service';
import { candlestick, ticklist,stock } from '../Model';
import {
  ChartComponent,
  ApexAxisChartSeries,
  ApexChart,
  ApexYAxis,
  ApexXAxis,
  ApexTitleSubtitle
} from "ng-apexcharts";
import { Subscription } from 'rxjs';
import { TokenStorageService } from '../token-storage.service';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  yaxis: ApexYAxis;
  title: ApexTitleSubtitle;
};

declare var google:any;

@Component({
  selector: 'app-ticker',
  templateUrl: './ticker.component.html',
  styleUrls: ['./ticker.component.css']
})
export class TickerComponent implements OnInit,AfterViewInit,OnDestroy,AfterViewChecked,AfterContentInit {
  result:any;
  time0:any; candle0:any;
  candlestick!:candlestick;
  ticker:string = '';
  data:string='';
  err:string='error';
  t0:string = '';

  subscription!:Subscription;
  tickerinfo!:ticklist;

  addStock!:stock;

 // @ViewChild("chart") chart!:ChartComponent;
  public chartOptions!:ChartOptions;


  constructor(private route:ActivatedRoute, private candleSvc:candleService, private router:Router,private tokenSvc:TokenStorageService) {
    //console.info(this.candlestick.time0)

      //  google.charts.load('current', {'packages':['corechart']});
      //   this.buildChart();

    this.chartOptions={
      series:[{
        name:"candle",
        data: [
          // {
          //   x: new Date(this.candlestick?.time0),  1644192000
          //   y: this.candlestick?.candle0[6629.81, 6650.5, 6623.04, 6633.33]
          // }
          {
            x: new Date(1644192000),
            y: [6629.81, 6650.5, 6623.04, 6633.33]
          }
              ]
      }],
      chart: {
        type: "candlestick",
        height: 350
      },
      title: {
        text: "CandleStick Chart",
        align: "left"
      },
      xaxis: {
        type: "datetime"
      },
      yaxis: {
        tooltip: {
          enabled: true
        }
      }
   }
   }
   ngAfterViewInit(): void {
    // google.charts.load('current', {'packages':['corechart']});
     //this.buildChart();
   }
   ngAfterContentInit(): void {
    //google.charts.load('current', {'packages':['corechart']});
     //this.buildChart();
   }

  // public chartOptions:ChartOptions={
  //     series:[{
  //       name:"candle",
  //       data: [
  //         {
  //           x: new Date(this.candlestick?.time0),
  //           y: this.candlestick?.candle0
  //         }
  //         // {
  //         //   x: new Date(1538778600000),
  //         //   y: [6629.81, 6650.5, 6623.04, 6633.33]
  //         // }
  //             ]
  //     }],
  //     chart: {
  //       type: "candlestick",
  //       height: 350
  //     },
  //     title: {
  //       text: "CandleStick Chart",
  //       align: "left"
  //     },
  //     xaxis: {
  //       type: "datetime"
  //     },
  //     yaxis: {
  //       tooltip: {
  //         enabled: true
  //       }
  //     }
  //  }
  ngOnInit(): void {
    this.ticker=this.route.snapshot.params['ticker'];
    this.subscription = this.candleSvc.getTickerInfo$.subscribe(
      (tickerinfo:ticklist)=>{this.tickerinfo=tickerinfo
      console.log('>>>SUBSCRIBABALE', this.tickerinfo)}
    );

      console.info("THIS TICKER>>" + this.ticker)

      this.candleSvc.getTickerCandles(this.ticker,this.tokenSvc.getToken())
        .then((result)=>{
          this.result=result
          this.candlestick=result
            console.info(this.result); //console.info(this.candlestick.candle0);
            console.info(this.candlestick.c, this.candlestick.t)
            google.charts.load('current', {'packages':['corechart']});
            this.buildChart();
          }).catch(error => this.err=error)

        //   google.charts.load('current', {'packages':['corechart']});
        //  this.buildChart();
    }
    buildChart(){
      var func = (chart:any) =>{
        var data = google.visualization.arrayToDataTable([
          [this.candlestick.t[0], Number(this.candlestick.l[0]), Number(this.candlestick.o[0]), Number(this.candlestick.c[0]), Number(this.candlestick.h[0])], //l o c h
          [this.candlestick.t[1], Number(this.candlestick.l[1]), Number(this.candlestick.o[1]), Number(this.candlestick.c[1]), Number(this.candlestick.h[1])],
          [this.candlestick.t[2], Number(this.candlestick.l[2]), Number(this.candlestick.o[2]), Number(this.candlestick.c[2]), Number(this.candlestick.h[2])],
          [this.candlestick.t[3], Number(this.candlestick.l[3]), Number(this.candlestick.o[3]), Number(this.candlestick.c[3]), Number(this.candlestick.h[3])],
          [this.candlestick.t[4], Number(this.candlestick.l[4]), Number(this.candlestick.o[4]), Number(this.candlestick.c[4]), Number(this.candlestick.h[4])],
          [this.candlestick.t[5], Number(this.candlestick.l[5]), Number(this.candlestick.o[5]), Number(this.candlestick.c[5]), Number(this.candlestick.h[5])],
          [this.candlestick.t[6], Number(this.candlestick.l[6]), Number(this.candlestick.o[6]), Number(this.candlestick.c[6]), Number(this.candlestick.h[6])],
          [this.candlestick.t[7], Number(this.candlestick.l[7]), Number(this.candlestick.o[7]), Number(this.candlestick.c[7]), Number(this.candlestick.h[7])],
          [this.candlestick.t[8], Number(this.candlestick.l[8]), Number(this.candlestick.o[8]), Number(this.candlestick.c[8]), Number(this.candlestick.h[8])],
          [this.candlestick.t[9], Number(this.candlestick.l[9]), Number(this.candlestick.o[9]), Number(this.candlestick.c[9]), Number(this.candlestick.h[9])],
          [this.candlestick.t[10], Number(this.candlestick.l[10]), Number(this.candlestick.o[10]), Number(this.candlestick.c[10]), Number(this.candlestick.h[10])],
          [this.candlestick.t[11], Number(this.candlestick.l[11]), Number(this.candlestick.o[11]), Number(this.candlestick.c[11]), Number(this.candlestick.h[11])],
          [this.candlestick.t[12], Number(this.candlestick.l[12]), Number(this.candlestick.o[12]), Number(this.candlestick.c[12]), Number(this.candlestick.h[12])],
          [this.candlestick.t[12], Number(this.candlestick.l[13]), Number(this.candlestick.o[13]), Number(this.candlestick.c[13]), Number(this.candlestick.h[13])],
          [this.candlestick.t[14], Number(this.candlestick.l[14]), Number(this.candlestick.o[14]), Number(this.candlestick.c[14]), Number(this.candlestick.h[14])],
          [this.candlestick.t[15], Number(this.candlestick.l[15]), Number(this.candlestick.o[15]), Number(this.candlestick.c[15]), Number(this.candlestick.h[15])]
          // ['Tasdue', 31, 38, 55, 66],
          // ['Weaasdd', 50, 55, 77, 80],
          // ['Thasdu', 77, 77, 66, 50],
          // ['Fasdri', 68, 66, 22, 15]
          // Treat first row as data as well.
        ], true);

        var options = {
          legend:'none',
          candlestick: {
            fallingColor: { strokeWidth: 0, fill: '#a52714' }, // red
            risingColor: { strokeWidth: 0, fill: '#0f9d58' }   // green
          }
        };
        chart().draw(data, options);
      }
      var chart = () => new google.visualization.CandlestickChart(document.getElementById('googleCandleStick'));
      var callback=()=>func(chart);
        // Set a callback to run when the Google Visualization API is loaded.
        google.charts.setOnLoadCallback(callback);
    }
    ngAfterViewChecked(): void {

     // this.buildChart();
    }
    ngOnDestroy() {
      this.subscription.unsubscribe()
    }
    back(){
      this.router.navigate(['/api/stock/ticklist'])
    }

    addToPort(){
      this.addStock = {} as stock
      this.addStock.ticker=this.ticker
      console.info(this.ticker, this.addStock.ticker)
      this.candleSvc.addToPortfolio(this.addStock, this.tokenSvc.getToken())
      this.router.navigate(['/api/stock/ticklist'])
    }

    // drawChart(){
    //   var data = google.visualization.arrayToDataTable([
    //     ['Mon', 20, 28, 38, 45],
    //     ['Tue', 31, 38, 55, 66],
    //     ['Wed', 50, 55, 77, 80],
    //     ['Thu', 77, 77, 66, 50],
    //     ['Fri', 68, 66, 22, 15]
    //     // Treat first row as data as well.
    //   ], true);

    //   var options = {
    //     legend:'none'
    //   };

    //   var chart = new google.visualization.CandlestickChart(document.getElementById('googleCandleStick'));

    //   chart.draw(data, options);
    // }

}
