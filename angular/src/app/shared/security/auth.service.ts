import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {TokenService} from '@app/shared/security/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router,
              private tokenService: TokenService) {
  }

  // basic login
  // login(email:string, password:string ) {
  //   return this.api.post<AppUser>('/auth/login', {email, password});
  //   // this is just the HTTP call,
  //   // we still need to handle the reception of the token
  //   // .shareReplay();
  // }

  logout() {
    this.tokenService.removeToken();

    alert("You're safely logged out!");
    this.router.navigate(['/']);
  }


  public authenticate(token: string): void {
    console.log('Obtained Access token');
    this.tokenService.setToken(token);
  }

  public isAuthenticated(): boolean {
    return this.tokenService.getToken() != null;
  }

  public isNotAuthenticated(): boolean {
    return !this.isAuthenticated();
  }
}
