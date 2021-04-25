import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '@app/shared/security/auth.service';
import { EnvConfigLoaderService } from '@app/shared/config/env-config-loader.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  // TODO use state instead of
  @Input()
  public isAuthenticated: boolean;

  activeId = 1;

  public authProvider: string;
  public GITHUB_AUTH_URL: string;

  constructor(private authService: AuthService, 
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
