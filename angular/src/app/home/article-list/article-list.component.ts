import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Article} from '@app/shared/model/article.model';
import {ArticleService} from '@app/home/article.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css'],
  providers: [ArticleService]
})
export class ArticleListComponent implements OnInit {

  articles$: Observable<Article[]>;
  total$: Observable<number>;

  constructor(public service: ArticleService) {
    this.articles$ = service.articles$;
    this.total$ = service.total$;
  }

  ngOnInit(): void {
  }

}
