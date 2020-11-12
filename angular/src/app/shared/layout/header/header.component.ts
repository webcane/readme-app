import { Component, OnInit } from '@angular/core';
import {AuthenticationService, User} from '@app/security';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  user: User;
  activeId = 1;

  constructor(private authenticationService: AuthenticationService) {
    this.authenticationService.user.subscribe(x => this.user = x);
  }

  ngOnInit(): void {
  }

  logout() {
    this.authenticationService.logout();
  }
}
