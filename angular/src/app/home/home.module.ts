import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {HomeAuthResolver} from '@app/home/home-auth-resolver.service';
import {BannerComponent} from './banner/banner.component';
import {ArticleListComponent} from './article-list/article-list.component';
import {TagListComponent} from './tag-list/tag-list.component';

@NgModule({
  declarations: [
    HomeComponent,
    BannerComponent,
    ArticleListComponent,
    TagListComponent
  ],
  imports: [
    CommonModule
  ],
  providers: [
    HomeAuthResolver
  ]
})
export class HomeModule {
}
