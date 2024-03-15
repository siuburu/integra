import { Component, Input } from '@angular/core';
import { ISistema } from '../../entities/sistema/sistema.model';

@Component({
  selector: 'jhi-card-sistema',
  standalone: true,
  imports: [],
  templateUrl: './card-sistema.component.html',
  styleUrl: './card-sistema.component.scss',
})
export class CardSistemaComponent {
  @Input() sistemas?: ISistema[];
}
