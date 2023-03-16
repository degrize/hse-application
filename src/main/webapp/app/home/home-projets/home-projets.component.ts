import { Component, Input, OnInit } from '@angular/core';
import { Account } from '../../core/auth/account.model';
import { IProjet } from '../../entities/projet/projet.model';
import { HttpResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { ProjetService } from '../../entities/projet/service/projet.service';

@Component({
  selector: 'jhi-home-projets',
  templateUrl: './home-projets.component.html',
  styleUrls: ['./home-projets.component.scss'],
})
export class HomeProjetsComponent implements OnInit {
  @Input() categorie: string | null | any = null;

  projets?: IProjet[];
  projetsList: IProjet[] = [];

  responsiveOptions;

  constructor(protected projetService: ProjetService) {
    this.responsiveOptions = [
      {
        breakpoint: '1024px',
        numVisible: 3,
        numScroll: 3,
      },
      {
        breakpoint: '768px',
        numVisible: 2,
        numScroll: 2,
      },
      {
        breakpoint: '560px',
        numVisible: 1,
        numScroll: 1,
      },
    ];
  }

  ngOnInit(): void {
    this.loadProjetList();
  }

  loadProjetList(): void {
    this.projetService.getProjetList().subscribe(
      (res: HttpResponse<IProjet[]>) => {
        this.projets = res.body ?? [];
        this.onSuccess();
      },
      () => {
        this.onErrorProjet();
      }
    );
  }

  protected onSuccess(): void {
    if (this.projets) {
      for (let i = 0; i < this.projets.length; i++) {
        if (this.projets[i].isDone === false && this.projets[i].ville === this.categorie.ville) {
          this.projetsList?.push(this.projets[i]);
        }
      }
    }
  }

  protected onErrorProjet(): void {
    this.projetsList = [];
  }
}
