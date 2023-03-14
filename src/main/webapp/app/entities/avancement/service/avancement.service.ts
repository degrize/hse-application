import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAvancement, NewAvancement } from '../avancement.model';

export type PartialUpdateAvancement = Partial<IAvancement> & Pick<IAvancement, 'id'>;

type RestOf<T extends IAvancement | NewAvancement> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestAvancement = RestOf<IAvancement>;

export type NewRestAvancement = RestOf<NewAvancement>;

export type PartialUpdateRestAvancement = RestOf<PartialUpdateAvancement>;

export type EntityResponseType = HttpResponse<IAvancement>;
export type EntityArrayResponseType = HttpResponse<IAvancement[]>;

@Injectable({ providedIn: 'root' })
export class AvancementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/avancements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(avancement: NewAvancement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avancement);
    return this.http
      .post<RestAvancement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(avancement: IAvancement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avancement);
    return this.http
      .put<RestAvancement>(`${this.resourceUrl}/${this.getAvancementIdentifier(avancement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(avancement: PartialUpdateAvancement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avancement);
    return this.http
      .patch<RestAvancement>(`${this.resourceUrl}/${this.getAvancementIdentifier(avancement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAvancement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAvancement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAvancementIdentifier(avancement: Pick<IAvancement, 'id'>): number {
    return avancement.id;
  }

  compareAvancement(o1: Pick<IAvancement, 'id'> | null, o2: Pick<IAvancement, 'id'> | null): boolean {
    return o1 && o2 ? this.getAvancementIdentifier(o1) === this.getAvancementIdentifier(o2) : o1 === o2;
  }

  addAvancementToCollectionIfMissing<Type extends Pick<IAvancement, 'id'>>(
    avancementCollection: Type[],
    ...avancementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const avancements: Type[] = avancementsToCheck.filter(isPresent);
    if (avancements.length > 0) {
      const avancementCollectionIdentifiers = avancementCollection.map(avancementItem => this.getAvancementIdentifier(avancementItem)!);
      const avancementsToAdd = avancements.filter(avancementItem => {
        const avancementIdentifier = this.getAvancementIdentifier(avancementItem);
        if (avancementCollectionIdentifiers.includes(avancementIdentifier)) {
          return false;
        }
        avancementCollectionIdentifiers.push(avancementIdentifier);
        return true;
      });
      return [...avancementsToAdd, ...avancementCollection];
    }
    return avancementCollection;
  }

  protected convertDateFromClient<T extends IAvancement | NewAvancement | PartialUpdateAvancement>(avancement: T): RestOf<T> {
    return {
      ...avancement,
      date: avancement.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAvancement: RestAvancement): IAvancement {
    return {
      ...restAvancement,
      date: restAvancement.date ? dayjs(restAvancement.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAvancement>): HttpResponse<IAvancement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAvancement[]>): HttpResponse<IAvancement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
