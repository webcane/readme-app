import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {shareReplay, tap} from 'rxjs/operators';
import Utils from '../service/utils';
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
    private config: Configuration;

  constructor(private http: HttpClient) { }

  public load(): Observable<Configuration> {
    console.log('Load env configuration file');
    return this.http.get<Configuration>(this.CONFIG_URL)
        .pipe(
          shareReplay(1),
          tap(next => {
              Utils.log('Fetched ' + this.CONFIG_URL);
              this.config = next;
            },
             error => {
              Utils.log('Env config file "' + this.CONFIG_URL + '" is not valid');
            }
          )
        );
  }

  public getConfig(key: any, defaultValue?: string) {
    return this.config[key] || defaultValue;
  }
}
