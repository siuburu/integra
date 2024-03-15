import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';

export interface IControleAcesso {
  id: number;
  dataAcesso?: dayjs.Dayjs | null;
  ipAcesso?: string | null;
  nomeDispositivo?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewControleAcesso = Omit<IControleAcesso, 'id'> & { id: null };
