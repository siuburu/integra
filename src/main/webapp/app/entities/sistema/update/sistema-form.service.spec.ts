import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sistema.test-samples';

import { SistemaFormService } from './sistema-form.service';

describe('Sistema Form Service', () => {
  let service: SistemaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SistemaFormService);
  });

  describe('Service methods', () => {
    describe('createSistemaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSistemaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            group: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            homeUrl: expect.any(Object),
            logoUrl: expect.any(Object),
            area: expect.any(Object),
          }),
        );
      });

      it('passing ISistema should create a new form with FormGroup', () => {
        const formGroup = service.createSistemaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            group: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            homeUrl: expect.any(Object),
            logoUrl: expect.any(Object),
            area: expect.any(Object),
          }),
        );
      });
    });

    describe('getSistema', () => {
      it('should return NewSistema for default Sistema initial value', () => {
        const formGroup = service.createSistemaFormGroup(sampleWithNewData);

        const sistema = service.getSistema(formGroup) as any;

        expect(sistema).toMatchObject(sampleWithNewData);
      });

      it('should return NewSistema for empty Sistema initial value', () => {
        const formGroup = service.createSistemaFormGroup();

        const sistema = service.getSistema(formGroup) as any;

        expect(sistema).toMatchObject({});
      });

      it('should return ISistema', () => {
        const formGroup = service.createSistemaFormGroup(sampleWithRequiredData);

        const sistema = service.getSistema(formGroup) as any;

        expect(sistema).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISistema should not enable id FormControl', () => {
        const formGroup = service.createSistemaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSistema should disable id FormControl', () => {
        const formGroup = service.createSistemaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
