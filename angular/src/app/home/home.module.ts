import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {HomeAuthResolver} from '@app/home/home-auth-resolver.service';
import {HomeRoutingModule} from '@app/home/home-routing.module';
import { BannerComponent } from './banner/banner.component';

@NgModule({
  declarations: [
    HomeComponent,
    BannerComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule
  ],
  providers: [
    HomeAuthResolver
  ]
})
export class HomeModule {
}
