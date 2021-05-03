import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { TokenCancelAction, TokenSetAction, TokenState } from '@app/shared/state/token.state';
import { Store } from '@ngxs/store';
import Utils from '../service/utils';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // if auth token is present
  private isAuth: boolean = false;

  constructor(private router: Router,
    private store: Store) {
    this.store.select(TokenState.token)
      // token is defined 
      .subscribe(token => this.isAuth = !!token);
  }

  logout() {
    this.store.dispatch(new TokenCancelAction());
    console.log("You're safely logged out!");
    
    Utils.reloadUrl(this.router);
  }

  public authenticate(token: string): void {
    console.log('Obtained Access token');
    this.store.dispatch(new TokenSetAction(token));
  }

  public isAuthenticated(): boolean {
    return this.isAuth;
  }
}
