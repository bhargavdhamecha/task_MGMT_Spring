import { HttpClient, HttpErrorResponse, } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { MessageService } from 'primeng/api';
import { Observable, catchError, throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class UtilService {

  constructor(private _http: HttpClient, private _msgService: MessageService ) { }

  setCustomHandler: boolean  = false;


  public handleRequest<T>(
    method: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH',
    url: string = environment.apiUrl,
    options?: {
      headers?: { [key: string]: string };
      body?: any;
      withCredentials?:boolean
    }
  ): Observable<T> {
    url = environment.apiUrl + url;
    options = {
      ...options,
      withCredentials: true // Set withCredentials to true by default
    };
    return this._http.request<T>(method, url, options);
  }

  // private handleCommonError(error: HttpErrorResponse): Observable<never> {
    // if(!this.setCustomHandler){
      // this.disaplyErrorMessage("Technical error!!!");
      // if(error.error && !error.error.title){
      //   console.error(error)
      //   return throwError(() => new Error('An unexpected error occurred.', {cause: error}));
      // }
      // return error;
    // }
  // }

  // global toast message display
  disaplyErrorMessage(message: string) {
    this._msgService.add({ severity: 'error', summary: 'Error', detail: message });
  }

  displaySuccessMessage(message: string): void{
    this._msgService.add({ severity: 'success', summary: 'Success', detail: message });
  }

  displayWarnMessage(message: string): void{
    this._msgService.add({ severity: 'warn', summary: 'Warning', detail: message });
  }

  displayInfoMessage(message: string): void{
    this._msgService.add({ severity: 'info', summary: 'Info', detail: message });
  }

  getDomainNameFromEmail(email:string): string{
    return email.split("@")[1];
  }
}
