import {Injectable} from '@angular/core';
import {User} from '@app/shared/security/user';
import {BehaviorSubject, Observable} from 'rxjs';
import {distinctUntilChanged} from 'rxjs/operators';
import {ApiService} from '@app/shared/service/api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUser$ = new BehaviorSubject<User>({} as User);
  public currentUser = this.currentUser$.asObservable().pipe(distinctUntilChanged());

  constructor(private api: ApiService) {
  }

  public getCurrentUser(): Observable<User> {
    this.api.get('/user/me').subscribe(
      (usr) => {
        console.log(usr);
        this.currentUser$.next(usr);
      }, (error) => {
        console.log(error);
      }
    );
    return this.currentUser;
  }
}
