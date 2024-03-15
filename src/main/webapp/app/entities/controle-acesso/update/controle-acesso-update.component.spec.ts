import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ControleAcessoService } from '../service/controle-acesso.service';
import { IControleAcesso } from '../controle-acesso.model';

import { ControleAcessoFormService } from './controle-acesso-form.service';

import { ControleAcessoUpdateComponent } from './controle-acesso-update.component';

describe('ControleAcesso Management Update Component', () => {
  let comp: ControleAcessoUpdateComponent;
  let fixture: ComponentFixture<ControleAcessoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let controleAcessoFormService: ControleAcessoFormService;
  let controleAcessoService: ControleAcessoService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ControleAcessoUpdateComponent],
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
      .overrideTemplate(ControleAcessoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControleAcessoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    controleAcessoFormService = TestBed.inject(ControleAcessoFormService);
    controleAcessoService = TestBed.inject(ControleAcessoService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const controleAcesso: IControleAcesso = { id: 456 };
      const user: IUser = { id: '8244caa4-1116-4205-bb23-688a05659cb4' };
      controleAcesso.user = user;

      const userCollection: IUser[] = [{ id: 'f10edee4-300e-4b99-8beb-1b8c45dc91d6' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ controleAcesso });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const controleAcesso: IControleAcesso = { id: 456 };
      const user: IUser = { id: '238854b8-f60e-45ca-9847-a60a12fde831' };
      controleAcesso.user = user;

      activatedRoute.data = of({ controleAcesso });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.controleAcesso).toEqual(controleAcesso);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleAcesso>>();
      const controleAcesso = { id: 123 };
      jest.spyOn(controleAcessoFormService, 'getControleAcesso').mockReturnValue(controleAcesso);
      jest.spyOn(controleAcessoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleAcesso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleAcesso }));
      saveSubject.complete();

      // THEN
      expect(controleAcessoFormService.getControleAcesso).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(controleAcessoService.update).toHaveBeenCalledWith(expect.objectContaining(controleAcesso));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleAcesso>>();
      const controleAcesso = { id: 123 };
      jest.spyOn(controleAcessoFormService, 'getControleAcesso').mockReturnValue({ id: null });
      jest.spyOn(controleAcessoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleAcesso: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleAcesso }));
      saveSubject.complete();

      // THEN
      expect(controleAcessoFormService.getControleAcesso).toHaveBeenCalled();
      expect(controleAcessoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleAcesso>>();
      const controleAcesso = { id: 123 };
      jest.spyOn(controleAcessoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleAcesso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(controleAcessoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
