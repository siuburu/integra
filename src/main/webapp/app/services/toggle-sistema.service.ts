import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ToggleSistemaService {
  activeArea: string = 'mais-acessados';
  constructor() {}
  updateActiveArea(newValue: string) {
    this.activeArea = newValue;
  }
}
