import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EditorComponent} from '@app/editor/editor.component';
import {EditorAuthResolver} from '@app/editor/editor-auth-resolver.service';
import {HomeComponent} from '@app/home/home.component';
import {HomeAuthResolver} from '@app/home/home-auth-resolver.service';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    resolve: {
      isAuthenticated: HomeAuthResolver
    }
  },
  {
    path: 'editor',
    component: EditorComponent,
    resolve: {
      isAuthenticated: EditorAuthResolver
    }
  },
  // otherwise redirect to home
  { path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
