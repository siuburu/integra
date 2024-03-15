import { IArea, NewArea } from './area.model';

export const sampleWithRequiredData: IArea = {
  id: 16997,
  nome: 'pulverise amongst consequently',
};

export const sampleWithPartialData: IArea = {
  id: 2762,
  nome: 'knottily',
};

export const sampleWithFullData: IArea = {
  id: 3832,
  nome: 'amid neuropathologist',
};

export const sampleWithNewData: NewArea = {
  nome: 'conk even pop',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
