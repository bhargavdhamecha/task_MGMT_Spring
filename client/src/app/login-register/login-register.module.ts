import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginRegisterRoutingModule } from './login-register-routing.module';
import { LoginComponent } from './login/login.component';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    SharedModule,
    LoginRegisterRoutingModule
  ]
})
export class LoginRegisterModule { }
