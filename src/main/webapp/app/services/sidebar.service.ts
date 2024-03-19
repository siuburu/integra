import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SidebarService {
  isCollapsed: boolean = false;
  constructor() {}
  updateIsCollapsed(newValue: boolean) {
    this.isCollapsed = newValue;
  }
}
