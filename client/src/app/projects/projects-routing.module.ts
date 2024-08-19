import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProjectMainComponentComponent } from './project-main-component/project-main-component.component';

const routes: Routes = [
  {path: '', component: ProjectMainComponentComponent, pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProjectsRoutingModule { }
