import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';

export default class Utils {

  static handleError<T>(operation = 'operation', result?: T): Observable<T> | any {
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

  static log(message: string): void {
    // this.messageService.add(`HeroService: ${message}`);
    console.log(message);
  }

  static reloadUrl(router: Router, url: string = '/') {
    router.routeReuseStrategy.shouldReuseRoute = () => false;
    router.onSameUrlNavigation = 'reload';
    router.navigate([url]);
}
}
