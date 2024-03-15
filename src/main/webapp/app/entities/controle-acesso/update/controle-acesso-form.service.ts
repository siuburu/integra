import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IControleAcesso, NewControleAcesso } from '../controle-acesso.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IControleAcesso for edit and NewControleAcessoFormGroupInput for create.
 */
type ControleAcessoFormGroupInput = IControleAcesso | PartialWithRequiredKeyOf<NewControleAcesso>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IControleAcesso | NewControleAcesso> = Omit<T, 'dataAcesso'> & {
  dataAcesso?: string | null;
};

type ControleAcessoFormRawValue = FormValueOf<IControleAcesso>;

type NewControleAcessoFormRawValue = FormValueOf<NewControleAcesso>;

type ControleAcessoFormDefaults = Pick<NewControleAcesso, 'id' | 'dataAcesso'>;

type ControleAcessoFormGroupContent = {
  id: FormControl<ControleAcessoFormRawValue['id'] | NewControleAcesso['id']>;
  dataAcesso: FormControl<ControleAcessoFormRawValue['dataAcesso']>;
  ipAcesso: FormControl<ControleAcessoFormRawValue['ipAcesso']>;
  nomeDispositivo: FormControl<ControleAcessoFormRawValue['nomeDispositivo']>;
  user: FormControl<ControleAcessoFormRawValue['user']>;
};

export type ControleAcessoFormGroup = FormGroup<ControleAcessoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ControleAcessoFormService {
  createControleAcessoFormGroup(controleAcesso: ControleAcessoFormGroupInput = { id: null }): ControleAcessoFormGroup {
    const controleAcessoRawValue = this.convertControleAcessoToControleAcessoRawValue({
      ...this.getFormDefaults(),
      ...controleAcesso,
    });
    return new FormGroup<ControleAcessoFormGroupContent>({
      id: new FormControl(
        { value: controleAcessoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataAcesso: new FormControl(controleAcessoRawValue.dataAcesso),
      ipAcesso: new FormControl(controleAcessoRawValue.ipAcesso),
      nomeDispositivo: new FormControl(controleAcessoRawValue.nomeDispositivo),
      user: new FormControl(controleAcessoRawValue.user),
    });
  }

  getControleAcesso(form: ControleAcessoFormGroup): IControleAcesso | NewControleAcesso {
    return this.convertControleAcessoRawValueToControleAcesso(
      form.getRawValue() as ControleAcessoFormRawValue | NewControleAcessoFormRawValue,
    );
  }

  resetForm(form: ControleAcessoFormGroup, controleAcesso: ControleAcessoFormGroupInput): void {
    const controleAcessoRawValue = this.convertControleAcessoToControleAcessoRawValue({ ...this.getFormDefaults(), ...controleAcesso });
    form.reset(
      {
        ...controleAcessoRawValue,
        id: { value: controleAcessoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ControleAcessoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAcesso: currentTime,
    };
  }

  private convertControleAcessoRawValueToControleAcesso(
    rawControleAcesso: ControleAcessoFormRawValue | NewControleAcessoFormRawValue,
  ): IControleAcesso | NewControleAcesso {
    return {
      ...rawControleAcesso,
      dataAcesso: dayjs(rawControleAcesso.dataAcesso, DATE_TIME_FORMAT),
    };
  }

  private convertControleAcessoToControleAcessoRawValue(
    controleAcesso: IControleAcesso | (Partial<NewControleAcesso> & ControleAcessoFormDefaults),
  ): ControleAcessoFormRawValue | PartialWithRequiredKeyOf<NewControleAcessoFormRawValue> {
    return {
      ...controleAcesso,
      dataAcesso: controleAcesso.dataAcesso ? controleAcesso.dataAcesso.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
