import { Injectable } from '@angular/core';
import {Tag} from '@app/shared/model/tag.model';
import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
import {debounceTime, delay, switchMap, tap} from 'rxjs/operators';
import {Article} from '@app/shared/model/article.model';
import {ARTICLES} from '@app/home/articles';
import {TAGS} from '@app/shared/service/tags';

interface SearchResult {
  tags: Tag[];
  total: number;
}

@Injectable({
  providedIn: 'root'
})
export class TagsService {

  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<SearchResult>();
  private _total$ = new BehaviorSubject<number>(0);
  private _tags$ = new BehaviorSubject<Tag[]>([]);

  constructor() {
    this._search$.pipe(
      tap(() => {
        this._loading$.next(true);
        console.log(true);
      }),
      switchMap(() => this._search()),
      tap(() => {
        this._loading$.next(false);
        console.log(false);
      })
    ).subscribe(result => {
      this._tags$.next(result.tags);
      this._total$.next(result.total);
    });

    this._search$.next();
  }

  get loading$() {
    return this._loading$.asObservable();
  }

  get total$() {
    return this._total$.asObservable();
  }

  get tags$() {
    return this._tags$.asObservable();
  }

  private _search(): Observable<SearchResult> {
    let tags = TAGS;
    const total = tags.length;

    // 3. paginate
    return of({tags, total});
  }
}
