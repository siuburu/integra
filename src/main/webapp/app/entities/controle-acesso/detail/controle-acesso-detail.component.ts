import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IControleAcesso } from '../controle-acesso.model';

@Component({
  standalone: true,
  selector: 'jhi-controle-acesso-detail',
  templateUrl: './controle-acesso-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ControleAcessoDetailComponent {
  @Input() controleAcesso: IControleAcesso | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
