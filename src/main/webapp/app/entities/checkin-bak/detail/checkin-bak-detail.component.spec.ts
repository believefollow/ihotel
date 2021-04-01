import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckinBakDetailComponent } from './checkin-bak-detail.component';

describe('Component Tests', () => {
  describe('CheckinBak Management Detail Component', () => {
    let comp: CheckinBakDetailComponent;
    let fixture: ComponentFixture<CheckinBakDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CheckinBakDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ checkinBak: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CheckinBakDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckinBakDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load checkinBak on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkinBak).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
