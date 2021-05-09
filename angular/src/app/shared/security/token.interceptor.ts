import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenState } from '@app/shared/state/token.state';
import { Store } from '@ngxs/store';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  private token: string;
  
  constructor(private store: Store) {
    this.store.select(TokenState.token)
      .subscribe(token => this.token = token);
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (isConfigUrl(request)) {
      console.info("skip not api requests");
    }

    // add auth header with jwt if user is logged in and request is to api url
    else {
      if (this.token) {
        const cloned = request.clone({
          setHeaders: {
            Authorization: `Bearer ${this.token}`
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


