import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TokenService} from '@app/shared/security/token.service';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {TokenInterceptor} from '@app/shared/security/token.interceptor';
import {HttpClientModule} from '@angular/common/http';
import {NgxsModule} from '@ngxs/store';
import {environment} from '@environments/environment';
import {TagsState} from '@app/shared/state/tags.state';
import { LoadingInterceptor } from './loading/loading.interceptor';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule,
    NgxsModule.forRoot([TagsState], { developmentMode: !environment.production }),
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    TokenService,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true}
  ],
})
export class SharedModule {
}
