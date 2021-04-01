import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DayearndetailDetailComponent } from './dayearndetail-detail.component';

describe('Component Tests', () => {
  describe('Dayearndetail Management Detail Component', () => {
    let comp: DayearndetailDetailComponent;
    let fixture: ComponentFixture<DayearndetailDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DayearndetailDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dayearndetail: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DayearndetailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DayearndetailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dayearndetail on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dayearndetail).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
