import { Component, OnInit } from '@angular/core';
import {AuthenticationService, User} from "@app/security";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
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
