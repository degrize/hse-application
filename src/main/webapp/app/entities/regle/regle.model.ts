import dayjs from 'dayjs/esm';
import { IProjet } from 'app/entities/projet/projet.model';

export interface IRegle {
  id: number;
  texte?: string | null;
  code?: string | null;
  date?: dayjs.Dayjs | null;
  projet?: Pick<IProjet, 'id' | 'titre'> | null;
}

export type NewRegle = Omit<IRegle, 'id'> & { id: null };
