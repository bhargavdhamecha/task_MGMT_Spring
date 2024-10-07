import { TemplateRef } from "@angular/core"
import { Dialog } from "primeng/dialog"

export interface ModalConfig {
    modalHeader: string
    dismissButtonLabel?: string
    closeButtonLabel?: string
    shouldClose?(): Promise<boolean> | boolean
    shouldDismiss?(): Promise<boolean> | boolean
    onCloseFun?: ()=> any
    onDismiss?(): Promise<boolean> | boolean
    disableCloseButton?(): boolean
    disableDismissButton?(): boolean
    hideCloseButton?: boolean
    hideDismissButton?: boolean
    closable?: boolean
    response?: boolean
    draggable?: boolean 
    resizable?: boolean
    // content: TemplateRef<any>
}