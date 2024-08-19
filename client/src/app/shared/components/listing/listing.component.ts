import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { CommonModalComponent } from '../common-modal/common-modal.component';

@Component({
  selector: 'app-listing',
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.scss'
})
export class ListingComponent {

  columnHeaders!: string[];
  listingData: any;

  @Input() fetchData!: Function;

  @Input() noRecordImagePath!: string;
  @Input() noRecordText!: string;


  @Input() headerTemplate: any
  @ViewChild(CommonModalComponent, { static: true }) commonModal!: CommonModalComponent;

  records!: any[];
  totalRecords!: number;
  loading: boolean = false;
  currentPage:number = 0;
  currentRows:number = 0;

  representatives!: any[];
  selectAll: boolean = false;

  selectedCustomers!: any[];

  constructor(public http: HttpClient) { }

  ngOnInit() {
    this.loading = true;
  }


  init(event: any, partialRefresh = false) {
    if (this.fetchData) {
      if(!partialRefresh){
        event.first = (event.first / event.rows) + 1;
        this.currentRows = event.rows;
        this.currentPage = event.first;
      }
      this.fetchRecordData(event).subscribe({
        next: (res: any) => {
          this.records = res.body.data;
          this.columnHeaders = res.body.colHeaders;
          this.totalRecords = res.body.totalRecords;
          this.loading = false;
        },
        error: (err: any) => {
          this.totalRecords = 0;
          this.loading = false;
        }
      });
    }
  }
  fetchRecordData(params?: any) {
    return this.fetchData(params)
  }


  onSelectionChange(value = []) {
    this.selectAll = value.length === this.totalRecords;
    this.selectedCustomers = value;
  }

  onSelectAllChange(event: any) {
    const checked = event.checked;

    if (checked) {
      this.fetchRecordData().then((res: any) => {
        this.selectedCustomers = res.body.data;
        this.selectAll = true;
      });
    } else {
      this.selectedCustomers = [];
      this.selectAll = false;
    }
  }


  showCreateNewProjectModal() {
    this.commonModal.showDialog();
  }

  refreshRecords(since:string){
    let params = {first: this.currentPage, sortField: undefined, sortOrder: undefined, rows: this.currentRows, partialRefresh: true, since}
    this.init(params, true);
  }
}
