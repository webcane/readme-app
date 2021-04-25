import { environment } from '@environments/environment';
import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {map, shareReplay, tap} from 'rxjs/operators';
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

  public load(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      if(environment.production) {
        console.log('Load env configuration file');
        this.http.get<Configuration>(this.CONFIG_URL)
        .pipe(
          shareReplay(1),
          tap(next => Utils.log('Fetched ' + this.CONFIG_URL),
             error => Utils.log('Env config file "' + this.CONFIG_URL + '" is not valid')),
        )
        .subscribe(
          (responseData) => {
            this.config = responseData;
            resolve(true);
          }
        );
      } else {
        console.log('Load environment configuration');
        this.config = {
          authProvider: environment.authProvider,
          apiUrl: environment.apiUrl,
          oauth2RedirectUri: environment.oauth2RedirectUri,
          githubAuthUrl: environment.githubAuthUrl
        };
        resolve(true);
      }
      
    });
  }

  public getConfig(key: any) {
      return this.config[key];
  }
}
