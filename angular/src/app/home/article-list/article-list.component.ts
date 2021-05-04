import {LoadingService} from '@app/shared/loading/loading.service';
import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Tag} from '@app/shared/model/tag.model';
import {TagsState} from '@app/shared/state/tags.state';
import {Select} from '@ngxs/store';
import {ArticlesService} from '@app/shared/service/articles.service';

interface State {
  page: number;
  pageSize: number;
  searchTerm: string;
}

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss'],
  providers: [ArticlesService]
})
export class ArticleListComponent implements OnInit {

  @Select(TagsState.selectedTags)
  selectedTags$: Observable<Tag[]>;

  public isMenuCollapsed = true;

  public state: State = {
    page: 1,
    pageSize: 4,
    searchTerm: ''
  };

  constructor(public articleService: ArticlesService,
              public loadingService: LoadingService) {
  }

  ngOnInit(): void {
    this.selectedTags$.subscribe(tags => {
      this.articleService.searchArticles(tags);
    });
  }

  toggleCollapsed(): void {
    console.log("toggle collapsed");
    this.isMenuCollapsed = !this.isMenuCollapsed;
  }
}
