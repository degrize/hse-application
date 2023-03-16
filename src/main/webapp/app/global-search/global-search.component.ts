import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { GlobalSearchService } from './global-search.service';

import { IProjet } from '../entities/projet/projet.model';

@Component({
  selector: 'jhi-global-search',
  templateUrl: './global-search.component.html',
  styleUrls: ['./global-search.component.scss'],
})
export class GlobalSearchComponent implements OnInit {
  loading$!: Observable<boolean>;
  projets$!: Observable<IProjet[]>;
  projetsList = false;

  params: any;
  searchInput: any;

  constructor(private route: ActivatedRoute, private router: Router, private globalSearchService: GlobalSearchService) {}

  ngOnInit(): void {
    this.initObservables();
    this.route.queryParams.subscribe(params => {
      this.searchInput = params['projetTitre'].toLowerCase();
      // on récupère les données depuis la base
      this.globalSearchService.getProjetsFromServer(this.searchInput);
    });
  }

  private initObservables() {
    this.loading$ = this.globalSearchService.loading$;
    this.projets$ = this.globalSearchService.projets$;

    this.loading$.subscribe(element => {
      this.projets$.subscribe(value => {
        if (value.length <= 0) {
          this.projetsList = true;
        } else {
          this.projetsList = false;
        }
      });
    });
  }
}
