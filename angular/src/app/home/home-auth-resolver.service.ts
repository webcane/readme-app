import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from '@app/shared/security/auth.service';

@Injectable()
export class HomeAuthResolver implements Resolve<boolean> {

  constructor(
    private router: Router,
    private authService: AuthService
  ) {
  }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    let token = route.queryParams['token'];

    if (token != null) {
      this.authService.authenticate(token);

      // navigate to home to update header links and reload content
      this.router.navigate(['/']);
    }

    return this.authService.isAuthenticated();
  }
}
