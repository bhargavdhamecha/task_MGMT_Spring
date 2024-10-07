import { Component, EventEmitter, Injectable, Input, Output, TemplateRef, ViewChild } from '@angular/core';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ModalConfig } from './modalConfig';
import { Dialog } from 'primeng/dialog';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-common-modal',
  templateUrl: './common-modal.component.html',
  styleUrl: './common-modal.component.scss'
})
@Injectable()
export class CommonModalComponent {
  @Input() public modalConfig!: ModalConfig;
  @ViewChild('dialogContent', {static: true}) private modalContent!: TemplateRef<CommonModalComponent>; 
  private modalRef!:  DynamicDialogRef;
  private dialog!: Dialog;

  isVisible: boolean = false;

  constructor(private dialogService: DialogService){}
  show(){
    this.isVisible=true;
    // return this.modalRef = this.dialogService.open( CommonModalComponent, {
    //   header: this.modalConfig.modalTitle,
    //   width: "50%",
    //   modal: true,
    //   closable: this.modalConfig.closable,
    //   data: { id: '12'} 
    // });
  }

  hide() {
    // this.modalRef.close();
  }

  // confirm() {
  //   this.onConfirm.emit();
  //   this.hide();
  // }

  // cancel() {
  //   this.onCancel.emit();
  //   this.hide();
  // }
  close() {
    // if (this.modalConfig.shouldClose === undefined || (await this.modalConfig.shouldClose())) {
    this.isVisible = false;
    if (this.modalConfig.onCloseFun)
      this.modalConfig.onCloseFun();
    
      // this.modalRef.close(result)
    
  }

  async dismiss(): Promise<void> {
    this.isVisible = false;
    if (this.modalConfig.shouldDismiss === undefined || (await this.modalConfig.shouldDismiss())) {
      const result = this.modalConfig.onDismiss === undefined || (await this.modalConfig.onDismiss())
      // this.modalRef.close(result)
    }
  }
}