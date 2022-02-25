import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { TicklistComponent } from './ticklist/ticklist.component';
import { TickerComponent } from './ticker/ticker.component';

import {MaterialModule} from './material.module'
import {FlexLayoutModule} from '@angular/flex-layout'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgApexchartsModule } from 'ng-apexcharts';
import { candleService } from './candle-service.service';
import { HttpClientModule } from '@angular/common/http';


const appRoutes:Routes=[
    {path:'' , component:LoginComponent},
    {path:'api/stock/ticklist', component:TicklistComponent},
    {path: 'api/stock/:ticker', component:TickerComponent}
]
@NgModule({
  declarations: [
    AppComponent,
    TicklistComponent,
    TickerComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    RouterModule,
    NgApexchartsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,ReactiveFormsModule,FlexLayoutModule
  ],
  providers: [candleService],
  bootstrap: [AppComponent]
})
export class AppModule { }
