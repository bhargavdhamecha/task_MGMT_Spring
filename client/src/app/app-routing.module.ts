import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { authGuardGuard } from './shared/auth-service/auth-guard.guard';
import { LayoutComponent } from './shared/components/layout/layout.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo:'agileflow' 
  },
  {
    path: 'agileflow',
    component: LandingPageComponent
  },
  {
    path: 'login',
    loadChildren: () => import('./login-register/login-register.module').then(m => m.LoginRegisterModule)
  },

  {
    path: 'home', component: LayoutComponent,
    children: [
      { path: '', loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule) },
    ],
    canActivateChild: [authGuardGuard]
  },
  {
    path: 'projects', component: LayoutComponent,
    children: [
      { path: '', loadChildren: () => import('./projects/projects.module').then(m => m.ProjectsModule) },
    ],
    canActivateChild: [authGuardGuard]
  },
  {
    path: 'unauthorized',
    loadComponent: ()=>import('./shared/components/unauthorized/unauthorized.component').then(m => m.UnauthorizedComponent)
  },
  {
    path: '**',
    loadComponent: ()=>import('./shared/components/not-found/not-found.component').then(m=>m.NotFoundComponent)
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
