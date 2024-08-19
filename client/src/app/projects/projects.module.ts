import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectsRoutingModule } from './projects-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ListingComponent } from 'src/app/shared/components/listing/listing.component';
import { FormsModule } from '@angular/forms';
import { ProjectMainComponentComponent } from './project-main-component/project-main-component.component';
import { CommonModalComponent } from '../shared/components/common-modal/common-modal.component';
import { CreateProjectComponent } from './create-project/create-project.component';
// import { CommonModalComponent } from '../shared/components/common-modal/common-modal.component';


@NgModule({
  declarations: [ CommonModalComponent,ProjectMainComponentComponent, ListingComponent, CreateProjectComponent],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class ProjectsModule { }
