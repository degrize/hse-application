import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRegle } from '../regle.model';
import { RegleService } from '../service/regle.service';

@Injectable({ providedIn: 'root' })
export class RegleRoutingResolveService implements Resolve<IRegle | null> {
  constructor(protected service: RegleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegle | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((regle: HttpResponse<IRegle>) => {
          if (regle.body) {
            return of(regle.body);
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
