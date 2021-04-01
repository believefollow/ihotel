import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckinAccountDetailComponent } from './checkin-account-detail.component';

describe('Component Tests', () => {
  describe('CheckinAccount Management Detail Component', () => {
    let comp: CheckinAccountDetailComponent;
    let fixture: ComponentFixture<CheckinAccountDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CheckinAccountDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ checkinAccount: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CheckinAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckinAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load checkinAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkinAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
