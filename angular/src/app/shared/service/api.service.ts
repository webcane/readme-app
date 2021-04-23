import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';

import {catchError} from 'rxjs/operators';
import { EnvConfigLoaderService } from '@app/shared/config/env-config-loader.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService implements OnInit {

  private apiUrl: string;

  constructor(private http: HttpClient, 
    private configService: EnvConfigLoaderService) { }

  ngOnInit(): void {
    this.configService.config$.subscribe(config => {
      this.apiUrl = config.apiUrl;
    });
  }

  get<T = unknown>(path: string, params: HttpParams = new HttpParams()): Observable<T> {
    return this.http.get<T>(`${this.apiUrl}${path}`, { params })
      .pipe(catchError(this.formatErrors));
  }

  put(path: string, body: Object = {}): Observable<any> {
    return this.http.put(
      `${this.apiUrl}${path}`,
      JSON.stringify(body)
    ).pipe(catchError(this.formatErrors));
  }

  post<T = unknown>(path: string, body: Object = {}): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('content-type', 'application/json');
    return this.http.post<T>(
      `${this.apiUrl}${path}`,
      JSON.stringify(body),
      {headers}
    ).pipe(catchError(this.formatErrors));
  }

  delete(path): Observable<any> {
    return this.http.delete(
      `${this.apiUrl}${path}`
    ).pipe(catchError(this.formatErrors));
  }

  private formatErrors(error: any) {
    return  throwError(error.error);
  }
}
