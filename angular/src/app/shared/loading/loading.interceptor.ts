import { LoadingService } from './loading.service';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {

  activeRequests = 0;

  constructor(private loadingService: LoadingService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.activeRequests === 0) {
      this.loadingService.start();
    }

    this.activeRequests++;

    return next.handle(request).pipe(
        finalize(() => {
            this.activeRequests--;
            if (this.activeRequests === 0) {
                this.loadingService.stop();
            }
        })
    );
  }
}
