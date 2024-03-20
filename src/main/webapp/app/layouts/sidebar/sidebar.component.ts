import { Component, Input, OnInit } from '@angular/core';
import { NgbCollapse, NgbNav, NgbNavOutlet, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { SidebarService } from '../../services/sidebar.service';
import { ToggleSistemaService } from '../../services/toggle-sistema.service';

@Component({
  selector: 'jhi-sidebar',
  standalone: true,
  imports: [NgbCollapse, NgbNavOutlet, NgbNav, NgbNavModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent implements OnInit {
  @Input() isCollapsed = false;

  constructor(
    protected sidebarService: SidebarService,
    protected toggleSistemaService: ToggleSistemaService,
  ) {}
  ngOnInit() {}

  toggleCollapse() {
    this.sidebarService.updateIsCollapsed(true);
  }

  toggleArea() {
    this.toggleSistemaService.updateActiveArea(this.active);
  }
  active = 'mais-acessados';
}
