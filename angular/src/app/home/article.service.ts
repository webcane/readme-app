import { Injectable } from '@angular/core';
import { Article } from '@app/shared/model/article.model';
import { Tag } from '@app/shared/model/tag.model';
import { ApiService } from '@app/shared/service/api.service';
import { BehaviorSubject, from, Observable, of, Subject } from 'rxjs';
import { catchError, debounceTime, delay, map, skip, switchMap, take, tap, toArray } from 'rxjs/operators';

interface SearchResult {
  articles: Article[];
  total: number;
}

interface State {
  page: number;
  pageSize: number;
  searchTerm: string;
  tags: Tag[];
}

// , pipe: PipeTransform
function matches(article: Article, term: string): boolean {
  if (article != null && term != null) {
    return article.url.toLowerCase().includes(term.toLowerCase())
      || article.title.toLowerCase().includes(term.toLowerCase())
      || article.preamble?.toLowerCase().includes(term.toLowerCase());
  }
  return false;
  // || pipe.transform(country.area).includes(term)
  // || pipe.transform(country.population).includes(term);
}

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private subLoading$ = new BehaviorSubject<boolean>(true);
  private subSearch$ = new Subject<void>();
  private subArticles$ = new BehaviorSubject<Article[]>([]);
  private subTotal$ = new BehaviorSubject<number>(0);

  private subAllArticles$ = new BehaviorSubject<Article>(null);

  private state: State = {
    page: 1,
    pageSize: 4,
    searchTerm: '',
    tags: []
  };

  constructor(private apiService: ApiService) {
    this.subSearch$.pipe(
      tap(() => this.subLoading$.next(true)),
      debounceTime(200),
      switchMap(() => this.search()),
      delay(200),
      tap(() => this.subLoading$.next(false))
    ).subscribe(result => {
      console.log(result);
      this.subArticles$.next(result.articles);
      this.subTotal$.next(result.total);
    });

    this.subSearch$.next();
  }

  get articles$(): Observable<Article[]> {
    return this.subArticles$.asObservable();
  }

  get total$(): Observable<number> {
    return this.subTotal$.asObservable();
  }

  get loading$(): Observable<boolean> {
    return this.subLoading$.asObservable();
  }

  get page(): number {
    return this.state.page;
  }

  get pageSize(): number {
    return this.state.pageSize;
  }

  get searchTerm(): string {
    return this.state.searchTerm;
  }

  get tags(): Tag[] {
    return this.state.tags;
  }

    // tslint:disable-next-line:adjacent-overload-signatures
  set page(page: number) {
    this.setState({page});
  }

    // tslint:disable-next-line:adjacent-overload-signatures
  set pageSize(pageSize: number) {
    this.setState({pageSize});
  }

  // tslint:disable-next-line:adjacent-overload-signatures
  set searchTerm(searchTerm: string) {
    this.setState({searchTerm});
  }

    // tslint:disable-next-line:adjacent-overload-signatures
  set tags(tags: Tag[]) {
    this.setState({tags});
  }

  private setState(patch: Partial<State>): void {
    Object.assign(this.state, patch);
    this.subSearch$.next();
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

  private search(): Observable<SearchResult> {
    const {pageSize, page, tags} = this.state;
    let total = 0;

    this.getArticles(tags)
      .subscribe(items => {
        total = items.length;
        console.log(JSON.stringify(items));
        from(items)
          .subscribe(t => {
            this.subAllArticles$.next(t);
          });
      });

    return this.subAllArticles$
      .pipe(
        // filtering
        // filter(article => matches(article, searchTerm)),
        // pagination
        skip((page - 1) * pageSize),
        take((page - 1) * pageSize + pageSize),
        toArray(),
        map((a) => {
          return {articles: a, total} as SearchResult;
        })
      );

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
