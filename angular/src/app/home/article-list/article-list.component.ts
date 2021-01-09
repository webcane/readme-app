import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Article} from '@app/shared/model/article.model';
import {ArticleService} from '@app/home/article.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss'],
  providers: [ArticleService]
})
export class ArticleListComponent implements OnInit {

  searchTerm: string;
  articles$: Observable<Article[]>;
  total$: Observable<number>;

  constructor(public articleService: ArticleService) {
    this.articles$ = articleService.articles$;
    this.total$ = articleService.total$;
  }

  ngOnInit(): void {
  }

}
