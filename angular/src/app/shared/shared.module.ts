import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TokenService} from '@app/shared/security/token.service';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {TokenInterceptor} from '@app/shared/security/token.interceptor';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    TokenService,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true}
  ],
})
export class SharedModule {
}
