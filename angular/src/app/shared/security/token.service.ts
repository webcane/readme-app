import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  TOKEN_KEY: string = 'token';

  constructor() { }

  public setToken(token: string): void {
    if (token != null) {
      localStorage.setItem(this.TOKEN_KEY, token);
      // console.warn('TOKEN: %s', token);
    }
  }

  public getToken(): string {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  public removeToken(): void {
    // remove access token from local storage to log out
    localStorage.removeItem(this.TOKEN_KEY);
  }
}
