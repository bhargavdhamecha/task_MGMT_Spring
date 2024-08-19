import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectMainComponentComponent } from './project-main-component.component';

describe('ProjectMainComponentComponent', () => {
  let component: ProjectMainComponentComponent;
  let fixture: ComponentFixture<ProjectMainComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProjectMainComponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProjectMainComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
