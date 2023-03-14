import { IProjet, NewProjet } from './projet.model';

export const sampleWithRequiredData: IProjet = {
  id: 59803,
  titre: 'b interface',
};

export const sampleWithPartialData: IProjet = {
  id: 92611,
  titre: 'IB',
  ville: 'up channels',
  fichier1: '../fake-data/blob/hipster.png',
  fichier1ContentType: 'unknown',
  fichier4: '../fake-data/blob/hipster.png',
  fichier4ContentType: 'unknown',
};

export const sampleWithFullData: IProjet = {
  id: 72105,
  titre: 'mobile magenta',
  description: 'THX Computers Granite',
  duree: 'parsing Berkshire cultivate',
  ville: 'indigo wireless compelling',
  code: 'XSS enterprise',
  fichier1: '../fake-data/blob/hipster.png',
  fichier1ContentType: 'unknown',
  fichier2: '../fake-data/blob/hipster.png',
  fichier2ContentType: 'unknown',
  fichier3: '../fake-data/blob/hipster.png',
  fichier3ContentType: 'unknown',
  fichier4: '../fake-data/blob/hipster.png',
  fichier4ContentType: 'unknown',
};

export const sampleWithNewData: NewProjet = {
  titre: 'solid protocol Loan',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
