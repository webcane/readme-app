import {Injectable} from '@angular/core';
import {Article} from '@app/shared/model/article.model';
import {BehaviorSubject, from, Observable, of, Subject} from 'rxjs';
import {catchError, debounceTime, delay, filter, map, skip, switchMap, take, tap, toArray} from 'rxjs/operators';
import {ApiService} from '@app/shared/service/api.service';
import {HttpClient} from '@angular/common/http';

interface SearchResult {
  articles: Article[];
  total: number;
}

interface State {
  page: number;
  pageSize: number;
  searchTerm: string;
  // sortColumn: SortColumn;
  // sortDirection: SortDirection;
}

// , pipe: PipeTransform
function matches(article: Article, term: string) {
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

  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _articles$ = new BehaviorSubject<Article[]>([]);
  private _total$ = new BehaviorSubject<number>(0);

  private _allArticles$ = new BehaviorSubject<Article>(null);

  private _state: State = {
    page: 1,
    pageSize: 4,
    searchTerm: '',
  };

  constructor(private http: HttpClient,
              private apiService: ApiService) {
    this._search$.pipe(
      tap(() => this._loading$.next(true)),
      debounceTime(200),
      switchMap(() => this._search()),
      delay(200),
      tap(() => this._loading$.next(false))
    ).subscribe(result => {
      console.log(result);
      this._articles$.next(result.articles);
      this._total$.next(result.total);
    });

    this._search$.next();
  }

  get articles$() {
    return this._articles$.asObservable();
  }

  get total$() {
    return this._total$.asObservable();
  }

  get loading$() {
    return this._loading$.asObservable();
  }

  get page() {
    return this._state.page;
  }

  get pageSize() {
    return this._state.pageSize;
  }

  get searchTerm() {
    return this._state.searchTerm;
  }

  set page(page: number) {
    this._set({page});
  }

  set pageSize(pageSize: number) {
    this._set({pageSize});
  }

  set searchTerm(searchTerm: string) {
    this._set({searchTerm});
  }

  private _set(patch: Partial<State>) {
    Object.assign(this._state, patch);
    this._search$.next();
  }

  private getArticles(): Observable<Article[]> {
    return this.apiService.get<Article[]>('/articles')
      .pipe(
        tap(_ => this.log('fetched articles')),
        catchError(this.handleError<Article[]>('getArticles', []))
      );
  }

  private _search(): Observable<SearchResult> {
    const {pageSize, page, searchTerm} = this._state;
    let total = 0;

    this.getArticles()
      .subscribe(items => {
        total = items.length;
        if (total > 0) {
          from(items).subscribe(
            t => {
              this._allArticles$.next(t);
            }
          );
        }
      });

    return this._allArticles$
      .pipe(
        // filtering
        filter(article => matches(article, searchTerm)),
        // pagination
        skip((page - 1) * pageSize),
        take((page - 1) * pageSize + pageSize),
        toArray(),
        map((a) => {
          return {articles: a, total: total} as SearchResult;
        })
      );

  }

  private handleError<T>(operation = 'operation', result?: T) {
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
