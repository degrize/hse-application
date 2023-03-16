import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISignalement, NewSignalement } from '../signalement.model';
import { IProjet } from '../../projet/projet.model';

export type PartialUpdateSignalement = Partial<ISignalement> & Pick<ISignalement, 'id'>;

type RestOf<T extends ISignalement | NewSignalement> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestSignalement = RestOf<ISignalement>;

export type NewRestSignalement = RestOf<NewSignalement>;

export type PartialUpdateRestSignalement = RestOf<PartialUpdateSignalement>;

export type EntityResponseType = HttpResponse<ISignalement>;
export type EntityArrayResponseType = HttpResponse<ISignalement[]>;

@Injectable({ providedIn: 'root' })
export class SignalementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/signalements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(signalement: NewSignalement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(signalement);
    return this.http
      .post<RestSignalement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(signalement: ISignalement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(signalement);
    return this.http
      .put<RestSignalement>(`${this.resourceUrl}/${this.getSignalementIdentifier(signalement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(signalement: PartialUpdateSignalement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(signalement);
    return this.http
      .patch<RestSignalement>(`${this.resourceUrl}/${this.getSignalementIdentifier(signalement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSignalement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSignalement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  getListByProjetId(id: number): Observable<HttpResponse<any>> {
    return this.http.get<IProjet[]>(`${this.resourceUrl}/listeByProjetId/${id}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSignalementIdentifier(signalement: Pick<ISignalement, 'id'>): number {
    return signalement.id;
  }

  compareSignalement(o1: Pick<ISignalement, 'id'> | null, o2: Pick<ISignalement, 'id'> | null): boolean {
    return o1 && o2 ? this.getSignalementIdentifier(o1) === this.getSignalementIdentifier(o2) : o1 === o2;
  }

  addSignalementToCollectionIfMissing<Type extends Pick<ISignalement, 'id'>>(
    signalementCollection: Type[],
    ...signalementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const signalements: Type[] = signalementsToCheck.filter(isPresent);
    if (signalements.length > 0) {
      const signalementCollectionIdentifiers = signalementCollection.map(
        signalementItem => this.getSignalementIdentifier(signalementItem)!
      );
      const signalementsToAdd = signalements.filter(signalementItem => {
        const signalementIdentifier = this.getSignalementIdentifier(signalementItem);
        if (signalementCollectionIdentifiers.includes(signalementIdentifier)) {
          return false;
        }
        signalementCollectionIdentifiers.push(signalementIdentifier);
        return true;
      });
      return [...signalementsToAdd, ...signalementCollection];
    }
    return signalementCollection;
  }

  protected convertDateFromClient<T extends ISignalement | NewSignalement | PartialUpdateSignalement>(signalement: T): RestOf<T> {
    return {
      ...signalement,
      date: signalement.date?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSignalement: RestSignalement): ISignalement {
    return {
      ...restSignalement,
      date: restSignalement.date ? dayjs(restSignalement.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSignalement>): HttpResponse<ISignalement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSignalement[]>): HttpResponse<ISignalement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
