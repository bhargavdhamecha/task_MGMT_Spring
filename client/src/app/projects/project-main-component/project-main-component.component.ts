import { AfterContentInit, Component, ContentChild, TemplateRef, ViewChild, ViewContainerRef } from "@angular/core";
import { CreateProjectComponent } from "../create-project/create-project.component";
import { ProjectService } from "../project.service";
import { HttpErrorResponse } from "@angular/common/http";
import { delay, Observable, of } from "rxjs";
import { ListingComponent } from "src/app/shared/components/listing/listing.component";

@Component({
  selector: 'app-project-main-component',
  templateUrl: './project-main-component.component.html',
  styleUrl: './project-main-component.component.scss',
})

export class ProjectMainComponentComponent {

  @ViewChild('listing') listingComponet!: ListingComponent;
  @ContentChild(CreateProjectComponent) projectModal!: CreateProjectComponent
  // @ViewChild(CreateProjectComponent) modal!: CreateProjectComponent;
  // @ViewChild('modalContainer', { read: ViewContainerRef }) modalContainer!: ViewContainerRef;
  projectsData: any;
  data$!: Observable<any>;

  constructor(private _viewRef: ViewContainerRef, private _project:ProjectService) {}

  ngOnInit(){
    // this.data$ = of('Async Data').pipe(delay(2000));
    // console.log("listing component", this.data$);
  }

  showCreateNewProjectModal(): void{
    const modalFactory = this._viewRef.createComponent(CreateProjectComponent);
    modalFactory.instance.showDialog();
    const since = new Date().toISOString();
    modalFactory.instance.ok.subscribe((res: boolean)=>{
      if(res){
        modalFactory.destroy();
        this.listingComponet.refreshRecords(since);
      }
    })
  }

  loadAllProjects(recordStartsFrom:number, sortField: string, sortOrder: number, batchSize: number, partialRefresh:boolean, since:string){
    return this._project.getAllProjects({recordStartsFrom, sortField, sortOrder, batchSize, partialRefresh, since});
  }
}
