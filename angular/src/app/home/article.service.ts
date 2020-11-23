import {Injectable} from '@angular/core';
import {Article} from '@app/shared/model/article.model';
import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
import {debounceTime, delay, filter, map, skip, switchMap, take, tap, toArray} from 'rxjs/operators';
import {ARTICLES} from '@app/home/articles';
import {ApiService} from '@app/shared/service/api.service';

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
  return article.url.toLowerCase().includes(term.toLowerCase())
  || article.title.toLowerCase().includes(term.toLowerCase())
  || article.preamble?.toLowerCase().includes(term.toLowerCase());
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

  private _state: State = {
    page: 1,
    pageSize: 4,
    searchTerm: '',
  };

  constructor(private apiService: ApiService) {
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

  private _search(): Observable<SearchResult> {
    const {pageSize, page, searchTerm} = this._state;

    let total = 0;
    return this.apiService.get('/articles')
    .pipe(
      tap(next => ++total),
      // filtering
      filter(article => matches(article as Article, searchTerm)),
      // pagination
      skip((page - 1) * pageSize),
      take((page - 1) * pageSize + pageSize),
      toArray(),
      map((a) => {
          return {articles: a, total: total};
        })
    );
  }
}
