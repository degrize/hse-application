import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ProjetService } from '../entities/projet/service/projet.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IProjet } from '../entities/projet/projet.model';
import { HttpResponse } from '@angular/common/http';

import villes from '../../content/villes.json';
import Swal from 'sweetalert2';
import List from 'list.js';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  categories?: { ville: string }[] = villes;

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
