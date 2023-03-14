import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAvancement } from '../avancement.model';
import { AvancementService } from '../service/avancement.service';

@Injectable({ providedIn: 'root' })
export class AvancementRoutingResolveService implements Resolve<IAvancement | null> {
  constructor(protected service: AvancementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvancement | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((avancement: HttpResponse<IAvancement>) => {
          if (avancement.body) {
            return of(avancement.body);
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
