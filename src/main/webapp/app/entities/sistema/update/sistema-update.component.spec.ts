import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IArea } from 'app/entities/area/area.model';
import { AreaService } from 'app/entities/area/service/area.service';
import { SistemaService } from '../service/sistema.service';
import { ISistema } from '../sistema.model';
import { SistemaFormService } from './sistema-form.service';

import { SistemaUpdateComponent } from './sistema-update.component';

describe('Sistema Management Update Component', () => {
  let comp: SistemaUpdateComponent;
  let fixture: ComponentFixture<SistemaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sistemaFormService: SistemaFormService;
  let sistemaService: SistemaService;
  let areaService: AreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SistemaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SistemaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SistemaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sistemaFormService = TestBed.inject(SistemaFormService);
    sistemaService = TestBed.inject(SistemaService);
    areaService = TestBed.inject(AreaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Area query and add missing value', () => {
      const sistema: ISistema = { id: 456 };
      const area: IArea = { id: 4676 };
      sistema.area = area;

      const areaCollection: IArea[] = [{ id: 20651 }];
      jest.spyOn(areaService, 'query').mockReturnValue(of(new HttpResponse({ body: areaCollection })));
      const additionalAreas = [area];
      const expectedCollection: IArea[] = [...additionalAreas, ...areaCollection];
      jest.spyOn(areaService, 'addAreaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sistema });
      comp.ngOnInit();

      expect(areaService.query).toHaveBeenCalled();
      expect(areaService.addAreaToCollectionIfMissing).toHaveBeenCalledWith(
        areaCollection,
        ...additionalAreas.map(expect.objectContaining),
      );
      expect(comp.areasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sistema: ISistema = { id: 456 };
      const area: IArea = { id: 4377 };
      sistema.area = area;

      activatedRoute.data = of({ sistema });
      comp.ngOnInit();

      expect(comp.areasSharedCollection).toContain(area);
      expect(comp.sistema).toEqual(sistema);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISistema>>();
      const sistema = { id: 123 };
      jest.spyOn(sistemaFormService, 'getSistema').mockReturnValue(sistema);
      jest.spyOn(sistemaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sistema });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sistema }));
      saveSubject.complete();

      // THEN
      expect(sistemaFormService.getSistema).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sistemaService.update).toHaveBeenCalledWith(expect.objectContaining(sistema));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISistema>>();
      const sistema = { id: 123 };
      jest.spyOn(sistemaFormService, 'getSistema').mockReturnValue({ id: null });
      jest.spyOn(sistemaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sistema: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sistema }));
      saveSubject.complete();

      // THEN
      expect(sistemaFormService.getSistema).toHaveBeenCalled();
      expect(sistemaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISistema>>();
      const sistema = { id: 123 };
      jest.spyOn(sistemaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sistema });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sistemaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareArea', () => {
      it('Should forward to areaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(areaService, 'compareArea');
        comp.compareArea(entity, entity2);
        expect(areaService.compareArea).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
