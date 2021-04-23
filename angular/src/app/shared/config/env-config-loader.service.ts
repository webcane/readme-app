import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {shareReplay} from 'rxjs/operators';

interface Configuration {
  authProvider: string;
  apiUrl: string;
  oauth2RedirectUri: string;
  githubAuthUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class EnvConfigLoaderService {

    private readonly CONFIG_URL = 'assets/config/envConfig.json';
    private configuration$: Observable<Configuration>;

  constructor(private http: HttpClient) { }

  public load(): Observable<Configuration> {
     if (!this.configuration$) {
        this.configuration$ = this.http.get<Configuration>(this.CONFIG_URL)
        .pipe(
          shareReplay(1)
        );
     }
     return this.configuration$;
  }

  get config$(): Observable<Configuration> {
    return this.configuration$;
  }

}
