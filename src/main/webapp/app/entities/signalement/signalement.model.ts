import dayjs from 'dayjs/esm';
import { IProjet } from 'app/entities/projet/projet.model';

export interface ISignalement {
  id: number;
  texte?: string | null;
  date?: dayjs.Dayjs | null;
  projet?: Pick<IProjet, 'id' | 'titre'> | null;
}

export type NewSignalement = Omit<ISignalement, 'id'> & { id: null };
