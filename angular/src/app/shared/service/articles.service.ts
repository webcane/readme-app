import {Injectable} from '@angular/core';
import {Article} from '@app/shared/model/article.model';
import {Tag} from '@app/shared/model/tag.model';
import {ApiService} from '@app/shared/service/api.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import Utils from '@app/shared/service/utils';

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {

  public articles$ = new BehaviorSubject<Article[]>([]);
  public total$ = new BehaviorSubject<number>(0);

  constructor(private apiService: ApiService) {
  }

  public create(article: Article): Observable<any> {
    let url = '/articles';
    return this.apiService.post<Article>(url, article)
    .pipe(
      tap(next => Utils.log('the article were published successfully'),
        error => Utils.log('There is problem during article publishing')
      )
    );
  }

  public searchArticles(tags: Tag[]): void {
    this.search(tags);
  }

  private search(tags: Tag[]): void {
    this.getArticles(tags)
      .subscribe(items => {
        console.log(JSON.stringify(items));
        this.articles$.next(items);
        this.total$.next(items.length);
      });
  }

  private getArticles(tags: Tag[]): Observable<Article[]> {
    const tagsLine = this.getTagLine(tags);
    const url = this.getUrl(tagsLine);

    return this.apiService.get<Article[]>(url)
      .pipe(
        tap(next => Utils.log('fetched articles' + (tagsLine ? ' by tags: ' + tagsLine : '')),
          error => Utils.log('there are no articles' + (tagsLine ? ' by tags: ' + tagsLine : ''))),
        catchError(Utils.handleError<Article[]>('getArticles', []))
      );
  }

  private getTagLine(tags: Tag[]): string {
    if (tags) {
      const tagNames = tags.map(item => {
        return item.value;
      });
      const tagLine = Array.from(tagNames).toString();
      console.log('tagLine: ' + JSON.stringify(tagLine));
      return tagLine;
    }
    return null;
  }

  private getUrl(tagName: string): string {
    let url = '/articles';
    if (tagName) {
      url = url + '/findBy?tags=' + tagName;
    }
    return url;
  }
}
