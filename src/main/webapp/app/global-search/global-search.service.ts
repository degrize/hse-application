import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { BehaviorSubject, delay, map, Observable, tap } from 'rxjs';
import { IProjet } from '../entities/projet/projet.model';

@Injectable({
  providedIn: 'root',
})
export class GlobalSearchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/projets');

  constructor(private http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  private _loading$ = new BehaviorSubject<boolean>(false);
  get loading$(): Observable<boolean> {
    return this._loading$.asObservable();
  }

  private _projets$ = new BehaviorSubject<IProjet[]>([]);
  get projets$(): Observable<IProjet[]> {
    return this._projets$.asObservable();
  }

  private setLoadingStatus(loading: boolean) {
    this._loading$.next(loading);
  }

  private lastProjetsLoad = 0;

  getProjetsFromServer(projetTitre: string) {
    this.setLoadingStatus(true);
    this.http
      .get<IProjet[]>(`${this.resourceUrl}/search/${projetTitre}`)
      .pipe(
        delay(1000),
        tap(projets => {
          this.lastProjetsLoad = Date.now();
          this._projets$.next(projets);
          this.setLoadingStatus(false);
        })
      )
      .subscribe();
  }
}
