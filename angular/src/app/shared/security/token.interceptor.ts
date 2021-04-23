import {Injectable, OnInit} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenService} from '@app/shared/security/token.service';
import { EnvConfigLoaderService } from '@app/shared/config/env-config-loader.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor, OnInit {

  private apiUrl: string;

  constructor(private tokenService: TokenService, 
    private configService: EnvConfigLoaderService) {
  }

  ngOnInit(): void {
    this.configService.config$.subscribe(config => {
      this.apiUrl = config.apiUrl;
    });
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add auth header with jwt if user is logged in and request is to api url
    const token = this.tokenService.getToken();

    if (token && isApiUrl(request)) {
      const cloned = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });

      return next.handle(cloned);
    } else {
      console.warn("No access token set.");
    }

    return next.handle(request);
  }
}
function isApiUrl(request: HttpRequest<any>): boolean {
  return request.url.startsWith(this.apiUrl);
}

