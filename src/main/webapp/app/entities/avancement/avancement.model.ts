import dayjs from 'dayjs/esm';
import { IProjet } from 'app/entities/projet/projet.model';
import { IUser } from '../user/user.model';

export interface IAvancement {
  id: number;
  description?: string | null;
  date?: dayjs.Dayjs | null;
  fichier1?: string | null;
  fichier1ContentType?: string | null;
  fichier2?: string | null;
  fichier2ContentType?: string | null;
  fichier3?: string | null;
  fichier3ContentType?: string | null;
  fichier4?: string | null;
  fichier4ContentType?: string | null;
  projet?: Pick<IProjet, 'id' | 'titre' | 'code'> | null;
  code?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewAvancement = Omit<IAvancement, 'id'> & { id: null };
