import { Component, OnInit } from '@angular/core';
import { validateBasis } from '@angular/flex-layout';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { candleService } from '../candle-service.service';
import { user,loginStatus,token } from '../Model';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!:FormGroup
  token!:token
  isLoggedIn:boolean = false
  isLogInFailed:string =''
  constructor(private fb:FormBuilder, private candleSvc:candleService,
    private router:Router, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.form=this.fb.group({
      username:this.fb.control('',[Validators.required]),
      //email:this.fb.control(['',Validators.email]),
      password: this.fb.control('',[Validators.required,Validators.minLength(4)])
    })
  }

  processForm(){
   const userLogin =this.form.value as user;
    this.candleSvc.loginInfoToSpring(userLogin).then(token => {this.token=token;
      console.info(this.token.token);
      this.tokenStorage.saveToken(this.token.token);
      //localStorage.setItem('jwttoken',token)
      this.isLoggedIn=true;
      this.router.navigate(['/api/stock/ticklist'])
    }).catch(error => {console.info(error); this.isLogInFailed = "invalid username or password"});

  }

}
