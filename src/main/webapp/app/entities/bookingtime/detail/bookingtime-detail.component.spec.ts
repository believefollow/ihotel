import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingtimeDetailComponent } from './bookingtime-detail.component';

describe('Component Tests', () => {
  describe('Bookingtime Management Detail Component', () => {
    let comp: BookingtimeDetailComponent;
    let fixture: ComponentFixture<BookingtimeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BookingtimeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ bookingtime: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(BookingtimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookingtimeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bookingtime on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookingtime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
