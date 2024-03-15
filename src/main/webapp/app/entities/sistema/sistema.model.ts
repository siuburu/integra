import { IArea } from 'app/entities/area/area.model';

export interface ISistema {
  id: number;
  group?: string | null;
  nome?: string | null;
  descricao?: string | null;
  homeUrl?: string | null;
  logoUrl?: string | null;
  area?: Pick<IArea, 'id'> | null;
}

export type NewSistema = Omit<ISistema, 'id'> & { id: null };
