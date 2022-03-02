import { Component, OnInit } from '@angular/core';
import { validateBasis } from '@angular/flex-layout';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { candleService } from '../candle-service.service';
import { user,loginStatus } from '../Model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!:FormGroup
  loginStatus!:loginStatus

  constructor(private fb:FormBuilder, private candleSvc:candleService) { }

  ngOnInit(): void {
    this.form=this.fb.group({
      username:this.fb.control('',[Validators.required]),
      //email:this.fb.control(['',Validators.email]),
      password: this.fb.control('',[Validators.required,Validators.minLength(4)])
    })
  }

  processForm(){
   const userLogin =this.form.value as user;
    this.candleSvc.loginInfoToSpring(userLogin).then(status => this.loginStatus=status);
    console.info(this.loginStatus.loginStatus);
  }

}
