import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../shared/auth-service/auth.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent {

  constructor( private _router:Router, private _auth:AuthService){}

  ngOnInit(){
    if(this._auth.isAuthenticated()){
      this._router.navigate(['home']);
    }
  }

  btnHandler(isLogin: boolean){
    this._router.navigate(['/login', {hasEnableSignup: !isLogin}])
  }
}
