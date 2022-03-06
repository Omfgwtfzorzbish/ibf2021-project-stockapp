import { Component, OnInit } from '@angular/core';
import { validateBasis } from '@angular/flex-layout';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { candleService } from '../candle-service.service';
import { user,loginStatus,token, registerUser } from '../Model';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!:FormGroup; form2!:FormGroup
  token!:token
  isLoggedIn:boolean = false
  showRegister:boolean = false
  isLogInFailed:string =''
  constructor(private fb:FormBuilder, private candleSvc:candleService,
    private router:Router, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.form=this.fb.group({
      username:this.fb.control('',[Validators.required]),
      //email:this.fb.control(['',Validators.email]),
      password: this.fb.control('',[Validators.required,Validators.minLength(4)])
    })
    this.form2=this.fb.group({
      username:this.fb.control('',[Validators.required]),
      email:this.fb.control('',[Validators.email, Validators.required]),
      password: this.fb.control('',[Validators.required,Validators.minLength(4)])
    })
  }

  processForm(){
   const userLogin =this.form.value as user;
    this.candleSvc.loginInfoToSpring(userLogin).then(token => {this.token=token;
      console.info(this.token.token);
      this.tokenStorage.saveToken(this.token.token,userLogin.username);
      //localStorage.setItem('jwttoken',token)
      this.isLoggedIn=true;
      this.router.navigate(['/api/stock/ticklist'])
    }).catch(error => {console.info(error); this.isLogInFailed = "invalid username or password"; this.showRegister=true});
  }

  processRegister(){
    const userRegister = this.form2.value as registerUser;
    this.candleSvc.registerUser(userRegister);
    this.showRegister=false
    //window.location.reload();
  }

  registerButton(){
    this.showRegister=true
  }
  toLogin(){
    this.showRegister=false
  }

}
