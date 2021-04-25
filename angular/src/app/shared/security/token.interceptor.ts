import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenService} from '@app/shared/security/token.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (isConfigUrl(request)) {
      console.info("skip for config");
    }
    else {
      // add auth header with jwt if user is logged in and request is to api url
      const token = this.tokenService.getToken();

      if (token) {
        const cloned = request.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`
          }
        });

        return next.handle(cloned);
      } else {
        console.warn("No access token set.");
      }
    }

    return next.handle(request);
  }
}

function isConfigUrl(request: HttpRequest<any>): boolean {
  return request.url.startsWith('assets/config');
}


