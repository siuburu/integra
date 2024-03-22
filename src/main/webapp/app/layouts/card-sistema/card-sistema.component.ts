import { Component, Input } from '@angular/core';
import { ISistema } from '../../entities/sistema/sistema.model';
import { SistemaService } from '../../entities/sistema/service/sistema.service';
import { NgIf, NgOptimizedImage } from '@angular/common';
import { ToggleSistemaService } from '../../services/toggle-sistema.service';
import { LocalStorageService } from '../../local-storage.service';

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
    protected localStorageService: LocalStorageService,
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

  logSistema(key: number, sistema: string | null | undefined) {
    const item = { key, sistema };
    this.localStorageService.adicionarAoHistorico(item);
    //this.localStorageService.setItem(key, sistema);
  }
  obterUltimosItens() {
    const ultimosItens = this.localStorageService.recuperarUltimosItens();
    return ultimosItens;
  }
  protected readonly JSON = JSON;
}
