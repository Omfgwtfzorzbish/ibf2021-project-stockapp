import { Injectable } from "@angular/core";

@Injectable()
export class TokenStorageService{

  constructor(){}

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string, username:string): void {
    window.sessionStorage.removeItem('token');
    window.sessionStorage.setItem('token', token);
    window.sessionStorage.removeItem('username');
    window.sessionStorage.setItem('username', username);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem('token');
  }

  public getUser(): string | null {
    return window.sessionStorage.getItem('username');
  }
}
