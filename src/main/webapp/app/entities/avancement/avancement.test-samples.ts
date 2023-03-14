import dayjs from 'dayjs/esm';

import { IAvancement, NewAvancement } from './avancement.model';

export const sampleWithRequiredData: IAvancement = {
  id: 88505,
  description: 'Games',
  date: dayjs('2023-03-13'),
};

export const sampleWithPartialData: IAvancement = {
  id: 98903,
  description: 'one-to-one Saint-Séverin',
  date: dayjs('2023-03-13'),
  fichier1: '../fake-data/blob/hipster.png',
  fichier1ContentType: 'unknown',
  fichier3: '../fake-data/blob/hipster.png',
  fichier3ContentType: 'unknown',
};

export const sampleWithFullData: IAvancement = {
  id: 8343,
  description: 'plum harness',
  date: dayjs('2023-03-13'),
  fichier1: '../fake-data/blob/hipster.png',
  fichier1ContentType: 'unknown',
  fichier2: '../fake-data/blob/hipster.png',
  fichier2ContentType: 'unknown',
  fichier3: '../fake-data/blob/hipster.png',
  fichier3ContentType: 'unknown',
  fichier4: '../fake-data/blob/hipster.png',
  fichier4ContentType: 'unknown',
};

export const sampleWithNewData: NewAvancement = {
  description: 'Beauty transmit projection',
  date: dayjs('2023-03-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
