import {Injectable} from '@angular/core';
import {Tag} from '@app/shared/model/tag.model';
import {BehaviorSubject, Observable} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {ApiService} from '@app/shared/service/api.service';
import Utils from '@app/shared/service/utils';

@Injectable({
  providedIn: 'root'
})
export class TagsService {

  public tags$ = new BehaviorSubject<Tag[]>([]);
  public total$ = new BehaviorSubject<number>(0);

  constructor(private apiService: ApiService) {
  }

  public searchTags(): void {
    this.search();
  }

  private search(): void {
    this.getTags()
      .subscribe(items => {
        console.log(JSON.stringify(items));
        this.tags$.next(items);
        this.total$.next(items.length);
      });
  }

  private getTags(): Observable<Tag[]> {
    const url = '/api/tags';

    return this.apiService.get<Tag[]>(url)
      .pipe(
        tap(next => Utils.log('fetched tags'),
          error => Utils.log('there are no tags')),
        catchError(Utils.handleError<Tag[]>('getTags', []))
      );
  }

}
