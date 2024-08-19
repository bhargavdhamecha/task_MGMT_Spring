import { Injectable } from '@angular/core';
import { UtilService } from '../shared/services/util/util.service';
import { API_CONSTANT } from '../api.constant';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private _util:UtilService) { }

  createNewProjct(data: any){
    return this._util.handleRequest('POST', API_CONSTANT.CREATE_PROJECT, {body: data});
  }

  getAllProjects(data: any){
    return this._util.handleRequest('POST', API_CONSTANT.GET_ALL_PROJECTS, {body: data})
  }
}
