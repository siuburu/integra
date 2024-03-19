import { Component, Input, OnInit } from '@angular/core';
import { NgbCollapse, NgbNav, NgbNavOutlet, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { SidebarService } from '../../services/sidebar.service';

@Component({
  selector: 'jhi-sidebar',
  standalone: true,
  imports: [NgbCollapse, NgbNavOutlet, NgbNav, NgbNavModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent implements OnInit {
  @Input() isCollapsed = false;

  constructor(protected sidebarService: SidebarService) {}
  ngOnInit() {}

  toggleCollapse() {
    this.sidebarService.updateIsCollapsed(!this.sidebarService.isCollapsed);
  }
  active = 'mais-acessados';
}
