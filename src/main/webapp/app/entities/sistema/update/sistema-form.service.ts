import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISistema, NewSistema } from '../sistema.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISistema for edit and NewSistemaFormGroupInput for create.
 */
type SistemaFormGroupInput = ISistema | PartialWithRequiredKeyOf<NewSistema>;

type SistemaFormDefaults = Pick<NewSistema, 'id'>;

type SistemaFormGroupContent = {
  id: FormControl<ISistema['id'] | NewSistema['id']>;
  group: FormControl<ISistema['group']>;
  nome: FormControl<ISistema['nome']>;
  descricao: FormControl<ISistema['descricao']>;
  homeUrl: FormControl<ISistema['homeUrl']>;
  logoUrl: FormControl<ISistema['logoUrl']>;
  area: FormControl<ISistema['area']>;
};

export type SistemaFormGroup = FormGroup<SistemaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SistemaFormService {
  createSistemaFormGroup(sistema: SistemaFormGroupInput = { id: null }): SistemaFormGroup {
    const sistemaRawValue = {
      ...this.getFormDefaults(),
      ...sistema,
    };
    return new FormGroup<SistemaFormGroupContent>({
      id: new FormControl(
        { value: sistemaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      group: new FormControl(sistemaRawValue.group, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      nome: new FormControl(sistemaRawValue.nome, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      descricao: new FormControl(sistemaRawValue.descricao, {
        validators: [Validators.required, Validators.minLength(10)],
      }),
      homeUrl: new FormControl(sistemaRawValue.homeUrl),
      logoUrl: new FormControl(sistemaRawValue.logoUrl),
      area: new FormControl(sistemaRawValue.area),
    });
  }

  getSistema(form: SistemaFormGroup): ISistema | NewSistema {
    return form.getRawValue() as ISistema | NewSistema;
  }

  resetForm(form: SistemaFormGroup, sistema: SistemaFormGroupInput): void {
    const sistemaRawValue = { ...this.getFormDefaults(), ...sistema };
    form.reset(
      {
        ...sistemaRawValue,
        id: { value: sistemaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SistemaFormDefaults {
    return {
      id: null,
    };
  }
}
