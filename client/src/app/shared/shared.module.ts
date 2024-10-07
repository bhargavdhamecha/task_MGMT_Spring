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
import { CommonModalComponent } from './components/common-modal/common-modal.component';
import { DialogService } from 'primeng/dynamicdialog';

const SHARED_MODULES = [
  CommonModule,
  NgPrimeModule,
  FormsModule,
  ReactiveFormsModule,
  FormsModule,
];

@NgModule({
  providers: [MessageService, ConfirmationService, DialogService],
  declarations: [ 
    AppMenuitemComponent,TopBarComponent, LayoutComponent, SidebarComponent, AppMenuComponent, CommonModalComponent
  ],
  imports: [ ...SHARED_MODULES],
  exports: [ ...SHARED_MODULES, CommonModalComponent]
})
export class SharedModule { }
