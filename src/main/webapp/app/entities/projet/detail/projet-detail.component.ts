import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IProjet } from '../projet.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { IRegle } from '../../regle/regle.model';
import { IAvancement } from '../../avancement/avancement.model';
import { ISignalement } from '../../signalement/signalement.model';
import { HttpResponse } from '@angular/common/http';
import { RegleService } from '../../regle/service/regle.service';

import { AccordionModule } from 'primeng/accordion';
import { AvancementService } from '../../avancement/service/avancement.service';
import { SignalementService } from '../../signalement/service/signalement.service';
import { SessionStorageService } from 'ngx-webstorage';
import { IUser } from '../../user/user.model';
import { AccountService } from '../../../core/auth/account.service';
import { User } from '../../../admin/user-management/user-management.model';
import { UserManagementService } from '../../../admin/user-management/service/user-management.service';

@Component({
  selector: 'jhi-projet-detail',
  templateUrl: './projet-detail.component.html',
  styleUrls: ['./projet-detail.component.scss'],
})
export class ProjetDetailComponent implements OnInit {
  projet: IProjet | null = null;

  regles?: IRegle[];
  avancements?: IAvancement[];
  signalements?: ISignalement[];
  user: IUser = { id: 1, login: '' };

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected regleService: RegleService,
    protected avancementService: AvancementService,
    protected signalementService: SignalementService,
    private sessionStorageService: SessionStorageService,
    private router: Router,
    private accountService: AccountService,
    private userManagementService: UserManagementService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projet }) => {
      this.projet = projet;
      if (this.projet?.id) {
        this.loadRegleList(this.projet.id);
        this.loadAvancementList(this.projet.id);
        this.loadSignalementList(this.projet.id);
      }
    });

    this.accountService.identity().subscribe(account => {
      if (account) {
        const login = account.login;
        if (login) {
          console.log(this.userManagementService.find(login));
          this.userManagementService.find(login).subscribe({
            next: (res: User) => {
              if (res.id) {
                this.user.login = res.login;
                this.user.id = res.id;
                alert(res.id);
              }
            },
            error: () => 'ERREUR',
          });
        }
      }
    });
  }

  loadRegleList(id: number): void {
    this.regleService.getListByProjetId(id).subscribe(
      (res: HttpResponse<IProjet[]>) => {
        this.regles = res.body ?? [];
      },
      () => {}
    );
  }

  goToLink(lien: any): void {
    this.sessionStorageService.store('projetId', this.projet?.id);
    this.router.navigate([lien]);
  }

  loadAvancementList(id: number): void {
    this.avancementService.getListByProjetId(id).subscribe(
      (res: HttpResponse<IProjet[]>) => {
        this.avancements = res.body ?? [];
      },
      () => {}
    );
  }

  loadSignalementList(id: number): void {
    this.signalementService.getListByProjetId(id).subscribe(
      (res: HttpResponse<IProjet[]>) => {
        this.signalements = res.body ?? [];
      },
      () => {}
    );
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
