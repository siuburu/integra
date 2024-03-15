import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SistemaService } from '../service/sistema.service';

import { SistemaComponent } from './sistema.component';

describe('Sistema Management Component', () => {
  let comp: SistemaComponent;
  let fixture: ComponentFixture<SistemaComponent>;
  let service: SistemaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'sistema', component: SistemaComponent }]),
        HttpClientTestingModule,
        SistemaComponent,
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
      .overrideTemplate(SistemaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SistemaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SistemaService);

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
    expect(comp.sistemas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to sistemaService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getSistemaIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getSistemaIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
