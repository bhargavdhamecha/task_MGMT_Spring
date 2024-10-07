
import { Component, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { LoginService } from 'src/app/services/login-service/login.service';
import { AuthService } from 'src/app/shared/auth-service/auth.service';
import { CommonModalComponent } from 'src/app/shared/components/common-modal/common-modal.component';
import { ModalConfig } from 'src/app/shared/components/common-modal/modalConfig';
import { UtilService } from 'src/app/shared/services/util/util.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent{
  private hasAccount!: boolean;

  private orgDetail!: string;
  
  constructor(
    private _util: UtilService,
    private _loginService: LoginService,
    private _fb: FormBuilder,
    private _route: Router,
    private _auth: AuthService,
    private _activteRoute: ActivatedRoute
  ) { 
    this.modalConfig.onCloseFun = ()=> this.createOrg();
  }
  /**
   * view child
   */
  @ViewChild('modal') private modalComponent!: CommonModalComponent;
  @ViewChild('content') private content!: TemplateRef<any>;
  public modalConfig: ModalConfig = {
    modalHeader: "Alert",
    closeButtonLabel: "ok",
    dismissButtonLabel: "cancel",
    closable: true,
    // onClose(): ()=> this.createOrg()
  };
  valCheck: string[] = ['remember'];
  hasEnableSignup!: boolean;
  isXhrInProgress!: boolean;
  isXhrCompleted!: boolean;
  isUserNameAvailable!: boolean;

  loginForm!: FormGroup;
  signupForm!: FormGroup;

  ngOnInit() {
    this._activteRoute.params.subscribe({
      next: (res: any)=>{
        this.hasEnableSignup = JSON.parse(Object.keys(res).length && res.hasEnableSignup);
      },
      error: (err)=>{

      }
    }) // Access the flag parameter

    this.loginForm = this._fb.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      rememberMe: new FormControl(false),
    });

    this.signupForm = this._fb.group({
      userName: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      userEmail: new FormControl('', Validators.email),
      confirm_pass: new FormControl('', Validators.required),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('')
    });

    this.signupForm.get('userName')?.valueChanges.pipe(
      debounceTime(700),
      distinctUntilChanged()
    ).subscribe((usernameVal) => {
      this.isUserNameAvailable = false;
      this.isXhrInProgress = true
      this.inputhandler(usernameVal);
    });

    // this.modalConfig.onClose();
  }

  public inputhandler(username: any) {
    let userNameVal = { "username": username };
    this._loginService.checkUserName({username}).subscribe({
      next: (res: any) => {
        this.isXhrInProgress = false;
        this.isXhrCompleted = true;
        if (res.body.canCreateUser) {
          this.isUserNameAvailable = true;
        }
      },
      error: (er: any) => {
        this.isXhrInProgress = false;
        this.isUserNameAvailable = false;
        console.log("API ERROR: ", er);
      }
    })
  }

  public btnClicked() {
    if (this.hasEnableSignup) {
      this.orgDetail = this._util.getDomainNameFromEmail(this.signupForm.controls['userEmail'].value);
      this._loginService.signup(this.signupForm.value).subscribe({
        next: (res: any) => {
          if(res.statusCode == 100){
            this.displayCreateOrg();
          }
          else if (res.statusCode == 201 || res.statusCode == 200) {
            this.signupForm.reset();
            this._util.displaySuccessMessage(res.message);
            setTimeout(() => {
              this._route.navigate(['home']);
            }, 500);
          }
          else{
            this._util.disaplyErrorMessage("something went wrong!");
          }
        },
        error: (e) => {
          console.log(e);
          this._util.disaplyErrorMessage(e.msg);

        }
      })

    }
    else {
      this._util.setCustomHandler = true;
      this._loginService.login(this.loginForm.value).subscribe({
        next: (res: any) => {
          if (res.statusCode == 200) {            
            this._util.displaySuccessMessage(res.message);
            this.navigateToHome();
          }
          this.loginForm.reset();
          this._util.setCustomHandler = false;
        },
        error: (er) => {
          this._util.disaplyErrorMessage(er.error.title);
          this._util.setCustomHandler = false;
        },  
      })
    }

  }

  displayCreateOrg(){
    // this.modalConfig.modalTitle="confirm";
    // this.modalConfig.content= this.content;
      this.modalComponent.show();
    // if(this.modal){
    //   this.modal.header = "Register Organization";
    //   this.modal.content = "<p>This Organization does not exist in Records, Would you like to register the organization to continue sign up?</p>"
    //   this.modal.show();
    // }
    // this._dialogService.open(CommonModalComponent, {
    //   header: 'Register Organization',
    //   width: '50%',
    //   data: {
    //     title:  "create orgazation.",
    //     message: 'This Organization does not exist in Records, Would you like to register the organization to continue sign up?'
    //   }
    // });
  }

  createOrg(){
    this._loginService.createOrg(this.signupForm.value).subscribe({
      next: (res: any)=>{
        if(res.statusCode == 201) {
          this._util.displaySuccessMessage("Registered successfully !");
          this.navigateToHome();
        }
      },
      error:(e: any)=>{
        this._util.disaplyErrorMessage(e.message || "something went wrong!")
      }
    });
  }

  navigateToHome(){
    setTimeout(() => {
      this._route.navigate(['home']);
    }, 500);
  }
}



