import { IUser } from '../user/user.model';

export interface IProjet {
  id: number;
  titre?: string | null;
  description?: string | null;
  duree?: string | null;
  uniteDuree?: string | null;
  ville?: string | null;
  code?: string | null;
  fichier1?: string | null;
  fichier1ContentType?: string | null;
  fichier2?: string | null;
  fichier2ContentType?: string | null;
  fichier3?: string | null;
  fichier3ContentType?: string | null;
  fichier4?: string | null;
  fichier4ContentType?: string | null;
  isDone?: boolean | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewProjet = Omit<IProjet, 'id'> & { id: null };
