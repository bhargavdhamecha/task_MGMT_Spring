import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  jwtHelper: JwtHelperService = new JwtHelperService();
  constructor(){  }

  isAuthenticated(): boolean {
    const cookies: string =  document.cookie;
    const token = cookies.split('=');
    if(!(cookies.length) || !(token[1].length)){
      return false;
    }
    return !this.jwtHelper.isTokenExpired(token[1]); 
  }
}
