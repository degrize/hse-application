import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RegleDetailComponent } from './regle-detail.component';

describe('Regle Management Detail Component', () => {
  let comp: RegleDetailComponent;
  let fixture: ComponentFixture<RegleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ regle: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RegleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RegleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load regle on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.regle).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
