import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';

import {HomeModule} from '@app/home/home.module';
import {HeaderComponent} from '@app/shared/layout/header/header.component';
import {EditorModule} from '@app/editor/editor.module';
import {SharedModule} from '@app/shared/shared.module';
import { EnvConfigLoaderService } from '@app/shared/config/env-config-loader.service';

export function initializeApp(configService: EnvConfigLoaderService) {
  return () => configService.load().toPromise();
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    SharedModule,
    HomeModule,
    EditorModule,
    NgbModule
  ],
  providers: [{
    provide: APP_INITIALIZER,
    useFactory: initializeApp,
    deps: [EnvConfigLoaderService],
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
