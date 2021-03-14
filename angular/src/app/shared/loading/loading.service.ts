import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  private loading = new BehaviorSubject<boolean>(true);

  constructor() { }

  get loading$(): Observable<boolean> {
    return this.loading.asObservable();
  }

  start(): void {
    this.loading.next(true);
  }

  stop(): void {
    this.loading.next(false);
  }

}
