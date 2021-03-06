import { Component, OnInit } from '@angular/core';
import { EnvConfigLoaderService } from '@app/shared/config/env-config-loader.service';
import { AuthService } from '@app/shared/security/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  activeId = 1;

  public authProvider: string;
  public GITHUB_AUTH_URL: string;

  constructor(public authService: AuthService, 
    private configService: EnvConfigLoaderService) {
  }

  ngOnInit(): void {
    this.GITHUB_AUTH_URL = this.configService.getConfig('githubAuthUrl');
    this.authProvider = this.configService.getConfig('authProvider');
  }

  logout() {
    this.authService.logout();
  }
}
