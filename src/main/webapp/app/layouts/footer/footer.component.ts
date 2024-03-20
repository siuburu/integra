import { Component } from '@angular/core';
import dayjs from 'dayjs';
import { VERSION } from 'app/app.constants';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';

@Component({
  standalone: true,
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
  imports: [FaIconComponent],
})
export class FooterComponent {
  data: number;
  version: String;
  endereco: String = 'Rua Jamary, 1555 - Olaria - Porto Velho/RO ';
  cep: String = 'CEP: 76.801-917 - Fone: 69 3216-3700';
  constructor() {
    this.version = VERSION ? 'v' + VERSION : '';
    this.data = dayjs().year();
  }

  ngOnInit(): void {}
}
