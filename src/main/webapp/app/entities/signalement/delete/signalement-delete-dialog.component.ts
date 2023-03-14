import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISignalement } from '../signalement.model';
import { SignalementService } from '../service/signalement.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './signalement-delete-dialog.component.html',
})
export class SignalementDeleteDialogComponent {
  signalement?: ISignalement;

  constructor(protected signalementService: SignalementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.signalementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
