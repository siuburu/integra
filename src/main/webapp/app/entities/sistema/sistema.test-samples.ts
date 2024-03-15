import { ISistema, NewSistema } from './sistema.model';

export const sampleWithRequiredData: ISistema = {
  id: 13638,
  group: 'ouch diet',
  nome: 'indeed beside',
  descricao: 'ripeXXXXXX',
};

export const sampleWithPartialData: ISistema = {
  id: 19776,
  group: 'ogle judgementally',
  nome: 'furthermore',
  descricao: 'huzzah since quintuple',
  logoUrl: 'whether vicinity grapefruit',
};

export const sampleWithFullData: ISistema = {
  id: 18983,
  group: 'onX',
  nome: 'unless abaft',
  descricao: 'aboveXXXXX',
  homeUrl: 'squash',
  logoUrl: 'with forenenst rightfully',
};

export const sampleWithNewData: NewSistema = {
  group: 'towards oof near',
  nome: 'twang',
  descricao: 'triumphantly about',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
