import dayjs from 'dayjs/esm';

import { IControleAcesso, NewControleAcesso } from './controle-acesso.model';

export const sampleWithRequiredData: IControleAcesso = {
  id: 8556,
};

export const sampleWithPartialData: IControleAcesso = {
  id: 27809,
};

export const sampleWithFullData: IControleAcesso = {
  id: 5174,
  dataAcesso: dayjs('2024-03-11T05:31'),
  ipAcesso: 'corral train run',
  nomeDispositivo: 'extirpate interestingly',
};

export const sampleWithNewData: NewControleAcesso = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
