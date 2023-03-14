import dayjs from 'dayjs/esm';

import { IRegle, NewRegle } from './regle.model';

export const sampleWithRequiredData: IRegle = {
  id: 88591,
  texte: 'Music',
  date: dayjs('2023-03-13T16:18'),
};

export const sampleWithPartialData: IRegle = {
  id: 53885,
  texte: 'networks invoice Generic',
  date: dayjs('2023-03-13T21:28'),
};

export const sampleWithFullData: IRegle = {
  id: 48476,
  texte: 'Auvergne Shoes',
  date: dayjs('2023-03-13T09:15'),
};

export const sampleWithNewData: NewRegle = {
  texte: 'Health',
  date: dayjs('2023-03-13T07:08'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
