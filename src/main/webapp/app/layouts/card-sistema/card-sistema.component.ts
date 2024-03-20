import { Component, Input } from '@angular/core';
import { ISistema } from '../../entities/sistema/sistema.model';
import { SistemaService } from '../../entities/sistema/service/sistema.service';
import { NgIf, NgOptimizedImage } from '@angular/common';
import { ToggleSistemaService } from '../../services/toggle-sistema.service';

@Component({
  selector: 'jhi-card-sistema',
  standalone: true,
  imports: [NgOptimizedImage, NgIf],
  templateUrl: './card-sistema.component.html',
  styleUrl: './card-sistema.component.scss',
})
export class CardSistemaComponent {
  constructor(
    protected sistemaService: SistemaService,
    protected toggleSistemaService: ToggleSistemaService,
  ) {}
  @Input() sistemas?: ISistema[];
  @Input() trackId = (_index: number, item: ISistema): number => this.sistemaService.getSistemaIdentifier(item);

  getArea(id: number | undefined) {
    if (id === 1001) {
      return 'area-fim';
    } else if (id === 1002) {
      return 'area-administrativa';
    } else {
      return 'mais-acessados';
    }
  }
}
