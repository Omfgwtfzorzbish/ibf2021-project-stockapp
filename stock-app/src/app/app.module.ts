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
import { TokenStorageService } from './token-storage.service';

const appRoutes:Routes=[
    {path:'login' , component:LoginComponent},
    {path:'api/stock/ticklist', component:TicklistComponent},
    {path: 'api/stock/:ticker', component:TickerComponent},
    { path: '**', redirectTo: '/login', pathMatch: 'full' }
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
  providers: [candleService,TokenStorageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
