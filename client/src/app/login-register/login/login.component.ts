import { HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { LoginService } from 'src/app/services/login-service/login.service';
import { AuthService } from 'src/app/shared/auth-service/auth.service';
import { UtilService } from 'src/app/shared/services/util/util.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  private hasAccount!: boolean;

  constructor(
    private _util: UtilService,
    private _loginService: LoginService,
    private _fb: FormBuilder,
    private _route: Router,
    private _auth: AuthService,
    private _activteRoute: ActivatedRoute
  ) { }

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
        this.hasEnableSignup = JSON.parse(res?.hasEnableSignup);
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
      lastName: new FormControl('', Validators.required)
    });

    this.signupForm.get('userName')?.valueChanges.pipe(
      debounceTime(700),
      distinctUntilChanged()
    ).subscribe((usernameVal) => {
      this.isUserNameAvailable = false;
      this.isXhrInProgress = true
      this.inputhandler(usernameVal);
    });

  }

  inputhandler(username: any) {
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

  btnClicked() {
    if (this.hasEnableSignup) {
      this._loginService.signup(this.signupForm.value).subscribe({
        next: (res: HttpResponse<any>) => {
          if (res.ok) {
            this._util.displaySuccessMessage(res.body.msg);
            setTimeout(() => {
              this._route.navigate(['home']);
            }, 500);
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
            setTimeout(() => {
              this._route.navigate(['home']);
            }, 500);
          }
          this._util.setCustomHandler = false;
        },
        error: (er) => {
          this._util.disaplyErrorMessage("Invalid credentials!");
          this._util.setCustomHandler = false;
        },  
      })
    }

  }
}



