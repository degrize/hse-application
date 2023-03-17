import dayjs from 'dayjs/esm';
import { IProjet } from 'app/entities/projet/projet.model';
import { IUser } from '../user/user.model';

export interface IRegle {
  id: number;
  texte?: string | null;
  code?: string | null;
  date?: dayjs.Dayjs | null;
  projet?: Pick<IProjet, 'id' | 'titre' | 'code'> | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewRegle = Omit<IRegle, 'id'> & { id: null };
