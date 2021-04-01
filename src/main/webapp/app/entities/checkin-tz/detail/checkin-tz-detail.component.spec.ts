import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckinTzDetailComponent } from './checkin-tz-detail.component';

describe('Component Tests', () => {
  describe('CheckinTz Management Detail Component', () => {
    let comp: CheckinTzDetailComponent;
    let fixture: ComponentFixture<CheckinTzDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CheckinTzDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ checkinTz: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CheckinTzDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckinTzDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load checkinTz on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkinTz).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
