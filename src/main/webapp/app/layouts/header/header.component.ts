import { Component } from '@angular/core';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';
import FindLanguageFromKeyPipe from '../../shared/language/find-language-from-key.pipe';
import HasAnyAuthorityDirective from '../../shared/auth/has-any-authority.directive';
import { NgbDropdown, NgbDropdownMenu, NgbDropdownToggle } from '@ng-bootstrap/ng-bootstrap';
import { RouterLink, RouterLinkActive } from '@angular/router';
import TranslateDirective from '../../shared/language/translate.directive';
import { SidebarService } from '../../services/sidebar.service';
import { IControleAcesso } from '../../entities/controle-acesso/controle-acesso.model';
import { AccountService } from '../../core/auth/account.service';
import { Account } from '../../core/auth/account.model';

@Component({
  selector: 'jhi-header',
  standalone: true,
  imports: [
    FaIconComponent,
    FindLanguageFromKeyPipe,
    HasAnyAuthorityDirective,
    NgbDropdown,
    NgbDropdownMenu,
    NgbDropdownToggle,
    RouterLink,
    RouterLinkActive,
    TranslateDirective,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  account: Account | null = null;

  constructor(
    public sidebarService: SidebarService,
    private accountService: AccountService,
  ) {}

  ngOnInit() {
    this.accountService.identity().subscribe(account => (this.account = account));
  }

  toggleCollapse() {
    this.sidebarService.updateIsCollapsed(!this.sidebarService.isCollapsed);
  }
}
