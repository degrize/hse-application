import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjet, NewProjet } from '../projet.model';

export type PartialUpdateProjet = Partial<IProjet> & Pick<IProjet, 'id'>;

export type EntityResponseType = HttpResponse<IProjet>;
export type EntityArrayResponseType = HttpResponse<IProjet[]>;

@Injectable({ providedIn: 'root' })
export class ProjetService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/projets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projet: NewProjet): Observable<EntityResponseType> {
    return this.http.post<IProjet>(this.resourceUrl, projet, { observe: 'response' });
  }

  update(projet: IProjet): Observable<EntityResponseType> {
    return this.http.put<IProjet>(`${this.resourceUrl}/${this.getProjetIdentifier(projet)}`, projet, { observe: 'response' });
  }

  partialUpdate(projet: PartialUpdateProjet): Observable<EntityResponseType> {
    return this.http.patch<IProjet>(`${this.resourceUrl}/${this.getProjetIdentifier(projet)}`, projet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProjetIdentifier(projet: Pick<IProjet, 'id'>): number {
    return projet.id;
  }

  compareProjet(o1: Pick<IProjet, 'id'> | null, o2: Pick<IProjet, 'id'> | null): boolean {
    return o1 && o2 ? this.getProjetIdentifier(o1) === this.getProjetIdentifier(o2) : o1 === o2;
  }

  addProjetToCollectionIfMissing<Type extends Pick<IProjet, 'id'>>(
    projetCollection: Type[],
    ...projetsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const projets: Type[] = projetsToCheck.filter(isPresent);
    if (projets.length > 0) {
      const projetCollectionIdentifiers = projetCollection.map(projetItem => this.getProjetIdentifier(projetItem)!);
      const projetsToAdd = projets.filter(projetItem => {
        const projetIdentifier = this.getProjetIdentifier(projetItem);
        if (projetCollectionIdentifiers.includes(projetIdentifier)) {
          return false;
        }
        projetCollectionIdentifiers.push(projetIdentifier);
        return true;
      });
      return [...projetsToAdd, ...projetCollection];
    }
    return projetCollection;
  }
}
