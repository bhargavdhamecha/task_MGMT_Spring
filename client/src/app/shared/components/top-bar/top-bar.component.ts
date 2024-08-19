import { Component, ElementRef, ViewChild } from '@angular/core';
import { LayoutService } from '../../services/layout/layout.service';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { UtilService } from '../../services/util/util.service';
import { LoginService } from 'src/app/services/login-service/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.scss',
})
export class TopBarComponent {

  menuItems: MenuItem[] = [];
  constructor(public layoutService: LayoutService, private _util: UtilService, private _loginService: LoginService, public confirmationService: ConfirmationService, private _route:Router) { }
  items!: MenuItem[];
  
  @ViewChild('menubutton') menuButton!: ElementRef;

  @ViewChild('topbarmenubutton') topbarMenuButton!: ElementRef;

  @ViewChild('topbarmenu') menu!: ElementRef;
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

  isDarkThemeEnabled: boolean = this.colorScheme === 'dark';

  ngOnInit(){
    this.menuItems = [
      {
          label: 'Settings', icon: 'pi pi-cog' 
      },
      {
          separator: true
      },
      {
          label: 'Logout', icon: 'pi pi-sign-out', command: ()=>{this.confirmLogout()}
      },
  ];
  }

  themeHandler(): void{
    if(this.isDarkThemeEnabled){
      this.changeTheme("lara-light-blue", "light");
    }
    else{
      this.changeTheme("lara-dark-blue", "dark");
    }
    this.isDarkThemeEnabled = !this.isDarkThemeEnabled;
  }

  changeTheme(theme: string, colorScheme: string) {
    this.theme = theme;
    this.colorScheme = colorScheme;
  }

  confirmLogout(){
    this.confirmationService.confirm({
      message: 'Are you sure you want to proceed?',
      header: 'Log Out',
      icon: 'pi pi-info-circle',
      acceptIcon:"none",
      rejectIcon:"none",
      rejectButtonStyleClass:"p-button-text",
      accept: () => {
        this.logout();
      },
  });
  }

  logout(){
    this._loginService.logout().subscribe({
      next:(res: any)=>{
        this._util.displaySuccessMessage('logged out successful!');
        setTimeout(()=>{
          this._route.navigate(['login']);
        },500)
      }
    })
  }

}
