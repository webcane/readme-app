import {Component, OnInit} from '@angular/core';
import {AuthService} from '@app/shared/security/auth.service';

@Component({
  selector: 'app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public isAuthenticated: boolean;

  constructor(public authService: AuthService) {
  }

  ngOnInit(): void {
    this.isAuthenticated = this.authService.isAuthenticated();
  }

}
