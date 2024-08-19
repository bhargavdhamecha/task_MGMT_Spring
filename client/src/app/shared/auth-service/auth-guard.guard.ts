import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { inject } from '@angular/core';


export const authGuardGuard: CanActivateFn = (route, state) => {
  const router:Router = inject(Router);
  const flag:boolean = inject(AuthService).isAuthenticated();
  return flag || router.navigate(['unauthorized']);
};
