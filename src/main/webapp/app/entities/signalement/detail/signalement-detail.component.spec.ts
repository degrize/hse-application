import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SignalementDetailComponent } from './signalement-detail.component';

describe('Signalement Management Detail Component', () => {
  let comp: SignalementDetailComponent;
  let fixture: ComponentFixture<SignalementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SignalementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ signalement: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SignalementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SignalementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load signalement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.signalement).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
