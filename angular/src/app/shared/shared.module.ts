import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TokenService} from '@app/shared/security/token.service';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {TokenInterceptor} from '@app/shared/security/token.interceptor';
import {HttpClientModule} from '@angular/common/http';
import {NgxsModule} from '@ngxs/store';
import {environment} from '@environments/environment';
import {TagsState} from '@app/shared/state/tags.state';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule,
    NgxsModule.forRoot([TagsState],
      { developmentMode: !environment.production })
  ],
  providers: [
    TokenService,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true}
  ],
})
export class SharedModule {
}
