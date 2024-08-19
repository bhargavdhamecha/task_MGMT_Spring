import { Component, EventEmitter, Input, Output, TemplateRef } from '@angular/core';

@Component({
  selector: 'app-common-modal',
  templateUrl: './common-modal.component.html',
  styleUrl: './common-modal.component.scss'
})
export class CommonModalComponent {
  public visible: boolean = false;
  @Input() header: string = '';
  @Input() okLabel: string = '';
  @Input() cancelLabel: string = '';
  @Input() contentTemplate!: TemplateRef<any>;

  @Output() cancel = new EventEmitter<void>();
  @Output() ok = new EventEmitter<void>();

  onCancel() {
    this.visible = false;
    this.cancel.emit();
  }

  onOk() {
    this.visible = false;
    this.ok.emit();
  }

  public showDialog() {
      this.visible = true;
  }
}
