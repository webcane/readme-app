import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AppComponent} from "@app/app.component";
import {AuthenticationGuard} from "@app/security";

const routes: Routes = [
  {
    path: '',
    component: AppComponent,
   // canActivate: [AuthenticationGuard]
  },

  // {
  //   path: 'admin',
  //   component: AdminComponent,
  //   canActivate: [AuthGuard],
  //   data: { roles: [Role.Admin] }
  // },

  // {
  //   path: 'login',
  //   component: LoginComponent
  // },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
