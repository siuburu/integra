import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ControleAcessoDetailComponent } from './controle-acesso-detail.component';

describe('ControleAcesso Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControleAcessoDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ControleAcessoDetailComponent,
              resolve: { controleAcesso: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ControleAcessoDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load controleAcesso on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ControleAcessoDetailComponent);

      // THEN
      expect(instance.controleAcesso).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
