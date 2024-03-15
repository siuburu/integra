import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ControleAcessoService } from '../service/controle-acesso.service';

import { ControleAcessoComponent } from './controle-acesso.component';

describe('ControleAcesso Management Component', () => {
  let comp: ControleAcessoComponent;
  let fixture: ComponentFixture<ControleAcessoComponent>;
  let service: ControleAcessoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'controle-acesso', component: ControleAcessoComponent }]),
        HttpClientTestingModule,
        ControleAcessoComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ControleAcessoComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControleAcessoComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ControleAcessoService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.controleAcessos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to controleAcessoService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getControleAcessoIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getControleAcessoIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
