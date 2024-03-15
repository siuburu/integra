import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../controle-acesso.test-samples';

import { ControleAcessoFormService } from './controle-acesso-form.service';

describe('ControleAcesso Form Service', () => {
  let service: ControleAcessoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ControleAcessoFormService);
  });

  describe('Service methods', () => {
    describe('createControleAcessoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createControleAcessoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAcesso: expect.any(Object),
            ipAcesso: expect.any(Object),
            nomeDispositivo: expect.any(Object),
            user: expect.any(Object),
          }),
        );
      });

      it('passing IControleAcesso should create a new form with FormGroup', () => {
        const formGroup = service.createControleAcessoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAcesso: expect.any(Object),
            ipAcesso: expect.any(Object),
            nomeDispositivo: expect.any(Object),
            user: expect.any(Object),
          }),
        );
      });
    });

    describe('getControleAcesso', () => {
      it('should return NewControleAcesso for default ControleAcesso initial value', () => {
        const formGroup = service.createControleAcessoFormGroup(sampleWithNewData);

        const controleAcesso = service.getControleAcesso(formGroup) as any;

        expect(controleAcesso).toMatchObject(sampleWithNewData);
      });

      it('should return NewControleAcesso for empty ControleAcesso initial value', () => {
        const formGroup = service.createControleAcessoFormGroup();

        const controleAcesso = service.getControleAcesso(formGroup) as any;

        expect(controleAcesso).toMatchObject({});
      });

      it('should return IControleAcesso', () => {
        const formGroup = service.createControleAcessoFormGroup(sampleWithRequiredData);

        const controleAcesso = service.getControleAcesso(formGroup) as any;

        expect(controleAcesso).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IControleAcesso should not enable id FormControl', () => {
        const formGroup = service.createControleAcessoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewControleAcesso should disable id FormControl', () => {
        const formGroup = service.createControleAcessoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
