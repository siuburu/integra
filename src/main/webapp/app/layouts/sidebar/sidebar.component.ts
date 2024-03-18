import { Component, Input } from '@angular/core';
import { NgbCollapse, NgbNav, NgbNavOutlet, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-sidebar',
  standalone: true,
  imports: [NgbCollapse, NgbNavOutlet, NgbNav, NgbNavModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent {
  @Input() isCollapsed = false;
  active = 'mais-acessados';
}
