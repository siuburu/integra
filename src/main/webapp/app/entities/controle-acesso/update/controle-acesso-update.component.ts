import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IControleAcesso } from '../controle-acesso.model';
import { ControleAcessoService } from '../service/controle-acesso.service';
import { ControleAcessoFormService, ControleAcessoFormGroup } from './controle-acesso-form.service';

@Component({
  standalone: true,
  selector: 'jhi-controle-acesso-update',
  templateUrl: './controle-acesso-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ControleAcessoUpdateComponent implements OnInit {
  isSaving = false;
  controleAcesso: IControleAcesso | null = null;

  usersSharedCollection: IUser[] = [];

  editForm: ControleAcessoFormGroup = this.controleAcessoFormService.createControleAcessoFormGroup();

  constructor(
    protected controleAcessoService: ControleAcessoService,
    protected controleAcessoFormService: ControleAcessoFormService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ controleAcesso }) => {
      this.controleAcesso = controleAcesso;
      if (controleAcesso) {
        this.updateForm(controleAcesso);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const controleAcesso = this.controleAcessoFormService.getControleAcesso(this.editForm);
    if (controleAcesso.id !== null) {
      this.subscribeToSaveResponse(this.controleAcessoService.update(controleAcesso));
    } else {
      this.subscribeToSaveResponse(this.controleAcessoService.create(controleAcesso));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControleAcesso>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(controleAcesso: IControleAcesso): void {
    this.controleAcesso = controleAcesso;
    this.controleAcessoFormService.resetForm(this.editForm, controleAcesso);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, controleAcesso.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.controleAcesso?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
