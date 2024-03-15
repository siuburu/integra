import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SistemaDetailComponent } from './sistema-detail.component';

describe('Sistema Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SistemaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SistemaDetailComponent,
              resolve: { sistema: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SistemaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load sistema on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SistemaDetailComponent);

      // THEN
      expect(instance.sistema).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
