import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegle } from '../regle.model';

@Component({
  selector: 'jhi-regle-detail',
  templateUrl: './regle-detail.component.html',
})
export class RegleDetailComponent implements OnInit {
  regle: IRegle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regle }) => {
      this.regle = regle;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
