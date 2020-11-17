import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {HomeAuthResolver} from '@app/home/home-auth-resolver.service';
import {BannerComponent} from './banner/banner.component';
import {ArticleListComponent} from './article-list/article-list.component';
import {TagListComponent} from './tag-list/tag-list.component';
import {FormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    HomeComponent,
    BannerComponent,
    ArticleListComponent,
    TagListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    NgbModule
  ],
  providers: [
    HomeAuthResolver
  ]
})
export class HomeModule {
}
