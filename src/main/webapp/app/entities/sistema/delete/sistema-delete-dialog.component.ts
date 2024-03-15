import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISistema } from '../sistema.model';
import { SistemaService } from '../service/sistema.service';

@Component({
  standalone: true,
  templateUrl: './sistema-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SistemaDeleteDialogComponent {
  sistema?: ISistema;

  constructor(
    protected sistemaService: SistemaService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sistemaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
