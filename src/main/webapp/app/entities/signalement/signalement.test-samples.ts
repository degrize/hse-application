import dayjs from 'dayjs/esm';

import { ISignalement, NewSignalement } from './signalement.model';

export const sampleWithRequiredData: ISignalement = {
  id: 10841,
  texte: 'Account',
  date: dayjs('2023-03-13T01:05'),
};

export const sampleWithPartialData: ISignalement = {
  id: 47958,
  texte: 'platforms salmon Champagne-Ardenne',
  date: dayjs('2023-03-13T00:52'),
};

export const sampleWithFullData: ISignalement = {
  id: 83120,
  texte: 'Awesome',
  date: dayjs('2023-03-13T18:10'),
};

export const sampleWithNewData: NewSignalement = {
  texte: 'Litas Wooden c',
  date: dayjs('2023-03-13T13:50'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
