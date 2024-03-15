import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IControleAcesso } from '../controle-acesso.model';
import { ControleAcessoService } from '../service/controle-acesso.service';

@Component({
  standalone: true,
  templateUrl: './controle-acesso-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ControleAcessoDeleteDialogComponent {
  controleAcesso?: IControleAcesso;

  constructor(
    protected controleAcessoService: ControleAcessoService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.controleAcessoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
