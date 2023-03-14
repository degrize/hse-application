import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISignalement } from '../signalement.model';

@Component({
  selector: 'jhi-signalement-detail',
  templateUrl: './signalement-detail.component.html',
})
export class SignalementDetailComponent implements OnInit {
  signalement: ISignalement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signalement }) => {
      this.signalement = signalement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
