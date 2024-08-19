import { Component, EventEmitter, OnDestroy, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { noop } from 'rxjs';
import { API_CONSTANT } from 'src/app/api.constant';
import { UtilService } from 'src/app/shared/services/util/util.service';
import { ProjectService } from '../project.service';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrl: './create-project.component.scss'
})
export class CreateProjectComponent{
  public visible: boolean = false;
  projectForm: FormGroup;
  xhr: any;
  @Output() cancel = new EventEmitter<boolean>();
  @Output() ok = new EventEmitter<boolean>();

  constructor(private _fb:FormBuilder, private _projectService:ProjectService, private _util:UtilService){
    this.projectForm = new FormGroup({
      project_title : new FormControl('', Validators.required),
      project_code: new FormControl('', Validators.required),
      project_manager: new FormControl('', Validators.required),
      project_description: new FormControl(''),
      // userid, created-at
    })
  }
  ngOnInit(): void{
    console.log("init");
    
  }
  createProject(){
    console.log(this.projectForm.value);
    this.xhr = true;
    this._projectService.createNewProjct(this.projectForm.value).subscribe({
      next: (res: any)=>{
        if(res.isSuccess){
          this._util.displaySuccessMessage(res.msg);
        }
      },
      error: (err)=>{
        this._util.disaplyErrorMessage(err.message);
        this.xhr = false;
      },
      complete:()=>{
        this.xhr = false
        this.ok.emit(true);
      }
    })
    // setTimeout(()=>{
    //   this.xhr = false;
    //   this.ok.emit(true);
    // },500)
  }

  onCancel(){
    this.visible = false;
  }
  showDialog(){
    this.visible = true;
  }

  ngOnDestroy(): void{
    this.xhr = noop;
    this.ok.unsubscribe();
    this.cancel.unsubscribe();
    console.log("destroyed");
    
  }
}
