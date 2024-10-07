import { HttpEvent, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CONSTANT } from 'src/app/api.constant';
import { UtilService } from 'src/app/shared/services/util/util.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private _util:UtilService) { }

  public checkUserName(data: object): Observable<HttpResponse<any>>{
    return this._util.handleRequest("POST", API_CONSTANT.CHECKUSERNAME, {body: data, withCredentials: false});
  }

  public login(data: object): Observable<HttpResponse<any>>{
    // return this._http.post(environment.apiUrl+API_CONSTANT.LOGIN, data, {withCredentials: true, observe: 'events'});
    return this._util.handleRequest("POST", API_CONSTANT.LOGIN, {body: data});
  }

  public signup(data: object): Observable<HttpResponse<any>>{
    return this._util.handleRequest('POST', API_CONSTANT.SIGNUP, {body: data});
  }

  dash(){
    return this._util.handleRequest('POST', API_CONSTANT.DASH);
  }

  public logout(){
    return this._util.handleRequest('POST', API_CONSTANT.LOGOUT);
  }

  public createOrg(data: object): Observable<HttpResponse<any>>{
    return this._util.handleRequest('POST', API_CONSTANT.CREATE_ORGANIZATION, {body: data})
  }
  
}
