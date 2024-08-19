import { Component } from '@angular/core';
import { UiConfigService } from './services/ui-config/ui-config.service';
import { LayoutService } from './shared/services/layout/layout.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'AgileFlow';

  constructor(private _uiconf: UiConfigService,
    private layoutService: LayoutService
  ) { }

  set theme(val: string) {
    this.layoutService.config.update((config) => ({
      ...config,
      theme: val,
    }));
  }
  get theme(): string {
    return this.layoutService.config().theme;
  }

  set colorScheme(val: string) {
    this.layoutService.config.update((config) => ({
      ...config,
      colorScheme: val,
    }));
  }
  get colorScheme(): string {
    return this.layoutService.config().colorScheme;
  }

  ngOnInit() {
    const darkModeMediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
    if (darkModeMediaQuery.matches) {
      this.changeTheme("lara-dark-blue", "dark");
    }
    else {
      this.changeTheme("lara-light-blue", "light");
    }
  }

  changeTheme(theme: string, colorScheme: string) {
    this.theme = theme;
    this.colorScheme = colorScheme;
  }
}
