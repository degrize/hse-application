import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISignalement } from '../signalement.model';
import { SignalementService } from '../service/signalement.service';

@Injectable({ providedIn: 'root' })
export class SignalementRoutingResolveService implements Resolve<ISignalement | null> {
  constructor(protected service: SignalementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISignalement | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((signalement: HttpResponse<ISignalement>) => {
          if (signalement.body) {
            return of(signalement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
