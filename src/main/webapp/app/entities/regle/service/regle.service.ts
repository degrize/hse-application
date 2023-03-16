import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRegle, NewRegle } from '../regle.model';
import { IProjet } from '../../projet/projet.model';

export type PartialUpdateRegle = Partial<IRegle> & Pick<IRegle, 'id'>;

type RestOf<T extends IRegle | NewRegle> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestRegle = RestOf<IRegle>;

export type NewRestRegle = RestOf<NewRegle>;

export type PartialUpdateRestRegle = RestOf<PartialUpdateRegle>;

export type EntityResponseType = HttpResponse<IRegle>;
export type EntityArrayResponseType = HttpResponse<IRegle[]>;

@Injectable({ providedIn: 'root' })
export class RegleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/regles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(regle: NewRegle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(regle);
    return this.http.post<RestRegle>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(regle: IRegle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(regle);
    return this.http
      .put<RestRegle>(`${this.resourceUrl}/${this.getRegleIdentifier(regle)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(regle: PartialUpdateRegle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(regle);
    return this.http
      .patch<RestRegle>(`${this.resourceUrl}/${this.getRegleIdentifier(regle)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRegle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  getListByProjetId(id: number): Observable<HttpResponse<any>> {
    return this.http.get<IProjet[]>(`${this.resourceUrl}/listeByProjetId/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRegle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRegleIdentifier(regle: Pick<IRegle, 'id'>): number {
    return regle.id;
  }

  compareRegle(o1: Pick<IRegle, 'id'> | null, o2: Pick<IRegle, 'id'> | null): boolean {
    return o1 && o2 ? this.getRegleIdentifier(o1) === this.getRegleIdentifier(o2) : o1 === o2;
  }

  addRegleToCollectionIfMissing<Type extends Pick<IRegle, 'id'>>(
    regleCollection: Type[],
    ...reglesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const regles: Type[] = reglesToCheck.filter(isPresent);
    if (regles.length > 0) {
      const regleCollectionIdentifiers = regleCollection.map(regleItem => this.getRegleIdentifier(regleItem)!);
      const reglesToAdd = regles.filter(regleItem => {
        const regleIdentifier = this.getRegleIdentifier(regleItem);
        if (regleCollectionIdentifiers.includes(regleIdentifier)) {
          return false;
        }
        regleCollectionIdentifiers.push(regleIdentifier);
        return true;
      });
      return [...reglesToAdd, ...regleCollection];
    }
    return regleCollection;
  }

  protected convertDateFromClient<T extends IRegle | NewRegle | PartialUpdateRegle>(regle: T): RestOf<T> {
    return {
      ...regle,
      date: regle.date?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restRegle: RestRegle): IRegle {
    return {
      ...restRegle,
      date: restRegle.date ? dayjs(restRegle.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRegle>): HttpResponse<IRegle> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRegle[]>): HttpResponse<IRegle[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
