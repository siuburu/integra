import { Component, Input } from '@angular/core';
import { ISistema } from '../../entities/sistema/sistema.model';
import { SistemaService } from '../../entities/sistema/service/sistema.service';
import { NgOptimizedImage } from '@angular/common';

@Component({
  selector: 'jhi-card-sistema',
  standalone: true,
  imports: [NgOptimizedImage],
  templateUrl: './card-sistema.component.html',
  styleUrl: './card-sistema.component.scss',
})
export class CardSistemaComponent {
  constructor(protected sistemaService: SistemaService) {}
  @Input() sistemas?: ISistema[];
  @Input() trackId = (_index: number, item: ISistema): number => this.sistemaService.getSistemaIdentifier(item);
}
