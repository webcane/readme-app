import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Article } from '@app/shared/model/article.model';
import { Tag } from '@app/shared/model/tag.model';
import { ApiService } from '@app/shared/service/api.service';
import { BehaviorSubject, from, Observable, of, Subject } from 'rxjs';
import { catchError, debounceTime, delay, map, skip, switchMap, take, tap, toArray } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {

  public articles$ = new BehaviorSubject<Article[]>([]);
  public total$ = new BehaviorSubject<number>(0);

  constructor(private apiService: ApiService) {
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
        tap(next => this.log('fetched articles' + (tagsLine ? ' by tags: ' + tagsLine : '')),
          error => this.log('there are no articles' + (tagsLine ? ' by tags: ' + tagsLine : ''))),
        catchError(this.handleError<Article[]>('getArticles', []))
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

  private handleError<T>(operation = 'operation', result?: T): Observable<T> | any {
    return (error: any): Observable<T> => {

      if (error != null) {
        // TODO: send the error to remote logging infrastructure
        console.error(error); // log to console instead

        // TODO: better job of transforming error for user consumption
        this.log('${operation} failed: ${error.message}');
      }
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  private log(message: string): void {
    // this.messageService.add(`HeroService: ${message}`);
    console.log(message);
  }
}
