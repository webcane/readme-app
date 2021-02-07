import {Component, Input, OnInit} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {AuthService} from '@app/shared/security/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Input()
  public isAuthenticated: boolean;

  activeId = 1;

  authProvider = environment.authProvider;
  GITHUB_AUTH_URL = environment.githubAuthUrl;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  logout() {
    this.authService.logout();
  }
}
