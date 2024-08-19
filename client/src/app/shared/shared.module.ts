import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgPrimeModule } from './modules/ng-prime/ng-prime.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TopBarComponent } from './components/top-bar/top-bar.component';
import { LayoutComponent } from './components/layout/layout.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { AppMenuComponent } from './components/menu/menu.component';
import { AppMenuitemComponent } from './components/menu/app.menu-item.component';

const SHARED_MODULES = [
  CommonModule,
  NgPrimeModule,
  FormsModule,
  ReactiveFormsModule,
  FormsModule,
];

@NgModule({
  providers: [MessageService, ConfirmationService],
  declarations: [ 
    AppMenuitemComponent,TopBarComponent, LayoutComponent, SidebarComponent, AppMenuComponent
  ],
  imports: [ ...SHARED_MODULES],
  exports: [ ...SHARED_MODULES]
})
export class SharedModule { }
