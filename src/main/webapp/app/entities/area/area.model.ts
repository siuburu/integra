import { ISistema } from 'app/entities/sistema/sistema.model';

export interface IArea {
  id: number;
  nome?: string | null;
  sistemas?: Pick<ISistema, 'id'>[] | null;
}

export type NewArea = Omit<IArea, 'id'> & { id: null };
